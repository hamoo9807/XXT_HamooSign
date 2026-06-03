package com.cofbro.qian.home

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import java.net.URLEncoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.fastjson.JSONObject
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cofbro.hymvvmutils.base.BaseFragment
import com.cofbro.hymvvmutils.base.BaseResponse
import com.cofbro.hymvvmutils.base.DataState
import com.cofbro.hymvvmutils.base.getBySp
import com.cofbro.qian.data.URL
import com.cofbro.qian.databinding.FragmentHomeBinding
import com.cofbro.qian.scan.ScanActivity
import com.cofbro.qian.signer.ProxySignManager
import com.cofbro.qian.utils.AccountManager
import com.cofbro.qian.utils.CacheUtils
import com.cofbro.qian.utils.CaptchaHelper
import com.cofbro.qian.utils.Constants
import com.cofbro.qian.utils.DebugLogCollector
import com.cofbro.qian.utils.Downloader
import com.cofbro.qian.utils.FaceUploadHelper
import com.cofbro.qian.utils.HtmlParser
import com.cofbro.qian.utils.NativeLocationUtils
import com.cofbro.qian.utils.NetworkUtils
import com.cofbro.qian.utils.PresetLocationManager
import com.cofbro.qian.utils.dp2px
import com.cofbro.qian.utils.getStatusBarHeight
import com.cofbro.qian.utils.safeParseToJson
import com.cofbro.qian.utils.getStringExt
import com.cofbro.qian.utils.getIntExt
import com.cofbro.qian.utils.getJSONArrayExt
import com.cofbro.qian.wrapper.WrapperActivity
import com.hjq.toast.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    private var scrolledDx = 0
    private var targetScrollDx = 0
    private var mAdapter: CourseListAdapter? = null
    private var data: JSONObject? = null
    private var latitude = 0.0
    private var longitude = 0.0
    private var locationText = ""
    private var isSigningIn = false  // 防止重复签到

    override fun onAllViewCreated(savedInstanceState: Bundle?) {
        initView()
        initObserver()
        loadJsonLocally()
        requestForUserInfo()
    }

    companion object {
        private const val TAG = "HomeFragment"
        private const val REQUEST_CODE_SCAN = 1001
        private const val REQUEST_CODE_ANTI_CHEAT = 1002  // 防作弊扫码
        private const val QR_DEBUG = "QR_DEBUG"
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SCAN && resultCode == AppCompatActivity.RESULT_OK) {
            val result = data?.getStringExtra("result") ?: return
            handleQrCodeResult(result)
        }
        if (requestCode == REQUEST_CODE_ANTI_CHEAT && resultCode == AppCompatActivity.RESULT_OK) {
            val result = data?.getStringExtra("result") ?: return
            startIntelligentSign(result)
        }
    }

    private fun handleQrCodeResult(result: String) {
        if (isSigningIn) {
            DebugLogCollector.w(QR_DEBUG, "签到进行中, 忽略重复扫码")
            return
        }
        isSigningIn = true
        try {
            handleQrCodeResultInternal(result)
        } finally {
            isSigningIn = false
            DebugLogCollector.d(QR_DEBUG, "========== 扫码签到结束 ==========")
        }
    }

    private fun handleQrCodeResultInternal(result: String) {
        DebugLogCollector.d(QR_DEBUG, "========== 扫码签到开始 ==========")
        DebugLogCollector.d(QR_DEBUG, "扫码原始结果: $result")
        val aid: String
        val qrCodeId: String
        var enc: String? = null
        var fullIdParams: String? = null  // pre-release方式: id=后面的全部参数(含enc等)

        if (result.contains("SIGNIN:aid=")) {
            aid = result.substringAfter("SIGNIN:aid=").substringBefore("&")
            qrCodeId = result.substringAfter("SIGNIN:aid=")
            fullIdParams = qrCodeId
            val encMatch = Regex("(?<=&enc=)[\\dA-Z]+").find(result)
            enc = encMatch?.value
            DebugLogCollector.d(QR_DEBUG, "提取到enc(SIGNIN): $enc")
        } else if (result.contains("id=")) {
            // pre-release方式: 取id=后面的全部内容作为fullIdParams
            fullIdParams = result.substringAfter("id=")
            qrCodeId = fullIdParams
            aid = qrCodeId.substringBefore("&")
            val encMatch = Regex("(?<=&enc=)[\\dA-Z]+").find(result)
            enc = encMatch?.value
            DebugLogCollector.d(QR_DEBUG, "提取到enc: $enc")
        } else if (result.matches(Regex("\\d+"))) {
            aid = result
            qrCodeId = result
            fullIdParams = result
        } else {
            DebugLogCollector.e(QR_DEBUG, "提取aid失败, 原始结果无法识别: $result")
            ToastUtils.show("无法识别签到二维码")
            return
        }
        DebugLogCollector.d(QR_DEBUG, "提取到aid: $aid, qrCodeId: $qrCodeId, enc=$enc, fullIdParams=$fullIdParams")
        val currentCookies = CacheUtils.getCurrentCookies()
        if (currentCookies.isEmpty()) {
            DebugLogCollector.e(QR_DEBUG, "内存中无cookie, 请先登录")
            ToastUtils.show("当前无登录Cookie，请先登录")
            return
        }
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // ===== 预检查: 获取签到类型+classId/courseId (用正确API getPPTActiveInfo) =====
                val preCheck = com.cofbro.qian.signer.SignPreCheck.fetch(aid)
                val signType = preCheck?.otherId ?: ""
                val fetchedClassId = preCheck?.clazzId ?: ""
                val fetchedCourseId = preCheck?.courseId ?: ""
                DebugLogCollector.d(QR_DEBUG, "签到类型: $signType (0=普通 2=二维码 3=手势 4=定位 5=签到码)")

                val uid = CacheUtils.cache["uid"] ?: ""
                val fid = CacheUtils.cache["fid"] ?: ""
                val name = CacheUtils.cache["username"] ?: ""

                // ===== Signer体系: 预检查 + 预处理 + 一次性签到 =====
                val signer = com.cofbro.qian.signer.SignDispatcher.createSigner(
                    aid, signType, uid, fid, name, requireContext(),
                    enc = enc, fullIdParams = qrCodeId,
                    extraParams = mapOf(
                        "classId" to fetchedClassId,
                        "courseId" to fetchedCourseId
                    )
                )

                // 设置已获取的preCheck, 避免重复请求
                signer.preCheck = preCheck

                // 预签到(含extContent POST, 因为classId/courseId已传入)
                signer.preSign()

                // 预检查(如果上面没获取到)
                if (signer.preCheck == null) signer.fetchPreCheck()

                // 二维码签到 → 获取位置
                if (signer is com.cofbro.qian.signer.QRCodeSigner) {
                    signer.acquireLocation()
                }

                // 预处理: 上传照片/获取faceEnc/验证码
                signer.prepareAll(requireContext())

                // 执行签到(enc只消耗一次, 所有参数已就绪)
                val signResult = signer.sign()

                // 处理结果
                DebugLogCollector.d(QR_DEBUG, "签到结果: success=${signResult.isSuccess()} msg=${signResult.message}")
                withContext(Dispatchers.Main) {
                    when {
                        signResult.isSuccess() -> ToastUtils.show("签到成功!")
                        signResult.isAlreadySigned -> ToastUtils.show("已签到过了")
                        signResult.isExpired -> ToastUtils.show("二维码已过期请重新扫描")
                        signResult.isLocationError -> ToastUtils.show("位置不在范围")
                        signResult.isEnded -> ToastUtils.show("签到已截止")
                        else -> ToastUtils.show("签到结果: ${signResult.message}")
                    }
                }
                if (signResult.isSuccess()) {
                    proxySignForAccounts(aid, qrCodeId, enc = enc,
                        preparedParams = signer.getPreparedParams())
                }
            } catch (e: Exception) {
                DebugLogCollector.e(QR_DEBUG, "扫码签到异常", e)
                withContext(Dispatchers.Main) {
                    ToastUtils.show("签到失败: ${e.message}")
                }
            } finally {
                isSigningIn = false
                DebugLogCollector.d(QR_DEBUG, "========== 扫码签到结束 ==========")
            }
        }
    }

    // ===== 以下方法已迁移到 signer/ 包下 =====
    // signWithCameraPreRelease → QRCodeSigner.sign()
    // autoLocationSign → LocationSigner.sign()
    // handleValidateSign / handleCheckFaceSign / handleSignResponse 保留供防作弊入口使用

    private suspend fun doPreSign(aid: String) {
        DebugLogCollector.d(QR_DEBUG, "--- 预签到: analysis + analysis2 ---")
        val analysisUrl = URL.getAnalysisPath(aid)
        val analysisResp = NetworkUtils.request(NetworkUtils.buildClientRequest(analysisUrl))
        val analysisBody = analysisResp.data?.body?.string() ?: ""
        val analysis2Code = analysisBody.substringAfter("code='+'").substringBefore("'")
        if (analysis2Code.isNotEmpty()) {
            NetworkUtils.request(NetworkUtils.buildClientRequest(URL.getAnalysis2Path(analysis2Code)))
        }
        DebugLogCollector.d(QR_DEBUG, "预签到完成")
    }

    private suspend fun querySignType(aid: String): String {
        val signTypeUrl = URL.getSignType(aid)
        val signTypeResp = NetworkUtils.request(NetworkUtils.buildClientRequest(signTypeUrl))
        val body = signTypeResp.data?.body?.string() ?: ""
        DebugLogCollector.d(QR_DEBUG, "签到类型响应: ${body.take(300)}")
        return body
    }

    /**
     * 处理签到响应(保留供防作弊入口使用)
     */
    private suspend fun handleSignResponse(
        signBody: String, aid: String, qrCodeId: String, uid: String,
        lat: String, lon: String, addr: String
    ): Boolean {
        val result = com.cofbro.qian.signer.SignResult.fromBody(signBody)
        return when {
            result.isSuccess() -> { withContext(Dispatchers.Main) { ToastUtils.show("签到成功!") }; proxySignForAccounts(aid, qrCodeId, lat = lat, lon = lon, addr = addr); true }
            result.validateToken != null -> handleValidateSign(aid, uid, result.validateToken!!, lat, lon, addr)
            result.faceToken != null -> handleCheckFaceSign(aid, uid, result.faceToken!!, lat, lon, addr)
            else -> { withContext(Dispatchers.Main) { ToastUtils.show(signBody) }; false }
        }
    }

    private suspend fun handleValidateSign(
        aid: String, uid: String, validateToken: String,
        lat: String, lon: String, addr: String
    ): Boolean {
        try {
            // 1. 查找预设照片
            val photoDir = java.io.File(requireContext().getExternalFilesDir(null), "preset_photo")
            val photoFile = if (photoDir.exists() && photoDir.isDirectory) {
                photoDir.listFiles()?.firstOrNull { it.extension.lowercase() in listOf("jpg", "jpeg", "png") }
            } else null

            if (photoFile == null || !photoFile.exists()) {
                DebugLogCollector.w(QR_DEBUG, "无预设照片, 无法完成拍照验证。请在个人中心设置预设照片")
                withContext(Dispatchers.Main) { ToastUtils.show("需要拍照验证, 但未设置预设照片") }
                return false
            }

            // 2. 获取上传token
            DebugLogCollector.d(QR_DEBUG, "获取上传token...")
            val tokenResp = NetworkUtils.request(NetworkUtils.buildClientRequest(URL.getUploadToken()))
            val tokenBody = tokenResp.data?.body?.string() ?: ""
            val tokenData = tokenBody.safeParseToJson()
            val token = tokenData?.getStringExt("_token") ?: ""
            if (token.isEmpty()) {
                DebugLogCollector.w(QR_DEBUG, "获取上传token失败: $tokenBody")
                return false
            }
            DebugLogCollector.d(QR_DEBUG, "上传token获取成功")

            // 3. 上传照片到超星云盘
            DebugLogCollector.d(QR_DEBUG, "上传预设照片: ${photoFile.name}, 大小: ${photoFile.length()}")
            val uploadResp = NetworkUtils.post(URL.getUploadImagePath(token), photoFile)
            val uploadBody = uploadResp.data?.body?.string() ?: ""
            val uploadData = uploadBody.safeParseToJson()
            val objectId = uploadData?.getStringExt("objectId") ?: ""
            if (objectId.isEmpty()) {
                DebugLogCollector.w(QR_DEBUG, "照片上传失败: $uploadBody")
                return false
            }
            DebugLogCollector.d(QR_DEBUG, "照片上传成功, objectId: $objectId")

            // 4. 优先使用submitPhotoValidate提交照片证据(最小参数, 不重签)
            DebugLogCollector.d(QR_DEBUG, "方式A: submitPhotoValidate提交validate=$validateToken")
            var vResp = NetworkUtils.submitPhotoValidate(aid, uid, validateToken, objectId)
            var vBody = vResp.data?.body?.string() ?: ""
            DebugLogCollector.d(QR_DEBUG, "validate提交响应: $vBody")
            if (vBody.contains("成功") || vBody.contains("success")) {
                withContext(Dispatchers.Main) { ToastUtils.show("拍照验证签到成功!") }
                return true
            }

            // 5. 方式B: 带objectId+validate+位置 完整POST签到
            DebugLogCollector.d(QR_DEBUG, "方式B: 完整POST签到 (validate+objectId+location)")
            var signResp = NetworkUtils.postSign(
                aid = aid, uid = uid, fid = CacheUtils.cache["fid"] ?: "",
                name = CacheUtils.cache["username"] ?: "",
                latitude = lat, longitude = lon, address = addr,
                objectId = objectId, validate = validateToken
            )
            var signBody = signResp.data?.body?.string() ?: ""
            DebugLogCollector.d(QR_DEBUG, "POST签到响应(带validate): $signBody")
            if (signBody.contains("成功") || signBody.contains("success")) {
                withContext(Dispatchers.Main) { ToastUtils.show("拍照验证签到成功!") }
                return true
            }

            // 6. 方式C: 不带validate, 仅objectId
            DebugLogCollector.d(QR_DEBUG, "方式C: 不带validate仅objectId")
            signResp = NetworkUtils.postSign(
                aid = aid, uid = uid, fid = CacheUtils.cache["fid"] ?: "",
                name = CacheUtils.cache["username"] ?: "",
                latitude = lat, longitude = lon, address = addr,
                objectId = objectId
            )
            signBody = signResp.data?.body?.string() ?: ""
            DebugLogCollector.d(QR_DEBUG, "POST签到响应(仅objectId): $signBody")
            if (signBody.contains("成功") || signBody.contains("success")) {
                withContext(Dispatchers.Main) { ToastUtils.show("拍照验证签到成功!") }
                return true
            }

            // 7. 方式D: GET签到+objectId
            DebugLogCollector.d(QR_DEBUG, "方式D: GET+objectId")
            val getResp = NetworkUtils.getSign(
                aid = aid, uid = uid, fid = CacheUtils.cache["fid"] ?: "",
                name = CacheUtils.cache["username"] ?: "",
                latitude = lat, longitude = lon, address = addr,
                objectId = objectId
            )
            val getBody = getResp.data?.body?.string() ?: ""
            DebugLogCollector.d(QR_DEBUG, "GET签到响应: $getBody")
            if (getBody.contains("成功") || getBody.contains("success")) {
                withContext(Dispatchers.Main) { ToastUtils.show("拍照验证签到成功!") }
                return true
            }
            DebugLogCollector.w(QR_DEBUG, "拍照验证签到最终失败: A=$vBody, B=$signBody, C=$signBody, D=$getBody")
        } catch (e: Exception) {
            DebugLogCollector.e(QR_DEBUG, "拍照验证处理异常", e)
        }
        return false
    }

    /**
     * 处理checkFace_人脸识别防作弊: 生成随机objectId, 调用mooc1-api绕过人脸验证
     */
    private suspend fun handleCheckFaceSign(
        aid: String, uid: String, faceToken: String,
        lat: String, lon: String, addr: String
    ): Boolean {
        try {
            DebugLogCollector.d(QR_DEBUG, "===== 处理人脸识别防作弊(checkFace), token=$faceToken =====")

            // 1. 生成随机objectId (32位随机字符串)
            val chars = "abcdefghijklmnopqrstuvwxyz0123456789"
            val objectId = (1..32).map { chars.random() }.joinToString("")
            DebugLogCollector.d(QR_DEBUG, "生成随机objectId: $objectId")

            // 2. 获取clazzId和courseId (从签到类型响应中获取)
            val signTypeBody = querySignType(aid)
            val signTypeData = signTypeBody.safeParseToJson()
            val clazzId = signTypeData.getStringExt("clazzId") ?: ""
            val courseId = signTypeData.getStringExt("courseId") ?: ""
            DebugLogCollector.d(QR_DEBUG, "checkFace参数: clazzId=$clazzId, courseId=$courseId")

            // 3. 方式1: 调用mooc1-api updateqrstatus (一版本人脸识别)
            DebugLogCollector.d(QR_DEBUG, "方式1: 调用updateqrstatus (一版本人脸识别)")
            try {
                val updateUrl = "https://mooc1-api.chaoxing.com/qr/updateqrstatus"
                val updateParams = "clazzId=$clazzId&courseId=$courseId&uuid=$faceToken&objectId=$objectId&qrcEnc=&failCount=0&compareResult=0"
                val updateResp = NetworkUtils.request(NetworkUtils.buildClientRequest("$updateUrl?$updateParams"))
                val updateBody = updateResp.data?.body?.string() ?: ""
                DebugLogCollector.d(QR_DEBUG, "updateqrstatus响应: $updateBody")
                if (updateBody.contains("成功") || updateBody.contains("success") || updateBody.contains("true")) {
                    withContext(Dispatchers.Main) { ToastUtils.show("人脸识别签到成功!(方式1)") }
                    return true
                }
            } catch (e: Exception) {
                DebugLogCollector.w(QR_DEBUG, "方式1异常: ${e.message}")
            }

            // 4. 方式2: 调用mooc1-api uploadInfo (二版本人脸识别)
            DebugLogCollector.d(QR_DEBUG, "方式2: 调用uploadInfo (二版本人脸识别)")
            try {
                val uploadInfoUrl = "https://mooc1-api.chaoxing.com/knowledge/uploadInfo"
                val uploadParams = "clazzId=$clazzId&courseId=$courseId&knowledgeId=0&uuid=$faceToken&objectId=$objectId"
                val uploadResp = NetworkUtils.request(NetworkUtils.buildClientRequest("$uploadInfoUrl?$uploadParams"))
                val uploadBody = uploadResp.data?.body?.string() ?: ""
                DebugLogCollector.d(QR_DEBUG, "uploadInfo响应: $uploadBody")
                if (uploadBody.contains("成功") || uploadBody.contains("success") || uploadBody.contains("true")) {
                    withContext(Dispatchers.Main) { ToastUtils.show("人脸识别签到成功!(方式2)") }
                    return true
                }
            } catch (e: Exception) {
                DebugLogCollector.w(QR_DEBUG, "方式2异常: ${e.message}")
            }

            // 5. 方式3: 带objectId重新POST签到
            DebugLogCollector.d(QR_DEBUG, "方式3: 带objectId重新POST签到")
            try {
                val signResp = NetworkUtils.postSign(
                    aid = aid, uid = uid, fid = CacheUtils.cache["fid"] ?: "",
                    name = CacheUtils.cache["username"] ?: "",
                    latitude = lat, longitude = lon, address = addr,
                    objectId = objectId
                )
                val signBody = signResp.data?.body?.string() ?: ""
                DebugLogCollector.d(QR_DEBUG, "带objectId POST签到响应: $signBody")
                if (signBody.contains("成功") || signBody.contains("success")) {
                    withContext(Dispatchers.Main) { ToastUtils.show("人脸识别签到成功!(方式3)") }
                    return true
                }
                // 如果返回validate_, 走拍照验证流程
                if (signBody.startsWith("validate_")) {
                    val validateToken = signBody.removePrefix("validate_")
                    DebugLogCollector.d(QR_DEBUG, "checkFace后需拍照验证, token=$validateToken")
                    return handleValidateSign(aid, uid, validateToken, lat, lon, addr)
                }
            } catch (e: Exception) {
                DebugLogCollector.w(QR_DEBUG, "方式3异常: ${e.message}")
            }

            // 6. 方式4: 上传预设照片获取真实objectId, 再POST签到
            DebugLogCollector.d(QR_DEBUG, "方式4: 上传预设照片获取objectId")
            try {
                val photoDir = java.io.File(requireContext().getExternalFilesDir(null), "preset_photo")
                val photoFile = if (photoDir.exists() && photoDir.isDirectory) {
                    photoDir.listFiles()?.firstOrNull { it.extension.lowercase() in listOf("jpg", "jpeg", "png") }
                } else null

                if (photoFile != null && photoFile.exists()) {
                    val tokenResp = NetworkUtils.request(NetworkUtils.buildClientRequest(URL.getUploadToken()))
                    val tokenBody = tokenResp.data?.body?.string() ?: ""
                    val tokenData = tokenBody.safeParseToJson()
                    val uploadToken = tokenData?.getStringExt("_token") ?: ""
                    if (uploadToken.isNotEmpty()) {
                        val uploadResp = NetworkUtils.post(URL.getUploadImagePath(uploadToken), photoFile)
                        val uploadBody = uploadResp.data?.body?.string() ?: ""
                        val uploadData = uploadBody.safeParseToJson()
                        val realObjectId = uploadData?.getStringExt("objectId") ?: ""
                        if (realObjectId.isNotEmpty()) {
                            DebugLogCollector.d(QR_DEBUG, "照片上传成功, realObjectId: $realObjectId")
                            // 用真实objectId调用updateqrstatus
                            val updateParams2 = "clazzId=$clazzId&courseId=$courseId&uuid=$faceToken&objectId=$realObjectId&qrcEnc=&failCount=0&compareResult=0"
                            val updateResp2 = NetworkUtils.request(NetworkUtils.buildClientRequest("https://mooc1-api.chaoxing.com/qr/updateqrstatus?$updateParams2"))
                            val updateBody2 = updateResp2.data?.body?.string() ?: ""
                            DebugLogCollector.d(QR_DEBUG, "真实objectId updateqrstatus响应: $updateBody2")
                            if (updateBody2.contains("成功") || updateBody2.contains("success") || updateBody2.contains("true")) {
                                withContext(Dispatchers.Main) { ToastUtils.show("人脸识别签到成功!(方式4)") }
                                return true
                            }
                            // 也尝试POST签到
                            val signResp2 = NetworkUtils.postSign(
                                aid = aid, uid = uid, fid = CacheUtils.cache["fid"] ?: "",
                                name = CacheUtils.cache["username"] ?: "",
                                latitude = lat, longitude = lon, address = addr,
                                objectId = realObjectId
                            )
                            val signBody2 = signResp2.data?.body?.string() ?: ""
                            DebugLogCollector.d(QR_DEBUG, "真实objectId POST响应: $signBody2")
                            if (signBody2.contains("成功") || signBody2.contains("success")) {
                                withContext(Dispatchers.Main) { ToastUtils.show("人脸识别签到成功!(方式4-POST)") }
                                return true
                            }
                            if (signBody2.startsWith("validate_")) {
                                val validateToken = signBody2.removePrefix("validate_")
                                return handleValidateSign(aid, uid, validateToken, lat, lon, addr)
                            }
                        }
                    }
                } else {
                    DebugLogCollector.w(QR_DEBUG, "无预设照片, 方式4跳过")
                }
            } catch (e: Exception) {
                DebugLogCollector.w(QR_DEBUG, "方式4异常: ${e.message}")
            }

            DebugLogCollector.w(QR_DEBUG, "人脸识别防作弊所有方式均失败")
            withContext(Dispatchers.Main) { ToastUtils.show("人脸识别防作弊无法绕过, 请手动签到") }
        } catch (e: Exception) {
            DebugLogCollector.e(QR_DEBUG, "checkFace处理异常", e)
        }
        return false
    }

    @Deprecated("Migrated to LocationSigner.sign() in signer/ package")
    private suspend fun autoLocationSign(aid: String) {
        val uid = CacheUtils.cache["uid"] ?: ""

        DebugLogCollector.d(QR_DEBUG, "--- 尝试定位签到(POST) ---")
        try {
            var nativeLat = 0.0
            var nativeLon = 0.0
            var nativeAddr = ""
            NativeLocationUtils.getCurrentLocation(
                requireContext(),
                onSuccess = { lat, lon, addr ->
                    nativeLat = lat
                    nativeLon = lon
                    nativeAddr = addr
                },
                onError = { err ->
                    DebugLogCollector.w(QR_DEBUG, "定位失败: $err")
                }
            )
            if (nativeLat != 0.0 && nativeLon != 0.0) {
                DebugLogCollector.d(QR_DEBUG, "定位成功: lat=$nativeLat, lon=$nativeLon, addr=$nativeAddr")
                val resp = NetworkUtils.postSign(
                    aid = aid, uid = uid, fid = CacheUtils.cache["fid"] ?: "",
                    name = CacheUtils.cache["username"] ?: "",
                    latitude = nativeLat.toString(), longitude = nativeLon.toString(), address = nativeAddr
                )
                val body = resp.data?.body?.string() ?: ""
                DebugLogCollector.d(QR_DEBUG, "定位签到响应: $body")
                val result = handleSignResponse(body, aid, aid, uid, nativeLat.toString(), nativeLon.toString(), nativeAddr)
                if (result) return
                DebugLogCollector.w(QR_DEBUG, "定位签到失败, 尝试预设地址")
            }
        } catch (e: Exception) {
            DebugLogCollector.w(QR_DEBUG, "定位异常: ${e.message}")
        }

        val presets = PresetLocationManager.loadAll(requireContext())
        val defaultLat = CacheUtils.cache["default_Sign_latitude"]
        val defaultLon = CacheUtils.cache["default_Sign_longitude"]
        val defaultAddr = CacheUtils.cache["default_Sign_Location"]

        if (presets.isNotEmpty()) {
            for ((index, loc) in presets.withIndex()) {
                DebugLogCollector.d(QR_DEBUG, "尝试预设地址${index + 1}: ${loc.name}")
                val lating = NativeLocationUtils.wgs84ToBd09(loc.longitude, loc.latitude)
                val resp = NetworkUtils.postSign(
                    aid = aid, uid = uid, fid = CacheUtils.cache["fid"] ?: "",
                    name = CacheUtils.cache["username"] ?: "",
                    latitude = lating.latitude.toString(), longitude = lating.longitude.toString(),
                    address = loc.address.ifEmpty { loc.name }
                )
                val body = resp.data?.body?.string() ?: ""
                DebugLogCollector.d(QR_DEBUG, "预设地址${index + 1}响应: $body")
                if (body.contains("成功") || body.contains("success")) {
                    withContext(Dispatchers.Main) { ToastUtils.show("定位签到成功! 地址: ${loc.name}") }
                    return
                }
            }
            DebugLogCollector.w(QR_DEBUG, "所有预设地址均失败, 尝试默认地址")
        }

        if (!defaultLat.isNullOrEmpty() && !defaultLon.isNullOrEmpty()) {
            val lating = NativeLocationUtils.wgs84ToBd09(defaultLon.toDouble(), defaultLat.toDouble())
            val resp = NetworkUtils.postSign(
                aid = aid, uid = uid, fid = CacheUtils.cache["fid"] ?: "",
                name = CacheUtils.cache["username"] ?: "",
                latitude = lating.latitude.toString(), longitude = lating.longitude.toString(),
                address = defaultAddr ?: ""
            )
            val body = resp.data?.body?.string() ?: ""
            if (body.contains("成功") || body.contains("success")) {
                withContext(Dispatchers.Main) { ToastUtils.show("定位签到成功!") }
                return
            }
        }

        DebugLogCollector.w(QR_DEBUG, "无可用地址, 签到失败")
        withContext(Dispatchers.Main) { ToastUtils.show("定位签到失败, 请设置预留地址") }
    }

    private fun showSignCodeOrBruteForceDialog(aid: String, signId: String, isGesture: Boolean) {
        val title = if (isGesture) "手势签到" else "签到码签到"
        val bruteForceHint = if (isGesture) "自动尝试常见手势(约8万种, 需几分钟)" else "自动尝试数字组合(数量大, 可能耗时很久)"
        val items = arrayOf("手动输入${if (isGesture) "手势码" else "签到码"}", "暴力破解 ($bruteForceHint)")

        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setItems(items) { _, which ->
                when (which) {
                    0 -> showSignCodeDialog(aid, signId, isGesture)
                    1 -> lifecycleScope.launch(Dispatchers.IO) {
                        bruteForceSignCode(aid, signId, isGesture)
                    }
                }
            }
            .setCancelable(true)
            .show()
    }

    private suspend fun bruteForceSignCode(aid: String, signId: String, isGesture: Boolean) {
        val codes = if (isGesture) generateGestureCodes() else generateDigitCodes()
        DebugLogCollector.d(QR_DEBUG, "暴力破解开始, 模式: ${if (isGesture) "手势" else "签到码"}, 候选码数量: ${codes.size}")
        withContext(Dispatchers.Main) { ToastUtils.show("正在自动尝试${if (isGesture) "手势" else "签到码"}...") }

        for ((index, code) in codes.withIndex()) {
            try {
                val checkUrl = URL.checkSignCodePath(aid, code)
                val checkResp = NetworkUtils.request(NetworkUtils.buildClientRequest(checkUrl))
                val checkBody = checkResp.data?.body?.string() ?: ""
                if (checkBody.contains("true") || checkBody.contains("success") || checkBody.contains("正确")) {
                    DebugLogCollector.d(QR_DEBUG, "找到正确签到码: $code (第${index + 1}次尝试)")
                    val signUrl = URL.getNormalSignPath("", "", signId, code)
                    DebugLogCollector.d(QR_DEBUG, "使用签到码签到, URL: $signUrl")
                    val signResp = NetworkUtils.request(NetworkUtils.buildClientRequest(signUrl))
                    val signBody = signResp.data?.body?.string() ?: ""
                    DebugLogCollector.d(QR_DEBUG, "签到响应: $signBody")
                    withContext(Dispatchers.Main) {
                        if (signBody.contains("成功") || signBody.contains("success")) {
                            ToastUtils.show("暴力破解成功! 签到码: $code")
                        } else {
                            ToastUtils.show("签到码正确但签到失败: $signBody")
                        }
                    }
                    if (signBody.contains("成功") || signBody.contains("success")) {
                        proxySignForAccounts(aid, aid)
                    }
                    return
                }
                if (index % 50 == 0 && index > 0) {
                    DebugLogCollector.d(QR_DEBUG, "已尝试 $index/${codes.size}...")
                    withContext(Dispatchers.Main) { ToastUtils.show("尝试中: $index/${codes.size}") }
                }
                delay(80)
            } catch (e: Exception) {
                DebugLogCollector.w(QR_DEBUG, "尝试签到码 $code 异常: ${e.message}")
            }
        }
        DebugLogCollector.w(QR_DEBUG, "暴力破解失败, 已尝试全部 ${codes.size} 个码")
        withContext(Dispatchers.Main) {
            showSignCodeDialog(aid, signId, isGesture)
        }
    }

    private fun generateGestureCodes(): List<String> {
        val codes = mutableListOf<String>()
        val digits = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        for (len in 4..6) {
            generatePermutations(digits, len) { codes.add(it.joinToString("")) }
        }
        return codes
    }

    private fun <T> generatePermutations(items: List<T>, length: Int, callback: (List<T>) -> Unit) {
        fun backtrack(current: MutableList<T>, used: BooleanArray) {
            if (current.size == length) {
                callback(current.toList())
                return
            }
            for (i in items.indices) {
                if (!used[i]) {
                    used[i] = true
                    current.add(items[i])
                    backtrack(current, used)
                    current.removeAt(current.size - 1)
                    used[i] = false
                }
            }
        }
        backtrack(mutableListOf(), BooleanArray(items.size))
    }

    private fun generateDigitCodes(): List<String> {
        val codes = mutableListOf<String>()
        for (len in 4..6) {
            val max = Math.pow(10.0, len.toDouble()).toInt()
            for (i in 0 until max) {
                codes.add(i.toString().padStart(len, '0'))
            }
        }
        return codes
    }

    private fun showSignCodeDialog(aid: String, signId: String, isGesture: Boolean = false) {
        val editText = android.widget.EditText(requireContext()).apply {
            hint = if (isGesture) "请输入手势码(如: 1236)" else "请输入签到码"
            setPadding(50, 30, 50, 30)
        }
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(if (isGesture) "手势签到(暴力破解失败, 手动输入)" else "签到码签到(暴力破解失败, 手动输入)")
            .setView(editText)
            .setPositiveButton("签到") { _, _ ->
                val code = editText.text.toString().trim()
                if (code.isEmpty()) {
                    ToastUtils.show("请输入签到码")
                    return@setPositiveButton
                }
                DebugLogCollector.d(QR_DEBUG, "手动输入签到码: $code")
                lifecycleScope.launch(Dispatchers.IO) {
                    val signUrl = URL.getNormalSignPath("", "", signId, code)
                    val signResp = NetworkUtils.request(NetworkUtils.buildClientRequest(signUrl))
                    val signBody = signResp.data?.body?.string() ?: ""
                    DebugLogCollector.d(QR_DEBUG, "手动签到响应: $signBody")
                    withContext(Dispatchers.Main) {
                        if (signBody.contains("成功") || signBody.contains("success")) {
                            ToastUtils.show("签到成功!")
                        } else {
                            ToastUtils.show("签到结果: $signBody")
                        }
                    }
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private suspend fun proxySignForAccounts(
        aid: String, qrCodeId: String, location: String = "", enc: String? = null,
        preparedParams: Map<String, String> = emptyMap(),
        lat: String = "-1", lon: String = "-1", addr: String = ""
    ) {
        val signWith = requireActivity().getBySp("signWith")?.toBoolean() ?: false
        if (!signWith) {
            DebugLogCollector.d(QR_DEBUG, "代签功能未开启, 跳过proxySign")
            return
        }

        DebugLogCollector.d(QR_DEBUG, "--- 代签: ProxySignManager ---")
        val manager = ProxySignManager(
            context = requireContext(),
            aid = aid,
            signType = Constants.SIGN.SCAN_QR,
            enc = enc,
            fullIdParams = qrCodeId,
            preparedParams = preparedParams,
            lat = lat, lon = lon, addr = addr
        )

        val progressJob = kotlinx.coroutines.Job()
        manager.onProgress = { current, total, name, success ->
            kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.show("代签($current/$total): ${if (success) "✅" else "❌"} $name")
            }
        }

        manager.loadUsers()
        if (manager.getUserCount() == 0) {
            DebugLogCollector.d(QR_DEBUG, "无代签用户")
            return
        }

        val summary = manager.signForAll()
        DebugLogCollector.d(QR_DEBUG, "代签完成: 成功${summary.successCount} 失败${summary.failCount} 共${summary.totalCount} 废弃${summary.obsoleteUsers.size}")
        withContext(Dispatchers.Main) {
            var msg = "代签: 成功${summary.successCount} 失败${summary.failCount} 共${summary.totalCount}"
            if (summary.obsoleteUsers.isNotEmpty()) {
                msg += "\n⚠ 以下账户需修复: ${summary.obsoleteUsers.joinToString(", ")}"
            }
            ToastUtils.show(msg)
        }
    }

    private suspend fun proxySignWithUrl(signUrl: String, aid: String) {
        val signWith = requireActivity().getBySp("signWith")?.toBoolean() ?: false
        if (!signWith) {
            DebugLogCollector.d(QR_DEBUG, "代签功能未开启, 跳过proxySign")
            return
        }

        DebugLogCollector.d(QR_DEBUG, "--- 代签(URL): ProxySignManager ---")
        val manager = ProxySignManager(
            context = requireContext(),
            aid = aid
        )

        manager.onProgress = { current, total, name, success ->
            kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.show("代签($current/$total): ${if (success) "✅" else "❌"} $name")
            }
        }

        manager.loadUsers()
        if (manager.getUserCount() == 0) return

        val summary = manager.signForAll()
        DebugLogCollector.d(QR_DEBUG, "代签完成: 成功${summary.successCount} 失败${summary.failCount} 共${summary.totalCount}")
        withContext(Dispatchers.Main) {
            ToastUtils.show("代签完成: 成功${summary.successCount} 失败${summary.failCount} 共${summary.totalCount}")
        }
    }

    private fun initView() {
        fitToolbar()

        mAdapter = CourseListAdapter()
        targetScrollDx = dp2px(requireContext(), 76)
        binding?.rvCourseList?.apply {
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val defaultPadding = dp2px(requireContext(), 15)
                    val toolbarHeight = binding?.appToolBar?.height ?: 0
                    if (parent.layoutManager?.getPosition(view) == 0) {
                        return outRect.set(
                            defaultPadding,
                            toolbarHeight + dp2px(requireContext(), 5),
                            defaultPadding,
                            defaultPadding
                        )
                    } else if (parent.layoutManager?.getPosition(view) == adapter?.itemCount?.minus(
                            1
                        )
                    ) {
                        return outRect.set(
                            defaultPadding,
                            0,
                            defaultPadding,
                            dp2px(requireContext(), 80)
                        )
                    }
                    return outRect.set(defaultPadding, 0, defaultPadding, defaultPadding)
                }
            })

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    computeVerticalScrollOffset().let {
                        if (it > targetScrollDx) {
                            solidAppToolBar(255)
                            return
                        }
                        scrolledDx = it
                    }
                    solidAppToolBar(((scrolledDx.toFloat() / targetScrollDx.toFloat()) * 255).toInt())

                    Log.d(
                        "MainActivity",
                        "scrolledDx: $scrolledDx, dx: $dx, alpha: ${binding?.appToolBar?.background?.alpha}"
                    )
                }
            })

            onFlingListener = object : RecyclerView.OnFlingListener() {
                override fun onFling(velocityX: Int, velocityY: Int): Boolean {
                    Log.d("MainActivity", "onFling: $velocityY")
                    if (velocityY >= 1200) {
                        binding?.appToolBar?.background?.alpha = 255
                    }
                    return false
                }
            }
        }

        val uid = CacheUtils.cache["uid"]
        uid?.let {
            val options = RequestOptions().transform(
                CenterCrop(),
                RoundedCorners(dp2px(requireContext(), 5))
            )
            Glide.with(this)
                .load(URL.getAvtarImgPath(it))
                .apply(options)
                .into(binding!!.ivUserAvtar)
        }

        // 扫一扫 → 默认智能签到模式
        binding?.ivScanBtn?.setOnClickListener {
            val intent = Intent(requireContext(), ScanActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ANTI_CHEAT)
        }

        binding?.ivDebugBtn?.setOnClickListener {
            showDebugLogDialog()
        }

    }



    /**
     * 构建代签预处理参数Map (从startIntelligentSign的本地变量)
     */
    private fun buildPreparedParams(
        faceEnc: String?, objectId: String?, captchaValidate: String?,
        courseId: String?, clazzId: String?
    ): Map<String, String> {
        val map = mutableMapOf<String, String>()
        faceEnc?.let { map["faceEnc"] = it }
        objectId?.let { map["objectId"] = it }
        captchaValidate?.let { map["validate"] = it }
        courseId?.let { map["courseId"] = it }
        clazzId?.let { map["clazzId"] = it }
        return map
    }

    /**
     * 智能签到: 先检查签到详情(ifNeedVCode/openCheckFaceFlag/ifPhoto),
     * 提前处理好验证码/人脸/照片, 再一次性签到(避免enc被消耗后无法补救)
     */
    private fun startIntelligentSign(result: String) {
        val aid: String
        var enc: String? = null
        var fullIdParams: String? = null

        if (result.contains("SIGNIN:aid=")) {
            aid = result.substringAfter("SIGNIN:aid=").substringBefore("&")
            fullIdParams = result.substringAfter("SIGNIN:aid=")
        } else if (result.contains("id=")) {
            fullIdParams = result.substringAfter("id=")
            aid = fullIdParams.substringBefore("&")
            val encMatch = Regex("(?<=&enc=)[\\dA-Z]+").find(result)
            enc = encMatch?.value
        } else if (result.matches(Regex("\\d+"))) {
            aid = result
            fullIdParams = result
        } else {
            ToastUtils.show("无法识别签到二维码")
            return
        }

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val uid = CacheUtils.cache["uid"] ?: ""
                DebugLogCollector.d(QR_DEBUG, "===== 智能签到: aid=$aid =====")
                withContext(Dispatchers.Main) { ToastUtils.show("智能签到分析中...") }

                // 1. 预签到
                doPreSign(aid)

                // 2. 获取签到详情(含ifNeedVCode/openCheckFaceFlag/ifPhoto)
                val signInfo = querySignInfoDetail(aid)
                val needVCode = FaceUploadHelper.isCaptchaRequired(signInfo)
                val needFace = FaceUploadHelper.isFaceRequired(signInfo)
                val needPhoto = FaceUploadHelper.isPhotoRequired(signInfo)
                DebugLogCollector.d(QR_DEBUG, "智能分析: needVCode=$needVCode, needFace=$needFace, needPhoto=$needPhoto")

                // 3. 获取签到类型
                val signTypeBody = querySignType(aid)
                val signTypeData = signTypeBody.safeParseToJson()
                val otherId = signTypeData?.getStringExt("otherId") ?: ""
                val clazzId = signTypeData?.getStringExt("clazzId") ?: ""
                val courseId = signTypeData?.getStringExt("courseId") ?: ""

                // 4. 提前处理人脸识别(获取faceEnc)
                var faceEnc: String? = null
                if (needFace) {
                    DebugLogCollector.d(QR_DEBUG, "需要人脸识别, 获取clientId + faceEnc...")
                    withContext(Dispatchers.Main) { ToastUtils.show("需人脸验证, 处理中...") }
                    val clientId = FaceUploadHelper.fetchClientId(requireContext())
                    if (clientId != null) {
                        CacheUtils.cache["clientId"] = clientId
                        // 尝试用预设照片获取faceEnc
                        val photoDir = java.io.File(requireContext().getExternalFilesDir(null), "preset_photo")
                        val photoFile = photoDir.listFiles()?.firstOrNull { it.extension.lowercase() in listOf("jpg", "jpeg", "png") }
                        if (photoFile != null && photoFile.exists()) {
                            val bitmap = android.graphics.BitmapFactory.decodeFile(photoFile.absolutePath)
                            if (bitmap != null) {
                                val faceObjectId = FaceUploadHelper.uploadFaceImage(requireContext(), bitmap)
                                if (faceObjectId != null) {
                                    faceEnc = FaceUploadHelper.getFaceEnc(aid, faceObjectId, clientId)
                                    DebugLogCollector.d(QR_DEBUG, "faceEnc获取: ${if (faceEnc != null) "成功" else "失败"}")
                                }
                            }
                        }
                    } else {
                        DebugLogCollector.d(QR_DEBUG, "获取clientId失败, 使用备用方式")
                    }
                }

                // 5. 提前处理验证码(使用滑块验证码, 不阻塞签到流程)
                var captchaValidate: String? = null
                if (needVCode) {
                    DebugLogCollector.d(QR_DEBUG, "需要验证码, 使用滑块验证码...")
                    val cData = CaptchaHelper.getCaptchaImage(
                        courseId = courseId, classId = clazzId,
                        activeId = aid, uid = CacheUtils.cache["uid"] ?: ""
                    )
                    if (cData != null) {
                        DebugLogCollector.d(QR_DEBUG, "验证码图片获取成功, 弹窗等待滑动...")
                        withContext(Dispatchers.Main) {
                            val dialog = com.cofbro.qian.view.dialog.SliderCaptchaDialog(
                                context = requireContext(), captchaData = cData,
                                activeId = aid, uid = CacheUtils.cache["uid"] ?: "",
                                courseId = courseId, classId = clazzId,
                                onResult = { captchaValidate = it }
                            )
                            dialog.show()
                        }
                        var w = 0; while (captchaValidate == null && w < 120) { delay(500); w++ }
                        if (captchaValidate != null) {
                            DebugLogCollector.d(QR_DEBUG, "验证码滑动完成: ${captchaValidate!!.take(20)}...")
                        } else {
                            DebugLogCollector.w(QR_DEBUG, "验证码等待超时(60s), 跳过")
                        }
                    } else {
                        DebugLogCollector.w(QR_DEBUG, "获取验证码图片失败, 无法弹窗")
                    }
                }

                // 6. 提前处理照片上传
                var objectId: String? = null
                if (needPhoto) {
                    val (success, objId) = NetworkUtils.preUploadPhoto()
                    if (success) objectId = objId
                }

                // 7. 获取位置: 真机GPS → 缓存 → 预设地址 (修复errorLocation1)
                var lat = "-1"; var lon = "-1"; var addr = ""
                // 7a. 尝试真机GPS定位
                var nativeLat = 0.0; var nativeLon = 0.0; var nativeAddr = ""
                try {
                    NativeLocationUtils.getCurrentLocation(
                        requireContext(),
                        onSuccess = { l1, l2, a -> nativeLat = l1; nativeLon = l2; nativeAddr = a },
                        onError = { DebugLogCollector.w(QR_DEBUG, "定位失败: $it") }
                    )
                } catch (_: Exception) {}
                if (nativeLat != 0.0 && nativeLon != 0.0) {
                    lat = nativeLat.toString(); lon = nativeLon.toString(); addr = nativeAddr
                    DebugLogCollector.d(QR_DEBUG, "使用GPS定位: lat=$lat, lon=$lon")
                } else {
                    // 7b. 尝试缓存位置
                    CacheUtils.cache["default_Sign_latitude"]?.let { cacheLat ->
                        CacheUtils.cache["default_Sign_longitude"]?.let { cacheLon ->
                            lat = cacheLat; lon = cacheLon
                            addr = CacheUtils.cache["default_Sign_Location"] ?: ""
                            DebugLogCollector.d(QR_DEBUG, "使用缓存位置: lat=$lat, lon=$lon")
                        }
                    }
                    // 7c. 尝试预设地址
                    if (lat == "-1") {
                        val presets = PresetLocationManager.loadAll(requireContext())
                        if (presets.isNotEmpty()) {
                            val loc = presets[0]
                            val bdLatLon = NativeLocationUtils.wgs84ToBd09(loc.longitude, loc.latitude)
                            lat = bdLatLon.latitude.toString(); lon = bdLatLon.longitude.toString()
                            addr = loc.address.ifEmpty { loc.name }
                            DebugLogCollector.d(QR_DEBUG, "使用预设地址[0]: ${loc.name}, lat=$lat, lon=$lon")
                        }
                    }
                }

                when (otherId) {
                    Constants.SIGN.SCAN_QR -> {
                        // QR码签到: 一次性带所有参数
                        if (!fullIdParams.isNullOrEmpty()) {
                            var body = ""
                            // 先尝试带真实坐标签到
                            val locationJson = """{"result":1,"latitude":$lat,"longitude":$lon,"address":"$addr"}"""
                            val locationEncoded = java.net.URLEncoder.encode(locationJson, "UTF-8")
                            val sb = StringBuilder("https://mobilelearn.chaoxing.com/pptSign/stuSignajax?activeId=$fullIdParams&location=$locationEncoded&uid=$uid")
                            faceEnc?.let { sb.append("&currentFaceId=${CacheUtils.cache["faceObjectId"] ?: ""}&ifCFP=0&courseId=$courseId&faceEnc=$it") }
                            objectId?.let { sb.append("&objectId=$it") }
                            captchaValidate?.let { sb.append("&validate=$it") }

                            val finalUrl = sb.toString()
                            DebugLogCollector.d(QR_DEBUG, "智能签到URL: ${finalUrl.take(200)}...")
                            val resp = NetworkUtils.request(NetworkUtils.buildClientRequest(finalUrl))
                            body = resp.data?.body?.string() ?: ""
                            DebugLogCollector.d(QR_DEBUG, "签到响应: $body")

                            // 如果仍然errorLocation, 遍历预设地址重试
                            if (body.startsWith("errorLocation")) {
                                DebugLogCollector.d(QR_DEBUG, "位置仍不在范围, 遍历预设地址重试...")
                                val presets = PresetLocationManager.loadAll(requireContext())
                                for ((i, loc) in presets.withIndex()) {
                                    val bdLatLon = NativeLocationUtils.wgs84ToBd09(loc.longitude, loc.latitude)
                                    val retryAddr = loc.address.ifEmpty { loc.name }
                                    val retryLocJson = """{"result":1,"latitude":${bdLatLon.latitude},"longitude":${bdLatLon.longitude},"address":"$retryAddr"}"""
                                    val retrySb = StringBuilder("https://mobilelearn.chaoxing.com/pptSign/stuSignajax?activeId=$fullIdParams&location=${java.net.URLEncoder.encode(retryLocJson, "UTF-8")}&uid=$uid")
                                    faceEnc?.let { retrySb.append("&currentFaceId=${CacheUtils.cache["faceObjectId"] ?: ""}&ifCFP=0&courseId=$courseId&faceEnc=$it") }
                                    objectId?.let { retrySb.append("&objectId=$it") }
                                    captchaValidate?.let { retrySb.append("&validate=$it") }
                                    val retryResp = NetworkUtils.request(NetworkUtils.buildClientRequest(retrySb.toString()))
                                    body = retryResp.data?.body?.string() ?: ""
                                    DebugLogCollector.d(QR_DEBUG, "预设地址[${i}]签到: $body")
                                    if (body.contains("成功") || body.contains("success")) break
                                    if (body.startsWith("validate_")) break
                                    if (body.startsWith("checkFace_")) break
                                    if (body.contains("重新扫描") || body.contains("已截止")) break
                                }
                            }

                            // CSF对齐: 已签到也触发代签(本人签过了, 但代签用户可能没签)
                            var isSuccess = body.contains("成功") || body.contains("success") || body.contains("已签到")

                            // CSF对齐: validate_响应在IO线程处理(区分验证码/拍照)
                            if (!isSuccess && body.startsWith("validate_")) {
                                val signResult = com.cofbro.qian.signer.SignResult.fromBody(body)
                                if (signResult.captchaToken != null && !captchaValidate.isNullOrEmpty()) {
                                    // 滑块验证码 → 用预获取的validate结果重签(IO线程)
                                    DebugLogCollector.d(QR_DEBUG, "识别为滑块验证码, 用预获取validate重签...")
                                    val retrySb = StringBuilder("https://mobilelearn.chaoxing.com/pptSign/stuSignajax?activeId=$fullIdParams&location=$locationEncoded&uid=$uid")
                                    faceEnc?.let { retrySb.append("&currentFaceId=${CacheUtils.cache["faceObjectId"] ?: ""}&ifCFP=0&courseId=$courseId&faceEnc=$it") }
                                    objectId?.let { retrySb.append("&objectId=$it") }
                                    retrySb.append("&validate=$captchaValidate")
                                    val retryResp = NetworkUtils.request(NetworkUtils.buildClientRequest(retrySb.toString()))
                                    body = retryResp.data?.body?.string() ?: ""
                                    isSuccess = body.contains("成功") || body.contains("success")
                                    DebugLogCollector.d(QR_DEBUG, "验证码重签响应: $body")
                                }
                            }

                            withContext(Dispatchers.Main) {
                                if (isSuccess) {
                                    ToastUtils.show("智能签到成功!")
                                } else if (body.startsWith("validate_")) {
                                    val signResult = com.cofbro.qian.signer.SignResult.fromBody(body)
                                    if (signResult.captchaToken != null) {
                                        DebugLogCollector.w(QR_DEBUG, "验证码未预获取, 无法自动重签")
                                        ToastUtils.show("需要滑块验证码")
                                    } else {
                                        // 拍照验证
                                        val vToken = signResult.validateToken ?: body.removePrefix("validate_")
                                        handleValidateSign(aid, uid, vToken, lat, lon, addr)
                                    }
                                } else if (body.startsWith("checkFace_")) {
                                    val fToken = body.removePrefix("checkFace_")
                                    handleCheckFaceSign(aid, uid, fToken, lat, lon, addr)
                                } else {
                                    if (body.contains("已签到")) ToastUtils.show("已签到过了, 正在代签...")
                                    else ToastUtils.show("签到结果: $body")
                                }
                            }
                            // 本人签到成功后触发代签 (含已签到)
                            if (isSuccess) {
                                val preparedParams = buildPreparedParams(faceEnc, objectId, captchaValidate, courseId, clazzId)
                                proxySignForAccounts(aid, fullIdParams ?: aid, enc = enc,
                                    preparedParams = preparedParams, lat = lat, lon = lon, addr = addr)
                            }
                        }
                    }
                    else -> {
                        // 非QR签到: POST + 预设地址遍历
                        var body = ""
                        val resp = NetworkUtils.postSign(aid = aid, uid = uid,
                            fid = CacheUtils.cache["fid"] ?: "", name = CacheUtils.cache["username"] ?: "",
                            latitude = lat, longitude = lon, address = addr,
                            objectId = objectId)
                        body = resp.data?.body?.string() ?: ""

                        // POST失败且是errorLocation → 遍历预设地址
                        if (body.startsWith("errorLocation") || body.contains("errorLocation")) {
                            DebugLogCollector.d(QR_DEBUG, "POST位置不在范围, 遍历预设地址...")
                            val presets = PresetLocationManager.loadAll(requireContext())
                            for ((i, loc) in presets.withIndex()) {
                                val bdLatLon = NativeLocationUtils.wgs84ToBd09(loc.longitude, loc.latitude)
                                val locAddr = loc.address.ifEmpty { loc.name }
                                val retryResp = NetworkUtils.postSign(aid = aid, uid = uid,
                                    fid = CacheUtils.cache["fid"] ?: "", name = CacheUtils.cache["username"] ?: "",
                                    latitude = bdLatLon.latitude.toString(), longitude = bdLatLon.longitude.toString(),
                                    address = locAddr, objectId = objectId)
                                body = retryResp.data?.body?.string() ?: ""
                                DebugLogCollector.d(QR_DEBUG, "预设地址[${i}]POST签到: $body")
                                if (body.contains("成功") || body.contains("success")) break
                                if (body.startsWith("validate_")) break
                                if (body.startsWith("checkFace_")) break
                            }
                        }

                        DebugLogCollector.d(QR_DEBUG, "智能签到响应: $body")
                        val isPostSuccess = body.contains("成功") || body.contains("success") || body.contains("已签到")
                        withContext(Dispatchers.Main) {
                            if (body.contains("已签到")) ToastUtils.show("已签到过了, 正在代签...")
                            else ToastUtils.show("签到结果: $body")
                        }
                        if (isPostSuccess) {
                            val preparedParams = buildPreparedParams(faceEnc, objectId, captchaValidate, courseId, clazzId)
                            proxySignForAccounts(aid, fullIdParams ?: aid, enc = enc,
                                preparedParams = preparedParams, lat = lat, lon = lon, addr = addr)
                        }
                    }
                }
            } catch (e: Exception) {
                DebugLogCollector.e(QR_DEBUG, "智能签到异常", e)
                withContext(Dispatchers.Main) { ToastUtils.show("智能签到失败: ${e.message}") }
            }
        }
    }

    /**
     * 查询签到详情(含ifNeedVCode, openCheckFaceFlag等)
     */
    private suspend fun querySignInfoDetail(aid: String): JSONObject? {
        return try {
            val url = "https://mobilelearn.chaoxing.com/v2/apis/active/getPPTActiveInfo?activeId=$aid"
            val resp = NetworkUtils.request(NetworkUtils.buildClientRequest(url))
            val body = resp.data?.body?.string() ?: ""
            val json = body.safeParseToJson()
            json?.getJSONObject("data")
        } catch (e: Exception) {
            DebugLogCollector.w(QR_DEBUG, "查询签到详情异常: ${e.message}")
            null
        }
    }

    /**
     * 显示验证码输入对话框
     */
    private fun showCaptchaInputDialog(aid: String, uid: String, validateToken: String, bitmap: android.graphics.Bitmap) {
        val imageView = android.widget.ImageView(requireContext()).apply {
            setImageBitmap(bitmap)
            setPadding(24, 12, 24, 12)
            scaleType = android.widget.ImageView.ScaleType.FIT_CENTER
        }
        val editText = android.widget.EditText(requireContext()).apply {
            hint = "输入验证码"
            setPadding(50, 20, 50, 20)
            textSize = 18f
            gravity = android.view.Gravity.CENTER
        }
        val layout = android.widget.LinearLayout(requireContext()).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            addView(imageView)
            addView(editText)
        }
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("输入验证码")
            .setView(layout)
            .setPositiveButton("提交") { _, _ ->
                val code = editText.text.toString().trim()
                if (code.isNotEmpty()) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        submitAntiCheatCaptcha(aid, uid, validateToken, code)
                    }
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    /**
     * 防作弊扫码结果处理 - 显示验证码/人脸识别/拍照验证选项
     */
    private fun showAntiCheatOptions(result: String) {
        val aid: String
        var enc: String? = null
        var fullIdParams: String? = null

        if (result.contains("SIGNIN:aid=")) {
            aid = result.substringAfter("SIGNIN:aid=").substringBefore("&")
            fullIdParams = result.substringAfter("SIGNIN:aid=")
        } else if (result.contains("id=")) {
            fullIdParams = result.substringAfter("id=")
            aid = fullIdParams.substringBefore("&")
            val encMatch = Regex("(?<=&enc=)[\\dA-Z]+").find(result)
            enc = encMatch?.value
        } else if (result.matches(Regex("\\d+"))) {
            aid = result
            fullIdParams = result
        } else {
            DebugLogCollector.e(QR_DEBUG, "防作弊: 无法识别二维码: $result")
            ToastUtils.show("无法识别签到二维码")
            return
        }

        val items = arrayOf(
            "🤖 智能签到 - 自动检测并处理(推荐)",
            "📷 验证码签到 - 加载图片并手动输入",
            "👤 人脸识别绕过 - 自动4种方式",
            "📸 照片验证签到 - 上传预设照片"
        )

        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("🔒 防作弊签到选择")
            .setItems(items) { _, which ->
                when (which) {
                    0 -> startIntelligentSign(result)
                    1 -> lifecycleScope.launch(Dispatchers.IO) {
                        handleAntiCheatCaptcha(aid, enc, fullIdParams)
                    }
                    2 -> lifecycleScope.launch(Dispatchers.IO) {
                        handleAntiCheatFace(aid, enc, fullIdParams)
                    }
                    3 -> lifecycleScope.launch(Dispatchers.IO) {
                        handleAntiCheatPhoto(aid, enc, fullIdParams)
                    }
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    /**
     * 防作弊-验证码签到: 加载验证码图片, 显示给用户输入
     */
    private suspend fun handleAntiCheatCaptcha(aid: String, enc: String?, fullIdParams: String?) {
        try {
            DebugLogCollector.d(QR_DEBUG, "===== 防作弊: 验证码签到开始 =====")
            val uid = CacheUtils.cache["uid"] ?: ""
            val fid = CacheUtils.cache["fid"] ?: ""
            val name = CacheUtils.cache["username"] ?: ""

            doPreSign(aid)

            // 方式A: 滑块验证码(captcha.chaoxing.com)
            DebugLogCollector.d(QR_DEBUG, "滑块验证码...")
            val captchaData = CaptchaHelper.getCaptchaImage(activeId = aid, uid = uid)

            if (captchaData != null) {
                var validateResult: String? = null
                withContext(Dispatchers.Main) {
                    val dialog = com.cofbro.qian.view.dialog.SliderCaptchaDialog(
                        context = requireContext(),
                        captchaData = captchaData,
                        onResult = { validateResult = it }
                    )
                    dialog.show()
                }
                // 等待dialog完成
                var wait = 0
                while (validateResult == null && wait < 120) { kotlinx.coroutines.delay(500); wait++ }

                if (validateResult != null) {
                    val signUrl = buildString {
                        append("https://mobilelearn.chaoxing.com/pptSign/stuSignajax?activeId=$aid&uid=$uid&fid=$fid&name=${java.net.URLEncoder.encode(name, "UTF-8")}&latitude=-1&longitude=-1&ifTiJiao=1&validate=$validateResult")
                        if (!enc.isNullOrEmpty()) append("&enc=$enc")
                    }
                    val resp = NetworkUtils.request(NetworkUtils.buildClientRequest(signUrl))
                    val body = resp.data?.body?.string() ?: ""
                    DebugLogCollector.d(QR_DEBUG, "滑块验证码签到: $body")
                    withContext(Dispatchers.Main) {
                        if (body.contains("成功") || body.contains("success")) ToastUtils.show("验证码签到成功!")
                        else ToastUtils.show("签到结果: $body")
                    }
                    return
                }
            }

            // 方式B: 手动输入验证码
            DebugLogCollector.d(QR_DEBUG, "手动输入验证码...")
            val postResp = NetworkUtils.postSign(aid = aid, uid = uid, fid = fid, name = name, latitude = "-1", longitude = "-1")
            val postBody = postResp.data?.body?.string() ?: ""
            if (!postBody.startsWith("validate_")) {
                withContext(Dispatchers.Main) { ToastUtils.show("无需验证码, 响应: $postBody") }
                return@handleAntiCheatCaptcha
            }
            val validateToken = postBody.removePrefix("validate_")

            val captchaResp = NetworkUtils.getCaptchaImage(aid, validateToken)
            val bytes = captchaResp.data?.body?.bytes()
            if (bytes == null || bytes.isEmpty()) {
                withContext(Dispatchers.Main) { ToastUtils.show("验证码加载失败") }
                return@handleAntiCheatCaptcha
            }
            val bitmap = android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            var userCode: String? = null
            withContext(Dispatchers.Main) {
                val et = android.widget.EditText(requireContext()).apply {
                    hint = "输入验证码"; setPadding(50, 20, 50, 20); textSize = 18f
                    gravity = android.view.Gravity.CENTER
                }
                val iv = android.widget.ImageView(requireContext()).apply {
                    setImageBitmap(bitmap); setPadding(24, 12, 24, 12)
                    scaleType = android.widget.ImageView.ScaleType.FIT_CENTER
                }
                val layout = android.widget.LinearLayout(requireContext()).apply {
                    orientation = android.widget.LinearLayout.VERTICAL; addView(iv); addView(et)
                }
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("输入验证码").setView(layout)
                    .setPositiveButton("提交") { _, _ -> userCode = et.text.toString().trim() }
                    .setNegativeButton("取消", null).show()
            }
            var w = 0; while (userCode == null && w < 120) { kotlinx.coroutines.delay(500); w++ }
            if (!userCode.isNullOrEmpty()) submitAntiCheatCaptcha(aid, uid, validateToken, userCode!!)
        } catch (e: Exception) {
            DebugLogCollector.e(QR_DEBUG, "验证码异常", e)
            withContext(Dispatchers.Main) { ToastUtils.show("验证码签到失败: ${e.message}") }
        }
    }

    /**
     * 提交验证码
     */
    private suspend fun submitAntiCheatCaptcha(aid: String, uid: String, validate: String, code: String) {
        try {
            DebugLogCollector.d(QR_DEBUG, "提交验证码: code=$code, validate=$validate")
            val resp = NetworkUtils.submitCaptcha(aid, uid, validate, code)
            val body = resp.data?.body?.string() ?: ""
            DebugLogCollector.d(QR_DEBUG, "验证码提交响应: $body")
            withContext(Dispatchers.Main) {
                if (body.contains("成功") || body.contains("success")) {
                    ToastUtils.show("验证码签到成功!")
                } else {
                    ToastUtils.show("验证码签到失败: $body")
                }
            }
        } catch (e: Exception) {
            DebugLogCollector.e(QR_DEBUG, "验证码提交异常", e)
            withContext(Dispatchers.Main) { ToastUtils.show("验证码提交失败: ${e.message}") }
        }
    }

    /**
     * 防作弊-人脸识别绕过
     */
    private suspend fun handleAntiCheatFace(aid: String, enc: String?, fullIdParams: String?) {
        try {
            DebugLogCollector.d(QR_DEBUG, "===== 防作弊: 人脸识别绕过 =====")
            val uid = CacheUtils.cache["uid"] ?: ""

            // 先POST签到触发checkFace_
            val postResp = NetworkUtils.postSign(
                aid = aid, uid = uid, fid = CacheUtils.cache["fid"] ?: "",
                name = CacheUtils.cache["username"] ?: "",
                latitude = "-1", longitude = "-1"
            )
            val postBody = postResp.data?.body?.string() ?: ""
            DebugLogCollector.d(QR_DEBUG, "POST响应: $postBody")

            if (postBody.startsWith("checkFace_")) {
                val faceToken = postBody.removePrefix("checkFace_")
                DebugLogCollector.d(QR_DEBUG, "检测到checkFace, token=$faceToken")
                val result = handleCheckFaceSign(aid, uid, faceToken, "-1", "-1", "")
                withContext(Dispatchers.Main) {
                    if (result) ToastUtils.show("人脸识别绕过成功!")
                    else ToastUtils.show("人脸识别绕过失败")
                }
            } else if (postBody.startsWith("validate_")) {
                DebugLogCollector.d(QR_DEBUG, "检测到validate_, 走照片验证")
                val validateToken = postBody.removePrefix("validate_")
                val result = handleValidateSign(aid, uid, validateToken, "-1", "-1", "")
                withContext(Dispatchers.Main) {
                    if (result) ToastUtils.show("照片验证成功!")
                    else ToastUtils.show("照片验证失败")
                }
            } else {
                withContext(Dispatchers.Main) {
                    ToastUtils.show("签到结果: $postBody")
                }
            }
        } catch (e: Exception) {
            DebugLogCollector.e(QR_DEBUG, "人脸识别绕过异常", e)
            withContext(Dispatchers.Main) { ToastUtils.show("人脸识别绕过失败: ${e.message}") }
        }
    }

    /**
     * 防作弊-照片验证签到: 上传预设照片+validate提交
     */
    private suspend fun handleAntiCheatPhoto(aid: String, enc: String?, fullIdParams: String?) {
        try {
            DebugLogCollector.d(QR_DEBUG, "===== 防作弊: 照片验证签到 =====")
            val uid = CacheUtils.cache["uid"] ?: ""

            // 先POST签到触发validate_
            val postResp = NetworkUtils.postSign(
                aid = aid, uid = uid, fid = CacheUtils.cache["fid"] ?: "",
                name = CacheUtils.cache["username"] ?: "",
                latitude = "-1", longitude = "-1"
            )
            val postBody = postResp.data?.body?.string() ?: ""
            DebugLogCollector.d(QR_DEBUG, "POST响应: $postBody")

            if (postBody.startsWith("validate_")) {
                val validateToken = postBody.removePrefix("validate_")
                DebugLogCollector.d(QR_DEBUG, "检测到validate_, token=$validateToken")
                val result = handleValidateSign(aid, uid, validateToken, "-1", "-1", "")
                withContext(Dispatchers.Main) {
                    if (result) ToastUtils.show("照片验证签到成功!")
                    else ToastUtils.show("照片验证签到失败")
                }
            } else if (postBody.startsWith("checkFace_")) {
                val faceToken = postBody.removePrefix("checkFace_")
                DebugLogCollector.d(QR_DEBUG, "检测到checkFace_, 走人脸识别绕过")
                val result = handleCheckFaceSign(aid, uid, faceToken, "-1", "-1", "")
                withContext(Dispatchers.Main) {
                    if (result) ToastUtils.show("人脸识别绕过成功!")
                    else ToastUtils.show("人脸识别绕过失败")
                }
            } else {
                withContext(Dispatchers.Main) {
                    ToastUtils.show("签到结果: $postBody")
                }
            }
        } catch (e: Exception) {
            DebugLogCollector.e(QR_DEBUG, "照片验证异常", e)
            withContext(Dispatchers.Main) { ToastUtils.show("照片验证失败: ${e.message}") }
        }
    }

    private fun showDebugLogDialog() {
        val scrollView = android.widget.ScrollView(requireContext())
        val textView = android.widget.TextView(requireContext()).apply {
            text = DebugLogCollector.getLogs().ifEmpty { "暂无日志" }
            textSize = 11f
            setPadding(24, 16, 24, 16)
            setTextIsSelectable(true)
        }
        scrollView.addView(textView)

        val layout = android.widget.LinearLayout(requireContext()).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            addView(scrollView, android.widget.LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT, 400))
        }

        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("操作日志")
            .setView(layout)
            .setNegativeButton("清空日志") { _, _ ->
                DebugLogCollector.clear()
                textView.text = "日志已清空"
                ToastUtils.show("日志已清空")
            }
            .setNeutralButton("复制日志") { _, _ ->
                val clipboard = requireContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                clipboard.setPrimaryClip(android.content.ClipData.newPlainText("log", DebugLogCollector.getLogs()))
                ToastUtils.show("日志已复制")
            }
            .setPositiveButton("关闭", null)
            .show()
    }

    /**
     * 手动签到面板: 当自动签到失败时, 用户可手动填参数补签
     */
    private fun showManualSignDialog() {
        val layout = android.widget.LinearLayout(requireContext()).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(48, 16, 48, 0)
        }

        val etAid = EditText(requireContext()).apply { hint = "activeId (必填)"; setSingleLine(); textSize = 13f }
        val etEnc = EditText(requireContext()).apply { hint = "enc (二维码签到需要)"; setSingleLine(); textSize = 13f }
        val etLat = EditText(requireContext()).apply { hint = "纬度(latitude) 可选"; setSingleLine(); textSize = 13f }
        val etLon = EditText(requireContext()).apply { hint = "经度(longitude) 可选"; setSingleLine(); textSize = 13f }
        val etObjectId = EditText(requireContext()).apply { hint = "objectId (拍照验证需要) 可选"; setSingleLine(); textSize = 13f }
        val etValidate = EditText(requireContext()).apply { hint = "validate token (验证码需要) 可选"; setSingleLine(); textSize = 13f }

        // 签到类型选择
        val types = arrayOf("普通签到", "二维码签到", "定位签到", "手势签到", "签到码签到")
        val typeValues = arrayOf(Constants.SIGN.NORMAl, Constants.SIGN.SCAN_QR, Constants.SIGN.LOCATION, Constants.SIGN.GESTURE, Constants.SIGN.SIGN_CODE)
        var selectedTypeIndex = 0

        val typePicker = androidx.appcompat.widget.AppCompatSpinner(requireContext()).apply {
            adapter = android.widget.ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, types)
            setSelection(0)
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT)
            setOnItemSelectedListener(object : android.widget.AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p: android.widget.AdapterView<*>?, v: android.view.View?, pos: Int, id: Long) { selectedTypeIndex = pos }
                override fun onNothingSelected(p: android.widget.AdapterView<*>?) {}
            })
        }

        // Type label
        val tvTypeLabel = android.widget.TextView(requireContext()).apply {
            text = "签到类型"
            textSize = 12f
            setPadding(0, 12, 0, 0)
        }

        layout.addView(tvTypeLabel)
        layout.addView(typePicker)
        layout.addView(etAid)
        layout.addView(etEnc)
        layout.addView(etLat)
        layout.addView(etLon)
        layout.addView(etObjectId)
        layout.addView(etValidate)

        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("🔧 手动签到")
            .setView(layout)
            .setPositiveButton("签到") { _, _ ->
                val aid = etAid.text.toString().trim()
                if (aid.isEmpty()) { ToastUtils.show("activeId不能为空"); return@setPositiveButton }

                val enc = etEnc.text.toString().trim().ifEmpty { null }
                val lat = etLat.text.toString().trim().ifEmpty { "-1" }
                val lon = etLon.text.toString().trim().ifEmpty { "-1" }
                val addr = ""
                val objectId = etObjectId.text.toString().trim().ifEmpty { null }
                val validate = etValidate.text.toString().trim().ifEmpty { null }

                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val uid = CacheUtils.cache["uid"] ?: ""
                        val fid = CacheUtils.cache["fid"] ?: ""
                        val name = CacheUtils.cache["username"] ?: ""

                        DebugLogCollector.d("ManualSign", "手动签到: aid=$aid type=${typeValues[selectedTypeIndex]} enc=$enc")

                        // 用Signer体系签到
                        val fullParams = if (enc != null) "$aid&enc=$enc" else null
                        val signer = com.cofbro.qian.signer.SignDispatcher.createSigner(
                            aid, typeValues[selectedTypeIndex], uid, fid, name, requireContext(),
                            enc = enc, fullIdParams = fullParams
                        )
                        signer.preSign()
                        signer.fetchPreCheck()
                        if (signer is com.cofbro.qian.signer.QRCodeSigner) {
                            signer.acquireLocation()
                        }
                        if (objectId != null) signer.preUploadedObjectId = objectId
                        if (validate != null) signer.preFetchedCaptchaValidate = validate
                        signer.prepareAll(requireContext())

                        val result = signer.sign()
                        DebugLogCollector.d("ManualSign", "手动签到结果: ${result.message}")
                        withContext(Dispatchers.Main) {
                            if (result.isSuccess()) ToastUtils.show("手动签到成功!")
                            else ToastUtils.show("签到结果: ${result.message}")
                        }
                    } catch (e: Exception) {
                        DebugLogCollector.e("ManualSign", "手动签到异常", e)
                        withContext(Dispatchers.Main) { ToastUtils.show("手动签到失败: ${e.message}") }
                    }
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun fitToolbar() {
        val params = binding?.appToolBar?.layoutParams
        params?.height = getStatusBarHeight(requireContext()) + dp2px(requireContext(), 45)
        binding?.appToolBar?.layoutParams = params
        solidAppToolBar(0)
    }

    private fun initObserver() {
        viewModel.loadCourseListLiveData.observe(this) {
            lifecycleScope.launch(Dispatchers.IO) {
                val s = it.data?.body?.string() ?: ""
                Downloader.download(requireContext(), Constants.RecycleJson.HOME_JSON_DATA, s)
                withContext(Dispatchers.Main) {
                    val jsonObject = s.safeParseToJson()
                    mAdapter?.setData(jsonObject)
                    data = jsonObject
                    binding?.rvCourseList?.adapter = mAdapter
                    binding?.rvCourseList?.layoutManager =
                        LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    mAdapter?.setOnItemClickListener(object :
                        CourseListAdapter.AdapterListener {
                        override fun onItemClick(
                            courseId: String,
                            classId: String,
                            cpi: String,
                            courseName: String
                        ) {
                            toWrapperActivity(courseId, classId, cpi, courseName)
                        }
                    })
                }
            }
        }

        viewModel.signLiveData.observe(this) {
            lifecycleScope.launch(Dispatchers.IO) {
                val data = it.data?.body?.string()
                Log.d("MainActivity", "initObserver: $data")
            }
            ToastUtils.show("签到成功!")
        }

        viewModel.userInfoLiveData.observe(this) {
            lifecycleScope.launch(Dispatchers.IO) {
                val data = it.data?.body?.string() ?: ""
                withContext(Dispatchers.Main) {
                    val username = HtmlParser.parseToUsername(data)
                    CacheUtils.persistCookie(Constants.USER.USERNAME, username)
                }
            }
        }
    }

    private fun doNetwork() {
        viewModel.loadCourseList(URL.getAllCourseListPath())
    }

    private fun requestForUserInfo() {
        if (CacheUtils.cache[Constants.USER.USERNAME].isNullOrEmpty()) {
            viewModel.requestForUserInfo(URL.getUserInfo())
        }
    }

    private fun getDataItemCount(): Int {
        data?.getJSONArray(Constants.CourseList.CHANNEL_LIST)?.let { array ->
            return (array.size - 1).takeIf { it >= 0 } ?: 0
        }
        return 0
    }

    private fun solidAppToolBar(alpha: Int) {
        binding?.appToolBar?.background?.alpha = alpha
    }

    private fun toWrapperActivity(courseId: String, classId: String, cpi: String, courseName: String) {
        val intent = Intent(requireActivity(), WrapperActivity::class.java)
        intent.apply {
            putExtra("courseId", courseId)
            putExtra("classId", classId)
            putExtra("cpi", cpi)
            putExtra("courseName", courseName)
        }
        startActivity(intent)
    }

    private fun loadJsonLocally() {
        lifecycleScope.launch(Dispatchers.IO) {
            val dataString = Downloader.acquire(requireContext(), Constants.RecycleJson.HOME_JSON_DATA)
            val firstLoad = CacheUtils.cache[Constants.DataLoad.FIRST_LOAD] ?: Constants.DataLoad.UNLOAD
            if (dataString.isEmpty() || firstLoad == Constants.DataLoad.UNLOAD) {
                doNetwork()
                CacheUtils.cache[Constants.DataLoad.FIRST_LOAD] = Constants.DataLoad.LOADED
                return@launch
            }
            val response = Response.Builder()
                .request(NetworkUtils.buildClientRequest(URL.getAllCourseListPath()))
                .header("refer", "")
                .protocol(Protocol.HTTP_1_1)
                .body(ResponseBody.create("application/json".toMediaTypeOrNull(), dataString))
                .code(200).message("OK").build()
            val baseResponse = BaseResponse<Response>()
            baseResponse.data = response
            baseResponse.dataState = DataState.STATE_INITIALIZE
            viewModel.loadCourseListLiveData.postValue(baseResponse)
        }
    }

    /**
     * 获取最近签到活动: 遍历所有课程, 查找进行中的签到
     */
    private fun fetchRecentSignActivities() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) { ToastUtils.show("正在查询最近签到活动...") }
                DebugLogCollector.d(QR_DEBUG, "===== 开始查询最近签到活动 =====")

                // 1. 获取课程列表
                val courseResp = NetworkUtils.request(NetworkUtils.buildClientRequest(URL.getAllCourseListPath()))
                val courseBody = courseResp.data?.body?.string() ?: ""
                val courseJson = courseBody.safeParseToJson()
                val channelArray = courseJson.getJSONArrayExt(Constants.CourseList.CHANNEL_LIST)
                if (channelArray.size == 0) {
                    DebugLogCollector.w(QR_DEBUG, "课程列表为空")
                    withContext(Dispatchers.Main) { ToastUtils.show("未获取到课程列表") }
                    return@launch
                }

                // 2. 提取courseId和classId
                data class CourseInfo(val courseId: String, val classId: String, val courseName: String)

                val courses = mutableListOf<CourseInfo>()
                for (i in 0 until channelArray.size) {
                    val item = channelArray.get(i) as? JSONObject ?: continue
                    val classId = item.getStringExt(Constants.CourseList.KEY) ?: ""
                    val cpi = item.getStringExt(Constants.CourseList.CPI) ?: ""
                    val itemArray = item.getJSONArrayExt(Constants.CourseList.DATA)
                    if (itemArray.size > 0) {
                        val courseItem = itemArray.get(0) as? JSONObject ?: continue
                        val courseId = courseItem.getStringExt(Constants.CourseList.COURSE_ID) ?: ""
                        val courseName = courseItem.getStringExt(Constants.CourseList.COURSE_NAME) ?: ""
                        if (courseId.isNotEmpty() && classId.isNotEmpty()) {
                            courses.add(CourseInfo(courseId, classId, courseName))
                        }
                    }
                }
                DebugLogCollector.d(QR_DEBUG, "共${courses.size}门课程, 开始遍历查找签到活动")

                // 3. 遍历课程查找进行中的签到活动
                data class SignActivity(
                    val aid: String, val name: String, val courseName: String,
                    val signType: String, val status: String
                )
                val foundActivities = mutableListOf<SignActivity>()
                for ((index, course) in courses.withIndex()) {
                    try {
                        val activeUrl = URL.getActiveListPath(course.courseId, course.classId)
                        val activeResp = NetworkUtils.request(NetworkUtils.buildClientRequest(activeUrl))
                        val activeBody = activeResp.data?.body?.string() ?: ""
                        val activeJson = activeBody.safeParseToJson()

                        val activeData = activeJson.getJSONObject("data")
                        if (activeData.size == 0) continue
                        val activeList = activeData.getJSONArrayExt(Constants.TaskList.ACTIVE_LIST)
                        if (activeList.size == 0) continue

                        for (j in 0 until activeList.size) {
                            val act = activeList.get(j) as? JSONObject ?: continue
                            val status = act.getIntExt(Constants.TaskList.STATUS)
                            val activeType = act.getIntExt(Constants.TaskList.ACTIVE_TYPE)
                            val name = act.getStringExt(Constants.TaskList.TITLE) ?: ""
                            val aid = act.getStringExt(Constants.TaskList.ID) ?: ""
                            val otherId = act.getStringExt("otherId") ?: "" // 签到类型

                            // status=1 进行中, activeType=2是签到活动
                            if (status == 1 && activeType == 2) {
                                val typeLabel = when (otherId) {
                                    "0" -> "普通签到"
                                    "2" -> "二维码签到"
                                    "3" -> "手势签到"
                                    "4" -> "定位签到"
                                    "5" -> "签到码"
                                    else -> "类型$otherId"
                                }
                                // 应用签到类型筛选(跳过用户关闭的类型)
                                if (!com.cofbro.qian.utils.SignFilterManager.isAutoSignEnabled(otherId)) {
                                    DebugLogCollector.d(QR_DEBUG, "用户已关闭此类型: [$name] type=$otherId, 跳过")
                                } else {
                                    foundActivities.add(SignActivity(aid, name, course.courseName, typeLabel, "进行中"))
                                    DebugLogCollector.d(QR_DEBUG, "发现进行中签到: [$name] aid=$aid, type=$typeLabel, course=${course.courseName}")
                                }
                            }
                        }
                        delay(200) // 避免请求过快
                    } catch (e: Exception) {
                        DebugLogCollector.w(QR_DEBUG, "查询课程${course.courseName}异常: ${e.message}")
                    }

                    if (index % 5 == 0 && index > 0) {
                        withContext(Dispatchers.Main) { ToastUtils.show("已查询 $index/${courses.size} 门课程...") }
                    }
                }

                // 4. 展示结果
                if (foundActivities.isEmpty()) {
                    DebugLogCollector.d(QR_DEBUG, "未发现进行中的签到活动")
                    withContext(Dispatchers.Main) { ToastUtils.show("未发现进行中的签到活动") }
                } else {
                    DebugLogCollector.d(QR_DEBUG, "共发现${foundActivities.size}个进行中签到")
                    withContext(Dispatchers.Main) {
                        val displayItems = foundActivities.map { 
                            "${it.name}\n[${it.signType}] ${it.courseName}" 
                        }.toTypedArray()
                        androidx.appcompat.app.AlertDialog.Builder(requireContext())
                            .setTitle("发现${foundActivities.size}个进行中签到（点击签到）")
                            .setItems(displayItems) { _, which ->
                                val activity = foundActivities[which]
                                DebugLogCollector.d(QR_DEBUG, "用户选择签到: ${activity.name}, aid=${activity.aid}, type=${activity.signType}")
                                lifecycleScope.launch(Dispatchers.IO) {
                                    startAutoSignFromActivity(activity.aid)
                                }
                            }
                            .setPositiveButton("关闭", null)
                            .show()
                    }
                }
            } catch (e: Exception) {
                DebugLogCollector.e(QR_DEBUG, "查询最近签到异常", e)
                withContext(Dispatchers.Main) { ToastUtils.show("查询失败: ${e.message}") }
            }
        }
    }

    /**
     * 从活动列表点击后自动签到
     */
    private suspend fun startAutoSignFromActivity(aid: String) {
        if (isSigningIn) {
            DebugLogCollector.w(QR_DEBUG, "签到进行中, 忽略重复请求")
            return
        }
        isSigningIn = true
        try {
            DebugLogCollector.d(QR_DEBUG, "===== 从活动列表自动签到: aid=$aid =====")
            withContext(Dispatchers.Main) { ToastUtils.show("开始自动签到: $aid") }

            // 预签到
            doPreSign(aid)

            // 查询签到类型
            val signTypeBody = querySignType(aid)
            val signTypeData = signTypeBody.safeParseToJson()
            val signType = if (signTypeData.size > 0) signTypeData.getStringExt("otherId") else ""
            val uid = CacheUtils.cache["uid"] ?: ""
            val fid = CacheUtils.cache["fid"] ?: ""
            val name = CacheUtils.cache["username"] ?: ""

            DebugLogCollector.d(QR_DEBUG, "签到类型: $signType")

            // 检查用户筛选: 如果此类型被关闭则跳过
            if (!com.cofbro.qian.utils.SignFilterManager.isAutoSignEnabled(signType)) {
                DebugLogCollector.d(QR_DEBUG, "用户已关闭此签到类型, 跳过: $signType")
                withContext(Dispatchers.Main) { ToastUtils.show("已跳过${signType}类型(签到筛选)") }
                return
            }

            // ===== Signer体系: 预检查 + 预处理 + 一次性签到 =====
            val signer = com.cofbro.qian.signer.SignDispatcher.createSigner(
                aid, signType, uid, fid, name, requireContext()
            )

            signer.fetchPreCheck()
            signer.prepareAll(requireContext())

            val signResult = signer.sign()

            DebugLogCollector.d(QR_DEBUG, "签到结果: success=${signResult.isSuccess()} msg=${signResult.message}")
            withContext(Dispatchers.Main) {
                when {
                    signResult.isSuccess() -> ToastUtils.show("签到成功!")
                    signResult.isAlreadySigned -> ToastUtils.show("已签到过了")
                    signResult.isExpired -> ToastUtils.show("签到已过期")
                    else -> ToastUtils.show("签到结果: ${signResult.message}")
                }
            }
        } catch (e: Exception) {
            DebugLogCollector.e(QR_DEBUG, "自动签到异常", e)
        } finally {
            isSigningIn = false
        }
    }
}

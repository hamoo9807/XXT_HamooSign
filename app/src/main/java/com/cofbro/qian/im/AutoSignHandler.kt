package com.cofbro.qian.im

import android.content.Context
import com.alibaba.fastjson.JSONObject
import com.cofbro.qian.data.URL
import com.cofbro.qian.utils.CacheUtils
import com.cofbro.qian.utils.DebugLogCollector
import com.cofbro.qian.utils.NetworkUtils
import com.cofbro.qian.utils.NativeLocationUtils
import com.cofbro.qian.utils.PresetLocationManager
import com.cofbro.qian.utils.getStringExt
import com.cofbro.qian.utils.safeParseToJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.URLEncoder

/**
 * IM签到自动处理器
 * 收到签到通知后自动判断类型并签到
 * 参照chaoxing-signin项目的接口格式
 */
class AutoSignHandler(private val context: Context) {

    companion object {
        private const val TAG = "AutoSign"
        // 签到类型
        private const val SIGN_NORMAL = 0       // 普通签到
        private const val SIGN_QRCODE = 2       // 二维码签到
        private const val SIGN_GESTURE = 3      // 手势签到
        private const val SIGN_LOCATION = 4     // 位置签到
        private const val SIGN_CODE = 5         // 签到码签到
    }

    fun handleAutoSign(aid: String, classId: String, courseId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val uid = CacheUtils.cache["uid"] ?: ""
                val fid = CacheUtils.cache["fid"] ?: ""
                val name = CacheUtils.cache["username"] ?: ""

                if (uid.isEmpty()) {
                    DebugLogCollector.w(TAG, "uid为空, 请先登录")
                    return@launch
                }

                DebugLogCollector.d(TAG, "开始自动签到: aid=$aid, classId=$classId, courseId=$courseId")

                // 1. 预签到
                if (courseId.isNotEmpty() && classId.isNotEmpty()) {
                    preSign(aid, classId, courseId, uid)
                }

                // 2. 获取签到类型
                val signTypeInfo = getSignType(aid)
                val otherId = signTypeInfo?.getIntValue("otherId") ?: -1
                val ifphoto = signTypeInfo?.getIntValue("ifphoto") ?: 0

                val signTypeName = when (otherId) {
                    SIGN_NORMAL -> if (ifphoto == 1) "拍照签到" else "普通签到"
                    SIGN_QRCODE -> "二维码签到"
                    SIGN_GESTURE -> "手势签到"
                    SIGN_LOCATION -> "位置签到"
                    SIGN_CODE -> "签到码签到"
                    else -> "未知类型($otherId)"
                }
                DebugLogCollector.d(TAG, "签到类型: $signTypeName (otherId=$otherId, ifphoto=$ifphoto)")

                // 3. 根据类型自动签到
                when (otherId) {
                    SIGN_NORMAL -> {
                        if (ifphoto == 1) {
                            DebugLogCollector.d(TAG, "拍照签到, 需上传照片")
                            handlePhotoSign(aid, uid, fid, name)
                        } else {
                            generalSign(aid, uid, fid, name)
                        }
                    }
                    SIGN_GESTURE -> {
                        DebugLogCollector.d(TAG, "手势签到: 尝试POST → 暴力破解手势码")
                        // 先尝试普通POST, 失败则暴力破解
                        val gSigner = com.cofbro.qian.signer.GestureSigner(aid, uid, fid, name)
                        val gResult = gSigner.sign()
                        if (!gResult.isSuccess()) {
                            // 需要手势码, 暴力破解
                            val bruteResult = gSigner.bruteForce()
                            if (bruteResult?.isSuccess() == true) {
                                DebugLogCollector.d(TAG, "手势暴力破解成功!")
                            } else {
                                DebugLogCollector.w(TAG, "手势暴力破解失败, 降级到通用签到")
                                generalSign(aid, uid, fid, name)
                            }
                        }
                    }
                    SIGN_CODE -> {
                        DebugLogCollector.d(TAG, "签到码签到: 尝试POST → 暴力破解数字码")
                        val pSigner = com.cofbro.qian.signer.PasswordSigner(aid, uid, fid, name)
                        val pResult = pSigner.sign()
                        if (!pResult.isSuccess()) {
                            val bruteResult = pSigner.bruteForce()
                            if (bruteResult?.isSuccess() == true) {
                                DebugLogCollector.d(TAG, "签到码暴力破解成功!")
                            } else {
                                DebugLogCollector.w(TAG, "签到码暴力破解失败, 降级到通用签到")
                                generalSign(aid, uid, fid, name)
                            }
                        }
                    }
                    SIGN_LOCATION -> {
                        DebugLogCollector.d(TAG, "位置签到: 自动定位签到")
                        locationSign(aid, uid, fid, name)
                    }
                    SIGN_QRCODE -> {
                        DebugLogCollector.w(TAG, "二维码签到: 需要扫码, IM监听无法自动处理")
                    }
                    else -> {
                        DebugLogCollector.w(TAG, "未知签到类型: $otherId")
                    }
                }

            } catch (e: Exception) {
                DebugLogCollector.e(TAG, "自动签到异常", e)
            }
        }
    }

    private suspend fun preSign(aid: String, classId: String, courseId: String, uid: String) {
        try {
            val url = URL.getPreSignPath(courseId, classId, aid, uid)
            val request = NetworkUtils.buildClientRequest(url)
            NetworkUtils.request(request)
            DebugLogCollector.d(TAG, "预签到完成")

            // analysis
            val analysisResp = NetworkUtils.request(NetworkUtils.buildClientRequest(URL.getAnalysisPath(aid)))
            val analysisBody = analysisResp.data?.body?.string() ?: ""
            val codeStart = analysisBody.indexOf("code='+'") + 8
            if (codeStart > 7) {
                var code = analysisBody.substring(codeStart)
                val codeEnd = code.indexOf("'")
                if (codeEnd > 0) {
                    code = code.substring(0, codeEnd)
                    NetworkUtils.request(NetworkUtils.buildClientRequest(URL.getAnalysis2Path(code)))
                    DebugLogCollector.d(TAG, "analysis完成")
                }
            }

            delay(500)
        } catch (e: Exception) {
            DebugLogCollector.w(TAG, "预签到异常: ${e.message}")
        }
    }

    private suspend fun getSignType(aid: String): com.alibaba.fastjson.JSONObject? {
        return try {
            val url = URL.getActiveInfoPath(aid)
            val request = NetworkUtils.buildClientRequest(url)
            val response = NetworkUtils.request(request)
            val body = response.data?.body?.string() ?: ""
            val json = body.safeParseToJson() ?: return null
            json.getJSONObject("data")
        } catch (e: Exception) {
            DebugLogCollector.w(TAG, "获取签到类型异常: ${e.message}")
            null
        }
    }

    /**
     * 通用签到(普通/手势/签到码)
     * 参照chaoxing-signin: GET方式
     * pptSign/stuSignajax?activeId=&uid=&clientip=&latitude=-1&longitude=-1&appType=15&fid=&name=
     */
    private suspend fun generalSign(aid: String, uid: String, fid: String, name: String) {
        // 先POST
        val postResp = NetworkUtils.postSign(
            aid = aid, uid = uid, fid = fid, name = name,
            latitude = "-1", longitude = "-1"
        )
        var body = postResp.data?.body?.string() ?: ""
        DebugLogCollector.d(TAG, "POST签到响应: $body")
        if (body.contains("成功") || body.contains("success")) {
            DebugLogCollector.d(TAG, "POST签到成功!")
            return
        }
        // 处理validate_ (拍照验证)
        if (body.startsWith("validate_")) {
            val validateToken = body.removePrefix("validate_")
            DebugLogCollector.d(TAG, "需要拍照验证, 尝试处理")
            handlePhotoSign(aid, uid, fid, name)
            return
        }
        // 处理checkFace_ (人脸识别防作弊)
        if (body.startsWith("checkFace_")) {
            val faceToken = body.removePrefix("checkFace_")
            DebugLogCollector.d(TAG, "检测到人脸识别防作弊, 尝试绕过")
            handleCheckFaceSign(aid, uid, faceToken, fid, name)
            return
        }

        // POST失败, 尝试GET(参照chaoxing-signin)
        DebugLogCollector.d(TAG, "POST失败, 尝试GET签到")
        val getResp = NetworkUtils.getSign(
            aid = aid, uid = uid, fid = fid, name = name,
            latitude = "-1", longitude = "-1"
        )
        body = getResp.data?.body?.string() ?: ""
        DebugLogCollector.d(TAG, "GET签到响应: $body")
        if (body.contains("成功") || body.contains("success")) {
            DebugLogCollector.d(TAG, "GET签到成功!")
        } else if (body.startsWith("validate_")) {
            val validateToken = body.removePrefix("validate_")
            DebugLogCollector.d(TAG, "GET需要拍照验证, 尝试处理")
            handlePhotoSign(aid, uid, fid, name)
        } else if (body.startsWith("checkFace_")) {
            val faceToken = body.removePrefix("checkFace_")
            DebugLogCollector.d(TAG, "GET检测到人脸识别防作弊, 尝试绕过")
            handleCheckFaceSign(aid, uid, faceToken, fid, name)
        } else {
            DebugLogCollector.w(TAG, "签到失败: $body")
        }
    }

    /**
     * 位置签到
     * 参照chaoxing-signin: GET方式
     * pptSign/stuSignajax?name=&address=&activeId=&uid=&clientip=&latitude=&longitude=&fid=&appType=15&ifTiJiao=1
     */
    private suspend fun locationSign(aid: String, uid: String, fid: String, name: String) {
        // 尝试获取当前位置
        var lat = 0.0
        var lon = 0.0
        var addr = ""
        NativeLocationUtils.getCurrentLocation(
            context,
            onSuccess = { l, o, a -> lat = l; lon = o; addr = a },
            onError = { DebugLogCollector.w(TAG, "定位失败: $it") }
        )

        if (lat != 0.0 && lon != 0.0) {
            DebugLogCollector.d(TAG, "使用当前位置: lat=$lat, lon=$lon")
            if (tryLocationSign(aid, uid, fid, name, lat.toString(), lon.toString(), addr)) return
        }

        // 遍历预留地址
        val presets = PresetLocationManager.loadAll(context)
        for ((i, loc) in presets.withIndex()) {
            val bdLatLon = NativeLocationUtils.wgs84ToBd09(loc.longitude, loc.latitude)
            val locAddr = loc.address.ifEmpty { loc.name }
            DebugLogCollector.d(TAG, "尝试预留地址${i + 1}: ${loc.name}")
            if (tryLocationSign(aid, uid, fid, name, bdLatLon.latitude.toString(), bdLatLon.longitude.toString(), locAddr)) return
            delay(1000)
        }
        DebugLogCollector.w(TAG, "位置签到失败, 所有地址均不可用")
    }

    /**
     * 尝试使用指定位置签到, 自动处理validate_和checkFace_
     */
    private suspend fun tryLocationSign(
        aid: String, uid: String, fid: String, name: String,
        lat: String, lon: String, addr: String
    ): Boolean {
        // 先POST
        val postResp = NetworkUtils.postSign(
            aid = aid, uid = uid, fid = fid, name = name,
            latitude = lat, longitude = lon, address = addr
        )
        var body = postResp.data?.body?.string() ?: ""
        DebugLogCollector.d(TAG, "POST位置签到响应: $body")
        if (body.contains("成功") || body.contains("success")) {
            DebugLogCollector.d(TAG, "位置签到成功!")
            return true
        }
        if (body.startsWith("validate_")) {
            val validateToken = body.removePrefix("validate_")
            DebugLogCollector.d(TAG, "位置签到需拍照验证")
            handlePhotoSign(aid, uid, fid, name)
            return true
        }
        if (body.startsWith("checkFace_")) {
            val faceToken = body.removePrefix("checkFace_")
            DebugLogCollector.d(TAG, "位置签到需人脸识别绕过")
            handleCheckFaceSign(aid, uid, faceToken, fid, name)
            return true
        }

        // POST失败, 尝试GET
        DebugLogCollector.d(TAG, "POST位置签到失败, 尝试GET")
        val getResp = NetworkUtils.getSign(
            aid = aid, uid = uid, fid = fid, name = name,
            latitude = lat, longitude = lon, address = addr
        )
        body = getResp.data?.body?.string() ?: ""
        DebugLogCollector.d(TAG, "GET位置签到响应: $body")
        if (body.contains("成功") || body.contains("success")) {
            DebugLogCollector.d(TAG, "GET位置签到成功!")
            return true
        }
        if (body.startsWith("validate_")) {
            val validateToken = body.removePrefix("validate_")
            DebugLogCollector.d(TAG, "GET位置签到需拍照验证")
            handlePhotoSign(aid, uid, fid, name)
            return true
        }
        if (body.startsWith("checkFace_")) {
            val faceToken = body.removePrefix("checkFace_")
            DebugLogCollector.d(TAG, "GET位置签到需人脸识别绕过")
            handleCheckFaceSign(aid, uid, faceToken, fid, name)
            return true
        }
        return false
    }

    private suspend fun handlePhotoSign(aid: String, uid: String, fid: String, name: String) {
        try {
            // 查找预设照片
            val photoDir = java.io.File(context.getExternalFilesDir(null), "preset_photo")
            val photoFile = photoDir.listFiles()?.firstOrNull {
                it.name.endsWith(".jpg") || it.name.endsWith(".png")
            }

            if (photoFile == null) {
                DebugLogCollector.w(TAG, "无预设照片, 拍照签到无法自动处理")
                return
            }

            // 获取上传token
            val tokenResp = NetworkUtils.request(NetworkUtils.buildClientRequest(URL.getUploadToken()))
            val tokenBody = tokenResp.data?.body?.string() ?: ""
            val tokenData = tokenBody.safeParseToJson() ?: return
            val token = tokenData.getStringExt("_token") ?: tokenData.getStringExt("token") ?: return

            // 获取PUID
            val puidResp = NetworkUtils.request(NetworkUtils.buildClientRequest(URL.getPuidPath()))
            val puidBody = puidResp.data?.body?.string() ?: ""
            val puidData = puidBody.safeParseToJson()
            val puid = puidData?.getJSONObject("msg")?.getStringExt("puid") ?: uid

            // 上传照片
            val uploadResp = NetworkUtils.post(URL.getUploadImagePath(token), photoFile)
            val uploadBody = uploadResp.data?.body?.string() ?: ""
            val uploadData = uploadBody.safeParseToJson() ?: return
            val objectId = uploadData.getStringExt("objectId") ?: ""
            if (objectId.isEmpty()) {
                DebugLogCollector.w(TAG, "照片上传失败: $uploadBody")
                return
            }

            // 带objectId签到
            val resp = NetworkUtils.postSign(
                aid = aid, uid = uid, fid = fid, name = name,
                latitude = "-1", longitude = "-1",
                objectId = objectId
            )
            val body = resp.data?.body?.string() ?: ""
            if (body.contains("成功") || body.contains("success")) {
                DebugLogCollector.d(TAG, "拍照签到成功!")
            } else {
                DebugLogCollector.w(TAG, "拍照签到失败: $body")
            }
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "拍照签到异常", e)
        }
    }

    /**
     * 处理checkFace_人脸识别防作弊
     */
    private suspend fun handleCheckFaceSign(
        aid: String, uid: String, faceToken: String,
        fid: String, name: String
    ) {
        try {
            DebugLogCollector.d(TAG, "===== 处理人脸识别防作弊(checkFace), token=$faceToken =====")

            // 获取clazzId和courseId
            val signTypeInfo = getSignType(aid)
            val clazzId = signTypeInfo?.getString("clazzId") ?: ""
            val courseId = signTypeInfo?.getString("courseId") ?: ""

            // 上传预设照片获取真实objectId
            DebugLogCollector.d(TAG, "上传预设照片获取真实objectId...")
            try {
                val photoDir = File(context.getExternalFilesDir(null), "preset_photo")
                val photoFile = photoDir.listFiles()?.firstOrNull {
                    it.name.endsWith(".jpg") || it.name.endsWith(".png") || it.name.endsWith(".jpeg")
                }
                if (photoFile == null || !photoFile.exists()) {
                    DebugLogCollector.w(TAG, "无预设照片, 人脸绕过无法完成")
                    return
                }

                val tokenResp = NetworkUtils.request(NetworkUtils.buildClientRequest(URL.getUploadToken()))
                val tokenBody = tokenResp.data?.body?.string() ?: ""
                val tokenData = tokenBody.safeParseToJson()
                val uploadToken = tokenData?.getStringExt("_token") ?: tokenData?.getStringExt("token") ?: ""
                if (uploadToken.isEmpty()) {
                    DebugLogCollector.w(TAG, "获取上传token失败")
                    return
                }

                val uploadResp = NetworkUtils.post(URL.getUploadImagePath(uploadToken), photoFile)
                val uploadBody = uploadResp.data?.body?.string() ?: ""
                val uploadData = uploadBody.safeParseToJson()
                val realObjectId = uploadData?.getStringExt("objectId") ?: ""
                if (realObjectId.isEmpty()) {
                    DebugLogCollector.w(TAG, "照片上传失败: $uploadBody")
                    return
                }
                DebugLogCollector.d(TAG, "真实objectId: $realObjectId")

                // 尝试1: updateqrstatus with real objectId
                val updateParams = "clazzId=$clazzId&courseId=$courseId&uuid=$faceToken&objectId=$realObjectId&qrcEnc=&failCount=0&compareResult=0"
                val updateResp = NetworkUtils.request(NetworkUtils.buildClientRequest("https://mooc1-api.chaoxing.com/qr/updateqrstatus?$updateParams"))
                val updateBody = updateResp.data?.body?.string() ?: ""
                DebugLogCollector.d(TAG, "updateqrstatus响应: $updateBody")
                if (updateBody.contains("成功") || updateBody.contains("success") || updateBody.contains("true")) {
                    DebugLogCollector.d(TAG, "人脸识别绕过成功!")
                    return
                }

                // 尝试2: 带真实objectId POST签到
                DebugLogCollector.d(TAG, "尝试带真实objectId POST签到")
                val signResp = NetworkUtils.postSign(
                    aid = aid, uid = uid, fid = fid, name = name,
                    latitude = "-1", longitude = "-1",
                    objectId = realObjectId
                )
                val signBody = signResp.data?.body?.string() ?: ""
                if (signBody.contains("成功") || signBody.contains("success")) {
                    DebugLogCollector.d(TAG, "人脸识别绕过成功!(POST)")
                    return
                }
                if (signBody.startsWith("validate_")) {
                    DebugLogCollector.d(TAG, "checkFace后需拍照验证")
                    handlePhotoSign(aid, uid, fid, name)
                    return
                }
                DebugLogCollector.w(TAG, "人脸绕过失败: $signBody")
            } catch (e: Exception) {
                DebugLogCollector.w(TAG, "人脸绕过异常: ${e.message}")
            }

            DebugLogCollector.w(TAG, "人脸识别防作弊所有方式均失败")
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "checkFace处理异常", e)
        }
    }
}

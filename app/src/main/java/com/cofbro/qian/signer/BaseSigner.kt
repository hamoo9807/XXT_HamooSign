package com.cofbro.qian.signer

import com.cofbro.qian.utils.CacheUtils
import com.cofbro.qian.utils.DebugLogCollector
import com.cofbro.qian.utils.NetworkUtils
import com.cofbro.qian.utils.FaceUploadHelper
import com.cofbro.qian.utils.CaptchaHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 签到基类
 * 所有签到类型的共性逻辑:
 * - preSign (analysis + analysis2)
 * - preCheck (ifNeedVCode / openCheckFaceFlag / ifPhoto)
 * - handleValidate (上传照片验证)
 * - handleCheckFace (人脸识别绕过)
 * - 代签代理
 */
abstract class BaseSigner(
    open val aid: String,
    open val uid: String = CacheUtils.cache["uid"] ?: "",
    open val fid: String = CacheUtils.cache["fid"] ?: "",
    open val name: String = CacheUtils.cache["username"] ?: "",
    open val classId: String = "",
    open val courseId: String = "",
    open val extContent: String = "",
    /** 代签时使用独立HTTP客户端, 为空则用全局NetworkUtils */
    open val proxyClient: okhttp3.OkHttpClient? = null
) {
    companion object {
        private const val TAG = "BaseSigner"
    }

    /** 签到前预检查结果 */
    var preCheck: SignPreCheck? = null

    /** 预上传的照片objectId */
    var preUploadedObjectId: String? = null

    /** 预获取的faceEnc */
    var preFetchedFaceEnc: String? = null

    /** 预获取的captchaValidate */
    var preFetchedCaptchaValidate: String? = null

    /**
     * 执行HTTP请求（优先使用proxyClient，否则用全局NetworkUtils）
     */
    open suspend fun httpGet(url: String): String {
        val cookies = CacheUtils.getCookies(uid)
        val request = okhttp3.Request.Builder().url(url)
            .addHeader("Accept-Language", "zh-Hans-CN;q=1, zh-Hant-CN;q=0.9")
            .addHeader("cookie", cookies)
            .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148")
            .build()
        return if (proxyClient != null) {
            proxyClient!!.newCall(request).execute().use { it.body?.string() ?: "" }
        } else {
            NetworkUtils.request(request).data?.body?.string() ?: ""
        }
    }

    // ==================== 子类必须实现 ====================

    /** 执行签到 */
    abstract suspend fun sign(): SignResult

    /** 检查是否已签到(预签到响应判断) */
    abstract suspend fun checkAlreadySigned(preSignBody: String): Boolean

    // ==================== 公共方法 ====================

    /**
     * 预签到: analysis + analysis2
     * 所有签到类型都需要
     */
    open suspend fun preSign() {
        DebugLogCollector.d(TAG, "预签到: extContent POST + analysis + analysis2")
        try {
            // Step 1: POST extContent to newsign/preSign (CSF alignment)
            if (classId.isNotEmpty() && courseId.isNotEmpty()) {
                val preSignUrl = com.cofbro.qian.data.URL.getPreSignPath(courseId, classId, aid, uid)
                val postBody = okhttp3.FormBody.Builder()
                    .addEncoded("ext", extContent.ifEmpty { "[]" })
                    .build()
                val request = okhttp3.Request.Builder()
                    .url(preSignUrl)
                    .post(postBody)
                    .addHeader("Accept-Language", "zh-Hans-CN;q=1, zh-Hant-CN;q=0.9")
                    .addHeader("cookie", CacheUtils.getCookies(uid))
                    .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148")
                    .build()
                if (proxyClient != null) {
                    proxyClient!!.newCall(request).execute().use { it.body?.string() }
                } else {
                    NetworkUtils.request(request)
                }
            }
            // Step 2: analysis GET
            val analysisUrl = "https://mobilelearn.chaoxing.com/pptSign/analysis?DB_STRATEGY=RANDOM&aid=$aid&vs=1"
            val analysisResp = NetworkUtils.request(NetworkUtils.buildClientRequest(analysisUrl))
            val analysisBody = analysisResp.data?.body?.string() ?: ""
            val analysis2Code = analysisBody.substringAfter("code='+'").substringBefore("'")
            if (analysis2Code.isNotEmpty()) {
                NetworkUtils.request(NetworkUtils.buildClientRequest("https://mobilelearn.chaoxing.com/pptSign/analysis2?DB_STRATEGY=RANDOM&code=$analysis2Code"))
            }
        } catch (e: Exception) {
            DebugLogCollector.w(TAG, "预签到异常: ${e.message}")
        }
    }

    /**
     * 预检查: 获取签到详情中的 ifNeedVCode / openCheckFaceFlag / ifPhoto
     */
    open suspend fun fetchPreCheck(): SignPreCheck? {
        preCheck = SignPreCheck.fetch(aid)
        if (preCheck == null) {
            // 降级: 用旧API
            preCheck = SignPreCheck.fromSignType(aid)
        }
        return preCheck
    }

    /**
     * 预处理所有前置验证
     * 在sign()之前调用, 确保enc只消耗一次
     */
    open suspend fun prepareAll(context: android.content.Context): SignPrepareResult {
        val check = preCheck ?: fetchPreCheck() ?: return SignPrepareResult(emptyMap())

        val prepared = mutableMapOf<String, String>()

        // 1. 需要照片 → 预上传
        if (check.needPhoto || preUploadedObjectId != null) {
            if (preUploadedObjectId == null) {
                val (success, objectId) = NetworkUtils.preUploadPhoto()
                if (success) preUploadedObjectId = objectId
            }
            if (!preUploadedObjectId.isNullOrEmpty()) {
                prepared["objectId"] = preUploadedObjectId!!
            }
        }

        // 2. 需要验证码 → 加载滑块验证码
        if (check.needVCode && preFetchedCaptchaValidate == null) {
            val captchaData = com.cofbro.qian.utils.CaptchaHelper.getCaptchaImage(
                courseId = preCheck?.courseId ?: "", classId = preCheck?.clazzId ?: "",
                activeId = aid, uid = uid
            )
            if (captchaData != null) {
                var validateResult: String? = null
                kotlinx.coroutines.withContext(Dispatchers.Main) {
                    val dialog = com.cofbro.qian.view.dialog.SliderCaptchaDialog(
                        context = context, captchaData = captchaData,
                        activeId = aid, uid = uid,
                        courseId = preCheck?.courseId ?: "", classId = preCheck?.clazzId ?: "",
                        onResult = { validateResult = it }
                    )
                    dialog.show()
                }
                var w = 0
                while (validateResult == null && w < 120) { kotlinx.coroutines.delay(500); w++ }
                if (validateResult != null) {
                    preFetchedCaptchaValidate = validateResult
                    prepared["validate"] = validateResult!!
                }
            }
        }

        // 3. 需要人脸 → 获取faceEnc
        if (check.needFace && preFetchedFaceEnc == null) {
            val clientId = CacheUtils.cache["clientId"]
                ?: FaceUploadHelper.fetchClientId(context)
            if (clientId != null) {
                CacheUtils.cache["clientId"] = clientId
                // 用预设照片获取faceEnc
                val photoDir = java.io.File(context.getExternalFilesDir(null), "preset_photo")
                val photoFile = photoDir.listFiles()?.firstOrNull { it.extension.lowercase() in listOf("jpg", "jpeg", "png") }
                if (photoFile != null && photoFile.exists()) {
                    val bitmap = android.graphics.BitmapFactory.decodeFile(photoFile.absolutePath)
                    if (bitmap != null) {
                        val faceObjectId = FaceUploadHelper.uploadFaceImage(context, bitmap)
                        if (faceObjectId != null) {
                            CacheUtils.cache["faceObjectId"] = faceObjectId
                            val faceEnc = FaceUploadHelper.getFaceEnc(aid, faceObjectId, clientId)
                            if (faceEnc != null) {
                                preFetchedFaceEnc = faceEnc
                                prepared["faceEnc"] = faceEnc
                                prepared["currentFaceId"] = faceObjectId
                                prepared["ifCFP"] = "0"
                            }
                        }
                    }
                }
            }
        }

        return SignPrepareResult(prepared)
    }

    /**
     * 处理validate_拍照验证
     */
    open suspend fun handleValidate(validateToken: String, lat: String = "-1", lon: String = "-1", addr: String = ""): SignResult {
        // 优先使用预上传的objectId
        val objectId = preUploadedObjectId ?: run {
            val (success, objId) = NetworkUtils.preUploadPhoto()
            objId.ifEmpty { null }
        }

        if (objectId == null) {
            DebugLogCollector.w(TAG, "无预设照片, validate无法完成")
            return SignResult(false, "预设照片未设置", validateToken = validateToken)
        }

        // 方式A: submitPhotoValidate (最小参数, 不重签)
        val vResp = NetworkUtils.submitPhotoValidate(aid, uid, validateToken, objectId)
        val vBody = vResp.data?.body?.string() ?: ""
        if (vBody.contains("成功") || vBody.contains("success")) {
            return SignResult(true, "拍照验证成功", vBody)
        }

        // 方式B: 完整POST
        val pResp = NetworkUtils.postSign(aid = aid, uid = uid, fid = fid, name = name,
            latitude = lat, longitude = lon, address = addr,
            objectId = objectId, validate = validateToken)
        val pBody = pResp.data?.body?.string() ?: ""
        return SignResult.fromBody(pBody)
    }

    /**
     * 处理checkFace_人脸识别防作弊
     */
    open suspend fun handleCheckFace(faceToken: String, lat: String = "-1", lon: String = "-1", addr: String = ""): SignResult {
        val clazzId = preCheck?.clazzId ?: ""
        val courseId = preCheck?.courseId ?: ""

        // 方式1: 用真实faceEnc签到 (最可靠)
        if (preFetchedFaceEnc != null) {
            val faceObjectId = CacheUtils.cache["faceObjectId"] ?: ""
            try {
                val url = "https://mobilelearn.chaoxing.com/pptSign/stuSignajax?&clientip=&appType=15&ifTiJiao=1&vpProbability=-1&vpStrategy=" +
                        "&activeId=$aid&uid=$uid&fid=$fid&name=${java.net.URLEncoder.encode(name, "UTF-8")}" +
                        "&latitude=$lat&longitude=$lon" +
                        "&currentFaceId=$faceObjectId&ifCFP=0&courseId=$courseId&faceEnc=${preFetchedFaceEnc}" +
                        "&deviceCode=${NetworkUtils.generateDeviceCode()}"
                val resp = NetworkUtils.request(NetworkUtils.buildClientRequest(url))
                val body = resp.data?.body?.string() ?: ""
                val result = SignResult.fromBody(body)
                if (result.isSuccess()) return result
                if (result.validateToken != null) return handleValidate(result.validateToken!!, lat, lon, addr)
            } catch (e: Exception) {
                DebugLogCollector.w(TAG, "faceEnc签到异常: ${e.message}")
            }
        }

        // 方式2: 上传真实照片获取objectId → updateqrstatus
        try {
            val (success, realObjId) = NetworkUtils.preUploadPhoto()
            if (success && realObjId.isNotEmpty()) {
                val url = "https://mooc1-api.chaoxing.com/qr/updateqrstatus?clazzId=$clazzId&courseId=$courseId&uuid=$faceToken&objectId=$realObjId&qrcEnc=&failCount=0&compareResult=0"
                val resp = NetworkUtils.request(NetworkUtils.buildClientRequest(url))
                val body = resp.data?.body?.string() ?: ""
                if (body.contains("成功") || body.contains("success") || body.contains("true"))
                    return SignResult(true, "人脸绕过成功(真实照片)", body)

                // 也尝试带objectId POST签到
                val pResp = NetworkUtils.postSign(aid = aid, uid = uid, fid = fid, name = name,
                    latitude = lat, longitude = lon, address = addr, objectId = realObjId)
                val pBody = pResp.data?.body?.string() ?: ""
                if (pBody.contains("成功") || pBody.contains("success"))
                    return SignResult(true, "人脸绕过成功(真实照片POST)", pBody)
                if (pBody.startsWith("validate_"))
                    return handleValidate(pBody.removePrefix("validate_"), lat, lon, addr)
            }
        } catch (_: Exception) {}

        return SignResult(false, "人脸绕过失败(需预设照片)", faceToken = faceToken)
    }

    /**
     * 处理签到响应, 自动分派到validate_/checkFace_/errorLocation等分支
     */
    open suspend fun handleSignResponse(body: String, lat: String = "-1", lon: String = "-1", addr: String = ""): SignResult {
        val result = SignResult.fromBody(body)
        return when {
            result.isSuccess() -> result
            // 验证码优先于拍照 (CSF对齐: validate_0XXXXXX = captcha, 不是拍照)
            result.captchaToken != null -> {
                if (preFetchedCaptchaValidate != null) {
                    DebugLogCollector.d(TAG, "验证码已预获取, 重签")
                    sign()  // 子类sign()会自动带上validate参数
                } else {
                    SignResult(false, "需滑块验证码(未预获取)", captchaToken = result.captchaToken, needCaptcha = true)
                }
            }
            result.validateToken != null -> handleValidate(result.validateToken!!, lat, lon, addr)
            result.faceToken != null -> handleCheckFace(result.faceToken!!, lat, lon, addr)
            else -> result
        }
    }

    /**
     * 导出所有预处理参数(供代签复用)
     * 代签时将这些参数注入ProxySignManager, 避免重复prepareAll
     */
    fun getPreparedParams(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        preUploadedObjectId?.let { map["objectId"] = it }
        preFetchedFaceEnc?.let { map["faceEnc"] = it }
        preFetchedCaptchaValidate?.let { map["validate"] = it }
        preCheck?.courseId?.let { map["courseId"] = it }
        preCheck?.clazzId?.let { map["clazzId"] = it }
        preCheck?.let {
            if (it.needPhoto) map["needPhoto"] = "1"
            if (it.needFace) map["needFace"] = "1"
            if (it.needVCode) map["needVCode"] = "1"
        }
        return map
    }
}

/**
 * 预处理结果
 */
data class SignPrepareResult(
    val preparedParams: Map<String, String>,
    val isReady: Boolean = true
)

package com.cofbro.qian.utils

import android.content.Context
import android.graphics.Bitmap
import com.alibaba.fastjson.JSONObject
import com.cofbro.qian.data.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import java.io.ByteArrayOutputStream

/**
 * 人脸识别辅助类
 * 用于获取真实的faceEnc, 带signToken签名
 */
object FaceUploadHelper {

    /**
     * 检查签到是否需要人脸识别
     */
    fun isFaceRequired(signInfo: JSONObject?): Boolean {
        return signInfo?.getIntValue("openCheckFaceFlag") == 1
    }

    /**
     * 检查签到是否需要验证码
     */
    fun isCaptchaRequired(signInfo: JSONObject?): Boolean {
        return signInfo?.getIntValue("ifNeedVCode") == 1
    }

    /**
     * 检查签到是否需要拍照
     */
    fun isPhotoRequired(signInfo: JSONObject?): Boolean {
        return signInfo?.getIntValue("ifPhoto") == 1
    }

    /**
     * 上传人脸照片到云盘, 获取objectId
     */
    suspend fun uploadFaceImage(context: Context, bitmap: Bitmap): String? = withContext(Dispatchers.IO) {
        try {
            // 获取上传token
            val tokenResp = NetworkUtils.request(NetworkUtils.buildClientRequest(com.cofbro.qian.data.URL.getUploadToken()))
            val tokenBody = tokenResp.data?.body?.string() ?: ""
            val tokenData = tokenBody.safeParseToJson()
            val token = tokenData?.getStringExt("_token") ?: tokenData?.getStringExt("token") ?: ""
            if (token.isEmpty()) return@withContext null

            // 压缩并上传
            val uid = CacheUtils.cache["uid"] ?: ""
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos)
            val imageData = bos.toByteArray()

            val uploadBody = okhttp3.MultipartBody.Builder()
                .setType(okhttp3.MultipartBody.FORM)
                .addFormDataPart("puid", uid)
                .addFormDataPart("file", "face_${System.currentTimeMillis()}.jpg",
                    okhttp3.RequestBody.create("image/jpeg".toMediaTypeOrNull(), imageData))
                .build()

            val request = Request.Builder()
                .url(com.cofbro.qian.data.URL.getUploadImagePath(token))
                .addHeader("cookie", CacheUtils.getCookies(uid))
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X)")
                .post(uploadBody)
                .build()

            val resp = NetworkUtils.request(request)
            val body = resp.data?.body?.string() ?: ""
            val data = body.safeParseToJson()
            val objectId = data?.getStringExt("objectId") ?: ""
            return@withContext objectId.ifEmpty { null }
        } catch (e: Exception) {
            DebugLogCollector.e("FaceUploadHelper", "上传人脸照片异常", e)
            null
        }
    }

    /**
     * 获取faceEnc (真实人脸加密串)
     * 流程: 上传人脸照片 → 构建faceResult(含signToken) → 调用check-face-result → 获取faceEnc
     */
    suspend fun getFaceEnc(
        activeId: String,
        faceObjectId: String,
        clientId: String? = null
    ): String? = withContext(Dispatchers.IO) {
        try {
            val cxtime = System.currentTimeMillis().toString()
            val faceResult = JSONObject().apply {
                put("currentFaceId", faceObjectId)
                put("LiveDetectionStatus", 1)
                put("collectStatus", 1)
                put("cxtime", cxtime)
            }

            // 尝试添加signToken (CSF对齐: 失败时优雅跳过, 不阻塞整个请求)
            val deviceInfo = try { DeviceInfoHelper.decryptClientId(clientId ?: "") } catch (e: Exception) { null }
            if (deviceInfo != null) {
                val cxcid = deviceInfo.getString("cid")
                val sc = deviceInfo.getString("sc")
                if (!cxcid.isNullOrEmpty() && !sc.isNullOrEmpty()) {
                    val fields = mapOf(
                        "currentFaceId" to faceObjectId,
                        "LiveDetectionStatus" to "1",
                        "collectStatus" to "1"
                    )
                    faceResult.put("cxcid", cxcid)
                    faceResult.put("signToken", DeviceInfoHelper.generateSignToken(cxcid, sc, fields, cxtime))
                }
            }

            val url = "${com.cofbro.qian.data.URL.CHECK_FACE_RESULT_URL}?activeId=$activeId&faceResult=${java.net.URLEncoder.encode(faceResult.toJSONString(), "UTF-8")}&DB_STRATEGY=PRIMARY_KEY&STRATEGY_PARA=activeId"
            val resp = NetworkUtils.request(NetworkUtils.buildClientRequest(url))
            val body = resp.data?.body?.string() ?: ""
            val json = body.safeParseToJson()
            val enc = json?.getString("enc") ?: json?.getStringExt("enc") ?: ""
            DebugLogCollector.d("FaceUploadHelper", "faceEnc获取结果: enc=${enc.take(20)}...")
            return@withContext enc.ifEmpty { null }
        } catch (e: Exception) {
            DebugLogCollector.e("FaceUploadHelper", "获取faceEnc异常", e)
            null
        }
    }

    /**
     * 获取用户clientId (通过上报加密设备信息)
     */
    suspend fun fetchClientId(context: Context): String? = withContext(Dispatchers.IO) {
        try {
            val encryptedInfo = DeviceInfoHelper.buildEncryptedDeviceInfo(context)
            val formBody = okhttp3.FormBody.Builder()
                .add("data", encryptedInfo)
                .build()
            val request = Request.Builder()
                .url(com.cofbro.qian.data.URL.getPuidPath())
                .addHeader("cookie", CacheUtils.getCurrentCookies())
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X) AppleWebKit/605.1.15")
                .post(formBody)
                .build()
            val resp = NetworkUtils.request(request)
            val body = resp.data?.body?.string() ?: ""
            val json = body.safeParseToJson()?.getJSONObject("msg")
            val clientId = json?.getString("clientId")?.takeIf { it.isNotEmpty() }
            DebugLogCollector.d("FaceUploadHelper", "clientId: ${clientId?.take(20)}...")
            return@withContext clientId
        } catch (e: Exception) {
            DebugLogCollector.e("FaceUploadHelper", "获取clientId异常", e)
            null
        }
    }
}

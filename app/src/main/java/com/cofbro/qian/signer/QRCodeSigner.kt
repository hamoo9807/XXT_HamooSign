package com.cofbro.qian.signer

import android.content.Context
import com.cofbro.qian.utils.DebugLogCollector
import com.cofbro.qian.utils.NetworkUtils
import com.cofbro.qian.utils.NativeLocationUtils
import com.cofbro.qian.utils.PresetLocationManager
import com.cofbro.qian.utils.CacheUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder

/**
 * 二维码签到 (优化版)
 *
 * 正确路径: GET stuSignajax?activeId=fullIdParams&location=...&uid=...&enc=...
 *   → 成功则立即返回
 *
 * 保底策略(仅正确路径失败时):
 * - errorLocation → 遍历预设地址
 * - validate_(验证码) → 用预获取validate重签
 * - validate_(拍照) → handleValidate
 * - checkFace_ → handleCheckFace
 *
 * 最终兜底: POST stuSignajax
 */
class QRCodeSigner(
    override val aid: String,
    override val uid: String = CacheUtils.cache["uid"] ?: "",
    override val fid: String = CacheUtils.cache["fid"] ?: "",
    override val name: String = CacheUtils.cache["username"] ?: "",
    val enc: String? = null,
    val fullIdParams: String? = null,
    val context: Context,
    override val classId: String = "",
    override val courseId: String = "",
    override val extContent: String = ""
) : BaseSigner(aid, uid, fid, name, classId, courseId, extContent) {

    companion object {
        private const val TAG = "QRCodeSigner"
    }

    private var lat = "-1"
    private var lon = "-1"
    private var addr = ""

    suspend fun acquireLocation() {
        var nativeLat = 0.0
        var nativeLon = 0.0
        var nativeAddr = ""

        try {
            NativeLocationUtils.getCurrentLocation(
                context,
                onSuccess = { l1, l2, a -> nativeLat = l1; nativeLon = l2; nativeAddr = a },
                onError = {}
            )
        } catch (_: Exception) {}

        if (nativeLat != 0.0 && nativeLon != 0.0) {
            lat = nativeLat.toString(); lon = nativeLon.toString(); addr = nativeAddr
            return
        }

        CacheUtils.cache["default_Sign_latitude"]?.let { cacheLat ->
            CacheUtils.cache["default_Sign_longitude"]?.let { cacheLon ->
                lat = cacheLat; lon = cacheLon
                addr = CacheUtils.cache["default_Sign_Location"] ?: ""
                return
            }
        }

        val presets = PresetLocationManager.loadAll(context)
        if (presets.isNotEmpty()) {
            val loc = presets[0]
            val bdLatLon = NativeLocationUtils.wgs84ToBd09(loc.longitude, loc.latitude)
            lat = bdLatLon.latitude.toString(); lon = bdLatLon.longitude.toString()
            addr = loc.address.ifEmpty { loc.name }
        }
    }

    override suspend fun checkAlreadySigned(preSignBody: String): Boolean = false

    override suspend fun sign(): SignResult = withContext(Dispatchers.IO) {
        if (preCheck == null) fetchPreCheck()

        if (preUploadedObjectId == null && preCheck?.needPhoto == true) {
            val (success, objId) = NetworkUtils.preUploadPhoto()
            if (success) preUploadedObjectId = objId
        }

        val addrEscaped = addr.replace("\"", "\\\"")
        val locationJson = """{"result":1,"latitude":$lat,"longitude":$lon,"address":"$addrEscaped"}"""
        val locationEncoded = URLEncoder.encode(locationJson, "UTF-8")

        // ===== 正确路径: 单次GET签到 =====
        if (!fullIdParams.isNullOrEmpty()) {
            val sb = StringBuilder("https://mobilelearn.chaoxing.com/pptSign/stuSignajax?activeId=$fullIdParams&location=$locationEncoded&uid=$uid")
            enc?.let { sb.append("&enc=$it") }
            preUploadedObjectId?.let { sb.append("&objectId=$it") }
            preFetchedFaceEnc?.let { sb.append("&currentFaceId=${CacheUtils.cache["faceObjectId"] ?: ""}&ifCFP=0&courseId=${preCheck?.courseId ?: ""}&faceEnc=$it") }
            preFetchedCaptchaValidate?.let { sb.append("&validate=$it") }

            try {
                val resp = NetworkUtils.request(NetworkUtils.buildClientRequest(sb.toString()))
                val body = resp.data?.body?.string() ?: ""
                var result = SignResult.fromBody(body)

                if (result.isSuccess()) return@withContext result
                if (result.isAlreadySigned || result.isExpired || result.isEnded) return@withContext result

                // 保底1: errorLocation → 遍历预设地址
                if (result.isLocationError) {
                    val presets = PresetLocationManager.loadAll(context)
                    for ((i, loc) in presets.withIndex()) {
                        val bdLatLon = NativeLocationUtils.wgs84ToBd09(loc.longitude, loc.latitude)
                        val retryAddr = loc.address.ifEmpty { loc.name }
                        val retryLocJson = """{"result":1,"latitude":${bdLatLon.latitude},"longitude":${bdLatLon.longitude},"address":"${retryAddr.replace("\"", "\\\"")}"}"""
                        val retrySb = StringBuilder("https://mobilelearn.chaoxing.com/pptSign/stuSignajax?activeId=$fullIdParams&location=${URLEncoder.encode(retryLocJson, "UTF-8")}&uid=$uid")
                        enc?.let { retrySb.append("&enc=$it") }
                        preUploadedObjectId?.let { retrySb.append("&objectId=$it") }
                        preFetchedFaceEnc?.let { retrySb.append("&currentFaceId=${CacheUtils.cache["faceObjectId"] ?: ""}&ifCFP=0&courseId=${preCheck?.courseId ?: ""}&faceEnc=$it") }
                        preFetchedCaptchaValidate?.let { retrySb.append("&validate=$it") }
                        val retryResp = NetworkUtils.request(NetworkUtils.buildClientRequest(retrySb.toString()))
                        val retryBody = retryResp.data?.body?.string() ?: ""
                        result = SignResult.fromBody(retryBody)
                        if (result.isSuccess() || result.isAlreadySigned || result.isExpired || result.isEnded) break
                        if (result.validateToken != null || result.faceToken != null) break
                    }
                    if (result.isSuccess() || result.isAlreadySigned) return@withContext result
                }

                // 保底2: validate_(验证码) → 用预获取validate重签
                if (result.captchaToken != null && !preFetchedCaptchaValidate.isNullOrEmpty()) {
                    val retrySb = StringBuilder("https://mobilelearn.chaoxing.com/pptSign/stuSignajax?activeId=$fullIdParams&location=$locationEncoded&uid=$uid")
                    enc?.let { retrySb.append("&enc=$it") }
                    preUploadedObjectId?.let { retrySb.append("&objectId=$it") }
                    preFetchedFaceEnc?.let { retrySb.append("&currentFaceId=${CacheUtils.cache["faceObjectId"] ?: ""}&ifCFP=0&courseId=${preCheck?.courseId ?: ""}&faceEnc=$it") }
                    retrySb.append("&validate=$preFetchedCaptchaValidate")
                    val retryResp = NetworkUtils.request(NetworkUtils.buildClientRequest(retrySb.toString()))
                    val retryBody = retryResp.data?.body?.string() ?: ""
                    result = SignResult.fromBody(retryBody)
                    if (result.isSuccess()) return@withContext result
                }

                // 保底3: validate_(拍照) / checkFace_ → 委托BaseSigner处理
                if (result.validateToken != null) return@withContext handleValidate(result.validateToken!!, lat, lon, addr)
                if (result.faceToken != null) return@withContext handleCheckFace(result.faceToken!!, lat, lon, addr)

                // GET签到失败, 降级到POST
                DebugLogCollector.w(TAG, "GET签到失败: ${result.message}, 降级POST")
            } catch (e: Exception) {
                DebugLogCollector.w(TAG, "GET签到异常: ${e.message}, 降级POST")
            }
        }

        // ===== 最终兜底: POST签到 =====
        try {
            val pResp = NetworkUtils.postSign(aid = aid, uid = uid, fid = fid, name = name,
                latitude = lat, longitude = lon, address = addr,
                objectId = preUploadedObjectId)
            val body = pResp.data?.body?.string() ?: ""
            return@withContext handleSignResponse(body, lat, lon, addr)
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "签到全部失败", e)
            return@withContext SignResult(false, "签到异常: ${e.message}")
        }
    }
}

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
 * 二维码签到
 * 支持pre-release原版 + enc方式
 * 签到前会预检查, 先处理好验证码/人脸/照片再签到(避免enc一次性消耗)
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

    /**
     * 获取位置(真实定位 → 缓存位置 → 预设地址)
     */
    suspend fun acquireLocation() {
        var nativeLat = 0.0
        var nativeLon = 0.0
        var nativeAddr = ""

        try {
            NativeLocationUtils.getCurrentLocation(
                context,
                onSuccess = { l1, l2, a -> nativeLat = l1; nativeLon = l2; nativeAddr = a },
                onError = { DebugLogCollector.w(TAG, "定位失败: $it") }
            )
        } catch (_: Exception) {}

        if (nativeLat != 0.0 && nativeLon != 0.0) {
            lat = nativeLat.toString(); lon = nativeLon.toString(); addr = nativeAddr
            return
        }

        // 尝试缓存位置
        CacheUtils.cache["default_Sign_latitude"]?.let { cacheLat ->
            CacheUtils.cache["default_Sign_longitude"]?.let { cacheLon ->
                lat = cacheLat; lon = cacheLon
                addr = CacheUtils.cache["default_Sign_Location"] ?: ""
                return
            }
        }

        // 尝试预设地址
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
        DebugLogCollector.d(TAG, "===== QRCodeSigner.sign() =====")

        val hasEnc = !enc.isNullOrEmpty()
        val hasFullParams = !fullIdParams.isNullOrEmpty()

        // 先预检查(如果还没做)
        if (preCheck == null) fetchPreCheck()

        // 预上传照片(如需要)
        if (preUploadedObjectId == null && preCheck?.needPhoto == true) {
            val (success, objId) = NetworkUtils.preUploadPhoto()
            if (success) preUploadedObjectId = objId
        }

        val addrEscaped = addr.replace("\"", "\\\"")
        val mockDataInner = """{"strategy":0,"probability":-1}"""
        val mockDataEscaped = mockDataInner.replace("\"", "\\\"")
        val locationJson = """{"result":1,"latitude":$lat,"longitude":$lon,"address":"$addrEscaped","mockData":"$mockDataEscaped"}"""
        val locationEncoded = URLEncoder.encode(locationJson, "UTF-8")
        val baseUrl = "https://mobilelearn.chaoxing.com/pptSign/stuSignajax?&clientip=&appType=15&ifTiJiao=1&vpProbability=-1&vpStrategy="

        // ===== 第0步: CSF式干净参数签到 =====
        // 关键: 用分开的latitude/longitude/address参数, 不用location JSON
        // 有些活动不认location JSON只认分开的参数
        if (hasEnc || hasFullParams) {
            try {
                val csfUrl = StringBuilder(baseUrl)
                if (hasEnc) csfUrl.append("&enc=$enc")
                csfUrl.append("&latitude=$lat&longitude=$lon")
                csfUrl.append("&address=${URLEncoder.encode(addr.ifEmpty { " " }, "UTF-8")}")
                csfUrl.append("&activeId=$aid&uid=$uid&name=${URLEncoder.encode(name, "UTF-8")}&fid=$fid")
                csfUrl.append("&deviceCode=${com.cofbro.qian.utils.NetworkUtils.generateDeviceCode()}")
                preUploadedObjectId?.let { csfUrl.append("&objectId=$it") }
                preFetchedFaceEnc?.let { csfUrl.append("&currentFaceId=${CacheUtils.cache["faceObjectId"] ?: ""}&ifCFP=0&courseId=${preCheck?.courseId ?: ""}&faceEnc=$it") }
                preFetchedCaptchaValidate?.let { csfUrl.append("&validate=$it") }

                var resp = NetworkUtils.request(NetworkUtils.buildClientRequest(csfUrl.toString()))
                var body = resp.data?.body?.string() ?: ""
                DebugLogCollector.d(TAG, "CSF式签到(分开参数): $body")

                // 如果分开参数返回errorLocation, 尝试加上location JSON再试
                if (body.startsWith("errorLocation")) {
                    val csfUrl2 = StringBuilder(csfUrl.toString())
                    csfUrl2.append("&location=$locationEncoded")
                    resp = NetworkUtils.request(NetworkUtils.buildClientRequest(csfUrl2.toString()))
                    body = resp.data?.body?.string() ?: ""
                    DebugLogCollector.d(TAG, "CSF式签到(加location JSON): $body")
                }

                val result = SignResult.fromBody(body)
                if (result.isSuccess()) return@withContext result
                if (result.captchaToken != null) {
                    if (preFetchedCaptchaValidate != null) {
                        DebugLogCollector.d(TAG, "验证码已预获取, 重签")
                    } else {
                        return@withContext SignResult(false, "需滑块验证码", captchaToken = result.captchaToken, needCaptcha = true)
                    }
                }
                if (result.validateToken != null) return@withContext handleValidate(result.validateToken!!, lat, lon, addr)
                if (result.faceToken != null) return@withContext handleCheckFace(result.faceToken!!, lat, lon, addr)
                if (result.isExpired) return@withContext result
                if (result.isLocationError) {
                    // 遍历预留地址(带mockData)
                    val presets = PresetLocationManager.loadAll(context)
                    for ((i, loc) in presets.withIndex()) {
                        val bdLatLon = NativeLocationUtils.wgs84ToBd09(loc.longitude, loc.latitude)
                        val retryAddr = loc.address.ifEmpty { loc.name }
                        val retryMockData = """{"strategy":0,"probability":-1}""".replace("\"", "\\\"")
                        val retryLocJson = """{"result":1,"latitude":${bdLatLon.latitude},"longitude":${bdLatLon.longitude},"address":"${retryAddr.replace("\"", "\\\"")}","mockData":"$retryMockData"}"""
                        val retryEncoded = URLEncoder.encode(retryLocJson, "UTF-8")
                        val retrySb = StringBuilder(baseUrl)
                        retrySb.append("&latitude=${bdLatLon.latitude}&longitude=${bdLatLon.longitude}")
                        retrySb.append("&activeId=$aid&uid=$uid&name=${URLEncoder.encode(name, "UTF-8")}&fid=$fid")
                        retrySb.append("&address=${URLEncoder.encode(retryAddr, "UTF-8")}&location=$retryEncoded")
                        retrySb.append("&deviceCode=${com.cofbro.qian.utils.NetworkUtils.generateDeviceCode()}")
                        preUploadedObjectId?.let { retrySb.append("&objectId=$it") }
                        preFetchedFaceEnc?.let { retrySb.append("&currentFaceId=${CacheUtils.cache["faceObjectId"] ?: ""}&ifCFP=0&courseId=${preCheck?.courseId ?: ""}&faceEnc=$it") }
                        preFetchedCaptchaValidate?.let { retrySb.append("&validate=$it") }
                        val retryResp = NetworkUtils.request(NetworkUtils.buildClientRequest(retrySb.toString()))
                        val rBody = retryResp.data?.body?.string() ?: ""
                        val rResult = SignResult.fromBody(rBody)
                        if (rResult.isSuccess()) return@withContext rResult
                        if (rResult.isExpired) break
                    }
                }
                // CSF式失败, 继续pre-release
                DebugLogCollector.d(TAG, "CSF式签到失败, 降级到pre-release")
            } catch (e: Exception) {
                DebugLogCollector.w(TAG, "CSF式签到异常: ${e.message}")
            }
        }

        // ===== 第1步: pre-release原版(enc方式, 保留兜底) =====
        if (hasFullParams) {
            val sb = StringBuilder("https://mobilelearn.chaoxing.com/pptSign/stuSignajax?activeId=$fullIdParams&location=$locationEncoded&uid=$uid")
            preUploadedObjectId?.let { sb.append("&objectId=$it") }
            preFetchedFaceEnc?.let { sb.append("&currentFaceId=${CacheUtils.cache["faceObjectId"] ?: ""}&ifCFP=0&courseId=${preCheck?.courseId ?: ""}&faceEnc=$it") }
            preFetchedCaptchaValidate?.let { sb.append("&validate=$it") }

            try {
                val resp = NetworkUtils.request(NetworkUtils.buildClientRequest(sb.toString()))
                val body = resp.data?.body?.string() ?: ""
                DebugLogCollector.d(TAG, "pre-release签到: $body")
                val result = SignResult.fromBody(body)
                if (result.isSuccess()) return@withContext result
                if (result.captchaToken != null) {
                    if (preFetchedCaptchaValidate != null) {
                        DebugLogCollector.d(TAG, "验证码已预获取, 重签")
                    } else {
                        return@withContext SignResult(false, "需滑块验证码", captchaToken = result.captchaToken, needCaptcha = true)
                    }
                }
                if (result.validateToken != null) return@withContext handleValidate(result.validateToken!!, lat, lon, addr)
                if (result.faceToken != null) return@withContext handleCheckFace(result.faceToken!!, lat, lon, addr)
                if (result.isExpired) return@withContext result
                if (result.isLocationError) {
                    // 尝试预留地址遍历
                    val presets = PresetLocationManager.loadAll(context)
                    for ((i, loc) in presets.withIndex()) {
                        val bdLatLon = NativeLocationUtils.wgs84ToBd09(loc.longitude, loc.latitude)
                        val retryMockData2 = """{"strategy":0,"probability":-1}""".replace("\"", "\\\"")
                        val retryLocJson = """{"result":1,"latitude":${bdLatLon.latitude},"longitude":${bdLatLon.longitude},"address":"${loc.address.ifEmpty { loc.name }.replace("\"", "\\\"")}","mockData":"$retryMockData2"}"""
                        val retrySb = StringBuilder("https://mobilelearn.chaoxing.com/pptSign/stuSignajax?activeId=$fullIdParams&location=${URLEncoder.encode(retryLocJson, "UTF-8")}&uid=$uid")
                        preUploadedObjectId?.let { retrySb.append("&objectId=$it") }
                        preFetchedFaceEnc?.let { retrySb.append("&currentFaceId=${CacheUtils.cache["faceObjectId"] ?: ""}&ifCFP=0&courseId=${preCheck?.courseId ?: ""}&faceEnc=$it") }
                        preFetchedCaptchaValidate?.let { retrySb.append("&validate=$it") }
                        val retryResp = NetworkUtils.request(NetworkUtils.buildClientRequest(retrySb.toString()))
                        val retryBody = retryResp.data?.body?.string() ?: ""
                        val rResult = SignResult.fromBody(retryBody)
                        if (rResult.isSuccess()) return@withContext rResult
                        if (rResult.validateToken != null) return@withContext handleValidate(rResult.validateToken!!, bdLatLon.latitude.toString(), bdLatLon.longitude.toString(), loc.address.ifEmpty { loc.name })
                        if (rResult.isExpired) break
                    }
                }
            } catch (e: Exception) {
                DebugLogCollector.w(TAG, "pre-release异常: ${e.message}")
            }
        }

        // ===== 第2步: GET+enc方式 =====
        if (hasEnc) {
            try {
                val getResp = NetworkUtils.getSign(aid = aid, uid = uid, fid = fid, name = name,
                    enc = enc, latitude = lat, longitude = lon, address = addr,
                    objectId = preUploadedObjectId)
                val body = getResp.data?.body?.string() ?: ""
                DebugLogCollector.d(TAG, "GET+enc签到: $body")
                val result = SignResult.fromBody(body)
                if (result.isSuccess()) return@withContext result
                if (result.captchaToken != null) {
                    if (preFetchedCaptchaValidate != null) {
                        DebugLogCollector.d(TAG, "验证码已预获取, 重签")
                    } else {
                        return@withContext SignResult(false, "需滑块验证码", captchaToken = result.captchaToken, needCaptcha = true)
                    }
                }
                if (result.validateToken != null) return@withContext handleValidate(result.validateToken!!, lat, lon, addr)
                if (result.faceToken != null) return@withContext handleCheckFace(result.faceToken!!, lat, lon, addr)
                if (result.isExpired) return@withContext result
            } catch (e: Exception) {
                DebugLogCollector.w(TAG, "GET+enc异常: ${e.message}")
            }
        }

        // ===== 第3步: POST兜底 =====
        try {
            val pResp = NetworkUtils.postSign(aid = aid, uid = uid, fid = fid, name = name,
                latitude = lat, longitude = lon, address = addr,
                objectId = preUploadedObjectId)
            val body = pResp.data?.body?.string() ?: ""
            DebugLogCollector.d(TAG, "POST兜底: $body")
            return@withContext handleSignResponse(body, lat, lon, addr)
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "签到全部失败", e)
            return@withContext SignResult(false, "签到异常: ${e.message}")
        }
    }
}

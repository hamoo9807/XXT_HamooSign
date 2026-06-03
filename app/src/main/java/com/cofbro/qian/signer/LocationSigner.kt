package com.cofbro.qian.signer

import android.content.Context
import com.cofbro.qian.utils.CacheUtils
import com.cofbro.qian.utils.DebugLogCollector
import com.cofbro.qian.utils.NativeLocationUtils
import com.cofbro.qian.utils.NetworkUtils
import com.cofbro.qian.utils.PresetLocationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 定位签到
 * 先获取定位 → 失败则遍历预设地址
 */
class LocationSigner(
    override val aid: String,
    override val uid: String = CacheUtils.cache["uid"] ?: "",
    override val fid: String = CacheUtils.cache["fid"] ?: "",
    override val name: String = CacheUtils.cache["username"] ?: "",
    val context: Context,
    override val classId: String = "",
    override val courseId: String = "",
    override val extContent: String = ""
) : BaseSigner(aid, uid, fid, name, classId, courseId, extContent) {

    companion object {
        private const val TAG = "LocationSigner"
    }

    override suspend fun checkAlreadySigned(preSignBody: String): Boolean = false

    override suspend fun sign(): SignResult = withContext(Dispatchers.IO) {
        DebugLogCollector.d(TAG, "===== LocationSigner.sign() =====")

        var result = tryLocationWithNative()
        if (result.isSuccess()) return@withContext result

        // 失败后遍历预设地址
        val presets = PresetLocationManager.loadAll(context)
        for ((i, loc) in presets.withIndex()) {
            val bdLatLon = NativeLocationUtils.wgs84ToBd09(loc.longitude, loc.latitude)
            val locAddr = loc.address.ifEmpty { loc.name }
            DebugLogCollector.d(TAG, "尝试预设地址${i + 1}: ${loc.name}")
            result = doSign(bdLatLon.latitude.toString(), bdLatLon.longitude.toString(), locAddr)
            if (result.isSuccess() || result.captchaToken != null || result.validateToken != null) {
                if (result.captchaToken != null) return@withContext SignResult(false, "需滑块验证码", captchaToken = result.captchaToken, needCaptcha = true)
                if (result.validateToken != null) return@withContext handleValidate(result.validateToken!!, bdLatLon.latitude.toString(), bdLatLon.longitude.toString(), locAddr)
                return@withContext result
            }
        }

        DebugLogCollector.w(TAG, "所有位置均失败")
        return@withContext result
    }

    private suspend fun tryLocationWithNative(): SignResult {
        var nativeLat = 0.0; var nativeLon = 0.0; var nativeAddr = ""
        try {
            NativeLocationUtils.getCurrentLocation(
                context,
                onSuccess = { l, o, a -> nativeLat = l; nativeLon = o; nativeAddr = a },
                onError = { DebugLogCollector.w(TAG, "定位失败: $it") }
            )
        } catch (_: Exception) {}

        return if (nativeLat != 0.0 && nativeLon != 0.0) {
            doSign(nativeLat.toString(), nativeLon.toString(), nativeAddr)
        } else {
            SignResult(false, "定位失败")
        }
    }

    private suspend fun doSign(lat: String, lon: String, address: String): SignResult {
        // 先POST
        val pResp = NetworkUtils.postSign(aid = aid, uid = uid, fid = fid, name = name,
            latitude = lat, longitude = lon, address = address)
        val pBody = pResp.data?.body?.string() ?: ""
        val pResult = SignResult.fromBody(pBody)
        if (pResult.isSuccess() || pResult.captchaToken != null || pResult.validateToken != null || pResult.faceToken != null)
            return pResult

        // POST失败, 尝试GET
        val gResp = NetworkUtils.getSign(aid = aid, uid = uid, fid = fid, name = name,
            latitude = lat, longitude = lon, address = address)
        val gBody = gResp.data?.body?.string() ?: ""
        val gResult = SignResult.fromBody(gBody)
        if (gResult.isSuccess() || gResult.captchaToken != null || gResult.validateToken != null || gResult.faceToken != null)
            return gResult

        // 都失败, 返回更详细的
        return if (pBody.contains("errorLocation")) pResult else gResult
    }
}

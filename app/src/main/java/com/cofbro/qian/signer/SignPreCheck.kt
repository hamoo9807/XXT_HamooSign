package com.cofbro.qian.signer

import com.cofbro.qian.utils.CacheUtils
import com.cofbro.qian.utils.DebugLogCollector
import com.cofbro.qian.utils.NetworkUtils
import com.cofbro.qian.utils.safeParseToJson
import com.alibaba.fastjson.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 签到预检查结果
 * 在签到前查询 ifNeedVCode / openCheckFaceFlag / ifPhoto
 * 提前处理好所有验证, 避免enc被消耗后无法补救
 */
data class SignPreCheck(
    val signType: String = "",       // otherId: 0=普通 2=二维码 3=手势 4=定位 5=签到码
    val needVCode: Boolean = false,  // ifNeedVCode
    val needFace: Boolean = false,   // openCheckFaceFlag
    val needPhoto: Boolean = false,  // ifPhoto
    val clazzId: String = "",
    val courseId: String = "",
    val otherId: String = "",
    val rawData: JSONObject? = null
) {
    companion object {
        private const val TAG = "SignPreCheck"

        /**
         * 从activeId查询签到详情, 解析预检查信息
         */
        suspend fun fetch(aid: String): SignPreCheck? = withContext(Dispatchers.IO) {
            try {
                val url = "https://mobilelearn.chaoxing.com/v2/apis/active/getPPTActiveInfo?activeId=$aid&duid=&denc="
                val resp = NetworkUtils.request(NetworkUtils.buildClientRequest(url))
                val body = resp.data?.body?.string() ?: return@withContext null
                val json = body.safeParseToJson() ?: return@withContext null
                val data = json.getJSONObject("data") ?: return@withContext null

                val preCheck = SignPreCheck(
                    signType = data.getString("otherId") ?: "",
                    needVCode = data.getIntValue("ifNeedVCode") == 1,
                    needFace = data.getIntValue("openCheckFaceFlag") == 1,
                    needPhoto = data.getIntValue("ifPhoto") == 1,
                    clazzId = data.getString("clazzId") ?: "",
                    courseId = data.getString("courseId") ?: "",
                    otherId = data.getString("otherId") ?: "",
                    rawData = data
                )

                DebugLogCollector.d(TAG, "预检查: type=${preCheck.signType} vcode=${preCheck.needVCode} face=${preCheck.needFace} photo=${preCheck.needPhoto} clazzId=${preCheck.clazzId} courseId=${preCheck.courseId}")
                return@withContext preCheck
            } catch (e: Exception) {
                DebugLogCollector.e(TAG, "预检查异常", e)
                null
            }
        }

        /**
         * 从signType响应解析(兼容旧方式, 但信息比fetch少)
         */
        suspend fun fromSignType(aid: String): SignPreCheck? = withContext(Dispatchers.IO) {
            try {
                val url = "https://mobilelearn.chaoxing.com/newsign/signDetail?activePrimaryId=$aid&type=1"
                val resp = NetworkUtils.request(NetworkUtils.buildClientRequest(url))
                val body = resp.data?.body?.string() ?: return@withContext null
                val json = body.safeParseToJson() ?: return@withContext null

                val preCheck = SignPreCheck(
                    signType = json.getString("otherId") ?: "",
                    clazzId = json.getString("clazzId") ?: "",
                    courseId = json.getString("courseId") ?: "",
                    otherId = json.getString("otherId") ?: "",
                    needPhoto = json.getString("ifPhoto") == "1",
                    needVCode = json.getIntValue("ifNeedVCode") == 1,
                    needFace = json.getIntValue("openCheckFaceFlag") == 1
                )
                DebugLogCollector.d(TAG, "fromSignType: type=${preCheck.signType} vcode=${preCheck.needVCode} face=${preCheck.needFace} photo=${preCheck.needPhoto}")
                return@withContext preCheck
            } catch (e: Exception) {
                DebugLogCollector.e(TAG, "fromSignType异常", e)
                null
            }
        }
    }
}

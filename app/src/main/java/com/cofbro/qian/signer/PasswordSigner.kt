package com.cofbro.qian.signer

import com.alibaba.fastjson.JSONObject
import com.cofbro.qian.utils.CacheUtils
import com.cofbro.qian.utils.DebugLogCollector
import com.cofbro.qian.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.URLEncoder

/**
 * 签到码签到(类型5) (CSF对齐版)
 * 修复: 使用正确的 pptSign/stuSignajax 端点, 包含完整参数, 添加延迟防止速率限制
 */
class PasswordSigner(
    override val aid: String,
    override val uid: String = CacheUtils.cache["uid"] ?: "",
    override val fid: String = CacheUtils.cache["fid"] ?: "",
    override val name: String = CacheUtils.cache["username"] ?: "",
    val signId: String = aid,
    override val classId: String = "",
    override val courseId: String = "",
    override val extContent: String = ""
) : BaseSigner(aid, uid, fid, name, classId, courseId, extContent) {

    companion object {
        private const val TAG = "PasswordSigner"
    }

    override suspend fun checkAlreadySigned(preSignBody: String): Boolean = false

    override suspend fun sign(): SignResult = withContext(Dispatchers.IO) {
        DebugLogCollector.d(TAG, "===== PasswordSigner.sign() =====")

        val pResp = NetworkUtils.postSign(aid = aid, uid = uid, fid = fid, name = name,
            latitude = "-1", longitude = "-1")
        val pBody = pResp.data?.body?.string() ?: ""
        val pResult = SignResult.fromBody(pBody)
        if (pResult.isSuccess()) return@withContext pResult
        if (pResult.validateToken != null) return@withContext handleValidate(pResult.validateToken!!)
        if (pResult.faceToken != null) return@withContext handleCheckFace(pResult.faceToken!!)
        if (pResult.isAlreadySigned) return@withContext pResult

        return@withContext pResult
    }

    /**
     * 用签到码签到 (CSF对齐: pptSign/stuSignajax + 完整参数)
     */
    suspend fun signWithCode(signCode: String): SignResult = withContext(Dispatchers.IO) {
        DebugLogCollector.d(TAG, "签到码签到: code=$signCode")

        try {
            val checkUrl = "https://mobilelearn.chaoxing.com/widget/sign/pcStuSignController/checkSignCode?activeId=$aid&signCode=$signCode"
            val checkResp = NetworkUtils.request(NetworkUtils.buildClientRequest(checkUrl))
            val checkBody = checkResp.data?.body?.string() ?: ""
            val checkJson = try { JSONObject.parseObject(checkBody) } catch (e: Exception) { null }
            val isCodeValid = checkJson?.getInteger("result") == 1
                    || checkBody.contains("true") || checkBody.contains("success")
            if (!isCodeValid) {
                return@withContext SignResult(false, "签到码错误", checkBody)
            }
        } catch (e: Exception) {
            return@withContext SignResult(false, "验证签到码异常: ${e.message}")
        }

        try {
            val encodedName = try { URLEncoder.encode(name, "UTF-8") } catch (e: Exception) { name }
            val signUrl = StringBuilder("https://mobilelearn.chaoxing.com/pptSign/stuSignajax?")
                .append("&clientip=&appType=15&ifTiJiao=1&vpProbability=-1&vpStrategy=")
                .append("&latitude=-1&longitude=-1")
                .append("&activeId=$signId&uid=$uid&name=$encodedName&fid=$fid")
                .append("&signCode=$signCode")
                .append("&deviceCode=${NetworkUtils.generateDeviceCode()}")
                .toString()
            val resp = NetworkUtils.request(NetworkUtils.buildClientRequest(signUrl))
            val body = resp.data?.body?.string() ?: ""
            return@withContext handleSignResponse(body)
        } catch (e: Exception) {
            return@withContext SignResult(false, "签到码签到异常: ${e.message}")
        }
    }

    /**
     * 暴力破解数字签到码 (带延迟防止速率限制)
     */
    suspend fun bruteForce(onProgress: (Int, Int) -> Unit = { _, _ -> }): SignResult? =
        withContext(Dispatchers.IO) {
            var totalTried = 0
            for (len in 4..6) {
                val max = Math.pow(10.0, len.toDouble()).toInt()
                for (i in 0 until max) {
                    val code = i.toString().padStart(len, '0')
                    totalTried++
                    if (totalTried % 200 == 0) onProgress(totalTried, max * 3)

                    // 每50次尝试延迟一下防止触发速率限制
                    if (totalTried % 50 == 0) delay(200)

                    try {
                        val checkUrl = "https://mobilelearn.chaoxing.com/widget/sign/pcStuSignController/checkSignCode?activeId=$aid&signCode=$code"
                        val checkResp = NetworkUtils.request(NetworkUtils.buildClientRequest(checkUrl))
                        val checkBody = checkResp.data?.body?.string() ?: ""
                        val checkJson = try { JSONObject.parseObject(checkBody) } catch (e: Exception) { null }
                        if (checkJson?.getInteger("result") == 1
                            || checkBody.contains("true") || checkBody.contains("success")) {
                            DebugLogCollector.d(TAG, "暴力破解成功! code=$code (第${totalTried}次)")
                            return@withContext signWithCode(code)
                        }
                    } catch (_: Exception) {}
                }
            }
            return@withContext null
        }
}

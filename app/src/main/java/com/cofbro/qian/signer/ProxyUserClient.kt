package com.cofbro.qian.signer

import android.content.Context
import android.util.Base64
import com.alibaba.fastjson.JSONObject
import com.cofbro.qian.utils.DebugLogCollector
import okhttp3.*
import java.util.concurrent.TimeUnit
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * 代签用户独立HTTP客户端
 * 直接使用原始cookie字符串注入header (对齐工作项目方案, 简单可靠)
 */
class ProxyUserClient(
    val phone: String,
    val password: String,
    val initialCookies: String,
    val uid: String,
    val fid: String,
    var name: String = phone
) {
    data class ProxySignState(
        var isSuccess: Boolean? = null,
        var error: String = "",
        var isLoading: Boolean = false
    )

    val state = ProxySignState()

    /** 当前有效cookie (重登后会更新) */
    var currentCookies: String = initialCookies
        private set

    var isSessionObsolete: Boolean = false
    var lastError: String = ""

    fun markObsolete(reason: String) {
        isSessionObsolete = true
        lastError = reason
        DebugLogCollector.w(TAG, "会话废弃: $phone, 原因: $reason")
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(8, TimeUnit.SECONDS)
        .readTimeout(8, TimeUnit.SECONDS)
        .writeTimeout(8, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148")
                .header("cookie", currentCookies)
                .build()
            val response = chain.proceed(request)

            // 检测过期: 302重定向或跳转到passport
            val isRedirect = response.code == 302 || response.priorResponse?.code == 302
            val location = response.header("Location") ?: ""
            val isPassportRedirect = location.contains("passport") || location.contains("login")
            if (isRedirect || isPassportRedirect) {
                if (password.isNotEmpty()) {
                    DebugLogCollector.d(TAG, "JSESSIONID过期, 尝试重登: $phone")
                    if (reLogin()) {
                        response.close()
                        return@addInterceptor chain.proceed(original)
                    } else {
                        markObsolete("重登失败")
                    }
                } else {
                    markObsolete("Cookie过期且无密码无法重登")
                }
            }
            response
        }
        .build()

    // ===== 公开方法 =====

    fun tryReLogin(): Boolean {
        if (password.isEmpty()) return false
        return reLogin()
    }

    /**
     * 获取真实姓名 (CSF对齐: POST userLogin4Uname.do)
     * CSF用加密设备信息POST, 失败则fallback GET
     */
    fun fetchRealName(context: android.content.Context? = null): String? {
        if (currentCookies.isEmpty()) return null
        return try {
            val simpleClient = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS)
                .followRedirects(false).build()

            // CSF方式: POST加密设备信息
            var body: String? = null
            if (context != null) {
                try {
                    val encryptedInfo = com.cofbro.qian.utils.DeviceInfoHelper.buildEncryptedDeviceInfo(context)
                    val postRequest = Request.Builder()
                        .url("https://sso.chaoxing.com/apis/login/userLogin4Uname.do")
                        .addHeader("cookie", currentCookies)
                        .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X)")
                        .post(FormBody.Builder().add("data", encryptedInfo).build())
                        .build()
                    body = simpleClient.newCall(postRequest).execute().use { it.body?.string() ?: "" }
                } catch (_: Exception) {}
            }

            // fallback: GET
            if (body.isNullOrEmpty()) {
                val getRequest = Request.Builder().url("https://sso.chaoxing.com/apis/login/userLogin4Uname.do")
                    .addHeader("cookie", currentCookies)
                    .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X)")
                    .get().build()
                body = simpleClient.newCall(getRequest).execute().use { it.body?.string() ?: "" }
            }

            val json = JSONObject.parseObject(body ?: "")
            val name = json.getJSONObject("msg")?.getString("name")?.takeIf { it.isNotEmpty() }
            if (!name.isNullOrEmpty()) DebugLogCollector.d(TAG, "真实姓名: $name")
            name
        } catch (e: Exception) {
            DebugLogCollector.w(TAG, "获取真实姓名失败: ${e.message}")
            null
        }
    }

    fun getDisplayName(): String {
        if (name.isNotEmpty() && !name.matches(Regex("\\d{11}"))) return name
        if (phone.length == 11) return "${phone.take(3)}****${phone.takeLast(2)}"
        return name.ifEmpty { phone }
    }

    fun execute(request: Request): okhttp3.Response {
        val newRequest = request.newBuilder()
            .header("cookie", currentCookies)
            .build()
        return client.newCall(newRequest).execute()
    }

    fun executeSign(aid: String, signUrl: String? = null): String {
        val url = signUrl ?: "https://mobilelearn.chaoxing.com/pptSign/stuSignajax?activeId=$aid&uid=$uid"
        val request = Request.Builder().url(url)
            .addHeader("Accept-Language", "zh-Hans-CN;q=1, zh-Hant-CN;q=0.9")
            .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X)")
            .addHeader("cookie", currentCookies)
            .build()
        DebugLogCollector.d(TAG, "$name executeSign: cookieLen=${currentCookies.length}")
        return client.newCall(request).execute().use { it.body?.string() ?: "" }
    }

    // ===== 私有方法 =====

    private fun reLogin(): Boolean {
        return try {
            val loginClient = OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .build()

            val uname = encryptAES(phone)
            val encryptedPwd = encryptAES(password)

            val formBody = FormBody.Builder()
                .addEncoded("fid", "-1")
                .addEncoded("uname", uname.replace("+", "%2B"))
                .addEncoded("password", encryptedPwd.replace("+", "%2B"))
                .addEncoded("refer", "https%3A%2F%2Fi.chaoxing.com")
                .addEncoded("t", "true")
                .addEncoded("forbidotherlogin", "0")
                .addEncoded("validate", "")
                .addEncoded("doubleFactorLogin", "0")
                .addEncoded("independentId", "0")
                .addEncoded("independentNameId", "0")
                .build()

            val request = Request.Builder()
                .url("https://passport2.chaoxing.com/fanyalogin")
                .post(formBody)
                .build()

            val response = loginClient.newCall(request).execute()
            val body = response.body?.string() ?: ""
            val json = JSONObject.parseObject(body)

            if (json.getBoolean("status") == true) {
                // 从Set-Cookie headers提取cookie字符串
                val newCookies = response.headers("Set-Cookie").joinToString(";") { header ->
                    header.split(";").first().trim()  // 取 name=value 部分, 去掉 Path/Domain 等属性
                }
                if (newCookies.isNotEmpty()) {
                    currentCookies = newCookies
                    DebugLogCollector.d(TAG, "重登成功: $phone, cookieLen=${newCookies.length}")
                    return true
                }
            }
            DebugLogCollector.w(TAG, "重登失败: ${json.getString("msg2")}")
            false
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "重登异常", e)
            false
        }
    }

    companion object {
        private const val TAG = "ProxyUserClient"
        private const val TRANSFER_KEY = "u2oh6Vu^HWe4_AES"

        private fun encryptAES(message: String): String {
            return try {
                val key = TRANSFER_KEY.toByteArray(Charsets.UTF_8)
                val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
                cipher.init(Cipher.ENCRYPT_MODE,
                    SecretKeySpec(key, "AES"),
                    IvParameterSpec(key))
                Base64.encodeToString(cipher.doFinal(message.toByteArray(Charsets.UTF_8)), Base64.NO_WRAP)
            } catch (e: Exception) { message }
        }
    }
}

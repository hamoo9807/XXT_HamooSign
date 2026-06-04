package com.cofbro.qian.utils

import android.content.Context
import com.cofbro.hymvvmutils.base.getBySp
import com.cofbro.hymvvmutils.base.saveUsedSp
import com.cofbro.qian.data.URL
import okhttp3.Cookie
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Request
import okhttp3.Response

object CookieRefresher {
    private const val KEY_LOGIN_TIME = "login_timestamp"
    private const val COOKIE_VALID_DURATION = 5 * 24 * 60 * 60 * 1000L

    @Volatile
    private var refreshing = false

    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun isCookieExpired(): Boolean {
        val ctx = appContext ?: return false
        val loginTime = ctx.getBySp(KEY_LOGIN_TIME)?.toLongOrNull() ?: 0L
        return System.currentTimeMillis() - loginTime > COOKIE_VALID_DURATION
    }

    fun isResponseCookieExpired(response: Response): Boolean {
        // Check for redirect to login page
        if (response.code == 302) {
            val location = response.header("Location") ?: ""
            if (location.contains("passport") || location.contains("login")) return true
        }
        // Check for unauthorized HTTP status codes
        if (response.code == 401 || response.code == 403) return true
        // Check response body for not-logged-in indicator
        try {
            val body = response.peekBody(1024L).string()
            if (body.contains("\"status\":false") && body.contains("未登录")) return true
        } catch (_: Exception) {
            // Body may not be peekable (e.g. streaming responses)
        }
        return false
    }

    @Synchronized
    fun refreshIfNeeded(context: Context): Boolean {
        if (refreshing) return false
        refreshing = true
        try {
            val ctx = context.applicationContext
            val username = ctx.getBySp("username") ?: return false
            // ★ AES解密 (兼容旧版明文密码)
            val rawPassword = ctx.getBySp("password") ?: return false
            val password = AccountManager.decryptPassword(rawPassword)
            if (username.isEmpty() || password.isEmpty()) return false

            val request = NetworkUtils.buildServerRequest(URL.getLoginPath(username, password))
            val response = NetworkUtils.request(request)
            val data = response.data ?: return false
            val body = data.body?.string() ?: return false
            val json = body.safeParseToJson() ?: return false

            if (json.getBoolean("status") == true) {
                val headers = data.headers
                val list = headers.values("Set-Cookie")
                val cookies = StringBuilder()
                var uid: String? = null
                var fid: String? = null
                if (list.isNotEmpty()) {
                    val loginUrl = "https://passport2-api.chaoxing.com".toHttpUrlOrNull()
                    if (loginUrl != null) {
                        for (header in list) {
                            val cookie = Cookie.parse(loginUrl, header) ?: continue
                            val name = cookie.name
                            val value = cookie.value
                            cookies.append(name).append("=").append(value).append(";")
                            if (name == "UID") uid = value
                            if (name == "fid") fid = value
                        }
                    }
                }
                if (cookies.isNotEmpty()) {
                    CacheUtils.persistCookies(
                        cookies.toString(),
                        uid ?: CacheUtils.cache[Constants.Login.UID] ?: "",
                        fid ?: CacheUtils.cache[Constants.Login.FID] ?: ""
                    )
                    ctx.saveUsedSp(KEY_LOGIN_TIME, System.currentTimeMillis().toString())
                    return true
                }
            }
            return false
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            refreshing = false
        }
    }

    fun recordLoginTime() {
        val ctx = appContext ?: return
        ctx.saveUsedSp(KEY_LOGIN_TIME, System.currentTimeMillis().toString())
    }
}

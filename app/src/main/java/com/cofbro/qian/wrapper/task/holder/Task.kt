package com.cofbro.qian.wrapper.task.holder

import android.util.Log
import com.alibaba.fastjson.JSONObject
import com.cofbro.qian.data.URL
import com.cofbro.qian.utils.AccountManager
import com.cofbro.qian.utils.Constants
import com.cofbro.qian.utils.NetworkUtils
import com.cofbro.qian.utils.getStringExt
import okhttp3.Response
import java.util.concurrent.Callable

class Task(private val user: JSONObject) : Callable<Result?> {
    private companion object {
        const val TAG = "ProxyTask"
    }

    var location = ""
    var qrCodeId = ""
    var aid = ""
    var courseId = ""
    var classId = ""
    var code = ""
    var preSignUrl = ""
    private var cookies = ""

    override fun call(): Result? {
        return try {
            if (!tryLoginIfNecessary()) {
                Log.w(TAG, "Login failed for user, skipping sign")
                return Result("", cookies, user.getStringExt(Constants.Account.REMARK))
            }
            sign()
        } catch (e: Exception) {
            Log.e(TAG, "Proxy sign failed", e)
            null
        }
    }

    private fun tryLoginIfNecessary(): Boolean {
        val username = user.getStringExt(Constants.Account.USERNAME)
        val password = AccountManager.decryptPassword(user.getStringExt(Constants.Account.PASSWORD))
        cookies = if (username.isNotEmpty() && password.isNotEmpty()) {
            try {
                val response = requestWithServer(URL.getLoginPath(username, password))
                val body = response?.body?.string() ?: ""
                if (body.contains("\"result\":true") || body.contains("\"result\":1")) {
                    val headers = response?.headers
                    headers?.values("Set-Cookie").toString()
                } else {
                    Log.w(TAG, "Login failed for: $username, response: $body")
                    return false
                }
            } catch (e: Exception) {
                Log.e(TAG, "Login exception for: $username", e)
                return false
            }
        } else {
            user.getStringExt(Constants.Account.COOKIE)
        }
        return cookies.isNotEmpty()
    }

    private fun sign(): Result? {
        val response = analysis(URL.getAnalysisPath(aid))
        if (response?.code != 200) {
            Log.w(TAG, "Analysis failed, code: ${response?.code}")
            return null
        }
        val data = response.body?.string()
        analysis2(data)
        try { Thread.sleep(100) } catch (_: InterruptedException) {}
        preSign()
        checkSign()
        val signBody = signReally()?.body?.string()
        return Result(signBody, cookies, user.getStringExt(Constants.Account.REMARK))
    }

    private fun analysis(url: String): Response? {
        val request = NetworkUtils.buildClientRequest(url, cookies)
        return NetworkUtils.request(request).data
    }

    private fun analysis2(data: String?) {
        val analysis2Code = data?.substringAfter("code='+'")?.substringBefore("'") ?: ""
        if (analysis2Code.isNotEmpty()) {
            request(URL.getAnalysis2Path(analysis2Code))
        }
    }

    private fun preSign() {
        try {
            val uid = findUID(cookies)
            if (preSignUrl.contains("uid=")) {
                val signWithPreSign = preSignUrl.substringBefore("uid=") + "uid=$uid"
                request(signWithPreSign)
            } else {
                request(preSignUrl)
            }
        } catch (e: Exception) {
            Log.w(TAG, "preSign error", e)
        }
    }

    private fun checkSign() {
        try {
            request(URL.checkSignCodePath(aid, code))
        } catch (e: Exception) {
            Log.w(TAG, "checkSign error", e)
        }
    }

    private fun signReally(): Response? {
        return if (qrCodeId.isNotEmpty()) {
            request(URL.getSignWithCameraPath(qrCodeId, location) + "&uid=${findUID(cookies)}")
        } else {
            request(URL.getNormalSignPath(courseId, classId, aid, code))
        }
    }

    private fun request(url: String): Response? {
        val request = NetworkUtils.buildClientRequest(url, cookies)
        return NetworkUtils.request(request).data
    }

    private fun requestWithServer(url: String): Response? {
        val request = NetworkUtils.buildServerRequest(url)
        return NetworkUtils.request(request).data
    }

    private fun findUID(cookies: String): String {
        val uid = cookies.substringAfter("UID=")
        return uid.substringBefore(";")
    }
}

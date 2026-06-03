package com.cofbro.qian.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences


object CacheUtils {
    val cache = hashMapOf<String, String>()
    val activities = hashMapOf<String, Activity>()

    private const val PREF_NAME = "cookie_persistence"
    private const val KEY_COOKIES = "cookies"
    private const val KEY_UID = "uid"
    private const val KEY_FID = "fid"
    private const val KEY_USERNAME = "username"

    private var sp: SharedPreferences? = null

    fun init(context: Context) {
        sp = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        restoreFromSp()
    }

    // ==================== Per-UID cookie helpers ====================

    /**
     * Get cookies for a specific user UID.
     * Falls back to the global "cookies" key for backward compatibility.
     */
    fun getCookies(uid: String): String {
        if (uid.isEmpty()) return cache[Constants.Login.COOKIES] ?: ""
        return cache["${Constants.Login.COOKIES}_$uid"] ?: cache[Constants.Login.COOKIES] ?: ""
    }

    /**
     * Set cookies for a specific user UID.
     * Also updates the current-active-user "cookies" key for backward compatibility.
     */
    fun setCookies(uid: String, value: String) {
        if (uid.isNotEmpty()) {
            cache["${Constants.Login.COOKIES}_$uid"] = value
        }
        // Also keep the global key for callers that don't have uid handy
        cache[Constants.Login.COOKIES] = value
    }

    /**
     * Get cookies for the currently active user (backward-compat shorthand).
     */
    fun getCurrentCookies(): String = cache[Constants.Login.COOKIES] ?: ""

    // ==================== Persist / restore ====================

    private fun restoreFromSp() {
        val prefs = sp ?: return
        // Restore per-UID cookies (keys like "cookies_191970813")
        val allEntries = prefs.all
        for ((key, value) in allEntries) {
            if (key is String && key.startsWith("${KEY_COOKIES}_") && value is String && value.isNotEmpty()) {
                cache[key] = value
            }
        }
        // Fallback: restore global cookies (from older app versions)
        if (cache[Constants.Login.COOKIES].isNullOrEmpty()) {
            prefs.getString(KEY_COOKIES, null)?.let {
                if (it.isNotEmpty()) cache[Constants.Login.COOKIES] = it
            }
        }
        prefs.getString(KEY_UID, null)?.let {
            if (it.isNotEmpty()) cache[Constants.Login.UID] = it
        }
        prefs.getString(KEY_FID, null)?.let {
            if (it.isNotEmpty()) cache[Constants.Login.FID] = it
        }
        prefs.getString(KEY_USERNAME, null)?.let {
            if (it.isNotEmpty()) cache[Constants.USER.USERNAME] = it
        }
    }

    fun persistCookie(key: String, value: String) {
        cache[key] = value
        val prefs = sp ?: return
        val spKey = when (key) {
            Constants.Login.COOKIES -> KEY_COOKIES
            Constants.Login.UID -> KEY_UID
            Constants.Login.FID -> KEY_FID
            Constants.USER.USERNAME -> KEY_USERNAME
            else -> return
        }
        prefs.edit().putString(spKey, value).apply()
    }

    fun persistCookies(cookies: String, uid: String, fid: String) {
        // Store with per-UID key
        setCookies(uid, cookies)
        cache[Constants.Login.UID] = uid
        cache[Constants.Login.FID] = fid
        sp?.edit()?.apply {
            putString("${KEY_COOKIES}_$uid", cookies)
            putString(KEY_UID, uid)
            putString(KEY_FID, fid)
            apply()
        }
    }

    fun clearPersistedCookies() {
        cache.remove(Constants.Login.COOKIES)
        cache.remove(Constants.Login.UID)
        cache.remove(Constants.Login.FID)
        // Also clear per-UID cookie entries
        val keysToRemove = cache.keys.filter { it.startsWith("${Constants.Login.COOKIES}_") }
        keysToRemove.forEach { cache.remove(it) }
        sp?.edit()?.clear()?.apply()
    }

    fun getExportCookieJson(): String {
        val cookies = cache[Constants.Login.COOKIES] ?: ""
        val uid = cache[Constants.Login.UID] ?: ""
        val fid = cache[Constants.Login.FID] ?: ""
        val username = cache[Constants.USER.USERNAME] ?: ""
        val phone = cache["phone"] ?: ""
        val password = cache["password"] ?: ""
        val name = cache["realName"] ?: ""  // 真实姓名(如有)
        return """{"cookies":"$cookies","uid":"$uid","fid":"$fid","username":"$username","phone":"$phone","password":"$password","name":"$name"}"""
    }

    fun setRealName(name: String) {
        cache["realName"] = name
    }
}

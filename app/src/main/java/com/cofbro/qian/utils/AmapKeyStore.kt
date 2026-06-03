package com.cofbro.qian.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * 高德API Key管理
 * - 无内置Key, 用户必须自行配置
 * - 前往 lbs.amap.com 注册获取Key后在"配置→高德Key"中输入
 */
object AmapKeyStore {
    private const val PREFS_NAME = "amap_config"
    private const val KEY_USER_API_KEY = "user_amap_api_key"

    private var cachedKey: String? = null
    private var prefs: SharedPreferences? = null

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * 获取高德API Key (仅返回用户设置的Key, 无内置Key)
     */
    fun getApiKey(): String {
        cachedKey?.let { return it }
        val userKey = prefs?.getString(KEY_USER_API_KEY, null) ?: ""
        cachedKey = userKey
        return userKey
    }

    fun setApiKey(context: Context, key: String) {
        prefs?.edit()?.putString(KEY_USER_API_KEY, key)?.apply()
        cachedKey = key
    }

    fun clearApiKey(context: Context) {
        prefs?.edit()?.remove(KEY_USER_API_KEY)?.apply()
        cachedKey = null
    }

    fun isUsingUserKey(): Boolean {
        val userKey = prefs?.getString(KEY_USER_API_KEY, null)
        return !userKey.isNullOrEmpty()
    }
}

package com.cofbro.qian.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * 签到类型筛选管理器
 * 用户可以选择自动处理哪些签到类型, 跳过哪些
 *
 * 场景: 老师发起"反向签到"（普通签到=缺勤标记）
 * → 用户关闭"普通签到"的自动处理, 就不会被自动签到坑
 */
object SignFilterManager {
    private const val PREFS_NAME = "sign_filter_prefs"
    private const val KEY_PREFIX = "auto_sign_type_"

    /** 签到类型标签(显示用) */
    val SIGN_TYPE_LABELS = mapOf(
        Constants.SIGN.NORMAl to "普通签到（含拍照）",
        Constants.SIGN.SCAN_QR to "二维码签到",
        Constants.SIGN.LOCATION to "定位签到",
        Constants.SIGN.GESTURE to "手势签到",
        Constants.SIGN.SIGN_CODE to "签到码签到"
    )

    /** 签到类型图标 */
    val SIGN_TYPE_ICONS = mapOf(
        Constants.SIGN.NORMAl to "📋",
        Constants.SIGN.SCAN_QR to "🔍",
        Constants.SIGN.LOCATION to "📍",
        Constants.SIGN.GESTURE to "✋",
        Constants.SIGN.SIGN_CODE to "🔑"
    )

    private var prefs: SharedPreferences? = null

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * 是否自动处理该签到类型
     * 默认全部开启
     */
    fun isAutoSignEnabled(signType: String): Boolean {
        // 默认所有类型都开启
        return prefs?.getBoolean(KEY_PREFIX + signType, true) ?: true
    }

    /**
     * 设置签到类型的自动处理开关
     */
    fun setAutoSignEnabled(signType: String, enabled: Boolean) {
        prefs?.edit()?.putBoolean(KEY_PREFIX + signType, enabled)?.apply()
    }

    /**
     * 获取所有签到类型的开关状态
     */
    fun getAllFilterStates(): Map<String, Boolean> {
        val states = mutableMapOf<String, Boolean>()
        for (key in SIGN_TYPE_LABELS.keys) {
            states[key] = isAutoSignEnabled(key)
        }
        return states
    }

    /**
     * 重置为全部开启
     */
    fun resetAll() {
        val editor = prefs?.edit()
        for (key in SIGN_TYPE_LABELS.keys) {
            editor?.putBoolean(KEY_PREFIX + key, true)
        }
        editor?.apply()
    }
}

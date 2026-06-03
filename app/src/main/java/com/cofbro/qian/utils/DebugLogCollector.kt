package com.cofbro.qian.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DebugLogCollector {
    private val logList = mutableListOf<String>()
    private const val MAX_LOG_SIZE = 200
    private var listener: (() -> Unit)? = null

    fun d(tag: String, msg: String) {
        Log.d(tag, msg)
        addLog("D", tag, msg)
    }

    fun e(tag: String, msg: String, throwable: Throwable? = null) {
        Log.e(tag, msg, throwable)
        addLog("E", tag, msg)
        throwable?.message?.let { addLog("E", tag, it) }
    }

    fun w(tag: String, msg: String) {
        Log.w(tag, msg)
        addLog("W", tag, msg)
    }

    private fun addLog(level: String, tag: String, msg: String) {
        val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        val line = "[$time][$level][$tag] $msg"
        synchronized(logList) {
            if (logList.size >= MAX_LOG_SIZE) {
                logList.removeAt(0)
            }
            logList.add(line)
        }
        listener?.invoke()
    }

    fun getLogs(): String {
        synchronized(logList) {
            return logList.joinToString("\n")
        }
    }

    fun clear() {
        synchronized(logList) {
            logList.clear()
        }
        listener?.invoke()
    }

    fun setListener(l: (() -> Unit)?) {
        listener = l
    }
}

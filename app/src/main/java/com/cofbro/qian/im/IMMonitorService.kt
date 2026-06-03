package com.cofbro.qian.im

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.cofbro.qian.R
import com.cofbro.qian.data.URL
import com.cofbro.qian.home.HomeFragment
import com.cofbro.qian.main.MainActivity
import com.cofbro.qian.utils.CacheUtils
import com.cofbro.qian.utils.DebugLogCollector
import com.cofbro.qian.utils.NetworkUtils
import com.cofbro.qian.utils.getStringExt
import com.cofbro.qian.utils.safeParseToJson
import com.hjq.toast.ToastUtils
import com.hyphenate.EMConnectionListener
import com.hyphenate.EMError
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Request
import org.jsoup.Jsoup

/**
 * 学习通IM监听服务
 * 基于环信SDK, 监听签到消息并自动处理
 */
class IMMonitorService : Service() {

    companion object {
        private const val TAG = "IMMonitor"
        private const val QR_DEBUG = "QR_DEBUG"
        private const val CHANNEL_ID = "im_monitor_channel"
        private const val NOTIFICATION_ID = 10001

        var isRunning = false
            private set

        var imEnabled = false
            private set

        fun start(context: Context) {
            val intent = Intent(context, IMMonitorService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        fun stop(context: Context) {
            val intent = Intent(context, IMMonitorService::class.java)
            context.stopService(intent)
        }

        fun setEnabled(context: Context, enabled: Boolean) {
            context.getSharedPreferences("im_settings", Context.MODE_PRIVATE)
                .edit().putBoolean("im_enabled", enabled).apply()
            imEnabled = enabled
            if (enabled) {
                start(context)
            } else {
                stop(context)
            }
        }

        fun isEnabled(context: Context): Boolean {
            return context.getSharedPreferences("im_settings", Context.MODE_PRIVATE)
                .getBoolean("im_enabled", false)
        }
    }

    private var messageListener: EMMessageListener? = null
    private var connectionListener: EMConnectionListener? = null
    private var signHandler: AutoSignHandler? = null

    override fun onCreate() {
        super.onCreate()
        isRunning = true
        imEnabled = isEnabled(this)
        signHandler = AutoSignHandler(this)
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, buildNotification("IM监听已启动"))
        DebugLogCollector.d(TAG, "IM监听服务已创建")

        // SDK已在App中初始化, 添加连接监听
        addConnectionListener()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!imEnabled) {
            stopSelf()
            return START_NOT_STICKY
        }
        // 连接IM
        connectIM()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        // 移除消息监听
        messageListener?.let { EMClient.getInstance().chatManager().removeMessageListener(it) }
        connectionListener?.let { EMClient.getInstance().removeConnectionListener(it) }
        // 登出环信
        EMClient.getInstance().logout(true)
        DebugLogCollector.d(TAG, "IM监听服务已销毁")
    }

    private fun addConnectionListener() {
        connectionListener = object : EMConnectionListener {
            override fun onConnected() {
                DebugLogCollector.d(TAG, "IM连接成功")
                updateNotification("IM已连接, 监听签到中...")
            }

            override fun onDisconnected(errorCode: Int) {
                DebugLogCollector.w(TAG, "IM连接断开, code=$errorCode")
                if (errorCode == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    DebugLogCollector.w(TAG, "账号在其他设备登录")
                    updateNotification("IM连接断开: 账号在其他设备登录")
                } else {
                    updateNotification("IM连接断开, 尝试重连...")
                }
            }
        }
        EMClient.getInstance().addConnectionListener(connectionListener)
    }

    private fun connectIM() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // 1. 获取IM参数
                val imParams = fetchIMParams()
                if (imParams == null) {
                    DebugLogCollector.w(TAG, "获取IM参数失败, 请先登录")
                    updateNotification("IM连接失败: 请先登录")
                    return@launch
                }

                DebugLogCollector.d(TAG, "IM参数: myTuid=${imParams.myTuid}, myName=${imParams.myName}")

                // 2. 登录环信(使用token登录)
                EMClient.getInstance().loginWithToken(imParams.myTuid, imParams.myToken, object : com.hyphenate.EMCallBack {
                    override fun onSuccess() {
                        DebugLogCollector.d(TAG, "环信登录成功, 开始监听消息")
                        updateNotification("IM已连接, 监听签到中...")
                        registerMessageListener()
                    }

                    override fun onError(code: Int, error: String?) {
                        DebugLogCollector.e(TAG, "环信登录失败: code=$code, error=$error")
                        updateNotification("IM登录失败: $error")
                    }

                    override fun onProgress(progress: Int, status: String?) {}
                })

            } catch (e: Exception) {
                DebugLogCollector.e(TAG, "IM连接异常", e)
                updateNotification("IM连接异常: ${e.message}")
            }
        }
    }

    private data class IMParams(val myName: String, val myToken: String, val myTuid: String)

    private fun fetchIMParams(): IMParams? {
        return try {
            val cookies = CacheUtils.getCurrentCookies()
            val request = Request.Builder()
                .url(URL.IM_ME_URL)
                .addHeader("Cookie", cookies)
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X)")
                .build()
            val response = NetworkUtils.request(request)
            val html = response.data?.body?.string() ?: ""
            if (html.isEmpty()) return null

            // 解析HTML中的隐藏字段
            val doc = Jsoup.parse(html)
            val myName = doc.select("#myName").attr("value")
            val myToken = doc.select("#myToken").attr("value")
            val myTuid = doc.select("#myTuid").attr("value")

            if (myToken.isEmpty() || myTuid.isEmpty()) {
                DebugLogCollector.w(TAG, "IM参数为空, Cookie可能已过期")
                return null
            }

            // 缓存IM参数
            CacheUtils.cache["im_myName"] = myName
            CacheUtils.cache["im_myToken"] = myToken
            CacheUtils.cache["im_myTuid"] = myTuid

            IMParams(myName, myToken, myTuid)
        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "获取IM参数异常", e)
            null
        }
    }

    private fun registerMessageListener() {
        messageListener = object : EMMessageListener {
            override fun onMessageReceived(messages: MutableList<EMMessage>?) {
                messages?.forEach { message ->
                    handleMessage(message)
                }
            }

            override fun onCmdMessageReceived(messages: MutableList<EMMessage>?) {}
            override fun onMessageRead(messages: MutableList<EMMessage>?) {}
            override fun onMessageDelivered(messages: MutableList<EMMessage>?) {}
            override fun onMessageRecalled(messages: MutableList<EMMessage>?) {}
            override fun onMessageChanged(message: EMMessage?, any: Any?) {}
        }
        EMClient.getInstance().chatManager().addMessageListener(messageListener)
    }

    private fun handleMessage(message: EMMessage) {
        try {
            // 获取消息的ext属性
            val ext = message.ext()
            if (ext == null || !ext.containsKey("attachment")) return

            val attachmentStr = ext["attachment"] as? String ?: return
            val attachment = JSON.parseObject(attachmentStr)
            val attChatCourse = attachment.getJSONObject("att_chat_course") ?: return

            val url = attChatCourse.getString("url") ?: ""
            if (!url.contains("sign")) return

            // 提取签到信息
            val aid = attChatCourse.getString("aid") ?: return
            val courseInfo = attChatCourse.getJSONObject("courseInfo")
            val classId = courseInfo?.getString("classid") ?: ""
            val courseId = courseInfo?.getString("courseid") ?: ""

            DebugLogCollector.d(TAG, "检测到签到活动: aid=$aid, classId=$classId, courseId=$courseId")
            updateNotification("检测到签到活动, 自动处理中...")

            // 自动签到
            signHandler?.handleAutoSign(aid, classId, courseId)

        } catch (e: Exception) {
            DebugLogCollector.e(TAG, "处理IM消息异常", e)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "IM签到监听",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "监听学习通签到通知"
                setShowBadge(false)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(text: String): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("学习通签到监听")
            .setContentText(text)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
    }

    private fun updateNotification(text: String) {
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID, buildNotification(text))
    }
}

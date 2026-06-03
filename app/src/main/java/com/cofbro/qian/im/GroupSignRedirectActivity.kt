package com.cofbro.qian.im

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cofbro.qian.signer.SignDispatcher
import com.cofbro.qian.utils.CacheUtils
import com.cofbro.qian.utils.Constants
import com.cofbro.qian.utils.DebugLogCollector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 从群聊签到活动跳转的签到处理Activity
 * 使用Signer体系: 预检查 → 预处理 → 一次性签到
 */
class GroupSignRedirectActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "GroupSignRedirect"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activeId = intent.getLongExtra("activeId", 0)
        val classId = intent.getIntExtra("classId", 0)
        val courseId = intent.getIntExtra("courseId", 0)
        val typeName = intent.getStringExtra("typeName") ?: "签到"

        if (activeId == 0L) {
            Toast.makeText(this, "无效的签到活动", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        Toast.makeText(this, "正在签到: $typeName", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val aid = activeId.toString()
                val uid = CacheUtils.cache["uid"] ?: ""
                val fid = CacheUtils.cache["fid"] ?: ""
                val name = CacheUtils.cache["username"] ?: ""

                // 先查签到类型
                val signTypeBody = com.cofbro.qian.utils.NetworkUtils.request(
                    com.cofbro.qian.utils.NetworkUtils.buildClientRequest(
                        "https://mobilelearn.chaoxing.com/newsign/signDetail?activePrimaryId=$aid&type=1"
                    )
                )
                val body = signTypeBody.data?.body?.string() ?: ""
                val json = com.alibaba.fastjson.JSONObject.parseObject(body)
                val signType = json.getString("otherId") ?: "0"

                DebugLogCollector.d(TAG, "签到类型: $signType typeName: $typeName")

                // 用Signer体系签到
                val result = SignDispatcher.dispatch(
                    aid = aid,
                    signType = signType,
                    context = this@GroupSignRedirectActivity
                )

                withContext(Dispatchers.Main) {
                    when {
                        result.isSuccess() -> Toast.makeText(this@GroupSignRedirectActivity, "签到成功！", Toast.LENGTH_LONG).show()
                        result.isAlreadySigned -> Toast.makeText(this@GroupSignRedirectActivity, "已签到过了", Toast.LENGTH_SHORT).show()
                        result.isExpired -> Toast.makeText(this@GroupSignRedirectActivity, "签到已过期", Toast.LENGTH_SHORT).show()
                        result.isEnded -> Toast.makeText(this@GroupSignRedirectActivity, "签到已截止", Toast.LENGTH_SHORT).show()
                        result.validateToken != null -> Toast.makeText(this@GroupSignRedirectActivity, "需拍照验证", Toast.LENGTH_LONG).show()
                        result.faceToken != null -> Toast.makeText(this@GroupSignRedirectActivity, "需人脸验证", Toast.LENGTH_LONG).show()
                        else -> Toast.makeText(this@GroupSignRedirectActivity, "结果: ${result.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                DebugLogCollector.e(TAG, "签到异常", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@GroupSignRedirectActivity, "签到失败: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            delay(1500)
            finish()
        }
    }
}

package com.cofbro.qian.im

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSONObject
import com.cofbro.qian.databinding.ActivityGroupDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupDetailActivity : AppCompatActivity() {
    private var binding: ActivityGroupDetailBinding? = null
    private var configJson: String? = null
    private var groupInfo: IMGroupInfo? = null

    companion object {
        private const val EXTRA_CONFIG = "config_json"
        private const val EXTRA_GROUP = "group_info"

        fun start(context: Context, configJson: String, group: IMGroupInfo) {
            val intent = Intent(context, GroupDetailActivity::class.java)
            intent.putExtra(EXTRA_CONFIG, configJson)
            intent.putExtra(EXTRA_GROUP, group)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        configJson = intent.getStringExtra(EXTRA_CONFIG)
        groupInfo = intent.getSerializableExtra(EXTRA_GROUP) as? IMGroupInfo

        binding?.ivBack?.setOnClickListener { finish() }
        binding?.tvTitle?.text = groupInfo?.chatName ?: "群聊签到活动"

        loadSignActivities()
    }

    private fun loadSignActivities() {
        val config = configJson ?: return
        val group = groupInfo ?: return

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val configObj = JSONObject.parseObject(config)
                val activities = IMGroupHelper.getIMSignActivities(configObj, group)
                withContext(Dispatchers.Main) {
                    if (activities.isEmpty()) {
                        Toast.makeText(this@GroupDetailActivity, "暂无签到活动", Toast.LENGTH_SHORT).show()
                    }
                    binding?.rvSignActivities?.layoutManager = LinearLayoutManager(this@GroupDetailActivity)
                    binding?.rvSignActivities?.adapter = SignActivityAdapter(activities) { activity ->
                        // 点击后打开签到页面
                        val signIntent = Intent(this@GroupDetailActivity, GroupSignRedirectActivity::class.java)
                        signIntent.putExtra("activeId", activity.activeId)
                        signIntent.putExtra("classId", activity.classId)
                        signIntent.putExtra("courseId", activity.courseId)
                        signIntent.putExtra("typeName", activity.activeTypeName)
                        startActivity(signIntent)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@GroupDetailActivity, "加载失败: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

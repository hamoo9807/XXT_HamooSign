package com.cofbro.qian.im

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cofbro.qian.databinding.ActivityGroupListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupListActivity : AppCompatActivity() {
    private var binding: ActivityGroupListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.ivBack?.setOnClickListener { finish() }
        binding?.tvRefresh?.setOnClickListener { loadGroups() }

        loadGroups()
    }

    private fun loadGroups() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val config = IMGroupHelper.getIMConfig()
                if (config == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@GroupListActivity, "获取IM配置失败，请先登录", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }
                val groups = IMGroupHelper.getIMGroups(config)
                withContext(Dispatchers.Main) {
                    if (groups.isEmpty()) {
                        Toast.makeText(this@GroupListActivity, "暂无群聊", Toast.LENGTH_SHORT).show()
                    }
                    binding?.rvGroupList?.layoutManager = LinearLayoutManager(this@GroupListActivity)
                    binding?.rvGroupList?.adapter = GroupListAdapter(groups) { group ->
                        GroupDetailActivity.start(this@GroupListActivity, config.toJSONString(), group)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@GroupListActivity, "加载失败: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

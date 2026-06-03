package com.cofbro.qian.home

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cofbro.hymvvmutils.base.BaseFragment
import com.cofbro.qian.databinding.FragmentGroupHomeBinding
import com.cofbro.qian.im.GroupDetailActivity
import com.cofbro.qian.im.GroupListActivity
import com.cofbro.qian.im.IMGroupHelper
import com.cofbro.qian.im.IMGroupInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupHomeFragment : BaseFragment<HomeViewModel, FragmentGroupHomeBinding>() {
    companion object {
        private const val TAG = "GroupHome"
        private const val REQUEST_CODE_ANTI_CHEAT = 1002
    }

    override fun onAllViewCreated(savedInstanceState: Bundle?) {
        initView()
        loadGroups()
    }

    private fun initView() {
        // 群聊签到 → 群聊列表
        binding?.cardGroupChat?.setOnClickListener {
            val intent = Intent(requireContext(), GroupListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadGroups() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val config = IMGroupHelper.getIMConfig()
                if (config == null) return@launch
                val groups = IMGroupHelper.getIMGroups(config)
                withContext(Dispatchers.Main) {
                    // 群聊列表的预览: 最多显示前5个
                    val preview = groups.take(5)
                    binding?.rvGroupList?.layoutManager = LinearLayoutManager(requireContext())
                    binding?.rvGroupList?.adapter = com.cofbro.qian.im.GroupListAdapter(preview) { group ->
                        GroupDetailActivity.start(requireContext(), config.toJSONString(), group)
                    }
                    binding?.tvGroupCount?.text = if (groups.isEmpty()) "暂无群聊" else "共${groups.size}个群聊"
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { binding?.tvGroupCount?.text = "加载失败" }
            }
        }
    }

}

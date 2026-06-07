package com.cofbro.qian.account.manager

import android.graphics.Rect
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.widget.PopupMenu
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.cofbro.hymvvmutils.base.BaseActivity
import com.cofbro.qian.R
import com.cofbro.qian.account.adapter.AccountsAdapter
import com.cofbro.qian.databinding.ActivityAccountmanagerBinding
import com.cofbro.qian.utils.AccountManager
import com.cofbro.qian.utils.CacheUtils
import com.cofbro.qian.utils.Constants
import com.cofbro.qian.utils.NetworkUtils
import com.cofbro.qian.utils.safeParseToJson
import com.cofbro.qian.utils.dp2px
import com.cofbro.qian.utils.getIntExt
import com.cofbro.qian.utils.getJSONArrayExt
import com.cofbro.qian.utils.getStatusBarHeight
import com.cofbro.qian.utils.getStringExt
import com.cofbro.qian.utils.safeParseToJson
import com.cofbro.qian.view.dialog.CodingDialog
import com.cofbro.qian.view.dialog.FullScreenDialog
import com.cofbro.qian.view.dialog.TipDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hjq.toast.ToastUtils
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountManagerActivity :
    BaseActivity<AccountManagerViewModel, ActivityAccountmanagerBinding>() {
    private var loadingView: FullScreenDialog? = null
    private var mAdapter: AccountsAdapter? = null
    private var data: JSONObject? = null
    private var toolbarHeight = 0
    private var mUsername = ""
    private var mPassword = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        loadAccountData()
        initView()
        initObserver()
        initEvent()
    }

    private fun initView() {
        initToolbar()

        mAdapter = AccountsAdapter().apply {
            setItemOnLongClickListener { view, itemData, pos ->
                showPopMenu(view, itemData, pos)
            }
            setDataChangedListener {
                updateAccountData(it)
                responseLottieView(it)
            }
        }
        notifyAdapterDataChanged(data)
        binding?.recyclerView?.apply {
            itemAnimator = OvershootInLeftAnimator()
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@AccountManagerActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun showPopMenu(view: View, itemData: JSONObject?, pos: Int) {
        val menu = PopupMenu(this, view)
        menu.menuInflater.inflate(R.menu.pop_menu, menu.menu)
        menu.setOnMenuItemClickListener {
            if (it.itemId == R.id.pop_add_remark) {
                menu.dismiss(); showRemarkDialog(itemData, pos); return@setOnMenuItemClickListener true
            } else if (it.itemId == R.id.pop_delete) {
                menu.dismiss(); showTipDialog(itemData); return@setOnMenuItemClickListener true
            }
            false
        }
        vibrate()
        menu.show()
    }

    private fun showRemarkDialog(itemData: JSONObject?, pos: Int) {
        CodingDialog(this).apply {
            show(); setCancelable(false)
            setHint(resources.getString(R.string.account_add_remark_hint))
            setTitle(resources.getString(R.string.account_add_remark_title))
            setContent(resources.getString(R.string.account_add_remark_content))
            setPositiveClickListener {
                itemData?.set(Constants.Account.REMARK, it)
                mAdapter?.notifyItemAccountChanged(pos)
                dismiss()
            }
            setNegativeClickListener { dismiss() }
        }
    }

    private fun showTipDialog(itemData: JSONObject?) {
        TipDialog(this).apply {
            show()
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            setNegativeClickListener { dismiss() }
            setPositiveClickListener {
                val uid = itemData?.getStringExt(Constants.Account.UID)
                mAdapter?.removeAccount(uid ?: "")
                dismiss()
            }
        }
    }

    private fun notifyAdapterDataChanged(data: JSONObject?) {
        mAdapter?.setAccounts(data)
    }

    private fun loadAccountData() {
        data = AccountManager.loadAllAccountData(this)
    }

    private fun updateAccountData(it: JSONObject?) {
        val tidiedString = it?.toJSONString() ?: ""
        if (tidiedString.isNotEmpty()) {
            AccountManager.updateAccountData(this, tidiedString)
        }
    }

    private fun responseLottieView(it: JSONObject?) {
        val size = it?.getIntExt(Constants.Account.SIZE).takeIf { s -> s != -1 } ?: 0
        if (size > 0) {
            binding?.lottieView?.visibility = View.GONE
            binding?.recyclerView?.visibility = View.VISIBLE
        }
    }

    private fun initObserver() {}
    private fun initEvent() {
        // ★ 绑定账号按钮
        binding?.tvBinding?.setOnClickListener {
            val username = binding?.etUsername?.text?.toString()?.trim() ?: ""
            val password = binding?.etPassword?.text?.toString()?.trim() ?: ""
            if (username.isEmpty() || password.isEmpty()) {
                ToastUtils.show("请输入账号和密码")
                return@setOnClickListener
            }
            binding?.tvBinding?.isEnabled = false
            binding?.tvBinding?.text = "绑定中..."
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    bindAccountWithCredentials(username, password)
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        ToastUtils.show("绑定失败: ${e.message}")
                        resetBindButton()
                    }
                }
            }
        }
    }

    private suspend fun bindAccountWithCredentials(username: String, password: String) {
        val resp = NetworkUtils.request(
            NetworkUtils.buildServerRequest(com.cofbro.qian.data.URL.getLoginPath(username, password))
        )
        val body = resp.data?.body?.string()?.safeParseToJson()
            ?: throw Exception("登录响应为空")
        if (body.getBoolean("status") != true) {
            throw Exception("账号或密码错误")
        }
        val headers = resp.data?.headers ?: throw Exception("响应头获取失败")
        val list = headers.values("Set-Cookie")
        if (list.isEmpty()) throw Exception("Cookie获取失败")
        val cookies = StringBuilder()
        var uid = ""
        var fid = ""
        for (header in list) {
            val temp = header.split(";".toRegex()).firstOrNull() ?: continue
            cookies.append(temp).append(";")
            if (temp.startsWith("UID")) uid = temp.substring(4)
            if (temp.startsWith("fid")) fid = temp.substring(4)
        }
        if (cookies.isEmpty()) throw Exception("Cookie解析失败")
        val cookieStr = cookies.toString()
        val account = AccountManager.buildAccount(username, password, uid, fid, cookieStr)
        // ★ 获取真实姓名作为备注 (避免列表显示手机号)
        try {
            val realName = fetchRealName(cookieStr)
            if (!realName.isNullOrEmpty()) {
                account[Constants.Account.REMARK] = realName
            }
        } catch (_: Exception) {}
        val accountData = AccountManager.loadAllAccountData(this@AccountManagerActivity)
        val updated = AccountManager.bindAccounts(this@AccountManagerActivity, accountData, account)
        if (updated == null) throw Exception("该账号已绑定，无需重复绑定")
        withContext(Dispatchers.Main) {
            ToastUtils.show("绑定成功!")
            // 刷新列表
            data = updated
            notifyAdapterDataChanged(data)
            // 清空输入
            binding?.etUsername?.text?.clear()
            binding?.etPassword?.text?.clear()
            resetBindButton()
        }
    }

    private fun resetBindButton() {
        binding?.tvBinding?.isEnabled = true
        binding?.tvBinding?.text = "绑定账号"
    }

    /** 用Cookie简单GET获取学习通真实姓名 (不依赖DeviceInfoHelper, 避免返回HTML) */
    private suspend fun fetchRealName(cookies: String): String? = withContext(Dispatchers.IO) {
        try {
            val client = okhttp3.OkHttpClient.Builder()
                .connectTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
                .build()
            // GET方式, 仅带Cookie, 返回 {"msg":"姓名",...}
            val req = okhttp3.Request.Builder()
                .url("https://sso.chaoxing.com/apis/login/userLogin4Uname.do")
                .addHeader("cookie", cookies)
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_2_1 like Mac OS X)")
                .get()
                .build()
            val body = client.newCall(req).execute().use { it.body?.string() ?: "" }
            // 解析: 反序列化JSON提取msg字段
            val cleaned = body.trim().replace("﻿", "")
            if (!cleaned.startsWith("{")) return@withContext null  // 不是JSON, 跳过
            val json = com.alibaba.fastjson.JSONObject.parseObject(cleaned)
            json.getString("msg")?.takeIf { it.isNotEmpty() && it != "null" && it.length < 30 }
        } catch (_: Exception) { null }
    }

    private fun initToolbar() {
        toolbarHeight = getStatusBarHeight(this).toInt()
        val marginLayoutParams = binding?.toolbar?.layoutParams as? MarginLayoutParams
        marginLayoutParams?.topMargin = toolbarHeight + dp2px(this, 5)
        binding?.tvBack?.setOnClickListener { finish() }
    }

    private fun vibrate() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) vibrator.vibrate(100L)
    }
}

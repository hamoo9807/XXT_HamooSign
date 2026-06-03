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
import com.cofbro.qian.data.URL
import com.cofbro.qian.databinding.ActivityAccountmanagerBinding
import com.cofbro.qian.utils.AccountManager
import com.cofbro.qian.utils.CacheUtils
import com.cofbro.qian.utils.Constants
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
    private var behavior: BottomSheetBehavior<NestedScrollView>? = null
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
        binding?.csContent?.apply {
            val height = resources.displayMetrics.heightPixels
            val layout = layoutParams
            layout.height = height
            layoutParams = layout
        }

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
    private fun initEvent() {}

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

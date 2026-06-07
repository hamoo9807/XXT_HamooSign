package com.cofbro.qian.account.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.fastjson.JSONObject
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cofbro.qian.databinding.ItemAccountsListBinding
import com.cofbro.qian.utils.Constants
import com.cofbro.qian.utils.dp2px
import com.cofbro.qian.utils.getIntExt
import com.cofbro.qian.utils.getJSONArrayExt
import com.cofbro.qian.utils.getStringExt

/**
 * {
 *     "history": "true",
 *     "size": "2"
 *     "users": [
 *         {
 *             "username": "cofbro",
 *             "password": "123456",
 *             "cookies": "",
 *             "uid": "000",
 *             "fid": "000",
 *             "picUrl": "xxxxxxxxx"
 *         },
 *         {
 *             "username": "cofbro",
 *             "password": "123456",
 *             "cookies": "",
 *             "uid": "000",
 *             "fid": "000",
 *             "picUrl": "xxxxxxxxx"
 *         }
 *       ]
 * }
 */
class AccountsAdapter : RecyclerView.Adapter<AccountsAdapter.AccountsHolder>() {
    private var accountData: JSONObject? = null
    private var itemClick: ((JSONObject?) -> Unit)? = null
    private var itemLongClick: ((View, JSONObject?, Int) -> Unit)? = null
    private var onDataChanged: ((JSONObject?) -> Unit)? = null
    private var appContext: android.content.Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsHolder {
        appContext = parent.context.applicationContext
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAccountsListBinding.inflate(inflater, parent, false)
        return AccountsHolder(binding)
    }

    override fun getItemCount(): Int {
        return accountData?.getStringExt("size", "0")?.toInt() ?: 0
    }

    override fun onBindViewHolder(holder: AccountsHolder, position: Int) {
        holder.bind(position)
    }

    inner class AccountsHolder(private val binding: ItemAccountsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            val itemData =
                accountData?.getJSONArrayExt(Constants.Account.USERS)?.getOrNull(position)
                    ?: JSONObject()
            (itemData as? JSONObject).let { itemValue ->
                val picUrl = itemValue?.getStringExt(Constants.Account.PIC_URL) ?: ""
                val username = itemValue?.getStringExt(Constants.Account.USERNAME) ?: ""
                val remark = itemValue?.getStringExt(Constants.Account.REMARK) ?: ""
                val uid = itemValue?.getStringExt(Constants.Account.UID) ?: ""

                // 显示名: remark > 脱敏手机号 > username
                val displayName = when {
                    remark.isNotEmpty() -> remark
                    username.length == 11 && username.matches(Regex("\\d{11}")) ->
                        "${username.take(3)}****${username.takeLast(2)}"
                    else -> username
                }

                // 绑定数据
                val options = RequestOptions().transform(
                    CenterCrop(),
                    RoundedCorners(dp2px(binding.root.context, 25))
                )
                Glide.with(binding.root)
                    .load(picUrl)
                    .apply(options)
                    .into(binding.ivTaskImage)
                // CSF对齐: CheckBox代签开关 (默认选中, excluded_uids=排除列表)
                val prefs = binding.root.context.getSharedPreferences("proxy_sign_selection", android.content.Context.MODE_PRIVATE)
                val excludedUids = prefs.getStringSet("excluded_uids", emptySet<String>()) ?: emptySet()
                val isChecked = uid !in excludedUids
                binding.cbProxySelect.isChecked = isChecked
                binding.tvTitle.text = displayName
                binding.tvTimeLine.text = "uid：$uid"

                // 点击item → 切换checkbox
                itemView.setOnClickListener {
                    val newChecked = !binding.cbProxySelect.isChecked
                    binding.cbProxySelect.isChecked = newChecked
                    val excluded = prefs.getStringSet("excluded_uids", emptySet<String>())?.toMutableSet() ?: mutableSetOf()
                    if (newChecked) excluded.remove(uid) else excluded.add(uid)
                    prefs.edit().putStringSet("excluded_uids", excluded).apply()
                }

                itemView.setOnLongClickListener {
                    itemLongClick?.invoke(it, itemValue, position)
                    false
                }
            }
        }
    }

    fun setItemOnLongClickListener(listener: (View, JSONObject?, pos: Int) -> Unit) {
        itemLongClick = listener
    }

    fun setDataChangedListener(listener: (JSONObject?) -> Unit) {
        onDataChanged = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAccounts(t: JSONObject?) {
        accountData = t
        notifyDataSetChanged()
        onDataChanged?.invoke(accountData)
    }

    fun getData(): JSONObject? {
        return accountData
    }

    fun removeAccount(uid: String) {
        var index = 0
        val array = accountData?.getJSONArrayExt(Constants.Account.USERS)
        array?.let {
            it.forEachIndexed { i, any ->
                val data = any as? JSONObject
                if (data?.getStringExt(Constants.Account.UID) == uid) {
                    index = i
                }
            }
            if (index < it.size) {
                array.removeAt(index)
                accountData?.set(Constants.Account.SIZE, array.size)
                accountData?.set(Constants.Account.USERS, array)
                notifyItemRemoved(index)
                onDataChanged?.invoke(accountData)
            }
        }
        // 同步清理excludedUids, 避免重新绑定后仍被排除
        if (uid.isNotEmpty()) {
            val prefs = appContext?.getSharedPreferences("proxy_sign_selection", android.content.Context.MODE_PRIVATE)
            val excluded = prefs?.getStringSet("excluded_uids", emptySet<String>())?.toMutableSet()
            if (excluded != null && uid in excluded) {
                excluded.remove(uid)
                prefs.edit().putStringSet("excluded_uids", excluded).apply()
            }
        }
    }

    fun addAccount() {
//        if (accountData == null) accountData = JSONObject()
//        val array = accountData?.getJSONArrayExt(Constants.Account.USERS) ?: JSONArray()
//        array.add(position, data)
//        accountData?.set(Constants.Account.SIZE, array.size)
//        accountData?.set(Constants.Account.USERS, array)
        val size = accountData?.getJSONArrayExt(Constants.Account.USERS)?.size ?: 0
        if (size == 0) return
        notifyItemInserted(size - 1)
    }

    fun notifyItemInserted() {
        val size = accountData?.getIntExt(Constants.Account.SIZE).takeIf {
            it != -1
        } ?: 0
        if (size > 0) {
            notifyItemInserted(size - 1)
            onDataChanged?.invoke(accountData)
        }
    }

    fun notifyItemAccountChanged(pos: Int) {
        if (pos < (accountData?.getIntExt(Constants.Account.SIZE) ?: 0) && pos >= 0) {
            notifyItemChanged(pos)
            onDataChanged?.invoke(accountData)
        }
    }

}
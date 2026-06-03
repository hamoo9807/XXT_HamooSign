package com.cofbro.qian.im

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cofbro.qian.R

class GroupListAdapter(
    private val groups: List<IMGroupInfo>,
    private val onItemClick: (IMGroupInfo) -> Unit
) : RecyclerView.Adapter<GroupListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivGroupIcon: ImageView = view.findViewById(R.id.iv_group_icon)
        val tvGroupName: TextView = view.findViewById(R.id.tv_group_name)
        val tvGroupInfo: TextView = view.findViewById(R.id.tv_group_info)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = groups[position]
        holder.tvGroupName.text = group.chatName
        holder.tvGroupInfo.text = if (group.isGroup) "群聊" else "单聊"

        // 加载群头像
        if (group.picArray.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(group.picArray[0])
                .placeholder(R.mipmap.ic_launcher)
                .circleCrop()
                .into(holder.ivGroupIcon)
        }

        holder.itemView.setOnClickListener { onItemClick(group) }
    }

    override fun getItemCount() = groups.size
}

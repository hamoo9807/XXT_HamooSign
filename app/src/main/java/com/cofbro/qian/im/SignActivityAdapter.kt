package com.cofbro.qian.im

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cofbro.qian.R

class SignActivityAdapter(
    private val activities: List<IMSignActivity>,
    private val onItemClick: (IMSignActivity) -> Unit
) : RecyclerView.Adapter<SignActivityAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSignType: TextView = view.findViewById(R.id.tv_sign_type)
        val tvSignTitle: TextView = view.findViewById(R.id.tv_sign_title)
        val tvSignCourse: TextView = view.findViewById(R.id.tv_sign_course)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sign_activity, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activity = activities[position]
        holder.tvSignTitle.text = activity.title

        val typeIcon = when {
            activity.activeTypeName.contains("二维码") -> "🔍"  // 🔍
            activity.activeTypeName.contains("位置") -> "📍"    // 📍
            activity.activeTypeName.contains("手势") -> "✋"           // ✋
            activity.activeTypeName.contains("密码") || activity.activeTypeName.contains("签到码") -> "🔑"  // 🔑
            activity.activeTypeName.contains("拍照") -> "📷"     // 📷
            else -> "📋"                                          // 📋
        }
        holder.tvSignType.text = typeIcon
        holder.tvSignType.background = null  // reset, use text emoji

        val courseInfo = if (activity.courseName.isNotEmpty()) activity.courseName else "课程ID: ${activity.courseId}"
        val timeInfo = if (activity.startTimeTitle.isNotEmpty()) " | ${activity.startTimeTitle}" else ""
        holder.tvSignCourse.text = "$courseInfo$timeInfo"

        holder.itemView.setOnClickListener { onItemClick(activity) }
    }

    override fun getItemCount() = activities.size
}

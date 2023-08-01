package com.smhrd.android.Chatting

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.smhrd.android.R

class ChatViewHolder(itemView: View) : ViewHolder(itemView) {

    lateinit var tvMsg: TextView
    lateinit var tvDate: TextView
    lateinit var tvIsRead: TextView

    init {
        tvMsg = itemView.findViewById(R.id.tvMsg)
        tvDate = itemView.findViewById(R.id.tvDate)
        tvIsRead = itemView.findViewById(R.id.tvIsRead)
    }
}
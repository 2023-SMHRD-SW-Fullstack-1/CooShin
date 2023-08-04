package com.smhrd.android.Chatting

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.smhrd.android.R

class ChatViewHolder(itemView: View) : ViewHolder(itemView) {

    lateinit var tvMsg: TextView
    lateinit var tvMsg2: TextView
    lateinit var tvDate: TextView
    lateinit var tvDate2: TextView
    lateinit var tvIsRead: TextView
    lateinit var tvIsRead2: TextView

    init {
        tvMsg = itemView.findViewById(R.id.tvMsg)
        tvMsg2 = itemView.findViewById(R.id.tvMsg2)
        tvDate = itemView.findViewById(R.id.tvDate)
        tvDate2 = itemView.findViewById(R.id.tvDate2)
        tvIsRead = itemView.findViewById(R.id.tvIsRead)
        tvIsRead2 = itemView.findViewById(R.id.tvIsRead2)
    }
}
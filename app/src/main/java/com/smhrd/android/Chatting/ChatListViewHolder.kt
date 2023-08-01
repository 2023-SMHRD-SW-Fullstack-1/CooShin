package com.smhrd.android.Chatting

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.smhrd.android.R

class ChatListViewHolder(itemView: View, private val onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

    lateinit var ivTeacherProfileImg: ImageView
    lateinit var tvTeacherName: TextView
    lateinit var tvLastChatTime: TextView
    lateinit var tvTeacherCity: TextView
    lateinit var tvTeacherService: TextView
    lateinit var tvServicePrice: TextView
    lateinit var tvLastChatContent: TextView

    init {
        ivTeacherProfileImg = itemView.findViewById(R.id.ivTeacherProfileImg)
        tvTeacherName = itemView.findViewById(R.id.tvTeacherName)
        tvLastChatTime = itemView.findViewById(R.id.tvLastChatTime)
        tvTeacherCity = itemView.findViewById(R.id.tvTeacherCity)
        tvTeacherService = itemView.findViewById(R.id.tvTeacherService)
        tvServicePrice = itemView.findViewById(R.id.tvServicePrice)
        tvLastChatContent = itemView.findViewById(R.id.tvLastChatContent)

        itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClick(position)
            }
        }
    }
}
package com.smhrd.android.Chatting

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.Data.DummyChatListVO
import com.smhrd.android.R

class ChatListAdapter(var context: Context, var data: ArrayList<DummyChatListVO>, private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ChatListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_template, parent, false)
        return ChatListViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        var d = data.get(position)
        holder.ivTeacherProfileImg.setImageResource(R.drawable.profiledefault)
        holder.tvTeacherName.text = d.tvTeacherName
        holder.tvTeacherCity.text = d.tvTeacherCity
        holder.tvTeacherService.text = d.tvTeacherService
        holder.tvLastChatContent.text = d.tvLastChatContent
        holder.tvLastChatTime.text = d.tvLastChatTime
        holder.tvServicePrice.text = d.tvServicePrice
    }

}
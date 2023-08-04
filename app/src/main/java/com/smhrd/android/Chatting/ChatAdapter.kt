package com.smhrd.android.Chatting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.smhrd.android.Data.ChatVO
import com.smhrd.android.Data.DummyChatItemVO
import com.smhrd.android.R

class ChatAdapter(var context: Context, var data: ArrayList<ChatVO>, var userId: String) :
    Adapter<ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.chat_template, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        if (data.get(position).chatUserId == userId) {
            holder.tvMsg.text = data.get(position).chatContent
            holder.tvDate.text = data.get(position).chatTime
            holder.tvMsg2.visibility = View.GONE
            holder.tvDate2.visibility = View.GONE
            holder.tvIsRead2.visibility = View.GONE
        } else {
            holder.tvMsg2.text = data.get(position).chatContent
            holder.tvDate2.text = data.get(position).chatTime
            holder.tvMsg.visibility = View.GONE
            holder.tvDate.visibility = View.GONE
            holder.tvIsRead.visibility = View.GONE
        }
    }
}
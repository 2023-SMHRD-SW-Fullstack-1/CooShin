package com.smhrd.android.Chatting

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.smhrd.android.Data.DummyChatItemVO
import com.smhrd.android.R

class ChatAdapter(var context: Context, var data: ArrayList<DummyChatItemVO>) :
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
        holder.tvMsg.text = data.get(position).tvMsg
        holder.tvDate.text = data.get(position).tvDate
    }
}
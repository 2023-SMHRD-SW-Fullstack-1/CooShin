package com.smhrd.android.Community

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.smhrd.android.Data.CommentVO
import com.smhrd.android.R

class CommentAdapter(var context: CommunityDetailActivity, private var data: ArrayList<CommentVO>):
    RecyclerView.Adapter<CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.commentlist, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = data[position]

        //type이 CharSequence이기 때문에 .toString() 붙여줌
       holder.bind(comment)
    }

    fun updateData(newData: ArrayList<CommentVO>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }


}
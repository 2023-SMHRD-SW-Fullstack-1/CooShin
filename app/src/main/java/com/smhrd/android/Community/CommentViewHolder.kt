package com.smhrd.android.Community

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.Data.CommentVO
import com.smhrd.android.R

class CommentViewHolder(var itemView : View) : RecyclerView.ViewHolder(itemView) {
    //Content List
    private var tv_writer: TextView = itemView.findViewById(R.id.tv_writer)
    private var tv_comment: TextView = itemView.findViewById(R.id.tv_comment)
//    var tv_date : TextView



    init{
        tv_writer = itemView.findViewById(R.id.tv_writer)
        tv_comment = itemView.findViewById(R.id.tv_comment)
//        tv_date = itemView.findViewById(R.id.tv_date)
    }

    fun bind(comment: CommentVO) {
        tv_writer.text = comment.commentWriter
        tv_comment.text = comment.commentContent
    }
}
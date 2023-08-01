package com.smhrd.android.Community

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.R

class CommunityViewHolder(var itemView : View) : RecyclerView.ViewHolder(itemView) {

    var c_title : TextView
    var c_writer : TextView
    var c_content : EditText

    init{
        c_title = itemView.findViewById(R.id.c_title)
        c_writer = itemView.findViewById(R.id.c_writer)
        c_content = itemView.findViewById(R.id.c_content)
    }


}
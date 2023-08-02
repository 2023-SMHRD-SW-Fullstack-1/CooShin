package com.smhrd.android.Community

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.R

class CommunityViewHolder(var itemView : View) : RecyclerView.ViewHolder(itemView) {

    //Community List
    var c_title : TextView
    var c_writer : TextView
    var c_date : TextView
    var c_Likes : TextView
    var c_Views : TextView


    init{
        c_title = itemView.findViewById(R.id.c_title)
        c_writer = itemView.findViewById(R.id.c_writer)
        c_date = itemView.findViewById(R.id.c_date)
        c_Likes = itemView.findViewById(R.id.c_Likes)
        c_Views = itemView.findViewById(R.id.c_Views)


    }


}
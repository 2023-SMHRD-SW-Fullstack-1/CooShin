package com.smhrd.android.Community

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.R

class CommunityViewHolder(var itemView : View) : RecyclerView.ViewHolder(itemView) {

    //Community List
    var c_title : TextView
//    var c_writer : TextView
//    var c_date : TextView
    var c_Likes : TextView
//    var c_Views : TextView
    var c_img : ImageView


    init{
        c_title = itemView.findViewById(R.id.c_title)
//        c_writer = itemView.findViewById(R.id.c_writer)
//        c_date = itemView.findViewById(R.id.c_date)
        c_Likes = itemView.findViewById(R.id.c_Likes)
//        c_Views = itemView.findViewById(R.id.c_Views)
        c_img = itemView.findViewById(R.id.c_img)

//        board_item.xml
//
//        1. viewHolder에 c_img변수 추가
//        c_writer,c_date,c_Views 변수삭제
//
//        2. CommunityFragment에
//        rc.layoutManager = GridLayoutManager(context, 2)
//        var adapter = CommunityAdapter(requireContext(), data )
//        rc.adapter = adapter 추가
//
//                3. CommunityAdapter에서 c_writer,c_date,c_Views 삭제

    }


}
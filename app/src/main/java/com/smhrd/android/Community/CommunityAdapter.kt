package com.smhrd.android.Community

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.Data.CommunityVO
import com.smhrd.android.R

class CommunityAdapter(var context : Context, var data : ArrayList<CommunityVO>):
RecyclerView.Adapter<CommunityViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {

        return CommunityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_community_list, parent, false)
        )

    }

    override fun getItemCount(): Int {
        //게시물 리스트 개수
        return data.size
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val commuinity = data[position]

        holder.c_title.text = commuinity.title
        holder.c_writer.text = commuinity.writer
        holder.c_content.text = commuinity.content


    }

}
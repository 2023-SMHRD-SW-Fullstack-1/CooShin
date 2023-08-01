package com.smhrd.android.Community

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.Data.BoardIdVO
import com.smhrd.android.R

class CommunityAdapter(var context : Context, var data : ArrayList<BoardIdVO>):
RecyclerView.Adapter<CommunityViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {

        return CommunityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.board_item, parent, false)
        )

    }

    override fun getItemCount(): Int {
        //게시물 리스트 개수
        return data.size
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val board = data[position]

        holder.c_title.text = board.boardTitle
        holder.c_writer.text = board.boardWriter
        holder.c_date.text = board.boardDate
        holder.c_Likes.text = board.boardLikes.toString()

        //editText타입



    }

}
package com.smhrd.android.Data

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.R

class HomeCommunityAdapter (var datas : ArrayList<BoardIdVO>, var context: Context) : RecyclerView.Adapter<HomeCommunityViewHolder>() {
    //View Holder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCommunityViewHolder {
        return HomeCommunityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.community_item_view, parent, false)
        )
    }
    //가지고 오는 아이템 수
    override fun getItemCount(): Int {
        return datas.size
    }
    //아이템을 View Holder에 바인딩
    override fun onBindViewHolder(holder: HomeCommunityViewHolder, position: Int) {

    }


}
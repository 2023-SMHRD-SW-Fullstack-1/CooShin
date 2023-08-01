package com.smhrd.android.Data

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.R

class HomeGosuAdapter (var datas : ArrayList<MemberVO>, var context: Context) : RecyclerView.Adapter<HomeGosuViewHolder>() {
    //View Holder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeGosuViewHolder {
        return HomeGosuViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.language_item_view, parent, false)
        )
    }
    //가지고 오는 아이템 수
    override fun getItemCount(): Int {
        return datas.size
    }
    //아이템을 View Holder에 바인딩
    override fun onBindViewHolder(holder: HomeGosuViewHolder, position: Int) {



    }


}
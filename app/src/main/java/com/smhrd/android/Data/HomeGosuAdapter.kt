package com.smhrd.android.Data

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.R
import com.smhrd.android.User.LoginActivity

class HomeGosuAdapter (var datas : ArrayList<MemberVO>, var review : ArrayList<ReviewVO>, var context: Context) : RecyclerView.Adapter<HomeGosuViewHolder>() {
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
        val member : MemberVO = datas[position]

        holder.tvGosuName.text = member.memberNick
        holder.tvReviewStar.text = review[position].reviewStars.toString()

        holder.itemView.setOnClickListener(){
            //intent 사용해서 DetailActivity로 이동
            var intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            intent.putExtra("gosuNick", member.memberNick)
            Log.d("gosuNick", member.memberNick)

            context.startActivity(intent)
        }


    }


}
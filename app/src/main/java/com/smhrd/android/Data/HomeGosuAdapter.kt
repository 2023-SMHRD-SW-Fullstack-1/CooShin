package com.smhrd.android.Data

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.R
import com.smhrd.android.User.LoginActivity
import java.util.Arrays

class HomeGosuAdapter (var datas : ArrayList<MemberVO>, var review : ArrayList<ReviewVO>, var context: Context) : RecyclerView.Adapter<HomeGosuViewHolder>() {
    //View Holder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeGosuViewHolder {
        return HomeGosuViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.language_item_view, parent, false)
        )
    }

    //별점이 4점 이상인 아이템이 있으면 count+1해주기
    override fun getItemCount(): Int {
        var count = 0
        for(i in 0 until review.size) {
            val reviewStar =  review.get(i).reviewStars
            if(reviewStar > 4){
                count++
            }
        }
        return count
    }

    override fun onBindViewHolder(holder: HomeGosuViewHolder, position: Int) {
        var highIndex = -1 //현재까지 별점 4점 이상인 게시물의 인덱스를 추적
        var displayCount = 0 //실제로 출력하려는 데이터의 인덱스를 저장
        for (i in 0 until review.size) {
            val reviewStar = review.get(i).reviewStars
            if (reviewStar > 4) {
                highIndex++
                if (highIndex == position) {
                    displayCount = i
                    break
                }
            }
        }
        if (displayCount < datas.size && displayCount < review.size) {
            val member: MemberVO = datas[position]

            holder.tvGosuName.text = member.memberNick
            holder.tvReviewStar.text = review[position].reviewStars.toString()

            holder.itemView.setOnClickListener() {
                //intent 사용해서 DetailActivity로 이동
                var intent = Intent(context, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                intent.putExtra("gosuNick", member.memberNick)
                Log.d("gosuNick", member.memberNick)

                context.startActivity(intent)
            }
        }
    }
}
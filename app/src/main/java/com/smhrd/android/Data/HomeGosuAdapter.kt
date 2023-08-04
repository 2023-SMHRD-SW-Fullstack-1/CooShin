package com.smhrd.android.Data

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smhrd.android.Fragment.HomeFragment.Companion.dataMap_favGosu_home
import com.smhrd.android.R
import com.smhrd.android.User.LoginActivity
import java.util.Arrays

class HomeGosuAdapter (var datas : ArrayList<TeacherIdVO>, var review : ArrayList<ReviewVO>, var context: Context) : RecyclerView.Adapter<HomeGosuViewHolder>() {
    //View Holder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeGosuViewHolder {
        return HomeGosuViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.language_item_view, parent, false)
        )
    }

    //별점이 4점 이상인 아이템이 있으면 count+1해주기
    override fun getItemCount(): Int {
//        var count = 0
//        for(i in 0 until review.size) {
//            val reviewStar =  review.get(i).reviewStars
//            if(reviewStar > 4){
//                count++
//            }
//        }
        return datas.size
    }

    override fun onBindViewHolder(holder: HomeGosuViewHolder, position: Int) {
        var highIndex = -1 //현재까지 별점 4점 이상인 게시물의 인덱스를 추적
        var displayCount = datas.size //실제로 출력하려는 데이터의 인덱스를 저장
//        for (i in 0 until review.size) {
//            val reviewStar = review.get(i).reviewStars
//            if (reviewStar > 4) {
//                highIndex++
//                if (highIndex == position) {
//                    displayCount = i
//                    break
//                }
//            }
//        }
//        if (displayCount < datas.size && displayCount < review.size) {
            val teacher: TeacherIdVO = datas[position]

            holder.tvGosuName.text = teacher.teacherName
            holder.tvReviewStar.text = datas[position].teacherLikes.toString()

            var memberId = findKeyByValue(dataMap_favGosu_home, datas[position])
            Firebase.database.getReference("memberList").child(memberId!!).child("member").child("memberImg").addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                       var imageUrl = snapshot.getValue(String::class.java)
                        if (!imageUrl.isNullOrEmpty()) {
                            Glide.with(context)
                                .load(imageUrl)
                                .into(holder.ivGosu)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                }
            )

            holder.itemView.setOnClickListener() {
                //intent 사용해서 DetailActivity로 이동
                var intent = Intent(context, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                intent.putExtra("teacherNick", teacher.teacherName)
                Log.d("teacherNick", teacher.teacherName)

                context.startActivity(intent)
            }
//        }
    }
    fun findKeyByValue(map: Map<String, Any>, value: Any): String? {
        return map.entries.find { it.value == value }?.key
    }
}

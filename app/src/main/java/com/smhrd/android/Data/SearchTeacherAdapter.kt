package com.smhrd.android.Data

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SearchTeacherAdapter(var context: Context, var template : Int, var data : ArrayList<TeacherIdVO>) :
    RecyclerView.Adapter<SearchTeacherHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTeacherHolder {
        return SearchTeacherHolder(
            LayoutInflater.from(context).inflate(template, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SearchTeacherHolder, position: Int) {
        holder.tvOneLine.text = data.get(position).teacherOneLine
        holder.tvReviewNum.text = data[position].reviews?.size.toString()
        holder.tvTeacherGender.text = data[position].teacherGender.toString()
        holder.tvTeacherName.text = data[position].teacherName

        
        //평균 평점 세기
        var starAvg : Double = 0.0

        for(i in 0 until data[position].reviews!!.size) {
            starAvg += data[position].reviews?.get(i)?.reviewStars!!.toDouble()
        }

        holder.tvStarAvg.text = (starAvg / data[position].reviews!!.size).toString()

//        holder.ivTeacherImg.resources =

        //화면전환이 필요한가?
//        //Activity가 아닌데 화면전환
//        //Flag
//        var it = Intent()
//        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        //mainACTIVIY 화면 정보를 생성자의 변수로 호출
//        context.startActivity(it!!)
    }
}
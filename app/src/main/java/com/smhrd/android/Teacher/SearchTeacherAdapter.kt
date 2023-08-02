package com.smhrd.android.Teacher

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.Data.TeacherIdVO

class SearchTeacherAdapter(var context: Context, var template : Int, var data : ArrayList<TeacherIdVO>,  private val onItemClickListener: SearchTeacherOnClick) :
    RecyclerView.Adapter<SearchTeacherHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTeacherHolder {
        val view = LayoutInflater.from(context).inflate(template, parent, false)
        return SearchTeacherHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SearchTeacherHolder, position: Int) {
        holder.tvOneLine.text = data.get(position).teacherOneLine
        holder.tvTeacherGender.text = data[position].teacherGender.toString()
        holder.tvTeacherName.text = data[position].teacherName


        //리뷰개수 확인하기

        data[position].reviews?.let{ holder.tvReviewNum.text = "( ${it.size.toString()} )"}?: run{holder.tvReviewNum.text = "( 0 )"}



        //평균 평점 세기
        var starAvg : Double = 0.0

        data[position].reviews?.let {
            for (i in 0 until data[position].reviews!!.size) {
                starAvg += data[position].reviews?.get(i)?.reviewStars!!.toDouble()
            }
            holder.tvStarAvg.text = (starAvg / data[position].reviews!!.size).toString()

        }?: run {
            holder.tvStarAvg.text = starAvg.toString()
        }
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
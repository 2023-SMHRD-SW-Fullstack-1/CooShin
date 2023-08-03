package com.smhrd.android.Teacher

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smhrd.android.Data.TeacherIdVO
import com.smhrd.android.Fragment.SearchGosuFragment.Companion.dataMap_searchGosu

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
        holder.tvTeacherGender.text = data[position].teacherService
        holder.tvTeacherName.text = data[position].teacherName

        val database = Firebase.database
        //코신 이미지 불러오기
        var imageUrl :String? = null

        var teacherId = findKeyByValue(dataMap_searchGosu, data[position])

        database.getReference("memberList").child(teacherId!!).child("member").child("memberImg").addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    imageUrl = snapshot.getValue(String::class.java)
                    if (!imageUrl.isNullOrEmpty()) {
                        Glide.with(context)
                            .load(imageUrl)
                            .into(holder.ivTeacherImg)
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }
            }
        )


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
    }
    fun findKeyByValue(map: Map<String, Any>, value: Any): String? {
        return map.entries.find { it.value == value }?.key
    }
}

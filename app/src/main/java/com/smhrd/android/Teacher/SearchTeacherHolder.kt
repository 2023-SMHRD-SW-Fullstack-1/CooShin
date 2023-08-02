package com.smhrd.android.Teacher

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.smhrd.android.R

class SearchTeacherHolder(itemView : View, private val onItemClickListener : SearchTeacherOnClick) : ViewHolder(itemView)  {
    var tvTeacherName : TextView
    var tvOneLine : TextView
    var tvTeacherGender : TextView
    var tvStarAvg : TextView
    var ivTeacherImg : ImageView
    var tvReviewNum : TextView

    init {
        tvTeacherName =itemView.findViewById(R.id.tvTeacherName_search)
        tvOneLine =itemView.findViewById(R.id.tvOneLine_search)
        tvTeacherGender = itemView.findViewById(R.id.tvTeacherGender_search)
        tvStarAvg = itemView.findViewById(R.id.tvStarAvg_search)
        ivTeacherImg = itemView.findViewById(R.id.ivTeacherImg_search)
        tvReviewNum = itemView.findViewById(R.id.tvReviewNum_search)

        itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClick(position)
            }
        }
    }
}
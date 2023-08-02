package com.smhrd.android.Data

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.R

class HomeGosuViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    lateinit var ivGosu : ImageView
    lateinit var tvGosuName : TextView
    lateinit var tvReviewStar : TextView

    init {
        ivGosu = itemView.findViewById(R.id.ivGosu)
        tvGosuName = itemView.findViewById(R.id.tvGosuName)
        tvReviewStar = itemView.findViewById(R.id.tvReviewStar)
    }

}
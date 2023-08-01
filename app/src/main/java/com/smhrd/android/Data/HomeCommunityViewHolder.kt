package com.smhrd.android.Data

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.R

class HomeCommunityViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
    lateinit var ivBoardImg : ImageView
    lateinit var tvBoardTitle : TextView
    lateinit var tvBoardLikes : TextView

    init {
        ivBoardImg = itemView.findViewById(R.id.ivBoardImg)
        tvBoardTitle = itemView.findViewById(R.id.tvBoardTitle)
        tvBoardLikes = itemView.findViewById(R.id.tvBoardLikes)
    }
}
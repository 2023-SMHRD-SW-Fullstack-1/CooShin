package com.smhrd.android.Data

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.R
import com.smhrd.android.User.LoginActivity
import com.smhrd.android.User.MypageActivity

class HomeCommunityAdapter (var datas : ArrayList<BoardIdVO>, var context: Context) : RecyclerView.Adapter<HomeCommunityViewHolder>() {
    //View Holder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCommunityViewHolder {
        return HomeCommunityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.community_item_view, parent, false)
        )
    }
    //가지고 오는 아이템 수
    override fun getItemCount(): Int {
        return datas.size
    }
    //아이템을 View Holder에 바인딩
    override fun onBindViewHolder(holder: HomeCommunityViewHolder, position: Int) {

        val board : BoardIdVO = datas[position]

        var img : ImageView = holder.ivBoardImg


        holder.tvBoardLikes.text = board.boardLikes.toString()
        holder.tvBoardTitle.text = board.boardTitle


        holder.itemView.setOnClickListener(){
            //intent 사용해서 DetailActivity로 이동
            var intent = Intent(context, MypageActivity::class.java)

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            intent.putExtra("boardTitle", board.boardTitle)
            intent.putExtra("boardWriter", board.boardWriter)
            Log.d("gosuNick", board.boardTitle)

            context.startActivity(intent)
        }

    }


}
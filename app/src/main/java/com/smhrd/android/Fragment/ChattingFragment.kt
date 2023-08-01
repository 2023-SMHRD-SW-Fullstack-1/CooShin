package com.smhrd.android.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.Chatting.ChatListAdapter
import com.smhrd.android.Chatting.ChattingRoomActivity
import com.smhrd.android.Chatting.OnItemClickListener
import com.smhrd.android.Data.DummyChatListVO
import com.smhrd.android.R

class ChattingFragment : Fragment() {

    lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_chatting, container, false)

        rv = view.findViewById(R.id.rv)

        var chatList = ArrayList<DummyChatListVO>()

        chatList.add(
            DummyChatListVO(
                R.drawable.profiledefault,
                "김신영",
                "4시 30분",
                "광주광역시 북구",
                "레슨",
                "총 200,000원",
                "안녕하세요"
            )
        )
        chatList.add(
            DummyChatListVO(
                R.drawable.profiledefault,
                "박정현",
                "오후 12:22",
                "광주광역시 서구",
                "레슨",
                "총 300,000원",
                "반갑습니다"
            )
        )

        var adapter = ChatListAdapter(requireActivity(), chatList, object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                var intent = Intent(requireActivity(), ChattingRoomActivity::class.java)
                startActivity(intent)
            }
        })

        rv.layoutManager = LinearLayoutManager(requireActivity())
        rv.adapter = adapter

        return view
    }
}
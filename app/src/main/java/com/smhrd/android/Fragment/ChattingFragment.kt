package com.smhrd.android.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smhrd.android.Chatting.ChatListAdapter
import com.smhrd.android.Chatting.ChattingRoomActivity
import com.smhrd.android.Chatting.OnItemClickListener
import com.smhrd.android.Data.DummyChatListVO
import com.smhrd.android.Data.MemberIdVO
import com.smhrd.android.R

class ChattingFragment : Fragment() {

    lateinit var rv: RecyclerView

    private fun readFirebaseData(userId: String) {
        val db = Firebase.database
        val chatRoomIdList = ArrayList<String>()
        val chatRoomList = ArrayList<DummyChatListVO>()

        db.getReference("memberList").child(userId).child("chatRoom").get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.getValue(object : GenericTypeIndicator<List<String>>() {})

                if (list != null) {
                    for (i in 0 until list.size) {
                        var roomId = list[i]

                        chatRoomIdList.add(roomId)

                        chatRoomList.add(
                            DummyChatListVO(
                                chatRoomIdList[i],
                                null,
                                "김신영",
                                "오후 04:30",
                                "광주광역시 북구",
                                "레슨",
                                "총 200,000원",
                                "안녕하세요"
                            )
                        )
                    }

                    // 데이터를 읽어오고 나서 어댑터 설정을 진행합니다.
                    setupRecyclerView(chatRoomList)
                }
            }.addOnFailureListener { e ->
                Log.e("firebase", "Error getting data", e)
            }
    }

    // RecyclerView 어댑터 설정
    private fun setupRecyclerView(chatRoomList: ArrayList<DummyChatListVO>) {
        if (!isAdded) {
            return
        }

        val adapter =
            ChatListAdapter(requireActivity(), chatRoomList, object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    var intent = Intent(requireActivity(), ChattingRoomActivity::class.java)

                    intent.putExtra("roomId", chatRoomList[position].roomId.toString())

                    startActivity(intent)
                }
            })

        rv.layoutManager = LinearLayoutManager(requireActivity())
        rv.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_chatting, container, false)

        rv = view.findViewById(R.id.rv)

        // 로그인한 경우 유저 아이디
        val spf =
            requireActivity().getSharedPreferences("memberInfoSpf", AppCompatActivity.MODE_PRIVATE)
        var userId = spf.getString("memberId", null).toString()

        // Firebase 데이터 읽어오는 함수 호출
        readFirebaseData(userId)

        return view
    }
}
package com.smhrd.android.Chatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smhrd.android.Data.ChatRoomListVO
import com.smhrd.android.Data.DummyChatItemVO
import com.smhrd.android.Data.MemberIdVO
import com.smhrd.android.R

class ChattingRoomActivity : AppCompatActivity() {

    lateinit var rv: RecyclerView
    lateinit var edtMsg: EditText
    lateinit var btnSend: Button
    lateinit var inputData: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting_room)

        rv = findViewById(R.id.rv)
        btnSend = findViewById(R.id.btnSend)
        edtMsg = findViewById(R.id.edtMsg)
        inputData = findViewById(R.id.inputData)

        val spf = getSharedPreferences("memberInfoSpf", MODE_PRIVATE)
        var userId = spf.getString("memberId", null)

        Log.d("화면 렌더링", userId.toString())

        val database = Firebase.database

        var data = ArrayList<DummyChatItemVO>()
        data.add(DummyChatItemVO("안녕하세요", "오후 04:34"))
        data.add(DummyChatItemVO("반갑습니다", "오후 04:36"))
        data.add(
            DummyChatItemVO(
                "서오세요어서오세요어서오세요어서오세요어서오세요",
                "오후 05:40"
            )
        )

        // chatRoom 임시데이터 -> 나중에 삭제할것
        var chatRoom = ArrayList<String>()
        chatRoom.add("roomId1")
        chatRoom.add("roomId2")
        chatRoom.add("roomId3")
        chatRoom.add("roomId4")
        chatRoom.add("roomId5")

        var adapter = ChatAdapter(applicationContext, data)

        rv.layoutManager = LinearLayoutManager(applicationContext)
        rv.adapter = adapter

        btnSend.setOnClickListener {
            Log.d("userId : ", userId.toString())
        }

        inputData.setOnClickListener {
            Log.d("거래하기 클릭!", userId.toString())

            database.getReference("roomList").setValue(chatRoom)
        }
    }
}
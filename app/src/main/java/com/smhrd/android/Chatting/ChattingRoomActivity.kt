package com.smhrd.android.Chatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.Data.DummyChatItemVO
import com.smhrd.android.R

class ChattingRoomActivity : AppCompatActivity() {

    lateinit var rv: RecyclerView
    lateinit var edtMsg: EditText
    lateinit var btnSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting_room)

        rv = findViewById(R.id.rv)
        btnSend = findViewById(R.id.btnSend)
        edtMsg = findViewById(R.id.edtMsg)

        var data = ArrayList<DummyChatItemVO>()

        data.add(DummyChatItemVO("안녕하세요", "오후 04:34"))
        data.add(DummyChatItemVO("반갑습니다", "오후 04:36"))
        data.add(
            DummyChatItemVO(
                "서오세요어서오세요어서오세요어서오세요어서오세요",
                "오후 05:40"
            )
        )

        var adapter = ChatAdapter(applicationContext, data)

        rv.layoutManager = LinearLayoutManager(applicationContext)
        rv.adapter = adapter


    }
}
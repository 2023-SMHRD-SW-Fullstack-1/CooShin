package com.smhrd.android.Community

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smhrd.android.Data.BoardIdVO
import com.smhrd.android.R

class CommunityCreateActivity : AppCompatActivity() {
    lateinit var etTitle : EditText
    lateinit var etContent : EditText
    lateinit var ivUpload : ImageView
    lateinit var btnComplete : Button
    lateinit var back : ImageButton
    lateinit var ibGallery : ImageButton
    lateinit var ibcamera : ImageButton
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_create)
        etTitle = findViewById(R.id.etTitle)
        etContent = findViewById(R.id.etContent)
        ivUpload = findViewById(R.id.ivUpload)
        btnComplete = findViewById(R.id.btnComplete)
        back = findViewById(R.id.back)
        ibGallery = findViewById(R.id.ibGallery)
        ibcamera  = findViewById(R.id.ibCamera)
        val database = Firebase.database

        val spf = getSharedPreferences("mySPF", Context.MODE_PRIVATE)



        //임의의 boardId를 만들어주고 setvalue해서 boardidVO에 게시물을 넣어준다
//        database.getReference("boardList").child(boardId).setValue(BoardIdVO)
//
//        //boardid를 list형태로 가져오고 거기에 내가 만든 임의의 boardId값을 넣어준다.
//        var list : MutableList<String> = database.getReference("memberList").child(id).get("board")
//        list.add




    }


}
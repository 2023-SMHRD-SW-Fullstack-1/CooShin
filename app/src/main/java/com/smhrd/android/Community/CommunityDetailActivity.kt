package com.smhrd.android.Community

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.R

class CommunityDetailActivity : AppCompatActivity() {
    lateinit var img : ImageView
    lateinit var tv_title : TextView
    lateinit var tv_content : TextView
    lateinit var et_comment : EditText
    lateinit var btn_comment : Button
    lateinit var rc_comment : RecyclerView
    lateinit var ibtn_back : ImageButton
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_detail)
        img = findViewById(R.id.img)
        tv_title = findViewById(R.id.tv_title)
        tv_content = findViewById(R.id.tv_content)
        et_comment = findViewById(R.id.et_comment)
        btn_comment = findViewById(R.id.btn_comment)
        rc_comment = findViewById(R.id.rc_comment)
        ibtn_back = findViewById(R.id.ibtn_back)






    }
}
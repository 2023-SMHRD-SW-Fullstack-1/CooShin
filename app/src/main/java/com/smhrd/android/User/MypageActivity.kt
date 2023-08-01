package com.smhrd.android.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import com.smhrd.android.R

class MypageActivity : AppCompatActivity() {
    lateinit var mypageIv_img : ImageView
    lateinit var mypageBtn_img : ImageButton
    lateinit var mypageTv_nick : EditText
    lateinit var mypageBtn_infoChange : Button
    lateinit var mypageBtn_likesGosu : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        mypageIv_img = findViewById(R.id.mypageIv_img)
        mypageBtn_img = findViewById(R.id.mypageBtn_img)
        mypageTv_nick = findViewById(R.id.mypageTv_nick)
        mypageBtn_infoChange = findViewById(R.id.mypageBtn_infoChange)
        mypageBtn_likesGosu = findViewById(R.id.mypageBtn_likesGosu)

        //마이페이지 프로필 이미지 버튼 눌렀을 때
        mypageBtn_img.setOnClickListener {

        }

        //마이페이지 회원정보수정 눌렀을때
        mypageBtn_infoChange.setOnClickListener {

        }

        //마이페이지 찜한 고수 눌렀을때
        mypageBtn_likesGosu.setOnClickListener {

        }
    }
}
package com.smhrd.android.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.smhrd.android.R

class LoginActivity : AppCompatActivity() {

    lateinit var loginEt_id : EditText
    lateinit var loginEt_Pw : EditText
    lateinit var loginBtn_login : Button
    lateinit var loginBtn_google : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEt_id = findViewById(R.id.loginEt_id)
        loginEt_Pw = findViewById(R.id.loginEt_pw)
        loginBtn_login = findViewById(R.id.loginBtn_login)
        loginBtn_google = findViewById(R.id.loginBtn_google)

        //일반 로그인 버튼 눌렀을 때
        loginBtn_login.setOnClickListener {

        }

        //google로그인 버튼 눌렀을 때
        loginBtn_google.setOnClickListener {

        }
    }
}
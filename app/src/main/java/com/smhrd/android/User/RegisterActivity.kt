package com.smhrd.android.User

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.smhrd.android.R

class RegisterActivity : AppCompatActivity() {

    lateinit var registerEt_id : EditText
    lateinit var registerEt_pw : EditText
    lateinit var registerEt_tel : EditText
    lateinit var registerEt_nick : EditText
    lateinit var registerBtn_idCheck : Button
    lateinit var registerBtn_join : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerEt_id = findViewById(R.id.registerEt_id)
        registerEt_pw = findViewById(R.id.registerEt_pw)
        registerEt_tel = findViewById(R.id.registerEt_tel)
        registerEt_nick = findViewById(R.id.registerEt_nick)
        registerBtn_idCheck = findViewById(R.id.registerBtn_idCheck)
        registerBtn_join = findViewById(R.id.registerBtn_join)

        //id 중복확인 버튼 눌렀을 때
        registerBtn_idCheck.setOnClickListener {
         var inputId = registerEt_id.text.toString()
        }

        //회원가입 버튼 눌렀을 때
        registerBtn_join.setOnClickListener {
            var inputId = registerEt_id.text.toString()
            var inputPw = registerEt_pw.text.toString()
            var inputTel = registerEt_tel.text.toString()
            var inputNick = registerEt_nick.text.toString()
        }
    }
}
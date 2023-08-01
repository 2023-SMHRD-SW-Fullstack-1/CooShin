package com.smhrd.android.User

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smhrd.android.Data.MemberIdVO
import com.smhrd.android.Data.MemberVO
import com.smhrd.android.MainActivity
import com.smhrd.android.R

class RegisterActivity : AppCompatActivity() {

    lateinit var registerEt_id : EditText
    lateinit var registerEt_pw : EditText
    lateinit var registerEt_tel : EditText
    lateinit var registerEt_nick : EditText
    lateinit var registerBtn_idCheck : Button
    lateinit var registerBtn_join : Button
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerEt_id = findViewById(R.id.registerEt_id)
        registerEt_pw = findViewById(R.id.registerEt_pw)
        registerEt_tel = findViewById(R.id.registerEt_tel)
        registerEt_nick = findViewById(R.id.registerEt_nick)
        registerBtn_idCheck = findViewById(R.id.registerBtn_idCheck)
        registerBtn_join = findViewById(R.id.registerBtn_join)

        val database = Firebase.database

        //id 중복확인 버튼 눌렀을 때
        registerBtn_idCheck.setOnClickListener {
         var inputId = registerEt_id.text.toString()

            //id 작성 안하고 중복확인 눌렀을때
            if (inputId.isEmpty()) {
                Toast.makeText(applicationContext, "ID를 입력하세요.", Toast.LENGTH_SHORT).show()
                //return@setOnClickListener
            }

            database.getReference("memberList").child(inputId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        //dataSnapshot.exists() 데이터가 존재하는지 확인!

                        if (dataSnapshot.exists()) {
                            // 중복 ID
                            Toast.makeText(applicationContext, "이미 사용 중인 ID 입니다. 다른 ID를 입력해주세요.", Toast.LENGTH_SHORT).show()
                        } else {
                            // 사용 가능한 ID
                            Toast.makeText(applicationContext, "사용 가능한 ID입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // 에러 메시지 출력
                        Toast.makeText(applicationContext, "오류 발생", Toast.LENGTH_SHORT).show()
                        Log.d("register idcheck error", databaseError.message.toString())
                    }
                })
        }

        //회원가입 버튼 눌렀을 때
        registerBtn_join.setOnClickListener {
            var inputId = registerEt_id.text.toString()
            var inputPw = registerEt_pw.text.toString()
            var inputTel = registerEt_tel.text.toString()
            var inputNick = registerEt_nick.text.toString()

            var memberVO = MemberVO(
                memberPw = inputPw,
                memberTel = inputTel,
                memberNick = inputNick,
                memberImg = null,
                teacherId = null,
                likeList = null
            )
            var memberIdVO = MemberIdVO(
                member = memberVO,
                chatList = null,
                board = null
            )
            database.getReference("memberList").child(inputId).setValue(memberIdVO)
                .addOnSuccessListener {
                    // 저장 성공
                    Toast.makeText(applicationContext, "회원 가입 완료", Toast.LENGTH_SHORT).show()
                    // MainActivity로 이동
                   // val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                .addOnFailureListener { e ->
                    // 저장 실패
                    //Toast.makeText(applicationContext, "회원 가입 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(applicationContext, "회원 가입 실패", Toast.LENGTH_SHORT).show()
                    Log.d("register error", e.toString())
                }

        }
    }
}
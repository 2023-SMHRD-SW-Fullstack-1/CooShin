package com.smhrd.android.User

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.smhrd.android.R
import com.google.firebase.database.FirebaseDatabase
import com.smhrd.android.Data.TeacherIdVO
import com.smhrd.android.MainActivity

class AddGosuActivity : AppCompatActivity() {

    lateinit var addteacherEt_name : EditText
    lateinit var addteacherEt_content : EditText
    lateinit var addteacherEt_oneline : EditText
    lateinit var addteacherEt_gender : EditText
    lateinit var addteacherEt_service : EditText
    lateinit var addteacherEt_city : EditText
    lateinit var addteacherEt_teltime : EditText
    lateinit var addteacherEt_worktime : EditText
    lateinit var addteacherBtn_add : Button


    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gosu)

        addteacherEt_name = findViewById(R.id.addteacherEt_name)
        addteacherEt_content = findViewById(R.id.addteacherEt_content)
        addteacherEt_oneline = findViewById(R.id.addteacherEt_oneline)
        addteacherEt_gender = findViewById(R.id.addteacherEt_gender)
        addteacherEt_service = findViewById(R.id.addteacherEt_service)
        addteacherEt_city = findViewById(R.id.addteacherEt_city)
        addteacherEt_teltime = findViewById(R.id.addteacherEt_teltime)
        addteacherEt_worktime = findViewById(R.id.addteacherEt_worktime)
        addteacherBtn_add = findViewById(R.id.addteacherBtn_add)

        //firebase에서 데이터를 가져오기 위함!
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("memberList")

        val memberId = getMemberInfoFromSpf()

        //등록 버튼 눌렀을때
        addteacherBtn_add.setOnClickListener {
            var inputName = addteacherEt_name.text.toString()
            var inputContent = addteacherEt_content.text.toString()
            var inputOneline = addteacherEt_oneline.text.toString()
            var inputGender = addteacherEt_gender.text.toString()
            var inputService = addteacherEt_service.text.toString()
            var inputCity = addteacherEt_city.text.toString()
            var inputTeltime = addteacherEt_teltime.text.toString()
            var inputWorktime = addteacherEt_worktime.text.toString()


            val teacherIdVO = TeacherIdVO(
                teacherName = inputName,
                teacherContent = inputContent,
                teacherOneLine = inputOneline,
                teacherGender = inputGender,
                teacherService = inputService,
                teacherCity = inputCity,
                teacherTelTime = inputTeltime,
                teacherWorkTime = inputWorktime,
                teacherLikes = 0,
                reviews = ArrayList(),
            )

            // memberId가 null인 경우
            if (memberId != null) {
                // teacherList 객체에 데이터 추가
                val teacherListRef = database.getReference("teacherList").child(memberId)
                teacherListRef.setValue(teacherIdVO)
                    .addOnSuccessListener {
                        Log.d("고수 등록 성공", teacherIdVO.toString())
                        Toast.makeText(applicationContext, "등록 완료", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@AddGosuActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(applicationContext, "등록 실패", Toast.LENGTH_SHORT).show()
                        Log.d("고수 등록 error", e.toString())
                    }
            } else {
                Toast.makeText(
                    applicationContext,
                    "멤버 아이디가 없습니다. 로그인 상태를 확인해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //memberId SPF가져오기
    fun getMemberInfoFromSpf(): String? {
        val sharedPreferences = getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
        return sharedPreferences.getString("memberId", null)
    }
}
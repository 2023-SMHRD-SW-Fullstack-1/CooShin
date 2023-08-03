package com.smhrd.android.User

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.smhrd.android.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
    lateinit var addteacherIv_img : ImageView


    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gosu)

        addteacherEt_name = findViewById(R.id.changeteacherEt_name)
        addteacherEt_content = findViewById(R.id.changeteacherEt_content)
        addteacherEt_oneline = findViewById(R.id.changeteacherEt_oneline)
        addteacherEt_gender = findViewById(R.id.changeteacherEt_gender)
        addteacherEt_service = findViewById(R.id.changeteacherEt_service)
        addteacherEt_city = findViewById(R.id.changeteacherEt_city)
        addteacherEt_teltime = findViewById(R.id.changeteacherEt_teltime)
        addteacherEt_worktime = findViewById(R.id.changeteacherEt_worktime)
        addteacherBtn_add = findViewById(R.id.changeteacherBtn_update)
        addteacherIv_img = findViewById(R.id.changeteacherIv_img)

        //firebase에서 데이터를 가져오기 위함!
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("memberList")
        val memberId = getMemberInfoFromSpf()

        val imgRef = memberId?.let { reference.child(it).child("member").child("memberImg") }
        Log.d("imgRef",imgRef.toString())

        if (imgRef != null) {
            imgRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // memberId 안에 있는 memberImg가 있는 경우
                        val imageUrl = snapshot.getValue(String::class.java)

                        // 사용자가 등록한 이미지를 ImageView에 로드하기 위해서 Glide 라이브러리를 사용합니다.
                        Glide.with(this@AddGosuActivity)
                            .load(imageUrl)
                            .into(addteacherIv_img)
                    } else {
                        // memberImg가 없는 경우 기본 이미지 로드
                        Glide.with(this@AddGosuActivity)
                            .load(R.drawable.defaultprofileimage) // 기본 이미지 (예를 들어, R.drawable.default_profile_image 로 변경하십시오.)
                            .into(addteacherIv_img)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Database Error", error.toString())
                }
            })
        }



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
                        Log.d("코신 등록 성공", teacherIdVO.toString())
                        Toast.makeText(applicationContext, "등록 완료", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@AddGosuActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(applicationContext, "등록 실패", Toast.LENGTH_SHORT).show()
                        Log.d("코신 등록 error", e.toString())
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
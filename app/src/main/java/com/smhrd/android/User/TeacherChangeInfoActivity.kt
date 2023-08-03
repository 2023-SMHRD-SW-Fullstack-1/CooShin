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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.smhrd.android.R

class TeacherChangeInfoActivity : AppCompatActivity() {
    lateinit var changeteacherIv_img : ImageView
    lateinit var changeteacherEt_name : EditText
    lateinit var changeteacherEt_content : EditText
    lateinit var changeteacherEt_oneline : EditText
    lateinit var changeteacherEt_gender : EditText
    lateinit var changeteacherEt_service : EditText
    lateinit var changeteacherEt_city : EditText
    lateinit var changeteacherEt_teltime : EditText
    lateinit var changeteacherEt_worktime : EditText
    lateinit var changeteacherBtn_update : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_change_info)

        changeteacherIv_img = findViewById(R.id.changeteacherIv_img)
        changeteacherEt_name = findViewById(R.id.changeteacherEt_name)
        changeteacherEt_content = findViewById(R.id.changeteacherEt_content)
        changeteacherEt_oneline = findViewById(R.id.changeteacherEt_oneline)
        changeteacherEt_gender = findViewById(R.id.changeteacherEt_gender)
        changeteacherEt_service = findViewById(R.id.changeteacherEt_service)
        changeteacherEt_city = findViewById(R.id.changeteacherEt_city)
        changeteacherEt_teltime = findViewById(R.id.changeteacherEt_teltime)
        changeteacherEt_worktime = findViewById(R.id.changeteacherEt_worktime)
        changeteacherBtn_update = findViewById(R.id.changeteacherBtn_update)

        val database = FirebaseDatabase.getInstance()
        val teacherReference = database.getReference("teacherList")
        // SharedPreferences에서 memberId 가져오기
        val memberId = getMemberInfoFromSpf()

        // SharedPreferences에서 이미지 URL 가져오기
        val sharedPreferences = getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
        val imageUrl = sharedPreferences.getString("memberImg", null)

        // 저장된 이미지가 있다면 프로필 이미지 띄우기
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(this@TeacherChangeInfoActivity)
                .load(imageUrl)
                .into(changeteacherIv_img)
        }


        //화면에 고수 정보 띄우기
        // memberId가 null이 아닐 때
        if (memberId != null) {
            teacherReference.child(memberId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        // 화면에 띄우기
                        changeteacherEt_name.setText(snapshot.child("teacherName").value.toString())
                        changeteacherEt_content.setText(snapshot.child("teacherContent").value.toString())
                        changeteacherEt_city.setText(snapshot.child("teacherCity").value.toString())
                        changeteacherEt_gender.setText(snapshot.child("teacherGender").value.toString())
                        changeteacherEt_oneline.setText(snapshot.child("teacherOneLine").value.toString())
                        changeteacherEt_service.setText(snapshot.child("teacherService").value.toString())
                        changeteacherEt_teltime.setText(snapshot.child("teacherTelTime").value.toString())
                        changeteacherEt_worktime.setText(snapshot.child("teacherWorkTime").value.toString())

                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.d("firebase에러", databaseError.toString())
                    }
                })

        }

        //수정 버튼 눌렀을때 업데이트 되게 하기
        changeteacherBtn_update.setOnClickListener {
            // 입력 필드에서 값 가져오기
            val updatedTeacherName = changeteacherEt_name.text.toString()
            val updatedTeacherContent = changeteacherEt_content.text.toString()
            val updatedTeacherCity = changeteacherEt_city.text.toString()
            val updatedTeacherGender = changeteacherEt_gender.text.toString()
            val updatedTeacherOneLine = changeteacherEt_oneline.text.toString()
            val updatedTeacherService = changeteacherEt_service.text.toString()
            val updatedTeacherTelTime = changeteacherEt_teltime.text.toString()
            val updatedTeacherWorkTime = changeteacherEt_worktime.text.toString()

            // memberId가 null이 아닐 때
            if (memberId != null) {
                // 데이터베이스 업데이트
                teacherReference.child(memberId).child("teacherName").setValue(updatedTeacherName)
                teacherReference.child(memberId).child("teacherContent").setValue(updatedTeacherContent)
                teacherReference.child(memberId).child("teacherCity").setValue(updatedTeacherCity)
                teacherReference.child(memberId).child("teacherGender").setValue(updatedTeacherGender)
                teacherReference.child(memberId).child("teacherOneLine").setValue(updatedTeacherOneLine)
                teacherReference.child(memberId).child("teacherService").setValue(updatedTeacherService)
                teacherReference.child(memberId).child("teacherTelTime").setValue(updatedTeacherTelTime)
                teacherReference.child(memberId).child("teacherWorkTime").setValue(updatedTeacherWorkTime)
                Toast.makeText(applicationContext,"고수 프로필 수정 완료",Toast.LENGTH_SHORT).show()
                Log.d("고수프로필수정완료", "고수프로필수정완료")

                val intent = Intent(this,MypageActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun getMemberInfoFromSpf(): String? {
        val sharedPreferences = getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
        return sharedPreferences.getString("memberId", null)
    }
}
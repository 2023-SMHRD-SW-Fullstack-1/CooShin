package com.smhrd.android.User

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.smhrd.android.Data.MemberVO
import com.smhrd.android.MainActivity
import com.smhrd.android.R


class InfoChangeActivity : AppCompatActivity() {
    lateinit var infochangeEt_updatePw: EditText
    lateinit var infochangeEt_updateTel: EditText
    lateinit var infochangeEt_updateNick: EditText
    lateinit var infochangeBtn_update: Button
    lateinit var infochangeBtn_deleteMem : Button
    val database = Firebase.database

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_change)
        infochangeEt_updatePw = findViewById(R.id.infochangeEt_updatePw)
        infochangeEt_updateTel = findViewById(R.id.infochangeEt_updateTel)
        infochangeEt_updateNick = findViewById(R.id.infochangeEt_updateNick)
        infochangeBtn_update = findViewById(R.id.infochangeBtn_update)
        infochangeBtn_deleteMem = findViewById(R.id.infochangeBtn_deleteMem)

        //firebase에서 데이터를 가져오기 위함!
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("memberList")

        //화면 나올 때 회원정보 바로 보이게 하기
        val memberId = getMemberInfoFromSpf()
        if (memberId != null) {
            reference.child(memberId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val member = dataSnapshot.child("member").getValue(MemberVO::class.java)
                    //MemberVO타입 member가 null이 아니라면 화면에 회원정보 띄우기
                    if (member != null) {
                        Log.d("member.참조닉네임", member.memberNick)
                        infochangeEt_updatePw.setText(member.memberPw)
                        infochangeEt_updateTel.setText(member.memberTel)
                        infochangeEt_updateNick.setText(member.memberNick)
                    } else {
                        Toast.makeText(applicationContext,"회원 정보 불러오기 실패",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("firebase에러",databaseError.toString())
                }
            })
        } else {
            // memberId가 SharedPreferences에 저장되어 있지 않을 때
        }

        //회원정보수정 버튼 눌렀을때 업데이트 하기!
        infochangeBtn_update.setOnClickListener {
            // EditText에서 새로운 값 가져오기
            val newPw = infochangeEt_updatePw.text.toString()
            val newTel = infochangeEt_updateTel.text.toString()
            val newNick = infochangeEt_updateNick.text.toString()

            // 유효성 검사도 추가하기
            // memberId가 null이 아닌 경우에만 업데이트
            memberId?.let {
                // pw,tel,nick  업데이트
                reference.child(it).child("member").child("memberPw").setValue(newPw)
                reference.child(it).child("member").child("memberTel").setValue(newTel)
                reference.child(it).child("member").child("memberNick").setValue(newNick)
                    .addOnSuccessListener {
                        // 업데이트 완료
                        Toast.makeText(applicationContext, "회원 정보 수정 성공", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { exception ->
                        // 업데이트 실패 시
                        Toast.makeText(applicationContext, "회원 정보 수정 실패", Toast.LENGTH_SHORT).show()
                    }
            } ?: run {
                // memberId 없을때

                Toast.makeText(applicationContext, "회원 정보 변경 실패", Toast.LENGTH_SHORT).show()
            }
        }
        //회원탈퇴 버튼 눌렀을때
        infochangeBtn_deleteMem.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("회원 탈퇴")
            builder.setMessage("회원 탈퇴하시겠습니까?")
            builder.setPositiveButton("예", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "회원을 탈퇴합니다.", Toast.LENGTH_SHORT).show()
                if (memberId != null) {
                    reference.child(memberId).removeValue().addOnSuccessListener {
                        // 회원 탈퇴 성공
                        Toast.makeText(applicationContext, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()

                        // 로그아웃 후 로그인 화면으로 이동
                        clearMemberSpf()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener { exception ->
                        // 회원 탈퇴 실패
                        Toast.makeText(applicationContext, "회원 탈퇴 실패: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }

                }
            })
            builder.setNegativeButton("아니요", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "회원 탈퇴를 취소합니다.", Toast.LENGTH_SHORT).show()
            })

            builder.show()
        }

    }
    //memberId SPF가져오기
    fun getMemberInfoFromSpf(): String? {
        val sharedPreferences = getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
        return sharedPreferences.getString("memberId", null)
    }

    // SharedPreferences의 회원 정보 초기화
    fun clearMemberSpf() {
        val sharedPreferences = getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

}

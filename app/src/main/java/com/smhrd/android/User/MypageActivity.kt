package com.smhrd.android.User

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.smhrd.android.Data.MemberIdVO
import com.smhrd.android.Data.MemberVO
import com.smhrd.android.R

class MypageActivity : AppCompatActivity() {
    lateinit var mypageIv_img: ImageView
    lateinit var mypageBtn_img: ImageButton
    lateinit var mypageTv_nick: TextView
    lateinit var mypageBtn_infoChange: Button
    lateinit var mypageBtn_likesGosu: Button
    lateinit var mypageBtn_addGosu : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        mypageIv_img = findViewById(R.id.mypageIv_img)
        mypageBtn_img = findViewById(R.id.mypageBtn_img)
        mypageTv_nick = findViewById(R.id.mypageTv_nick)
        mypageBtn_infoChange = findViewById(R.id.mypageBtn_infoChange)
        mypageBtn_likesGosu = findViewById(R.id.mypageBtn_likesGosu)
        mypageBtn_addGosu = findViewById(R.id.mypageBtn_addGosu)

//firebase에서 데이터를 가져오기 위함!
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("memberList")

        val memberId = getMemberInfoFromSpf()
        Log.d("memberID",memberId.toString())
        if (memberId != null) {
            reference.child(memberId).child("member").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val memberNick = dataSnapshot.child("memberNick").getValue(String::class.java)
                    Log.d("memberNick", memberNick.toString())

// memberNick이 null이 아니라면 화면에 회원정보 띄우기
                    if (memberNick != null) {
                        mypageTv_nick.text= "${memberNick}고객님"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("데이터베이스오류", error.toString())
                }
            })
        }


// 마이페이지 프로필 이미지 버튼 눌렀을 때
        mypageBtn_img.setOnClickListener{

        }

// 마이페이지 회원정보수정 눌렀을때
        mypageBtn_infoChange.setOnClickListener{
            val intent = Intent(this@MypageActivity, InfoChangeActivity::class.java)
            startActivity(intent)
        }

// 마이페이지 찜한 고수 눌렀을때
        mypageBtn_likesGosu.setOnClickListener{

        }

//마이페이지 고수 등록 눌렀을때
        mypageBtn_addGosu.setOnClickListener{
            val intent = Intent(this@MypageActivity, AddGosuActivity::class.java)
            startActivity(intent)
        }
    }

    // SharedPreferences에서 memberId 가져오는 함수
//    fun getMemberInfoFromSpf(): Pair<String?, String?> {
//        val sharedPreferences = getSharedPreferences("memberInfoSpf", MODE_PRIVATE)
//        val memberId = sharedPreferences.getString("memberId", null)
//        val memberNick = sharedPreferences.getString("memberNick", null)
//        return memberId to memberNick
//    }
    fun getMemberInfoFromSpf(): String? {
        val sharedPreferences = getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
        return sharedPreferences.getString("memberId", null)
    }
}



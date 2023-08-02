package com.smhrd.android.User

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smhrd.android.Data.MemberIdVO
import com.smhrd.android.R

class InfoChangeActivity : AppCompatActivity() {
    lateinit var infochangeEt_updatePw: EditText
    lateinit var infochangeEt_updateTel: EditText
    lateinit var infochangeEt_updateNick: EditText
    lateinit var infochangeBtn_update: Button
    val database = Firebase.database

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_change)
        infochangeEt_updatePw = findViewById(R.id.infochangeEt_updatePw)
        infochangeEt_updateTel = findViewById(R.id.infochangeEt_updateTel)
        infochangeEt_updateNick = findViewById(R.id.infochangeEt_updateNick)
        infochangeBtn_update = findViewById(R.id.infochangeBtn_update)

        val memberId = getMemberInfoFromSpf()
        if (memberId != null) {
            fetchMemberInfo(memberId, { member ->
                Log.d("infochange",member.member?.memberPw.toString() )
                infochangeEt_updatePw.setText(member.member?.memberPw)
                infochangeEt_updateTel.setText(member.member?.memberTel)
                infochangeEt_updateNick.setText(member.member?.memberNick)
            }, {
                // Handle failure here
            })
        }

        //회원정보 수정 버튼 눌렀을때
        infochangeBtn_update.setOnClickListener {

        }
    }

    private fun fetchMemberInfo(
        memberId: String,
        onSuccessListener: (member: MemberIdVO) -> Unit,
        onFailureListener: () -> Unit
    ) {
        val ref = database.getReference("MemberList").child(memberId)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val memberInfo = dataSnapshot.getValue(MemberIdVO::class.java)
                if (memberInfo != null) {
                    onSuccessListener(memberInfo)
                } else {
                    onFailureListener()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onFailureListener()
            }
        })
    }

    private fun getMemberInfoFromSpf(): String? {
        val sharedPreferences = getSharedPreferences("your_spf_name", Context.MODE_PRIVATE)
        return sharedPreferences.getString("member_id", null)
    }
}

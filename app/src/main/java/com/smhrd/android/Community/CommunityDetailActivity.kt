package com.smhrd.android.Community

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.smhrd.android.R

class CommunityDetailActivity : AppCompatActivity() {
    lateinit var img : ImageView
    lateinit var tv_title : TextView
    lateinit var tv_content : TextView
    lateinit var et_comment : EditText
    lateinit var btn_comment : Button
    lateinit var rc_comment : RecyclerView
    lateinit var ibtn_back : ImageButton
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_detail)
        img = findViewById(R.id.img)
        tv_title = findViewById(R.id.tv_title)
        tv_content = findViewById(R.id.tv_content)
        et_comment = findViewById(R.id.et_comment)
        btn_comment = findViewById(R.id.btn_comment)
        rc_comment = findViewById(R.id.rc_comment)
        ibtn_back = findViewById(R.id.ibtn_back)

    }

    fun updateComment(commentId: String, updatedComment: String) {
        // Firebase Realtime Database의 댓글 데이터 참조 경로
        val commentRef = FirebaseDatabase.getInstance().getReference("memberList").child(commentId)

        // 댓글 데이터 수정
        commentRef.child("comment").setValue(updatedComment)
            .addOnSuccessListener {
                // 데이터 수정이 완료되었을 때의 처리
                Toast.makeText(this, "댓글이 수정되었습니다.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                // 데이터 수정 중 에러가 발생했을 때의 처리
                Toast.makeText(this, "댓글 수정에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
    }

    fun deleteComment(commentId: String) {
        // Firebase Realtime Database의 댓글 데이터 참조 경로
        val commentRef = FirebaseDatabase.getInstance().getReference("memberList").child(commentId)

        // 댓글 데이터 삭제
        commentRef.removeValue()
            .addOnSuccessListener {
                // 데이터 삭제가 완료되었을 때의 처리
                Toast.makeText(this, "댓글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                // 데이터 삭제 중 에러가 발생했을 때의 처리
                Toast.makeText(this, "댓글 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
    }
}
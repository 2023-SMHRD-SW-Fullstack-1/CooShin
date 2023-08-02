package com.smhrd.android.Community

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.smhrd.android.Data.BoardIdVO
import com.smhrd.android.Data.CommentVO
import com.smhrd.android.Data.MemberIdVO
import com.smhrd.android.Data.MemberVO
import com.smhrd.android.R
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale

class CommunityCreateActivity : AppCompatActivity() {
    lateinit var etTitle : EditText
    lateinit var etContent : EditText
    lateinit var ivUpload : ImageView
    lateinit var btnComplete : Button
    lateinit var back : ImageButton
    lateinit var ibGallery : ImageButton
    lateinit var ibcamera : ImageButton
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_create)

        etTitle = findViewById(R.id.etTitle)
        etContent = findViewById(R.id.etContent)
        ivUpload = findViewById(R.id.ivUpload)
        btnComplete = findViewById(R.id.btnComplete)
        back = findViewById(R.id.back)
        ibGallery = findViewById(R.id.ibGallery)
        ibcamera = findViewById(R.id.ibCamera)

        val databaseReference = FirebaseDatabase.getInstance().reference
        val spf = getSharedPreferences("mySPF", Context.MODE_PRIVATE)
        val storageReference = FirebaseStorage.getInstance().reference



        btnComplete.setOnClickListener {
            val title = etTitle.text.toString()
            val content = etContent.text.toString()

//            val firebaseUser = FirebaseAuth.getInstance().currentUser

            val loggedInUserId = intent.getStringExtra("loggedInUserId")
            val user = MemberIdVO().member
            Log.d("userid", loggedInUserId.toString())


            if (title.isNotEmpty() && content.isNotEmpty() && loggedInUserId != null) {
                // Create a new MemberIdVO object to add the board to the member's board list
                val memberIdVO = MemberIdVO(
                    member = MemberVO() // Set email and pw to null as they are not needed here
                )

                val boardId = databaseReference.child("boardList").push().key
                val commentList: ArrayList<CommentVO> = ArrayList() // Add your comments if available

                val boardIdVO = BoardIdVO(
                    boardTitle = title,
                    boardContent = content,
                    boardWriter = loggedInUserId,
                    boardDate = SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss",
                        Locale.getDefault()
                    ).format(Date()),
                    boardImg = null,
                    boardViews = 0,
                    boardLikes = 0,
                    comment = commentList
                )


                if (boardId != null) {
                    // Save the BoardIdVO object under "boardList" with the generated boardId
                    databaseReference.child("boardList").child(boardId).setValue(boardIdVO)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Add the boardId to the member's board list
                                databaseReference.child("memberList")
                                    .child(loggedInUserId)
                                    .child("board")
                                    .child(boardId)
                                    .setValue(true)
                                    .addOnCompleteListener { memberTask ->
                                        if (memberTask.isSuccessful) {
                                            Toast.makeText(
                                                this,
                                                "Post saved successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                this,
                                                "Failed to save post",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Failed to save post",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            } else {
                // Handle the case where the title, content, or loggedInUserId is null or empty
                Toast.makeText(
                    applicationContext,
                    "Please fill all fields and log in.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        }

    }

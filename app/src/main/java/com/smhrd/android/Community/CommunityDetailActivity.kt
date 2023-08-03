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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.smhrd.android.Data.CommentVO
import com.smhrd.android.R

class CommunityDetailActivity : AppCompatActivity() {
    lateinit var img: ImageView
    lateinit var tv_title: TextView
    lateinit var tv_content: TextView
    lateinit var et_comment: EditText
    lateinit var btn_comment: Button
    lateinit var rc_comment: RecyclerView
    lateinit var ibtn_back: ImageButton
    val databaseReference = FirebaseDatabase.getInstance().reference

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

        val intent = getIntent()


        tv_title.text = intent.getStringExtra("c_title")
        tv_content.text = intent.getStringExtra("c_content")


        val boardId = intent.getStringExtra("boardId")
        val content = intent.getStringExtra("c_content")
        fetchComments(boardId)

        btn_comment.setOnClickListener {
            val commentText = et_comment.text.toString().trim()

            if (commentText.isNotEmpty()) {
                // Create a new CommentVO object for the new comment
                val newComment = CommentVO(boardId!!, commentText)

                // Add the new comment to the comment list and update Firebase
                addComment(newComment)

                // Clear the EditText after adding the comment
                et_comment.text.clear()
            } else {
                // Show a toast if the comment text is empty
                Toast.makeText(this, "Comment cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    var commentArr: ArrayList<CommentVO> = arrayListOf()
    private fun fetchComments(boardId: String?) {
        if (boardId != null) {
            val commentsRef = databaseReference.child("boardList").child(boardId).child("comments")
            commentsRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val comments = commentArr
                    for (commentSnapshot in snapshot.children) {
                        val comment = commentSnapshot.getValue(CommentVO::class.java)
                        comment?.let { comments.add(it) }
                    }

                    // After fetching the comments, create the adapter and set it to the RecyclerView
                    val adapter = CommentAdapter(comments)
                    rc_comment.layoutManager = LinearLayoutManager(this@CommunityDetailActivity)
                    rc_comment.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle any errors that may occur during data retrieval
                    Toast.makeText(
                        this@CommunityDetailActivity,
                        "Failed to fetch comments.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun addComment(comment: CommentVO) {
        val boardId = comment.commentWriter
        if (boardId != null) {
            val commentsRef = databaseReference.child("boardList").child(boardId).child("comments")
            val newCommentRef = commentsRef.push()
            newCommentRef.setValue(comment)
                .addOnSuccessListener {
                    // Comment added successfully
                    Toast.makeText(this, "Comment added.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // Error occurred while adding the comment
                    Toast.makeText(this, "Failed to add comment.", Toast.LENGTH_SHORT).show()
                }
        }
    }
}


package com.smhrd.android.Community

import android.annotation.SuppressLint
import android.content.Context
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

    private var adapter: CommentAdapter? = null

    @SuppressLint("MissingInflatedId", "ResourceType")
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


        val spf = getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
        var loginMember = spf?.getString("memberId", "")
        val boardId = intent.getStringExtra("boardId")
        val content = intent.getStringExtra("c_content")
//        fetchComments(boardId)

        fetchComments(boardId)


        //load comment  => 결과값을 ArrayList<CommentVO>
        //commentArr에 담아주기


        var commentArr: ArrayList<CommentVO> = arrayListOf()

        val adapter = CommentAdapter(this, commentArr)

        rc_comment.adapter = adapter
        rc_comment.layoutManager = LinearLayoutManager(this)
        val commentText = boardId?.let { it1 ->
            content?.let { it2 ->
                CommentVO(
                    commentWriter = loginMember!!,
                    commentContent = et_comment.text.toString()
                )
            }
        }
        commentArr.add(commentText!!)


        btn_comment.setOnClickListener {
            val commentText = boardId?.let { it1 ->
                content?.let { it2 ->
                    CommentVO(
                        commentWriter = loginMember!!,
                        commentContent = et_comment.text.toString()
                    )
                }
            }
            commentArr.add(commentText!!)



            if (commentText != null) {
                // Generate a unique ID for the comment
//                val commentId = FirebaseDatabase.getInstance().reference.push().key ?: ""

                if (boardId != null) {
                    databaseReference.child("boardList").child(boardId).get()
                }

                // Save the comment to Firebase using the generated ID
                val commentRef =
                    FirebaseDatabase.getInstance().getReference("boardList").child(boardId!!)
                commentRef.child("comment").setValue(commentArr)
                    .addOnSuccessListener {
                        // Comment added successfully
                        Toast.makeText(this, "Comment added.", Toast.LENGTH_SHORT).show()
                        // Clear the EditText after adding the comment
                        et_comment.text.clear()
                    }
                    .addOnFailureListener {
                        // Error occurred while adding the comment
                        Toast.makeText(this, "Failed to add comment.", Toast.LENGTH_SHORT).show()
                    }
            } else {
                // Show a toast if the comment text is empty
                Toast.makeText(this, "Comment cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }


        // Call the fetchComments function with the boardId to fetch and display comments
        fetchComments(boardId)

    }

    private fun fetchComments(boardId: String?) {
        if (boardId != null) {
            val commentRef = databaseReference.child("boardList").child(boardId).child("comment")
            commentRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val comments = ArrayList<CommentVO>()

                    for (commentSnapshot in snapshot.children) {
                        val commentWriter =
                            commentSnapshot.child("commentWriter").getValue(String::class.java)
                        val commentContent =
                            commentSnapshot.child("commentContent").getValue(String::class.java)

                        if (commentWriter != null && commentContent != null) {
                            val comment = CommentVO(commentWriter, commentContent)
                            comments.add(comment)
                        }
                    }

                    // After fetching the comments, update the adapter with the new data
                    adapter?.updateData(comments)
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

}


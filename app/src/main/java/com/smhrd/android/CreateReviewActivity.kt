package com.smhrd.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smhrd.android.Chatting.ReviewChildEvent
import com.smhrd.android.Data.ReviewVO

class CreateReviewActivity : AppCompatActivity() {

    lateinit var ivStar1: ImageView
    lateinit var ivStar2: ImageView
    lateinit var ivStar3: ImageView
    lateinit var ivStar4: ImageView
    lateinit var ivStar5: ImageView
    lateinit var edtWriteReview: EditText
    lateinit var btnSubmitReview: Button

    var score = 0
    var isStar1Click = false
    var isStar2Click = false
    var isStar3Click = false
    var isStar4Click = false
    var isStar5Click = false

    val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_review)

        ivStar1 = findViewById(R.id.ivStar1)
        ivStar2 = findViewById(R.id.ivStar2)
        ivStar3 = findViewById(R.id.ivStar3)
        ivStar4 = findViewById(R.id.ivStar4)
        ivStar5 = findViewById(R.id.ivStar5)
        edtWriteReview = findViewById(R.id.edtWriteReview)
        btnSubmitReview = findViewById(R.id.btnSubmitReview)

        var intent = getIntent()
        var userId = intent.getStringExtra("userId").toString()
        var teacherId = intent.getStringExtra("teacherId").toString()

        var data = ArrayList<ReviewVO>()

        var reviewRef = database.getReference("teacherList").child(teacherId).child("reviewList")

        btnSubmitReview.setOnClickListener {
            var memberId = userId
            var reviewContent = edtWriteReview.text.toString()
            var reviewImg = null
            var reviewDate = null
            var reviewStars = score

            reviewRef.push()
                .setValue(ReviewVO(memberId, reviewContent, reviewImg, reviewDate, reviewStars))
            finish()
        }
        reviewRef.addChildEventListener(ReviewChildEvent(data))


        ivStar1.setOnClickListener {
            if (!isStar1Click) {
                ivStar1.setImageResource(R.drawable.star)
                ivStar2.setImageResource(R.drawable.star1)
                ivStar3.setImageResource(R.drawable.star1)
                ivStar4.setImageResource(R.drawable.star1)
                ivStar5.setImageResource(R.drawable.star1)
                score = 1
                isStar1Click = true
            } else {
                ivStar1.setImageResource(R.drawable.star1)
                ivStar2.setImageResource(R.drawable.star1)
                ivStar3.setImageResource(R.drawable.star1)
                ivStar4.setImageResource(R.drawable.star1)
                ivStar5.setImageResource(R.drawable.star1)
                score = 0
                isStar1Click = false
                isStar2Click = false
                isStar3Click = false
                isStar4Click = false
                isStar5Click = false
            }
            Log.d("1번 버튼 클릭", score.toString())
        }
        ivStar2.setOnClickListener {
            if (!isStar2Click) {
                ivStar1.setImageResource(R.drawable.star)
                ivStar2.setImageResource(R.drawable.star)
                ivStar3.setImageResource(R.drawable.star1)
                ivStar4.setImageResource(R.drawable.star1)
                ivStar5.setImageResource(R.drawable.star1)
                score = 2
                isStar2Click = true
            } else {
                ivStar3.setImageResource(R.drawable.star1)
                ivStar4.setImageResource(R.drawable.star1)
                ivStar5.setImageResource(R.drawable.star1)
                score = 0
                isStar2Click = false
                isStar3Click = false
                isStar4Click = false
                isStar5Click = false
            }
            Log.d("2번 버튼 클릭", score.toString())
        }
        ivStar3.setOnClickListener {
            if (!isStar3Click) {
                ivStar1.setImageResource(R.drawable.star)
                ivStar2.setImageResource(R.drawable.star)
                ivStar3.setImageResource(R.drawable.star)
                ivStar4.setImageResource(R.drawable.star1)
                ivStar5.setImageResource(R.drawable.star1)
                score = 3
                isStar3Click = true
            } else {
                ivStar4.setImageResource(R.drawable.star1)
                ivStar5.setImageResource(R.drawable.star1)
                score = 0
                isStar3Click = false
                isStar4Click = false
                isStar5Click = false
            }
            Log.d("3번 버튼 클릭", score.toString())
        }
        ivStar4.setOnClickListener {
            if (!isStar4Click) {
                ivStar1.setImageResource(R.drawable.star)
                ivStar2.setImageResource(R.drawable.star)
                ivStar3.setImageResource(R.drawable.star)
                ivStar4.setImageResource(R.drawable.star)
                ivStar5.setImageResource(R.drawable.star1)
                score = 4
                isStar4Click = true
            } else {
                ivStar5.setImageResource(R.drawable.star1)
                score = 0
                isStar4Click = false
                isStar5Click = false
            }
            Log.d("4번 버튼 클릭", score.toString())
        }
        ivStar5.setOnClickListener {
            if (!isStar5Click) {
                ivStar1.setImageResource(R.drawable.star)
                ivStar2.setImageResource(R.drawable.star)
                ivStar3.setImageResource(R.drawable.star)
                ivStar4.setImageResource(R.drawable.star)
                ivStar5.setImageResource(R.drawable.star)
                score = 5
                isStar5Click = true
            } else {
                score = 0
                isStar5Click = false
            }
            Log.d("5번 버튼 클릭", score.toString())
        }
    }
}
package com.smhrd.android.Teacher

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smhrd.android.Chatting.ChattingRoomActivity
import com.smhrd.android.Data.TeacherIdVO
import com.smhrd.android.Fragment.ChattingFragment
import com.smhrd.android.R

class SearchTeacher_detailActivity : AppCompatActivity() {

    lateinit var ibtnToBack : ImageButton
    lateinit var tvTeacherName : TextView
    lateinit var btnIsLike : ImageButton
    lateinit var ivTeacherImage : ImageView
    lateinit var tvTeacherOneLine : TextView
    lateinit var tvTeacherLocate : TextView
    lateinit var btnReview : Button
    lateinit var btnServiceInfo : Button
    lateinit var btnTeacherInfo : Button
    lateinit var tvTelTime : TextView
    lateinit var tvWorkTime : TextView
    lateinit var tvTeacherContent : TextView
    lateinit var tvStarAvg : TextView
    lateinit var tvReviewNum : TextView
    lateinit var tvReviewStar_1 : TextView
    lateinit var ivReviewImg_1 : ImageView
    lateinit var tvReviewContent_1 : TextView
    lateinit var tvReviewStar_2 : TextView
    lateinit var ivReviewImg_2 : ImageView
    lateinit var tvReviewContent_2 : TextView
    lateinit var tvToReviewList : TextView

    lateinit var btnSendTalk : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_teacher_detail)

        ibtnToBack = findViewById(R.id.ibtnToBack_detail)
        tvTeacherName  = findViewById(R.id.tvTeacherName_detail)
        btnIsLike = findViewById(R.id.btnIsLike_detail)
        ivTeacherImage = findViewById(R.id.ivTeacherImg_detail)
        tvTeacherOneLine = findViewById(R.id.tvTeacherOneLine_detail)
        tvTeacherLocate = findViewById(R.id.tvTeacherLocate_detail)
        btnReview = findViewById(R.id.btnReview_detail)
        btnServiceInfo = findViewById(R.id.btnServiceInfo_detail)
        btnTeacherInfo = findViewById(R.id.btnTeacherInfo_detail)
        tvTelTime = findViewById(R.id.tvTelTime_detail)
        tvWorkTime = findViewById(R.id.tvWorkTime_detail)
        tvTeacherContent = findViewById(R.id.tvTeacherContent_detail)
        tvStarAvg = findViewById(R.id.tvStarAvg_detail)
        tvReviewNum = findViewById(R.id.tvReviewNum_detail)
        tvReviewStar_1 = findViewById(R.id.tvReviewStar1_detail)
        ivReviewImg_1 = findViewById(R.id.ivReviewImg1_detail)
        tvReviewContent_1 = findViewById(R.id.tvReviewContent1_detail)
        tvReviewStar_2 = findViewById(R.id.tvReviewStar2_detail)
        ivReviewImg_2 = findViewById(R.id.ivReviewImg2_detail)
        tvReviewContent_2 = findViewById(R.id.tvReviewContent2_detail)
        tvToReviewList = findViewById(R.id.tvToReviewList_detail)
        btnSendTalk = findViewById(R.id.btnSendTalk_detail)
        val gson = Gson()

        //로그인한 사용자 정보 불러오기
        var sharedPreferences = getSharedPreferences("memberInfoSpf", MODE_PRIVATE)
        val memberId = sharedPreferences.getString("memberId", null)

        //코신 정보 불러오기
        val intent = getIntent()
        val teacherId = intent.getStringExtra("teacherId")
//        val teacherVO = gson.toJson(intent.getStringExtra("teacherVO"))

        // JSON 문자열을 Map으로 파싱
//        val mapType = object : TypeToken<TeacherIdVO>() {}.type
//        val dataMap: TeacherIdVO = gson.fromJson(teacherVO, mapType)

//        Log.d("sse", dataMap.teacherOneLine)

        //뒤로가기 버튼
        ibtnToBack.setOnClickListener { onBackPressed() }


        //채팅하기 버튼
        btnSendTalk.setOnClickListener {
            val sendIntent = Intent(this.applicationContext, ChattingRoomActivity::class.java)
            var roomId = " ${memberId} room ${teacherId} "

            sendIntent.putExtra("roomId", roomId)
            startActivity(sendIntent)
        }


    }
}
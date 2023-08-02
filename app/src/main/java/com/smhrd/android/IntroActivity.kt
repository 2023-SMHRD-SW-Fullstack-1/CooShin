package com.smhrd.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

      //intro 화면 3초 동안 보여준 후 Mainactivity로 화면 전환
        val handler = Handler()
        handler.postDelayed({
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}
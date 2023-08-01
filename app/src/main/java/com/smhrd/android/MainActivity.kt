package com.smhrd.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smhrd.android.Fragment.ChattingFragment
import com.smhrd.android.Fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    lateinit var fl : FrameLayout
    lateinit var bnv : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fl = findViewById(R.id.fl)
        bnv = findViewById(R.id.bnv)

        supportFragmentManager.beginTransaction().replace(R.id.fl, HomeFragment()).commit()

        bnv.setOnItemSelectedListener{
            Log.d("id", it.itemId.toString())

            when(it.itemId){
                //switch~case문과 비슷
                R.id.tab1 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fl,
                        HomeFragment()
                    ).commit()
                }
                R.id.tab4 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fl,
                        ChattingFragment()
                    ).commit()
                }

            }
            //boolean : true / false(이벤트 인식을 잘 못함)
            true

        }


    }//onCreate 끝
}
package com.smhrd.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smhrd.android.Fragment.ChattingFragment
import com.smhrd.android.Fragment.CommunityFragment
import com.smhrd.android.Fragment.HomeFragment
import com.smhrd.android.Fragment.SearchGosuFragment
import com.smhrd.android.User.LoginActivity

class MainActivity : AppCompatActivity() {

    lateinit var fl : FrameLayout
    lateinit var bnv : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fl = findViewById(R.id.fl)
        bnv = findViewById(R.id.bnv)


        //firebase
        val database = Firebase.database
        val myRef = database.getReference("memberList")

        supportFragmentManager.beginTransaction().replace(R.id.fl, HomeFragment()).commit()

        //Fragmemt 클릭했을 때
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
                R.id.tab2 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fl,
                        SearchGosuFragment()
                    ).commit()
                }
                R.id.tab3 -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fl,
                        CommunityFragment()
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
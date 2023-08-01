package com.smhrd.android.Community

import android.annotation.SuppressLint
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import com.smhrd.android.R

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
        ibcamera  = findViewById(R.id.ibCamera)

    }
}
package com.smhrd.android.User

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import com.smhrd.android.Data.MemberIdVO
import com.smhrd.android.Data.MemberVO
import com.smhrd.android.R

class MypageActivity : AppCompatActivity() {
    lateinit var mypageIv_img: ImageView
    lateinit var mypageBtn_img: ImageButton
    lateinit var mypageTv_nick: TextView
    lateinit var mypageBtn_infoChange: Button
    lateinit var mypageBtn_likesGosu: Button
    lateinit var mypageBtn_addGosu : Button
   // val STORAGE_CODE = 1000
    companion object {
        const val STORAGE_REQUEST_CODE = 100
    }


    //이미지 불러오기
    override fun onResume() {
        super.onResume()

        // SharedPreferences에서 이미지 URL 가져오기
        val sharedPreferences = getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
        val imageUrl = sharedPreferences.getString("memberImg", null)

        // 저장된 이미지가 있다면 프로필 이미지로 설정
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(this@MypageActivity)
                .load(imageUrl)
                .into(mypageIv_img)
        }
    }

    //프로필 업로드하기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == STORAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val uri: Uri? = data.data

            uri?.let {
                val memberId = getMemberInfoFromSpf()

                val storageRef = Firebase.storage.reference
                val userProfileRef = storageRef.child("memberList/${memberId}")
                Log.d("userProfileRef", userProfileRef.toString())
                val uploadTask = userProfileRef.putFile(it)
                uploadTask.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { downloadUri ->
                        Log.d("프로필 업로드", "업로드 완료")
                        updateUserImage(memberId, downloadUri.toString())

                        // 성공한 경우 Glide를 사용하여 이미지를 이미지 뷰에 표시
                        Glide.with(this@MypageActivity)
                            .load(downloadUri)
                            .into(mypageIv_img)
                    }
                }.addOnFailureListener { exception ->
                    Log.d("프로필 업로드 실패", exception.toString())
                }
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        mypageIv_img = findViewById(R.id.mypageIv_img)
        mypageBtn_img = findViewById(R.id.mypageBtn_img)
        mypageTv_nick = findViewById(R.id.mypageTv_nick)
        mypageBtn_infoChange = findViewById(R.id.mypageBtn_infoChange)
        mypageBtn_likesGosu = findViewById(R.id.mypageBtn_likesGosu)
        mypageBtn_addGosu = findViewById(R.id.mypageBtn_addGosu)

        //firebase에서 데이터를 가져오기 위함!
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("memberList")

        val memberId = getMemberInfoFromSpf()
        Log.d("memberID",memberId.toString())
        if (memberId != null) {
            reference.child(memberId).child("member").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val memberNick = dataSnapshot.child("memberNick").getValue(String::class.java)
                    Log.d("memberNick", memberNick.toString())

            // memberNick이 null이 아니라면 화면에 회원정보 띄우기
                    if (memberNick != null) {
                        mypageTv_nick.text= "${memberNick}고객님"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("데이터베이스오류", error.toString())
                }
            })
        }


// 마이페이지 프로필 이미지 버튼 눌렀을 때
        mypageBtn_img.setOnClickListener{

            val intent = Intent(Intent.ACTION_GET_CONTENT)

            intent.type = "image/*"
            // 다른 액티비티 시작된 후 그 액티비티의 결과를 다시 가져오고 싶을 때 사용
           // startActivityForResult(intent, STORAGE_CODE)
            startActivityForResult(intent, STORAGE_REQUEST_CODE)

        }

// 마이페이지 회원정보수정 눌렀을때
        mypageBtn_infoChange.setOnClickListener{
            val intent = Intent(this@MypageActivity, InfoChangeActivity::class.java)
            startActivity(intent)
        }

// 마이페이지 찜한 고수 눌렀을때
        mypageBtn_likesGosu.setOnClickListener{

        }

//마이페이지 고수 등록 눌렀을때
        mypageBtn_addGosu.setOnClickListener{
            val intent = Intent(this@MypageActivity, AddGosuActivity::class.java)
            startActivity(intent)
        }
    }


    fun getMemberInfoFromSpf(): String? {
        val sharedPreferences = getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
        return sharedPreferences.getString("memberId", null)
    }
//    fun updateUserImage(memberId: String?, url: String) {
//        if (!memberId.isNullOrEmpty()) {
//            val reference = FirebaseDatabase.getInstance().getReference("memberList").child(memberId)
//            reference.child("member").child("memberImg").setValue(url).addOnCompleteListener {
//                if (it.isSuccessful) {
//                    Log.d("프로필 사진 업데이트 완료", "URL : $url")
//                } else {
//                    Log.d("프로필 사진 업데이트 실패", it.exception.toString())
//                }
//            }
//        }
//    }

    fun updateUserImage(memberId: String?, url: String) {
        if (!memberId.isNullOrEmpty()) {
            val reference = FirebaseDatabase.getInstance().getReference("memberList").child(memberId)
            reference.child("member").child("memberImg").setValue(url).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("프로필 사진 업데이트 완료", "URL : $url")

                    // SharedPreferences에 이미지 URL 저장
                    val sharedPreferences = getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("memberImg", url)
                    editor.apply()

                } else {
                    Log.d("프로필 사진 업데이트 실패", it.exception.toString())
                }
            }
        }
    }

}



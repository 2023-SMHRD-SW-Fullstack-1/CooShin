package com.smhrd.android.User

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
    lateinit var mypageBtn_changeGosu : Button
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

        Log.d("imageUrl마이페이지", imageUrl.toString())

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



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        mypageIv_img = findViewById(R.id.mypageIv_img)
        mypageBtn_img = findViewById(R.id.mypageBtn_img)
        mypageTv_nick = findViewById(R.id.mypageTv_nick)
        mypageBtn_infoChange = findViewById(R.id.mypageBtn_infoChange)
        mypageBtn_likesGosu = findViewById(R.id.mypageBtn_likesGosu)
        mypageBtn_addGosu = findViewById(R.id.mypageBtn_addGosu)
        mypageBtn_changeGosu = findViewById(R.id.mypageBtn_changeGosu)

        //firebase에서 데이터를 가져오기 위함!
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("memberList")

        val memberId = getMemberInfoFromSpf()
        Log.d("memberID",memberId.toString())

        // SharedPreferences에서 이미지 URL 가져오기
        val sharedPreferences = getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
        val imageUrl = sharedPreferences.getString("memberImg", null)
        if (memberId != null) {
            reference.child(memberId).child("member").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val memberNick = dataSnapshot.child("memberNick").getValue(String::class.java)
                    Log.d("memberNick", memberNick.toString())

                    val googleEmail = getGoogleEmailFromSpf()
                    Log.d("googleEmail",googleEmail.toString())


                    // memberNick이 null이 아니라면 화면에 닉네임 띄우기
                    if (memberNick != null) {
                        mypageTv_nick.text= "${memberNick}고객님"
                    }else if(!googleEmail.isNullOrEmpty()){
                        mypageTv_nick.text = "$googleEmail 고객님"
                        // 구글 로그아웃 버튼 띄우기 (예를 들어, 바로 아래 코드처럼 버튼 객체 생성 후 setOnClickListener 설정)
                        /*
                        val logoutButton = Button(this@MypageActivity)
                        logoutButton.text = "로그아웃"
                        someLayout.addView(logoutButton)
                        logoutButton.setOnClickListener {
                            // 로그아웃 코드
                        }
                        */
                    }

                    // Firebase에서 이미지 URL 가져오기
                    val memberImgUrl = dataSnapshot.child("memberImg").getValue(String::class.java)

                    // memberImgUrl이 null이 아니고 SharedPreferences에 저장된 값과 다른 경우,
                    // Glide를 사용하여 이미지 뷰에 적용하고 SharedPreferences에 URL을 저장
                    if (memberImgUrl != null && memberImgUrl != imageUrl) {
                        Glide.with(this@MypageActivity)
                            .load(memberImgUrl)
                            .into(mypageIv_img)

                        val sharedPreferencesEditor = sharedPreferences.edit()
                        sharedPreferencesEditor.putString("memberImg", memberImgUrl)
                        sharedPreferencesEditor.apply()
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

        // 마이페이지 찜한 코신 눌렀을때
        mypageBtn_likesGosu.setOnClickListener{

        }

        // 마이페이지 코신 프로필 수정 눌렀을 때
        mypageBtn_changeGosu.setOnClickListener {
            val teacherReference = database.getReference("teacherList")
            if (memberId != null) { // memberId가 null이 아닐 때
                teacherReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.d("마이페이지 코신 프로필수정 snapshot",snapshot.toString())
                        if (snapshot.child(memberId).exists()) {
                            // memberId가 teacherList에 존재하는 경우
                            Log.d("memberId teacherList에 존재", snapshot.child(memberId).toString())
                            val intent = Intent(this@MypageActivity, TeacherChangeInfoActivity::class.java)
                            startActivity(intent)
                        } else {
                            // memberId가 teacherList에 존재하지 않는 경우
                            Toast.makeText(this@MypageActivity, "코신으로 등록되지 않은 회원입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.d("Database Error", error.toString())
                    }
                })
            } else {
                Toast.makeText(this@MypageActivity, "로그인 정보를 확인할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }


        //마이페이지 코신 등록 눌렀을때
        mypageBtn_addGosu.setOnClickListener {
            val teacherReference = database.getReference("teacherList")
            if (memberId != null) { //memberId가 null 아닐때
                teacherReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.child(memberId).exists()) {
                            // memberId가 teacherList에 존재하는 경우
                            Toast.makeText(this@MypageActivity, "이미 등록된 코신입니다. 추가 등록은 불가능합니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            // memberId가 teacherList에 존재하지 않는 경우
                            val intent = Intent(this@MypageActivity, AddGosuActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.d("Database Error", error.toString())
                    }
                })
            }
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

    private fun getGoogleEmailFromSpf(): String? {
        val sharedPreferences = getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
        return sharedPreferences.getString("googleEmail", null)
    }

    private fun saveGoogleEmailToSpf(email: String) {
        // getSharedPreferences 객체 생성, "memberInfoSpf"는 파일 이름, Context.MODE_PRIVATE는 접근 권한
        val sharedPreferences = getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
        // SharedPreferences.Editor 인터페이스를 사용하여 데이터 저장
        val editor = sharedPreferences.edit()
        // putString 메소드를 사용하여 이메일 저장 ("googleEmail"은 키, email은 값)
        editor.putString("googleEmail", email)
        // 변경 사항 저장
        editor.apply()
    }



}



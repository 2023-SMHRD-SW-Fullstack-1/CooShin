package com.smhrd.android.User

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smhrd.android.Data.MemberIdVO
import com.smhrd.android.MainActivity
import com.smhrd.android.R
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {

    lateinit var loginEt_id : EditText
    lateinit var loginEt_Pw : EditText
    lateinit var loginBtn_login : Button
    lateinit var loginBtn_google : Button
    lateinit var loginBtn_join : Button
    lateinit var googleSignInClient: GoogleSignInClient

    //구글 로그인을 위한 함수
    companion object {
        private const val RC_SIGN_IN = 9001
    }

     fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestScopes(Scope("https://www.googleapis.com/auth/pubsub"))
            .requestProfile()
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(this, googleSignInOption)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEt_id = findViewById(R.id.loginEt_id)
        loginEt_Pw = findViewById(R.id.loginEt_pw)
        loginBtn_login = findViewById(R.id.loginBtn_login)
        loginBtn_google = findViewById(R.id.loginBtn_google)
        loginBtn_join = findViewById(R.id.loginBtn_join)
        val database = Firebase.database
        val loginRef = database.getReference("memberList")

        googleSignInClient = getGoogleClient()

        //일반 로그인 버튼 눌렀을 때
        loginBtn_login.setOnClickListener{
            var inputId = loginEt_id.text.toString()
            var inputPw = loginEt_Pw.text.toString()

        //id나 pw중 입력하지 않았을때
            if (inputId.isEmpty() || inputPw.isEmpty()) {
                Toast.makeText(applicationContext, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

            loginRef.child(inputId).get().addOnSuccessListener{dataSnapshot->
                if (dataSnapshot.exists()) {
                    val memberIdVO = dataSnapshot.getValue(MemberIdVO::class.java)
                    val user = memberIdVO?.member
                    if (user?.memberPw  == inputPw) {
                        // 로그인 성공
                        Toast.makeText(applicationContext, "로그인 성공!", Toast.LENGTH_SHORT).show()
                        Log.d("로그인성공시닉네임",user.memberNick)
                        memberInfoSpf(inputId, user.memberNick)
                        // 메인 액티비티로 이동
                        var intent = Intent(this@LoginActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                    // 비밀번호 틀렸을때
                        Toast.makeText(applicationContext, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // 아이디 없음
                    Toast.makeText(applicationContext, "해당 아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{
                    error->
                // Database 조회 실패했을때 에러
                Toast.makeText(applicationContext, "로그인 실패! 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                Log.d("error", error.toString())
            }
        }

        //google로그인 버튼 눌렀을 때
        loginBtn_google.setOnClickListener{
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        loginBtn_join.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener(this) {
                // 로그아웃 성공 시 처리, 예: 토스트 메시지
                Toast.makeText(applicationContext, "구글 로그아웃 성공!", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d("구글로그인성공","로그인 성공")
                Toast.makeText(applicationContext, "구글 로그인 성공!", Toast.LENGTH_SHORT).show()
                googleEmailSPF(account)
                val userEmail = account.email
                Log.d("googleemail",userEmail.toString())
            } catch (e: ApiException) {
                Log.d( "Google sign in failed", e.toString())
                Toast.makeText(applicationContext, "구글 로그인 실패! 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //SharedPreferences
    //로그인 했을때 Id, 닉네임 값 저장!
    fun memberInfoSpf(memberId: String,memberNick :String) {
        val sharedPreferences = getSharedPreferences("memberInfoSpf",MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("memberId", memberId)
        editor.putString("memberNick", memberNick)
        editor.apply()
    }
//    fun getmemberInfoSpf(): String? {
//        val sharedPreferences = getSharedPreferences("memberInfoSpf",MODE_PRIVATE)
//        return sharedPreferences.getString("memberId", null)
//    }
//    fun clearmemberInfoSpf() {
//        val sharedPreferences = getSharedPreferences("memberInfoSpf",MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.clear()
//        editor.apply()
//    }
 fun googleEmailSPF(account: GoogleSignInAccount) {
    val sharedPreferences = getSharedPreferences("googleEmail", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        //putString("googleid", account.id)
//        putString("user_display_name", account.displayName)
        putString("googleEmail", account.email)
        apply()
    }
}
}

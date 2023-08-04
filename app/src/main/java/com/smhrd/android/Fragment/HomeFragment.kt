package com.smhrd.android.Fragment

import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smhrd.android.Data.BoardIdVO
import com.smhrd.android.Data.HomeCommunityAdapter
import com.smhrd.android.Data.HomeGosuAdapter
import com.smhrd.android.Data.ReviewVO
import com.smhrd.android.Data.TeacherIdVO
import com.smhrd.android.R
import com.smhrd.android.User.LoginActivity
import com.smhrd.android.User.MypageActivity


class HomeFragment : Fragment() {

    lateinit var rvPopularGosu: RecyclerView
    lateinit var rvCommunity: RecyclerView
    lateinit var btnLogin: Button
    lateinit var btnLogout: Button
    lateinit var ibMyPage: ImageButton
    lateinit var ivC: ImageButton
    lateinit var ivCPlus: ImageButton
    lateinit var ivCShap: ImageButton
    lateinit var ivPython: ImageButton
    lateinit var ivJava: ImageButton
    lateinit var ivJS: ImageButton
    lateinit var ivVB: ImageButton
    lateinit var ivPHP: ImageButton

    lateinit var database: DatabaseReference

    companion object{
        var dataMap_favGosu_home = mutableMapOf<String, TeacherIdVO>()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // inflate 란? xml파일을 View객체로 바꿔주는 것
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        //componet 가져오기
        rvPopularGosu = view.findViewById(R.id.rvPopularGosu)
        rvCommunity = view.findViewById(R.id.rvCommunity)
        btnLogin = view.findViewById(R.id.btnLogin)
        btnLogout = view.findViewById(R.id.btnLogout)
        ibMyPage = view.findViewById(R.id.ibMyPage)
        ivC = view.findViewById(R.id.ivC)
        ivCPlus = view.findViewById(R.id.ivCPlus)
        ivCShap = view.findViewById(R.id.ivCShap)
        ivPython = view.findViewById(R.id.ivPhython)
        ivJava = view.findViewById(R.id.ivJava)
        ivJS = view.findViewById(R.id.ivJS)
        ivVB = view.findViewById(R.id.ivVB)
        ivPHP = view.findViewById(R.id.ivPHP)
        var teacherList = ArrayList<TeacherIdVO>()
        var boardList = ArrayList<BoardIdVO>()
        var review = ArrayList<ReviewVO>()

        //일반 로그인 spf
        val spf = activity?.getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
        var loginMember = spf?.getString("memberId", "")
        Log.d("loginMember", loginMember.toString())

        //구글 로그인 spf
        val spf2 = activity?.getSharedPreferences("googleEmail", Context.MODE_PRIVATE)
        var googleMember = spf2?.getString("googleEmail", "")
        Log.d("googleMember", googleMember.toString())

        //firebase에서 데이터를 가져오기 위함!
        val database = FirebaseDatabase.getInstance()
        val teacherListD = database.getReference("teacherList")
        val boardListD = database.getReference("boardList")


        if (loginMember != null) {
            teacherListD.child(loginMember).child("teacherName")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val teacherName = dataSnapshot.getValue(String::class.java)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("데이터베이스오류", error.toString())
                    }
                })
        }
        if (loginMember != null) {
            teacherListD.child(loginMember).child("reviewList").child("reviewStar")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val reviewStar = dataSnapshot.getValue(String::class.java)
                        Log.d("reviewStar", "Img: $reviewStar")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("데이터베이스오류", error.toString())
                    }
                })
        }


        if (loginMember != null) {
            boardListD.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val key = snapshot.key
                        val boardTitle = snapshot.child("boardTitle").getValue(String::class.java)
                        val boardImg = snapshot.child("boardImg").getValue(String::class.java)
//                        val boardLikes = snapshot.child("boardLikes").getValue(String::class.java)
                        Log.d("boardImg", "Img: $boardImg")
//                        Log.d("boardLikes", "Likes: $boardLikes")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("데이터베이스오류", error.toString())
                }
            })

        }


        //인기있는 코신 출력 ( 별점기준 )
//        for(i in 0 until review.size) {
//            val reviewStar =  review.get(i).reviewStars
//            if(reviewStar > 4){
//                rvPopularGosu.layoutManager = GridLayoutManager(context, 2)
//                var adapter = HomeGosuAdapter(teacherList, review, requireContext())
//                rvPopularGosu.adapter = adapter
//            }
//        }


        // 인기있는 코신 ( likes 기준 )
        //like를 가져올 통신이 필요함
        val gson = Gson()

        var likeTeacher = arrayListOf<TeacherIdVO>()

        teacherListD.get().addOnSuccessListener {
            var jsonResult = gson.toJson(it.getValue())

            // JSON 문자열을 Map으로 파싱
            val mapType = object : TypeToken<Map<String, TeacherIdVO>>() {}.type
            dataMap_favGosu_home = gson.fromJson(jsonResult, mapType)

            // 모든 key값들을 추출하여 출력
            for (value in dataMap_favGosu_home.values) {
                //값들을 전부 data에 저장
                if (value.teacherLikes!! > 3){
                    likeTeacher.add(value)
                 }
            }


            for(i in 0 until likeTeacher.size) {
                    rvPopularGosu.layoutManager = GridLayoutManager(context, 2)
                    var adapter = HomeGosuAdapter(likeTeacher, review, requireContext())
                    rvPopularGosu.adapter = adapter

            }

        }

            //커뮤니티 출력
            for (i in 0 until boardList.size) {
                val boardLikes = boardList.get(i).boardLikes
                if (boardLikes!!.toInt() > 3) {

                    rvCommunity.layoutManager = GridLayoutManager(context, 2)
                    var adapter2 = HomeCommunityAdapter(boardList, requireContext())
                    rvCommunity.adapter = adapter2
                }
            }


            //C 클릭했을 때
            ivC.setOnClickListener {
                navigateToSearchGosuFragment("C")
            }
            //C++ 클릭했을 때
            ivCPlus.setOnClickListener {
                navigateToSearchGosuFragment("C++")
            }
            //C# 클릭했을 때
            ivCShap.setOnClickListener {
                navigateToSearchGosuFragment("C#")
            }
            //Python 클릭했을 때
            ivPython.setOnClickListener {
                navigateToSearchGosuFragment("Python")
            }
            //Java 클릭했을 때
            ivJava.setOnClickListener {
                navigateToSearchGosuFragment("Java")
            }
            //JavaScript 클릭했을 때
            ivJS.setOnClickListener {
                navigateToSearchGosuFragment("JavaScript")
            }
            //Visual Basic 클릭했을 때
            ivVB.setOnClickListener {
                navigateToSearchGosuFragment("Visual Basic")
            }
            //PHP 클릭했을 때
            ivPHP.setOnClickListener {
                navigateToSearchGosuFragment("PHP")
            }


            //로그인 버튼 클릭했을 때
            btnLogin.setOnClickListener {
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
            }

            if (loginMember.toString() == "" && googleMember.toString() == "") {
                //로그인 안되어 있을 때
                btnLogout.visibility = View.INVISIBLE
            } else {
                //로그인되어 있을 때
                btnLogin.visibility = View.INVISIBLE
                btnLogout.setOnClickListener {
                    Toast.makeText(context, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
                    var editor = spf?.edit()
                    editor?.clear()
                    editor?.commit()
                    btnLogout.visibility = View.INVISIBLE
                    btnLogin.visibility = View.VISIBLE
                }
            }

            //마이페이지 버튼 클릭했을 때
            ibMyPage.setOnClickListener {
                val intent = Intent(requireActivity(), MypageActivity::class.java)
                startActivity(intent)
            }

            return view
        }
        private fun navigateToSearchGosuFragment(language: String) {
            val searchGosuFragment = SearchGosuFragment()

            val bundle = Bundle()
            bundle.putString("language", language)
            searchGosuFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fl, searchGosuFragment)
                .addToBackStack(null)
                .commit()
        }

    }

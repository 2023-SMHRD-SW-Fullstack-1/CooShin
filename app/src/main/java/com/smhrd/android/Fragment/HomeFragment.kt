package com.smhrd.android.Fragment

import android.content.Context
import android.content.Intent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.Data.HomeGosuAdapter
import com.smhrd.android.Data.MemberVO
import com.smhrd.android.R
import com.smhrd.android.User.LoginActivity
import com.smhrd.android.User.MypageActivity

class HomeFragment : Fragment() {

    lateinit var rvPopularGosu : RecyclerView
    lateinit var rvCommunity : RecyclerView
    lateinit var btnLogin : Button
    lateinit var ibMyPage : ImageButton



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
        ibMyPage = view.findViewById(R.id.ibMyPage)
        var list = ArrayList<MemberVO>()

        rvPopularGosu.layoutManager = GridLayoutManager(context, 4)
        var adapter = HomeGosuAdapter(list, requireContext())
        rvPopularGosu.adapter = adapter

        rvCommunity.layoutManager = GridLayoutManager(context, 4)

        //로그인 버튼 클릭했을 때
        btnLogin.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }
        //마이페이지 버튼 클릭했을 때
        ibMyPage.setOnClickListener {
            val intent = Intent(requireActivity(), MypageActivity::class.java)
            startActivity(intent)
        }




        return view
    }
}
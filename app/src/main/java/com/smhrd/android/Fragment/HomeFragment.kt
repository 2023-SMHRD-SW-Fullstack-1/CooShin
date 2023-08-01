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
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.Data.HomeGosuAdapter
import com.smhrd.android.R
import com.smhrd.android.User.LoginActivity

class HomeFragment : Fragment() {

    lateinit var rvPopularGosu : RecyclerView
    lateinit var rvCommunity : RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate 란? xml파일을 View객체로 바꿔주는 것
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        //componet 가져오기
        rvPopularGosu = view.findViewById(R.id.rvPopularGosu)
        rvCommunity = view.findViewById(R.id.rvCommunity)

        val spf = requireActivity().getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
        var userId = spf.getString("memberId", null)
        Log.d("로그인 후 유저 아이디 :", userId.toString())





        return view
    }
}
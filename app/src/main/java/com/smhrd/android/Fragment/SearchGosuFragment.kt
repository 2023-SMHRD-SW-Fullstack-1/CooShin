package com.smhrd.android.Fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smhrd.android.Data.MemberIdVO
import com.smhrd.android.Data.MemberVO
import com.smhrd.android.Data.ReviewVO
import com.smhrd.android.Data.SearchTeacherAdapter
import com.smhrd.android.Data.TeacherIdVO
import com.smhrd.android.R

class SearchGosuFragment : Fragment() {

    lateinit var citySpinner: Spinner
    lateinit var sigunguSpinner: Spinner
    lateinit var rvTeacherList : RecyclerView
    var data = ArrayList<TeacherIdVO>()
    var sigunguId : Int = 0
    val database = Firebase.database("https://sumgo-e4e21-default-rtdb.firebaseio.com").reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var views = inflater.inflate(R.layout.fragment_search_gosu, container, false)

        //Spinner
        sigunguSpinner = views.findViewById(R.id.spinLocal_sigungu)
        citySpinner = views.findViewById(R.id.spinLocal_city)
        initAddressSpinner()

        //도시
        var cityAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_region,
            android.R.layout.simple_spinner_item
        )
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = cityAdapter
        
        //시군구
        if(sigunguId != 0){
            var sigunguAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.spinner_region_seoul,
                android.R.layout.simple_spinner_item
            )
            sigunguAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sigunguSpinner.adapter = sigunguAdapter
        }
        
        //리싸이클러 뷰
        rvTeacherList = views.findViewById(R.id.rvTeacherList_search)
//
////        임의데이터 수집
//        var arr = ArrayList<ReviewVO>()
//        arr.add(ReviewVO("id2","좋았어요", "8888", "23일",4))
////
//        database.getReference("teacherList").child("teacher1").setValue(TeacherIdVO("김김김", "자바", "안녕하세요 자바 김김김입니다", "Male",
//            "Java", "서울","13:00~18:00", "13:00~18:00", 44, arr))

        var memberVO = MemberVO(
            memberPw = "123123",
            memberTel = "01010101102",
            memberNick = "alex",
            memberImg = null,
            teacherId = null,
            likeList = null
        )
        var memberIdVO = MemberIdVO(
            member = memberVO,
            chatList = null,
            board = null
        )
        database.child("memberList").child("connecting").setValue(memberIdVO)




        var adapter = SearchTeacherAdapter(requireContext(), R.layout.search_teacher_template, data)
        rvTeacherList.layoutManager = LinearLayoutManager(requireContext())
        rvTeacherList.adapter = adapter


        return views
    }


    fun initAddressSpinner(){
        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ): Unit {
              sigunguId = when (position) {
                    1 -> R.array.spinner_region_seoul
                    2 -> R.array.spinner_region_busan
                    3 -> R.array.spinner_region_daegu
                    4 -> R.array.spinner_region_incheon
                    5 -> R.array.spinner_region_gwangju
                    6 -> R.array.spinner_region_daejeon
                    7 -> R.array.spinner_region_ulsan
                    8 ->R.array.spinner_region_sejong
                    9 -> R.array.spinner_region_gyeonggi
                    10 -> R.array.spinner_region_gangwon
                    11 -> R.array.spinner_region_chung_buk
                    12 -> R.array.spinner_region_chung_nam
                    13 -> R.array.spinner_region_jeon_buk
                    14 -> R.array.spinner_region_jeon_nam
                    15 -> R.array.spinner_region_gyeong_buk
                    16 -> R.array.spinner_region_gyeong_nam
                    17 -> R.array.spinner_region_jeju
                    else -> R.array.spinner_region
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }
}





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
import com.smhrd.android.Data.SearchTeacherAdapter
import com.smhrd.android.Data.TeacherIdVO
import com.smhrd.android.R

class SearchGosuFragment : Fragment() {

    lateinit var citySpinner: Spinner
    lateinit var sigunguSpinner: Spinner
    lateinit var rvTeacherList : RecyclerView
    var data = ArrayList<TeacherIdVO>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var views = inflater.inflate(R.layout.fragment_search_gosu, container, false)

        //Spinner
        sigunguSpinner = views.findViewById(R.id.spinLocal_sigungu)

        citySpinner = views.findViewById(R.id.spinLocal_city)
        var cityAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_region,
            android.R.layout.simple_spinner_item
        )
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = cityAdapter

        initAddressSpinner()

        var sigunguAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_region,
            android.R.layout.simple_spinner_item
        )
        sigunguAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sigunguSpinner.adapter = sigunguAdapter

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
                // 시군구, 동의 스피너를 초기화한다.
                when (position) {
                    0 -> sigunguSpinner.adapter = null
                    1 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_seoul)
                    2 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_busan)
                    3 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_daegu)
                    4 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_incheon)
                    5 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_gwangju)
                    6 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_daejeon)
                    7 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_ulsan)
                    8 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_sejong)
                    9 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_gyeonggi)
                    10 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_gangwon)
                    11 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_chung_buk)
                    12 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_chung_nam)
                    13 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_jeon_buk)
                    14 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_jeon_nam)
                    15 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_gyeong_buk)
                    16 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_gyeong_nam)
                    17 -> sigunguSpinner.adapter.getItem(R.array.spinner_region_jeju)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }
}





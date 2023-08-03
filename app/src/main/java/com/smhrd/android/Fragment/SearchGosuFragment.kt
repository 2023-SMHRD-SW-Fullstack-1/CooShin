package com.smhrd.android.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smhrd.android.Teacher.SearchTeacherAdapter
import com.smhrd.android.Data.TeacherIdVO
import com.smhrd.android.R
import com.smhrd.android.Teacher.SearchTeacherOnClick
import com.smhrd.android.Teacher.SearchTeacher_detailActivity

class SearchGosuFragment : Fragment() {

    lateinit var citySpinner: Spinner
    lateinit var sigunguSpinner: Spinner
    lateinit var rvTeacherList: RecyclerView
    lateinit var serviceSpinner: Spinner
    val gson = Gson()
    var data = ArrayList<TeacherIdVO>()
    var sigunguId: Int = 0
    val database = Firebase.database
    var cityValue: String? = null
    var sigunguValue: String? = null
    var serviceValue: String? = null

    companion object {
    lateinit var dataMap_searchGosu: Map<String, TeacherIdVO>
}
    fun reLoadRV(service : String? = null, city : String? = null, sigungu : String? = null){
        var result = data


        service?.let{result = result.filter{ value -> value.teacherService.contains(service)}.toCollection(ArrayList())}
        city?.let{result = data.filter { value -> value.teacherService.contains(city) }.toCollection(ArrayList())}
        sigungu?.let{result = data.filter { value -> value.teacherService.contains(sigungu) }.toCollection(ArrayList())}


        var rvAdapter = SearchTeacherAdapter(requireContext(), R.layout.search_teacher_template, result,  object : SearchTeacherOnClick {
            override fun onItemClick(position: Int) {
                var intent = Intent(requireActivity(), SearchTeacher_detailActivity::class.java)
                var selectedTeacherId = findKeyByValue(dataMap_searchGosu, data[position])
                Log.d("ss", data[position].toString())

                //데이터 가공 해서 전송
                intent.putExtra("teacherId", selectedTeacherId.toString())
                intent.putExtra("teacherName", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherName)
                intent.putExtra("teacherContent", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherContent)
                intent.putExtra("teacherOneLine", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherOneLine)
                intent.putExtra("teacherGender", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherGender)
                intent.putExtra("teacherService", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherService)
                intent.putExtra("teacherCity", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherCity)
                intent.putExtra("teacherTelTime", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherTelTime)
                intent.putExtra("teacherWorkTime", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherWorkTime)
                intent.putExtra("teacherLikes", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherLikes)
                intent.putExtra("reviews", dataMap_searchGosu[selectedTeacherId.toString()]!!.reviews)
                startActivity(intent)
            }
        })
        rvTeacherList.layoutManager = LinearLayoutManager(requireContext())
        rvTeacherList.adapter = rvAdapter // 어댑터에 새로운 데이터 설정
        rvAdapter.notifyDataSetChanged() // 어댑터에 변경 사항 알림

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var views = inflater.inflate(R.layout.fragment_search_gosu, container, false)

        //===================================Spinner===============================================
        sigunguSpinner = views.findViewById(R.id.spinLocal_sigungu)
        citySpinner = views.findViewById(R.id.spinLocal_city)
        serviceSpinner = views.findViewById(R.id.spinService_search)

        //언어 선택 스피너
        var serviceAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_language,
            android.R.layout.simple_spinner_item
        )
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        serviceSpinner.adapter = serviceAdapter


        //도시
        var cityAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_region,
            android.R.layout.simple_spinner_item
        )
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = cityAdapter


        //기본 시군구 ( 도시 선택 X)
        var sigunguAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_none,
            android.R.layout.simple_spinner_item
        )
        sigunguAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sigunguSpinner.adapter = sigunguAdapter

        initAddressSpinner()
        //===============================Spinner End=============================================



        //================================Recycler View=========================================

        //리싸이클러 뷰 data에 teacheridVO넣어줄 것
        rvTeacherList = views.findViewById(R.id.rvTeacherList_search)


        requireActivity().runOnUiThread {
            database.getReference("teacherList").get().addOnSuccessListener{
                var jsonResult = gson.toJson(it.getValue())

                // JSON 문자열을 Map으로 파싱
                val mapType = object : TypeToken<Map<String, TeacherIdVO>>() {}.type
                dataMap_searchGosu = gson.fromJson(jsonResult, mapType)

                // 모든 key값들을 추출하여 출력
                for (value in dataMap_searchGosu.values) {
                    //값들을 전부 data에 저장
                    data.add(value)
                    Log.d("r", data.get(0).teacherOneLine)
                }

                var rvAdapter = SearchTeacherAdapter(requireContext(), R.layout.search_teacher_template, data,  object : SearchTeacherOnClick {
                    override fun onItemClick(position: Int) {
                        var intent = Intent(requireActivity(), SearchTeacher_detailActivity::class.java)
                        var selectedTeacherId = findKeyByValue(dataMap_searchGosu, data[position])
                        Log.d("ss", data[position].toString())

                        //데이터 가공 해서 전송
                        intent.putExtra("teacherId", selectedTeacherId.toString())
                        intent.putExtra("teacherName", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherName)
                        intent.putExtra("teacherContent", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherContent)
                        intent.putExtra("teacherOneLine", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherOneLine)
                        intent.putExtra("teacherGender", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherGender)
                        intent.putExtra("teacherService", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherService)
                        intent.putExtra("teacherCity", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherCity)
                        intent.putExtra("teacherTelTime", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherTelTime)
                        intent.putExtra("teacherWorkTime", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherWorkTime)
                        intent.putExtra("teacherLikes", dataMap_searchGosu[selectedTeacherId.toString()]!!.teacherLikes)
                        intent.putExtra("reviews", dataMap_searchGosu[selectedTeacherId.toString()]!!.reviews)
                        startActivity(intent)
                    }
                })
                rvTeacherList.layoutManager = LinearLayoutManager(requireContext())

                rvTeacherList.adapter = rvAdapter // 어댑터에 새로운 데이터 설정
                rvAdapter.notifyDataSetChanged() // 어댑터에 변경 사항 알림
            }
        }

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
                    0 ->  R.array.spinner_none
                    1 ->  R.array.spinner_region_seoul
                    2 ->  R.array.spinner_region_busan
                    3 ->  R.array.spinner_region_daegu
                    4 ->  R.array.spinner_region_incheon
                    5 ->  R.array.spinner_region_gwangju
                    6 ->  R.array.spinner_region_daejeon
                    7 ->  R.array.spinner_region_ulsan
                    8 ->  R.array.spinner_region_sejong
                    9 ->  R.array.spinner_region_gyeonggi
                    10 -> R.array.spinner_region_gangwon
                    11 -> R.array.spinner_region_chung_buk
                    12 -> R.array.spinner_region_chung_nam
                    13 -> R.array.spinner_region_jeon_buk
                    14 -> R.array.spinner_region_jeon_nam
                    15 -> R.array.spinner_region_gyeong_buk
                    16 -> R.array.spinner_region_gyeong_nam
                    17 -> R.array.spinner_region_jeju
                    else -> R.array.spinner_none
                }

                //시군구
                if(sigunguId != 0){
                    var sigunguAdapter = ArrayAdapter.createFromResource(
                        requireContext(),
                        sigunguId,
                        android.R.layout.simple_spinner_item
                    )
                    sigunguAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    sigunguSpinner.adapter = sigunguAdapter
                }

                cityValue = citySpinner.selectedItem.toString()
                serviceValue = serviceSpinner.selectedItem.toString()
                sigunguValue = sigunguSpinner.selectedItem.toString()
                if(cityValue == "선택") cityValue = null
                if(serviceValue == "선택") serviceValue = null
                if(sigunguValue == "선택") sigunguValue = null


                reLoadRV(serviceValue, cityValue, sigunguValue)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }



        serviceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ): Unit {
                cityValue = citySpinner.selectedItem.toString()
                serviceValue = serviceSpinner.selectedItem.toString()
                sigunguValue = sigunguSpinner.selectedItem.toString()
                if(cityValue == "선택") cityValue = null
                if(serviceValue == "선택") serviceValue = null
                if(sigunguValue == "선택") sigunguValue = null


                reLoadRV(serviceValue, cityValue, sigunguValue)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


        sigunguSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ): Unit {
                cityValue = citySpinner.selectedItem.toString()
                serviceValue = serviceSpinner.selectedItem.toString()
                sigunguValue = sigunguSpinner.selectedItem.toString()

                if(cityValue == "선택") cityValue = null
                if(serviceValue == "선택") serviceValue = null
                if(sigunguValue == "선택") sigunguValue = null

                reLoadRV(serviceValue, cityValue, sigunguValue)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    fun findKeyByValue(map: Map<String, Any>, value: Any): String? {
        return map.entries.find { it.value == value }?.key
    }


}





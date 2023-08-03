package com.smhrd.android.Data

data class TeacherIdVO(
    val teacherName: String, val teacherContent: String, val teacherOneLine: String,
    val teacherGender: String, val teacherService: String, val teacherCity: String,
    val teacherTelTime: String, val teacherWorkTime: String, val teacherLikes: Int? = 0,
    val reviews: ArrayList<ReviewVO>?
) {
    constructor() : this("", "", "", "", "", "", "", "", 0, null)
}


//파이어 베이스에 데이터를 저장 할때는

// teacherList type : MutableMap<String, TeacherIdVO>
// 실제 memberId가 가지고 있는 teacherId값 :String
// 해당 teacherId에 대한 정보 : TeacherIdVO


//업데이트 하면 Key값이 동일하기 때문에 자동으로 업데이트 됨

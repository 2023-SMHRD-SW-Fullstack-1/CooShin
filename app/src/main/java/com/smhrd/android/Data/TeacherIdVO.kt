package com.smhrd.android.Data

data class TeacherIdVO(val teacherName : String, val teacherContent: String, val teacherOneLine : String,
                     val teacherGender : Char, val teacherService : String, val teacherCity :String,
                     val teacherTelTime : String, val teacherWorkTime : String, val teacherLikes : Int,
                     val reviews : ArrayList<ReviewVO>)

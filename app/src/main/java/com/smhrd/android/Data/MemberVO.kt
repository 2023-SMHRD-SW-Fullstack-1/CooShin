package com.smhrd.android.Data

data class MemberVO(
    var memberPw: String = "",
    var memberTel: String = "",
    var memberNick: String = "",
    var memberImg: String? = null,
    var teacherId: String? = null,
    var likeList: MutableList<String>? = mutableListOf()
)



//LikeList에는 찜한 TeacherId 를 배열로 저장


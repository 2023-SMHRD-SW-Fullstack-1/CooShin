package com.smhrd.android.Data



data class MemberVO(val memberPw : String, val memberTel : String ,
                    val memberNick : String, val memberImg : String?,
                    val teacherId : String?,
                    val likeList : MutableList<String>?)


//LikeList에는 찜한 TeacherId 를 배열로 저장


package com.smhrd.android.Data

data class MemberIdVO(
    val member: MemberVO? = null, val chatRoom: ArrayList<String>? = null,
    val board: MutableList<String>? = null
)


//MemberIdVO를 저장할 때 유의사항
//파이어 베이스에 데이터를 저장 할때는

// MemberList type: MutableMap<String, MemberIdVO>
// put으로 데이터 삽입 < 실제 멤버 ID : String,  멤버 ID에 대한 정보 : MemberIdVO >


// MemberIdVO가 멤버 정보인 member

// chatList는 Map형식으로 String = 상대방ID,  ArrayList<ChatVo>는 채팅 상대와의 내용( add로 List에 추가 )

// board는 boardId를 담고 있는 리스트
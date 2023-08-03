package com.smhrd.android.Data

data class BoardIdVO(
    val boardTitle: String, val boardContent: String, val boardWriter: String?,
    val boardDate: String, val boardImg: String?,
    val boardViews: Int? = 0, val boardLikes: Int? = 0,
    val comment: ArrayList<CommentVO>?){
    constructor() : this("", "", "", "", null, 0, 0, null)
}



//BoardIdVO를 저장할 때 유의사항
//파이어 베이스에 데이터를 저장 할때는

// BoardList type: MutableMap<String, BoardIdVO>
// put으로 데이터 삽입 < 보드ID : String,  보드ID에대한 정보 : BoardIdVO >

//boardWrither는 작성 당시 SPF의 memberId값


package com.smhrd.android.Data

data class BoardIdVO(val boardTitle : String, val boardContent : String, val boardWriter : String, val boardDate : String,
                     val boardImg : String, val boardViews : Int, val boardLikes : Int, val comment : ArrayList<CommentVO>)

package com.smhrd.android.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.Community.CommunityCreateActivity
import com.smhrd.android.Data.BoardIdVO
import com.smhrd.android.Data.CommentVO
import com.smhrd.android.Data.MemberIdVO
import com.smhrd.android.R


class CommunityFragment : Fragment() {

    lateinit var btn_write : Button
    lateinit var tv_title : TextView
    lateinit var rc : RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)

        btn_write = view.findViewById(R.id.btnwrite)
//        tv_title = view.findViewById(R.id.tv_title)

        btn_write.setOnClickListener {
            val intent = Intent(requireActivity(), CommunityCreateActivity::class.java)
            startActivity(intent)
        }
        rc = view.findViewById(R.id.rcBoard)

        val data = ArrayList<BoardIdVO>()

//        val result = data.


        return inflater.inflate(R.layout.fragment_community, container, false)
    }

}
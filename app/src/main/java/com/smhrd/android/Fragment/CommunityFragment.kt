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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smhrd.android.Community.CommunityAdapter
import com.smhrd.android.Community.CommunityCreateActivity
import com.smhrd.android.Data.BoardIdVO
import com.smhrd.android.Data.CommentVO
import com.smhrd.android.Data.MemberIdVO
import com.smhrd.android.R
import org.json.JSONArray
import org.json.JSONObject


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
        rc = view.findViewById(R.id.rcBoard)


        btn_write.setOnClickListener {
            val intent = Intent(requireActivity(), CommunityCreateActivity::class.java)
            startActivity(intent)
        }


        val data = ArrayList<BoardIdVO>()
        val result = JSONArray()
        data.add(BoardIdVO("title","내용","작성자1","오늘","이미지1",1,1,null))
        data.add(BoardIdVO("title","내용","작성자1","오늘","이미지1",1,1,null))
        data.add(BoardIdVO("title","내용","작성자1","오늘","이미지1",1,1,null))
        data.add(BoardIdVO("title","내용","작성자1","오늘","이미지1",1,1,null))
        data.add(BoardIdVO("title","내용","작성자1","오늘","이미지1",1,1,null))
        data.add(BoardIdVO("title","내용","작성자1","오늘","이미지1",1,1,null))


        val adapter = CommunityAdapter(requireActivity(), data)

        rc.layoutManager = LinearLayoutManager(view.context)
        rc.adapter = adapter



        return view
    }

}
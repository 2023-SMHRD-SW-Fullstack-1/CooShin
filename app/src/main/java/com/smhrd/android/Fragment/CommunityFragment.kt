package com.smhrd.android.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.smhrd.android.R


class CommunityFragment : Fragment() {

    lateinit var btn_write : Button
    lateinit var tv_title : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)

        btn_write = view.findViewById(R.id.btnwrite)
        tv_title = view.findViewById(R.id.tv_title)


        return inflater.inflate(R.layout.fragment_community, container, false)
    }

}
package com.smhrd.android.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.smhrd.android.Community.CommunityAdapter
import com.smhrd.android.Community.CommunityCreateActivity
import com.smhrd.android.Community.CommunityDetailActivity
import com.smhrd.android.Data.BoardIdVO
import com.smhrd.android.Data.MemberIdVO
import com.smhrd.android.R
import org.json.JSONArray


class CommunityFragment : Fragment() {

    lateinit var btn_write : Button
    lateinit var tv_title : TextView
    lateinit var rc : RecyclerView

    private fun getLoggedInUserId(): String? {
        val sharedPreferences = requireActivity().getSharedPreferences("mySPF", Context.MODE_PRIVATE)
        return sharedPreferences.getString("loggedInUserId", null)
    }

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

            val spf = activity?.getSharedPreferences("memberInfoSpf", Context.MODE_PRIVATE)
            var loginMember = spf?.getString("memberId", "")
            Log.d("loginMember", loginMember.toString())
                startActivity(intent)
        }



        val databaseReference = FirebaseDatabase.getInstance().reference
        val data = ArrayList<BoardIdVO>()
        var result : MutableMap<String, BoardIdVO> = mutableMapOf()

        databaseReference.child("boardList").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Iterate through the children nodes of "boardList"
                if(snapshot.exists()){
                    for (childSnapshot in snapshot.children) {
                        // Deserialize each child node to a BoardIdVO object
                        val boardIdVO = childSnapshot.getValue(BoardIdVO::class.java)
                        if (boardIdVO != null) {
                            // Add the BoardIdVO object to the data ArrayList
                            data.add(boardIdVO)
                        }
                        Log.d("snap", childSnapshot.key!!)


                        result.put(childSnapshot.key.toString(), childSnapshot.getValue(BoardIdVO::class.java) as BoardIdVO )
                    }
                }


                // After retrieving data, create the adapter and set it to the RecyclerView
                var adapter = CommunityAdapter(requireContext(), data) { clickedBoard ->
                    // Handle the click event here
                    // Navigate to the detail page with the clickedBoard's information
                    val intent = Intent(requireContext(), CommunityDetailActivity::class.java)
                    intent.putExtra("c_title", clickedBoard.boardTitle) // Assuming "boardId" is the id of the post
                    intent.putExtra("c_content",clickedBoard.boardContent)

                    intent.putExtra("boardId",findKeyByValue(result, clickedBoard))
//                    findKeyByValue(result, clickedBoard)

                    startActivity(intent)
                }
                rc.layoutManager = GridLayoutManager(context, 2)

                rc.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors that may occur during data retrieval
            }
        })





        return view
    }

    fun findKeyByValue(map : MutableMap<String, BoardIdVO>, value : BoardIdVO): String? {
        //탐색해서 반환을 String?로처리

        return map.entries.find { it.value == value }?.key
    }
}
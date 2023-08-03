package com.smhrd.android.Chatting

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class RoomListChildEvent(var data:ArrayList<String>):ChildEventListener {
    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        var temp = snapshot.getValue().toString()

        data.add(temp!!)
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        TODO("Not yet implemented")
    }

    override fun onChildRemoved(snapshot: DataSnapshot) {
        TODO("Not yet implemented")
    }

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        TODO("Not yet implemented")
    }

    override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
    }
}
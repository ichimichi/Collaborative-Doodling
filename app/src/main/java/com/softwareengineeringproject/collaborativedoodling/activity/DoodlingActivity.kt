package com.softwareengineeringproject.collaborativedoodling.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.softwareengineeringproject.collaborativedoodling.R
import kotlinx.android.synthetic.main.activity_doodling.*


class DoodlingActivity : AppCompatActivity() {
    companion object {
        const val ROOM_NAME = "ROOM_NAME"
    }

    private val database = FirebaseDatabase.getInstance()
    private lateinit var room: DatabaseReference
    private var user: FirebaseUser? = null
    private lateinit var activeUsers: DatabaseReference
    private lateinit var key: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doodling)
        room = database.getReference(intent.getStringExtra(ROOM_NAME))
        user = FirebaseAuth.getInstance().currentUser
        activeUsers = room.child("activeUsers")
        key = activeUsers.child(user!!.uid)
        key.setValue(user!!.displayName)


        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView.init(metrics, intent.getStringExtra(ROOM_NAME))

        activeUsers.addValueEventListener(newUserEventListener)


        clearBtn.setOnClickListener {
            paintView.clear()
        }

        colorRedBtn.setOnClickListener {
            paintView.changeColor("#F44336")
        }

        colorCyanBtn.setOnClickListener {
            paintView.changeColor("#03A9F4")
        }

        colorGreenBtn.setOnClickListener {
            paintView.changeColor("#4CAF50")
        }

        colorWhiteBtn.setOnClickListener {
            paintView.changeColor("#FFFFFF")
        }

        colorBlackBtn.setOnClickListener {
            paintView.changeColor("#000000")
        }

    }

    private val newUserEventListener = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            Log.w("error", "Failed to read value.", error.toException())
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val td = dataSnapshot.value as HashMap<String, String>

            val listOfActiveUsers = ArrayList(td.values)

            activeUsersListTV.text = ""

            for (user: String in listOfActiveUsers) {
                activeUsersListTV.append("$user\n")
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activeUsers.removeEventListener(newUserEventListener)
        key.removeValue()
    }
}

package com.softwareengineeringproject.collaborativedoodling.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.softwareengineeringproject.collaborativedoodling.R
import kotlinx.android.synthetic.main.activity_doodling.*



class DoodlingActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val activeUsers = database.getReference("activeUsers")
    private val user = FirebaseAuth.getInstance().currentUser
    private val key = activeUsers.child(user!!.uid)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doodling)

        key.setValue(user!!.displayName)


        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView.init(metrics)

        activeUsers.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("error", "Failed to read value.", error.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val td = dataSnapshot.value as HashMap<String, String>

                val listOfActiveUsers = ArrayList(td.values)

                activeUsersListTV.text = ""

                for( user: String in listOfActiveUsers){
                    activeUsersListTV.append("$user\n")
                }

            }
        })

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

    override fun onDestroy() {
        super.onDestroy()
        key.removeValue()
    }
}

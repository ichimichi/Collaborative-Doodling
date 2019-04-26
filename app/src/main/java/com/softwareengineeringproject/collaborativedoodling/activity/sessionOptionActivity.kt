package com.softwareengineeringproject.collaborativedoodling.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.softwareengineeringproject.collaborativedoodling.R
import kotlinx.android.synthetic.main.activity_session_option.*

class sessionOptionActivity : AppCompatActivity() {

    companion object {
        var index: Int = 0
    }

    val strokeList: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_option)

        populateStrokeList()

        joinRoomBtn.setOnClickListener {
            val intent = Intent(this, DoodlingActivity::class.java)
            intent.putExtra(DoodlingActivity.ROOM_NAME, roomNameET.text.toString())
            startActivity(intent)
        }

        pencilAV.setOnClickListener {
            index = ((index + 1) % strokeList.size )
            pencilAV.setAnimation(strokeList[index])
            pencilAV.resumeAnimation()
        }


    }

    fun populateStrokeList() {
        strokeList.add(R.raw.stroke_1)
        strokeList.add(R.raw.stroke_2)
        strokeList.add(R.raw.stroke_3)
        strokeList.add(R.raw.stroke_4)
        strokeList.add(R.raw.stroke_5)
        strokeList.add(R.raw.stroke_6)
        strokeList.add(R.raw.stroke_7)
        strokeList.add(R.raw.stroke_8)
        strokeList.add(R.raw.stroke_9)
    }
}

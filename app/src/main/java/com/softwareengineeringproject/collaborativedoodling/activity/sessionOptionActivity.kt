package com.softwareengineeringproject.collaborativedoodling.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.softwareengineeringproject.collaborativedoodling.R
import kotlinx.android.synthetic.main.activity_session_option.*

class sessionOptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_option)

        joinRoomBtn.setOnClickListener {
            val intent = Intent(this, DoodlingActivity::class.java)
            intent.putExtra(DoodlingActivity.ROOM_NAME, roomNameET.text.toString())
            startActivity(intent)
        }
    }
}

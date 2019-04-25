package com.softwareengineeringproject.collaborativedoodling.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.softwareengineeringproject.collaborativedoodling.R
import com.softwareengineeringproject.collaborativedoodling.adapter.SavedDoodleAdapter
import kotlinx.android.synthetic.main.activity_saved_doodle.*

class SavedDoodleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_doodle)

        saved_doodleRV.layoutManager = LinearLayoutManager(this)
        saved_doodleRV.adapter = SavedDoodleAdapter()
    }
}

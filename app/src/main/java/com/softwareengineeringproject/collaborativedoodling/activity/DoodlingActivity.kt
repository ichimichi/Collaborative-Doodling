package com.softwareengineeringproject.collaborativedoodling.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.softwareengineeringproject.collaborativedoodling.R
import kotlinx.android.synthetic.main.activity_doodling.*

class DoodlingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doodling)

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView.init(metrics)

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
}

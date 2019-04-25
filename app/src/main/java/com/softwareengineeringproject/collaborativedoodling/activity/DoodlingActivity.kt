package com.softwareengineeringproject.collaborativedoodling.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.google.firebase.database.FirebaseDatabase
import com.softwareengineeringproject.collaborativedoodling.R
import com.softwareengineeringproject.collaborativedoodling.model.Instruction
import kotlinx.android.synthetic.main.activity_doodling.*

class DoodlingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doodling)

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView.init(metrics)



    }

    override fun onDestroy() {
        super.onDestroy()
        val database = FirebaseDatabase.getInstance()
        val drawingInstruction = database.getReference("drawingInstruction")
        val instruction: Instruction = Instruction()
        instruction.command = "init"
        instruction.x = 0.0F
        instruction.y = 0.0F
        drawingInstruction.setValue(instruction)
    }
}

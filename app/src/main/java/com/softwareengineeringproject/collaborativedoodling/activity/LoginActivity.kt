package com.softwareengineeringproject.collaborativedoodling.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.softwareengineeringproject.collaborativedoodling.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            val intent = Intent( this , MainActivity::class.java)
            startActivity(intent)
        }
    }
}

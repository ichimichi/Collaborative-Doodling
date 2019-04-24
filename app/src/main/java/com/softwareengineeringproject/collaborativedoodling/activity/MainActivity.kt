package com.softwareengineeringproject.collaborativedoodling.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.softwareengineeringproject.collaborativedoodling.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
//            val photoUrl = user.photoUrl

            // Check if user's email is verified
//            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid

            titleTV.text = " Hi, $name !"

        }

        savedDoodleBtn.setOnClickListener{
            val intent = Intent(this,SavedDoodleActivity::class.java)
            startActivity(intent)
        }

        newDoodleBtn.setOnClickListener {
            val intent = Intent(this,DoodlingActivity::class.java)
            startActivity(intent)
        }

        profileBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


    }


}

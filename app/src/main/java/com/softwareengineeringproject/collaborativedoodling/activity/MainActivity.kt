package com.softwareengineeringproject.collaborativedoodling.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.softwareengineeringproject.collaborativedoodling.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val headingList: ArrayList<LottieAnimationView> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        populateHeadingList()

        val user = FirebaseAuth.getInstance().currentUser

        val t = Thread(Runnable {
            if(user == null){
                runOnUiThread { createSignInIntent() }
            }
        })
        t.start()


        savedDoodleBtn.setOnClickListener{
            val intent = Intent(this,SavedDoodleActivity::class.java)
            startActivity(intent)
        }

        newDoodleBtn.setOnClickListener {
            val intent = Intent(this,sessionOptionActivity::class.java)
            startActivity(intent)
        }

        profileBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        for(heading:LottieAnimationView in headingList){
            heading.setOnClickListener {
                heading.resumeAnimation()
            }
        }


    }

    private fun gotoMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val customLayout = AuthMethodPickerLayout.Builder(R.layout.custom_login)
            .setGoogleButtonId(R.id.googleBtn)
            .setEmailButtonId(R.id.emailBtn)
            .build()

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setAuthMethodPickerLayout(customLayout)
                .build(),
            RC_SIGN_IN
        )
        // [END auth_fui_create_intent]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in

                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show()

                gotoMain()

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    fun populateHeadingList() {
        headingList.add(heading11)
        headingList.add(heading12)
        headingList.add(heading13)
        headingList.add(heading14)
        headingList.add(heading15)
        headingList.add(heading16)
        headingList.add(heading17)
        headingList.add(heading18)
        headingList.add(heading19)
        headingList.add(heading110)
        headingList.add(heading111)
        headingList.add(heading112)
        headingList.add(heading113)
        headingList.add(heading21)
        headingList.add(heading22)
        headingList.add(heading23)
        headingList.add(heading24)
        headingList.add(heading25)
        headingList.add(heading26)
        headingList.add(heading27)
        headingList.add(heading28)
    }

    companion object {

        private const val RC_SIGN_IN = 123
    }



}



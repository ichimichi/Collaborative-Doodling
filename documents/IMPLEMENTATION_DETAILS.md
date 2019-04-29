# IMPLEMENTATION DETAILS #

The implementation of the application can be explained in two parts
* Front End
* Back end 

The front end is the visible area of the application where the user interacts and back end containing all of the code that drives the application.

### Front End ###
#### XML ####
The front end gives an easy and user friendly interface to users
which is done with the help of **XML (eXtensible Markup Language)** in Android Studio 3.4.
All the UI and layout of our app is designed using XML. Unlike Kotlin (which is Back Bone of your app), 
XML helps you to design our app , how it will look , how components like buttons , 
textview , etc will be placed and their styling.

Example XML code of Profile Layout
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".activity.ProfileActivity">

    <ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        tools:srcCompat="@tools:sample/avatars"
        android:id="@+id/profilePic"
        android:layout_marginTop="32dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:srcCompat="@drawable/fui_ic_googleg_color_24dp"/>

    <TextView
        tools:text="User Name"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/nameTV" android:layout_marginTop="32dp"

        app:layout_constraintTop_toBottomOf="@+id/profilePic"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"/>

    <TextView
        tools:text="Email-ID"
        android:layout_width="wrap_content"
        android:layout_height="19dp"
        android:id="@+id/emailTV"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        android:layout_marginTop="16dp"
        app:layout_constraintTop_toBottomOf="@+id/nameTV"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```
and the corresponding Profile Layout when viewed by a user

<img width=300 src="https://github.com/ichimichi/Collaborative-Doodling/blob/master/documents/images/Screenshot_5.png?raw=true"></img>

### Back End ###

#### Kotlin ####
Kotlin is a programming language introduced by JetBrains, the official designer of the most intelligent Java IDE, named Intellij IDEA. 
Kotlin is a strongly statically typed language that runs on JVM. In 2017, Google announced Kotlin is an official language for android development. 
Kotlin is an open source programming language that combines object-oriented programming and functional features into a unique platform. 

<img width=400 src="https://github.com/ichimichi/Collaborative-Doodling/blob/master/documents/images/kotlin_logo.png?raw=true"></img>

In every screen or activity, users interact in the activity area. 
Activities extended from the base Activity class. Each activity associated with 
an XML layout file which supplies the leading back end visuals.
```kotlin
class ProfileActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }
}
```
Events are produced, started again, and closed through the Android Operating 
System through many callback methods, which are called instantly through the 
OS on the appropriate occasions. 
A few examples of callback methods: onCreate, onDestroy, onResume, onStop. 
These techniques need to be overwritten for the activity to operate properly.
```kotlin
saveBtn.setOnClickListener 
{
    val newbitmap = takeScreenShot(paintView)
    save(newbitmap!!)
}
```
```kotlin
override fun onCreate(savedInstanceState: Bundle?)
{
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_doodling)
}
    
override fun onDestroy() {
    super.onDestroy()
    activeUsers.removeEventListener(newUserEventListener)
    key.removeValue()
}
```

Information sent between activities is known as intent. 
The intent's carry standard data strings modified to hold extra data. 
For instance, clicking Join Room Button adds the text in Room Name EditText 
in the intent as an Extra string and transmits it to the Doodling activity where it creates a room
under that room name entered in the Database for that user.

<img width=400 src="https://github.com/ichimichi/Collaborative-Doodling/blob/master/documents/images/Screenshot_6.png?raw=true"></img>

```kotlin
joinRoomBtn.setOnClickListener
{
    val intent = Intent(this, DoodlingActivity::class.java)
    intent.putExtra(DoodlingActivity.ROOM_NAME, roomNameET.text.toString())
    startActivity(intent)
}
```

#### Firebase ####

In our Application we have used **Realtime Database** and **Authentication** services of Firebase.

Firebase is a mobile and web app development platform that provides developers with a plethora of 
tools and services to help them develop high-quality apps, grow their user base, and earn more profit.

**Firebase Services**

Firebase Services can be divided into two groups:

* Develop & testyour app
    * Realtime Database
    * Authentication
    * Test Lab
    * Crashlytics
    * Cloud Functions
    * Firestore
    * Cloud Storage
    * Performance Monitoring
    * Crash Reporting
    * Hosting
* Grow & Engage your audience
    * Firebase Analytics
    * Invites
    * Cloud Messaging
    * Predictions
    * AdMob
    * Dynamic Links
    * Adwords
    * Remote Config
    * App Indexing
    
**Realtime Database**

The Firebase Realtime Database is a cloud-hosted NoSQL database that lets us store and sync between our users in realtime.
The Realtime Database is really just one big JSON object that the developers can manage in realtime.

Our Realtime Database in action

<img width=500 src="https://github.com/ichimichi/Collaborative-Doodling/blob/master/documents/images/Firebase_3.gif?raw=true"></img>

With just a single API, the Firebase database provides your app with both the current value of the data and any updates to that data.
```kotlin
database = FirebaseDatabase.getInstance()
drawingInstruction = database!!.getReference(room).child("drawingInstruction")

drawingInstruction!!.addValueEventListener(object : ValueEventListener {
    override fun onCancelled(error: DatabaseError) {
        Log.w("error", "Failed to read value.", error.toException())
    }

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        val value = dataSnapshot.getValue(Instruction::class.java)
        Log.d("value", value.toString())

        when (value!!.command) {
            "init" -> {
            }
            "touchStart" -> {
                touchStart(value.x!!, value.y!!)
                invalidate()

            }
            "touchMove" -> {
                touchMove(value.x!!, value.y!!)
                invalidate()

            }
            "touchUp" -> {
                touchUp()
                invalidate()
                instruction.command = "init"
                drawingInstruction!!.setValue(instruction)
            }
            "changeColor" -> {
                currentColor = value!!.color
            }
        }
    }
})
```

Realtime syncing makes it easy for our users to access their data from any device, be it web or mobile. 
Realtime Database also helps our users collaborate with one another.

**Authentication**

Firebase Authentication provides backend services, easy-to-use SDKs, and ready-made UI libraries to authenticate users to our app.

Normally, it would take you months to set up our own authentication system. 
And even after that, we would need to keep a dedicated team to maintain that system. 
But if we use Firebase, we can set up the entire system in under 10 lines of code that 
will handle everything for us, including complex operations like account merging.

```kotlin
private fun createSignInIntent() {
    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    val customLayout = AuthMethodPickerLayout.Builder(R.layout.custom_login)
        .setGoogleButtonId(R.id.googleBtn)
        .setEmailButtonId(R.id.emailBtn)
        .build()

    startActivityForResult(
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setAuthMethodPickerLayout(customLayout)
            .build(),
        RC_SIGN_IN
    )
}
```

We have authenticate our application's users through the following methods:
* Email & Password
* Google

<img width=300 src="https://github.com/ichimichi/Collaborative-Doodling/blob/master/documents/images/Screenshot_1.png?raw=true"></img>

Using Firebase Authentication makes building secure authentication systems easier, while also improving the sign-in and onboarding experience for end users.

List of users signed up in our application

<img width=650 src="https://github.com/ichimichi/Collaborative-Doodling/blob/master/documents/images/Firebase_1.png?raw=true"></img>

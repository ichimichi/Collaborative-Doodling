# IMPLEMENTATION DETAILS #

The implementation of the application can be explained in two parts
* Front End
* Back end 

The front end is the visible area of the application where the user interacts and back end containing all of the code that drives the application.

### Front End ###

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

**Kotlin** is a programming language introduced by JetBrains, the official designer of the most intelligent Java IDE, named Intellij IDEA. 
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
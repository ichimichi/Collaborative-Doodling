package com.softwareengineeringproject.collaborativedoodling.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.softwareengineeringproject.collaborativedoodling.R
import kotlinx.android.synthetic.main.activity_doodling.*
import android.graphics.Bitmap
import android.view.View.DRAWING_CACHE_QUALITY_LOW
import android.view.View
import android.os.Environment.getExternalStorageDirectory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import java.nio.file.Files.delete
import java.nio.file.Files.exists
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStoragePublicDirectory
import com.softwareengineeringproject.collaborativedoodling.util.PaintView
import java.io.File
import java.io.FileOutputStream
import java.util.*


class DoodlingActivity : AppCompatActivity() {
    companion object {
        const val ROOM_NAME = "ROOM_NAME"
    }

    private val database = FirebaseDatabase.getInstance()
    private lateinit var room: DatabaseReference
    private var user: FirebaseUser? = null
    private lateinit var activeUsers: DatabaseReference
    private lateinit var key: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doodling)
        room = database.getReference(intent.getStringExtra(ROOM_NAME))
        user = FirebaseAuth.getInstance().currentUser
        activeUsers = room.child("activeUsers")
        key = activeUsers.child(user!!.uid)
        key.setValue(user!!.displayName)


        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView.init(metrics, intent.getStringExtra(ROOM_NAME))

        activeUsers.addValueEventListener(newUserEventListener)


        clearBtn.setOnClickListener {
            paintView.clear()
        }

        saveBtn.setOnClickListener {
            val newbitmap = takeScreenShot(paintView)
            save(newbitmap!!)
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

    private val newUserEventListener = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            Log.w("error", "Failed to read value.", error.toException())
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val td = dataSnapshot.value as HashMap<String, String>

            val listOfActiveUsers = ArrayList(td.values)

            activeUsersListTV.text = ""

            for (user: String in listOfActiveUsers) {
                activeUsersListTV.append("$user\n")
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activeUsers.removeEventListener(newUserEventListener)
        key.removeValue()
    }

    fun takeScreenShot(view: PaintView): Bitmap? {
        val snapshot = Bitmap.createBitmap(view.getBitmap())
        return snapshot
    }

    private fun save(finalBitmap: Bitmap) {
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
        val myDir = File("$root/CollaborativeDoodling")

        if (!myDir.exists()) {
            myDir.mkdirs()
        }

        val generator = Random()
        var n = 10000
        n = generator.nextInt(n)
        val iname = "doodle$n.jpg"
        val file = File(myDir, iname)
        if (file.exists())
            file.delete()
        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
            Toast.makeText(
                applicationContext,
                "Saved Sucessfully",
                Toast.LENGTH_LONG
            ).show()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        MediaScannerConnection.scanFile(this, arrayOf(file.toString()), null,
            object : MediaScannerConnection.OnScanCompletedListener {
                override fun onScanCompleted(path: String, uri: Uri) {
                    Log.i("ExternalStorage", "Scanned $path:")
                    Log.i("ExternalStorage", "-> uri=$uri")
                }
            })

    }

}

package com.softwareengineeringproject.collaborativedoodling.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
import java.io.File
import java.io.FileOutputStream
import java.util.*


class DoodlingActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val activeUsers = database.getReference("activeUsers")
    private val user = FirebaseAuth.getInstance().currentUser
    private val key = activeUsers.child(user!!.uid)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doodling)

        key.setValue(user!!.displayName)


        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView.init(metrics)

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

            for( user: String in listOfActiveUsers){
                activeUsersListTV.append("$user\n")
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activeUsers.removeEventListener(newUserEventListener)
        key.removeValue()
    }

    fun takeScreenShot(view: View): Bitmap? {
        // configuramos para que la view almacene la cache en una imagen
        view.setDrawingCacheEnabled(true)
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW)
        view.buildDrawingCache()
        if (view.getDrawingCache() == null) return null // Verificamos antes de que no sea null
        // utilizamos esa cache, para crear el bitmap que tendra la imagen de la view actual
        val snapshot = Bitmap.createBitmap(view.getDrawingCache())
        view.setDrawingCacheEnabled(false)
        view.destroyDrawingCache()
        return snapshot
    }

    private fun save(finalBitmap: Bitmap) {
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
        println("$root Root value in saveImage Function")
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

        val Image_path = "${Environment.getExternalStorageDirectory()}/Pictures/CameraFilter/$iname"
        val files = myDir.listFiles()
        val numberOfImages = files.size
        println("Total images in Folder $numberOfImages")
    }

}

package com.example.tolga.e_mail_tool

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast

import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {
    private var count = 0
    private var startMillis: Long = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)

        val send = this.findViewById<View>(R.id.btn) as Button

        send.setOnClickListener {
            Toast.makeText(this, "tap 3  times for send email", Toast.LENGTH_SHORT).show()

        }


    }

    fun ShareViaEmail(folder_name: String, file_name: String) {
        try {
            val Root = Environment.getExternalStorageDirectory()
            val filelocation = Root.absolutePath + folder_name + "/" + file_name
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.type = "text/plain"
            val message = "File to be shared is $file_name."
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://$filelocation"))
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.data = Uri.parse("mailto:tolgatas@kukapps.com")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent)
        } catch (e: Exception) {
            println("is exception raises during sending mail$e")
        }

    }


    private fun shareScreen() {
        try {
            var imgName="/"
            imgName+=getRandomString()


            val cacheDir = File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "devdeeds")

            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }

            val path = File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "devdeeds").toString() + imgName


            Utils.savePic(Utils.takeScreenShot(this), path)

            Toast.makeText(applicationContext, "Screenshot Saved", Toast.LENGTH_SHORT).show()

            ShareViaEmail(
                    "/devdeeds", "screenshot5.jpg")


        } catch (ignored: NullPointerException) {
            ignored.printStackTrace()
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val eventaction = event.action
        if (eventaction == MotionEvent.ACTION_UP) {

            //get system current milliseconds
            val time = System.currentTimeMillis()


            //if it is the first time, or if it has been more than 3 seconds since the first tap ( so it is like a new try), we reset everything
            if (startMillis == 0L || time - startMillis > 3000) {
                startMillis = time
                count = 1
            } else { //  time-startMillis< 3000
                count++
            }//it is not the first, and it has been  less than 3 seconds since the first

            if (count == 3) {
                //do whatever you need
                shareScreen()

            }
            return true
        }
        return false
    }

    companion object {

        private val REQUEST_CODE = 20
    }

    fun getRandomString(): String {
        val chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray()
        val sb = StringBuilder(20)
        val random = Random()
        for (i in 0..19) {
            val c = chars[random.nextInt(chars.size)]
            sb.append(c)
        }
        return sb.toString()
    }

}

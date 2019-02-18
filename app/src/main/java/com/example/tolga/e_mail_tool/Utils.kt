package com.example.tolga.e_mail_tool

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Rect
import android.view.View

import java.io.FileOutputStream
import java.io.IOException

object Utils {

    fun takeScreenShot(activity: Activity): Bitmap {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val b1 = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top

        //Find the screen dimensions to create bitmap in the same size.
        val width = activity.windowManager.defaultDisplay.width
        val height = activity.windowManager.defaultDisplay.height

        val b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight)
        view.destroyDrawingCache()
        return b
    }

    fun savePic(b: Bitmap, strFileName: String) {
        val fos: FileOutputStream
        try {
            fos = FileOutputStream(strFileName)
            b.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}
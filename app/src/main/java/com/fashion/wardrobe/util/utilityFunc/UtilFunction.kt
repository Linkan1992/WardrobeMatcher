@file:Suppress("UNREACHABLE_CODE")

package com.fashion.wardrobe.util.utilityFunc

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Base64
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


object UtilFunction {


    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    fun createImagePDFFile(context: Context): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PDF_${timeStamp}_", ".pdf", storageDir)
    }

    fun getBitmapFromUri(context: Context, uri: Uri) : Bitmap {
        val input = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(input)
        input!!.close()
        return bitmap
    }

/*    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = File(context.filesDir, Environment.DIRECTORY_PICTURES)
        if (!storageDir?.exists()!!)
            storageDir.mkdir()
        return File.createTempFile( "JPEG_${timeStamp}_", ".jpg", storageDir)
    }*/

    private fun writeFileToStorage(base64Pdf: String, context: Context) : String{
        val file = createImageFile(context)
        try {
            val fos = FileOutputStream(file)
            fos.write(Base64.decode(base64Pdf, Base64.NO_WRAP))
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return file.absolutePath
    }

}
package com.fashion.wardrobe.util.utilityFunc

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


object PermissionUtil {

    const val REQUEST_STORAGE_CODE = 100
    const val REQUEST_CAMERA_CODE = 101
    const val REQUEST_TAKE_PHOTO = 201
    const val IMAGE_SELECTION_CODE = 202

    fun getStorageReadPermission(appCompatActivity: AppCompatActivity?): Boolean {
        if (ContextCompat.checkSelfPermission(
                appCompatActivity!!,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) return true
        ActivityCompat.requestPermissions(
            appCompatActivity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_STORAGE_CODE
        )
        return false
    }


    fun getStorageWritePermission(appCompatActivity: AppCompatActivity?): Boolean {
        if (ContextCompat.checkSelfPermission(
                appCompatActivity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) return true
        ActivityCompat.requestPermissions(
            appCompatActivity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_STORAGE_CODE
        )
        return false
    }


    fun deviceCameraPermission(appCompatActivity: AppCompatActivity?): Boolean {
        if (ContextCompat.checkSelfPermission(
                appCompatActivity!!,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) return true
        ActivityCompat.requestPermissions(
            appCompatActivity,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_CODE
        )
        return false
    }

}
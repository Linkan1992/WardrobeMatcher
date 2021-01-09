package com.fashion.wardrobe.base


import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.fashion.wardrobe.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.fashion.wardrobe.util.constUtil.AppConstants
import com.fashion.wardrobe.util.constUtil.ConstMessage
import com.fashion.wardrobe.util.utilityFunc.PermissionUtil
import com.fashion.wardrobe.util.utilityFunc.UtilFunction
import dagger.android.support.DaggerAppCompatActivity
import java.io.File
import java.io.IOException


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : DaggerAppCompatActivity() {

    lateinit var viewDataBinding: T
        private set

    var mViewModel: V? = null


    @get:LayoutRes
    abstract val layoutId: Int

    abstract val viewModel: V

    abstract val bindingVariable: Int

    abstract val toolbar: Toolbar?

    abstract fun initOnCreate(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()

        setActionWithToolbar()
        initOnCreate(savedInstanceState)

        title = resources.getString(R.string.empty)
    }

    private fun setActionWithToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        mViewModel = mViewModel ?: viewModel
        viewDataBinding.setVariable(bindingVariable, mViewModel)

        viewDataBinding.executePendingBindings()
        // Immediate Binding
        // When a variable or observable changes, the binding will be scheduled to change before
        // the next frame. There are times, however, when binding must be executed immediately.
        // To force execution, use the executePendingBindings() method.
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showCustomSnackBar(message: String, success: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        snackBar.setActionTextColor(Color.parseColor("#721b65"))
        snackBar.setTextColor(Color.parseColor("#ffe2ff"))
        if (success) {
            snackBar.view.setBackgroundColor(Color.parseColor("#1eb2a6"))
        } else
            snackBar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_red_color))

        snackBar.show()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // If request is cancelled, the result arrays are empty.
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission was granted, yay! Do the
            // contacts-related task you need to do.
            permissionGranted(requestCode)
        } else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
        }

    }

    abstract fun permissionGranted(requestCode: Int)


    fun openAlertDialog() {
        MaterialAlertDialogBuilder(this, R.style.Theme_Tasker_Dialog).apply {
            setTitle(ConstMessage.TERMINATE)
            setMessage(ConstMessage.EXIT_MESSAGE)
            setPositiveButton(AppConstants.BUTTON_OK) { dialog, itemPosition ->
                super.onBackPressed()
                dialog.dismiss()
            }
            setNegativeButton(AppConstants.BUTTON_CANCEL, null)
        }
            .show()
            .setCanceledOnTouchOutside(false)
    }


    fun dispatchTakePictureIntent(): String? {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            .setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            .also { takePictureIntent ->

                takePictureIntent.resolveActivity(packageManager)?.also {

                    val photoFile: File? = try {
                        UtilFunction.createImageFile(this)
                    } catch (ex: IOException) {
                        null
                    }

                    photoFile?.also {
                        val photoURI: Uri = getContentUri(it)

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, PermissionUtil.REQUEST_TAKE_PHOTO)
                    }

                    return photoFile?.absolutePath
                }
            }

        return null
    }


    fun getContentUri(file: File): Uri = file.run {
        FileProvider.getUriForFile(
            this@BaseActivity,
            "${packageName}.fileprovider",
            this
        )
    }



}
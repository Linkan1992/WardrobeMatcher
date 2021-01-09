package com.fashion.wardrobe.ui.activity.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fashion.wardrobe.BR
import com.fashion.wardrobe.R
import com.fashion.wardrobe.ViewModelProviderFactory
import com.fashion.wardrobe.base.BaseActivity
import com.fashion.wardrobe.data.entiity.db.ProductStore
import com.fashion.wardrobe.databinding.ActivityMainBinding
import com.fashion.wardrobe.ui.adapter.productAdapter.ProductStoreAdapter
import com.fashion.wardrobe.util.constUtil.AppConstants
import com.fashion.wardrobe.util.extension.Result
import com.fashion.wardrobe.util.utilityFunc.FileUtility
import com.fashion.wardrobe.util.utilityFunc.PermissionUtil
import com.fashion.wardrobe.util.utilityFunc.SnapHelperOneByOne
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private var capturePhotoPath : String? = null

    private lateinit var productWearType : String

    companion object {

        fun newIntent(context: Context) {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var layoutManagerTop: LinearLayoutManager

    @Inject
    lateinit var layoutManagerBottom: LinearLayoutManager

    @Inject
    lateinit var topStoreAdapter: ProductStoreAdapter

    @Inject
    lateinit var bottomStoreAdapter: ProductStoreAdapter

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(
            this@MainActivity,
            viewModelProviderFactory
        ).get(MainViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override val viewModel: MainViewModel
        get() = mainViewModel

    override val bindingVariable: Int
        get() = BR.viewModel

    override val toolbar: Toolbar?
        get() = null

    override fun permissionGranted(requestCode: Int) {
        when(requestCode){
            PermissionUtil.REQUEST_CAMERA_CODE -> capturePhotoPath = dispatchTakePictureIntent()
            PermissionUtil.REQUEST_STORAGE_CODE -> pickImageFromGallery()
        }
    }

    override fun initOnCreate(savedInstanceState: Bundle?) {
        viewDataBinding.includedAppBar.title.text = resources.getString(R.string.match_pair)

        subscribeToLiveData()
        setUpRecyclerView()
        setUpRecyclerScrollListener()
        setUpClickListener()
    }

    private fun setUpClickListener() {
        viewDataBinding.apply {
            addTop.setOnClickListener { createChooserDialog(AppConstants.ProductType.TOP_WEAR) }

            addBottom.setOnClickListener { createChooserDialog(AppConstants.ProductType.BOTTOM_WEAR) }

            syncPair.setOnClickListener {
                it.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.clock_rotate))
                mainViewModel.shuffleTopBottomPair()
            }
        }
    }

    private fun setUpRecyclerView() {

        layoutManagerTop.orientation = RecyclerView.HORIZONTAL
        viewDataBinding.topRecylerview.layoutManager = layoutManagerTop
        viewDataBinding.topRecylerview.itemAnimator = DefaultItemAnimator()
        viewDataBinding.topRecylerview.adapter = topStoreAdapter.apply {
           // mEmptyBinding?.imgDummy?.setImageResource(R.drawable.ic_sweater)
        }
        val linearTopSnapHelper = SnapHelperOneByOne()
        linearTopSnapHelper.attachToRecyclerView(viewDataBinding.topRecylerview)

        layoutManagerBottom.orientation = RecyclerView.HORIZONTAL
        viewDataBinding.bottomRecylerview.layoutManager = layoutManagerBottom
        viewDataBinding.bottomRecylerview.itemAnimator = DefaultItemAnimator()
        viewDataBinding.bottomRecylerview.adapter = bottomStoreAdapter.apply {
           // mEmptyBinding?.imgDummy?.setImageResource(R.drawable.ic_trousers)
        }
        val linearBottomSnapHelper = SnapHelperOneByOne()
        linearBottomSnapHelper.attachToRecyclerView(viewDataBinding.bottomRecylerview)
    }

    private val topScrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

           /* if (newState === RecyclerView.SCROLL_STATE_SETTLING) {

                // if you just want to know if the new "page" comes in view
                val pageViewPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
            }*/
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                // if you just want to know if the new "page" comes in view
                val currentViewPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()

                topStoreAdapter.getData()[currentViewPosition]?.also {
                    mainViewModel.topWearId = it.productId
                    mainViewModel.checkIsFavPair(it.productId, mainViewModel.bottomWearId)
                }

                mainViewModel.randomTopPosition = currentViewPosition
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
        }
    }

    private val bottomScrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                // if you just want to know if the new "page" comes in view
                val currentViewPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()

                bottomStoreAdapter.getData()[currentViewPosition]?.also {
                    mainViewModel.bottomWearId = it.productId
                    mainViewModel.checkIsFavPair(mainViewModel.topWearId, it.productId)
                }

                mainViewModel.randomBottomPosition = currentViewPosition
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
        }
    }

    private fun setUpRecyclerScrollListener() {

        viewDataBinding.topRecylerview.addOnScrollListener(topScrollListener)
        viewDataBinding.bottomRecylerview.addOnScrollListener(bottomScrollListener)
    }

    private fun subscribeToLiveData() {

        mainViewModel.mTopStoreLiveData.observe(this@MainActivity, Observer {
             if(it.isEmpty()) topStoreAdapter.mEmptyBinding?.imgDummy?.setImageResource(R.drawable.ic_sweater)
             else mainViewModel.updateTopWearList(it)
        })

        mainViewModel.mBottomStoreLiveData.observe(this@MainActivity, Observer {
            if(it.isEmpty()) bottomStoreAdapter.mEmptyBinding?.imgDummy?.setImageResource(R.drawable.ic_trousers)
            else mainViewModel.updateBottomWearList(it)
        })

        topStoreAdapter.mProductLiveData.observe(this@MainActivity, Observer {
            mainViewModel.topWearId = it.productId
            mainViewModel.checkIsFavPair(it.productId, mainViewModel.bottomWearId)
        })

        bottomStoreAdapter.mProductLiveData.observe(this@MainActivity, Observer {
            mainViewModel.bottomWearId = it.productId
            mainViewModel.checkIsFavPair(mainViewModel.topWearId, it.productId)
        })




        mainViewModel.mFavPairLiveData.observe(this@MainActivity, Observer {
            when(it){
                is Result.Success -> viewDataBinding.favPair.setImageResource(R.drawable.ic_selected_heart)
                is Result.Error -> {
                    viewDataBinding.favPair.setImageResource(R.drawable.ic_unselected_heart)
                  //  showCustomSnackBar(it.message ?: "Unknown Error", false)
                }
            }
        })

        mainViewModel.mFavPairListLiveData.observe(this@MainActivity, Observer {
            it.filter { it1 -> (mainViewModel.topWearId == it1.favTopWearId) and (mainViewModel.bottomWearId == it1.favBottomWearId)}.run {
                if (size > 0) viewDataBinding.favPair.setImageResource(R.drawable.ic_selected_heart) else viewDataBinding.favPair.setImageResource(R.drawable.ic_unselected_heart)
            }
        })

    }


    private fun createChooserDialog(productType : String){

        productWearType = productType

        val dialogBuilder: AlertDialog = AlertDialog.Builder(this).create()
        val inflater: LayoutInflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.dialog_product_chooser, null)

        val btnCamera: AppCompatButton = dialogView.findViewById(R.id.btn_camera)
        val btnGallery: AppCompatButton = dialogView.findViewById(R.id.btn_gallery)

        btnCamera.setOnClickListener {
            if(PermissionUtil.deviceCameraPermission(this@MainActivity))
                capturePhotoPath = dispatchTakePictureIntent()

            dialogBuilder.dismiss()
        }

        btnGallery.setOnClickListener {
            if(PermissionUtil.getStorageReadPermission(this@MainActivity))
                pickImageFromGallery()

            dialogBuilder.dismiss()
        }

        dialogBuilder.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK
                && event.action == KeyEvent.ACTION_UP) {
                dialog.dismiss()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        dialogBuilder.setView(dialogView)
        dialogBuilder.show()
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PermissionUtil.IMAGE_SELECTION_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        /**
         * Since we are using capture photo path as ContentUri hence data will always be null
         */
        when (resultCode) {
            Activity.RESULT_OK -> {
               when(requestCode){
                   PermissionUtil.IMAGE_SELECTION_CODE -> data?.data?.also {
                       val imagePath = FileUtility.getPath(this@MainActivity,it)
                       mainViewModel.insertProductInStore(
                           ProductStore(imagePath = imagePath ?: resources.getString(R.string.empty), productType = productWearType)
                       )
                   }
                   PermissionUtil.REQUEST_TAKE_PHOTO -> capturePhotoPath?.also {
                       mainViewModel.insertProductInStore(ProductStore(imagePath = it, productType = productWearType))
                   }
               }
            }
            Activity.RESULT_CANCELED -> showCustomSnackBar("Action Cancelled", false)
            else -> showCustomSnackBar("Action Failed", false)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}

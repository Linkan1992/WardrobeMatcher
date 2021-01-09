package com.fashion.wardrobe.ui.activity.splash

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fashion.wardrobe.BR
import com.fashion.wardrobe.R
import com.fashion.wardrobe.ViewModelProviderFactory
import com.fashion.wardrobe.base.BaseActivity
import com.fashion.wardrobe.databinding.ActivitySplashBinding
import com.fashion.wardrobe.ui.activity.main.MainActivity
import javax.inject.Inject
import com.fashion.wardrobe.util.extension.Result


class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory


    private val splashViewModel: SplashViewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory).get(SplashViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.activity_splash


    override val viewModel: SplashViewModel
        get() = splashViewModel


    override val bindingVariable: Int
        get() = BR.viewModel


    override val toolbar: Toolbar?
        get() = null


    override fun initOnCreate(savedInstanceState: Bundle?) {
        subscribeToLiveData()
    }

    override fun permissionGranted(requestCode: Int) {

    }

    private fun subscribeToLiveData() {


        splashViewModel.mStatusLiveData.observe(this, Observer { result ->
            when (result) {
                is Result.Success -> redirectToMainActivity()
                is Result.Error -> {}
            }
        })

    }


    private fun redirectToMainActivity() {
        MainActivity.newIntent(this@SplashActivity)
        finish()
    }


}


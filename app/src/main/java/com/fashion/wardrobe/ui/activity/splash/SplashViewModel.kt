package com.fashion.wardrobe.ui.activity.splash

import androidx.lifecycle.LiveData
import com.fashion.wardrobe.base.BaseViewModel
import com.fashion.wardrobe.util.constUtil.AppConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.fashion.wardrobe.util.extension.StateLiveData
import com.fashion.wardrobe.util.extension.applyResultTransformation
import com.fashion.wardrobe.util.extension.Result

class SplashViewModel(
    private val ioCoroutineScope: CoroutineScope
) : BaseViewModel() {


    private val statusLiveData: StateLiveData<String> by lazy { StateLiveData<String>() }

    val mStatusLiveData: LiveData<Result<String>>
        get() = statusLiveData.applyResultTransformation { createResultData(result = it) }

    init {
        ioCoroutineScope.launch {

            delay(AppConstants.SPLASH_TIME_IN_MILLIS)

            statusLiveData.postSuccess(AppConstants.SUCCESS)
        }
    }

}
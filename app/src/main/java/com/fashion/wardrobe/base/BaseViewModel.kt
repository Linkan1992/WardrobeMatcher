package com.fashion.wardrobe.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.fashion.wardrobe.util.extension.Result

abstract class BaseViewModel : ViewModel() {

    protected val TAG = BaseViewModel::class.java.simpleName

    protected val loading = ObservableBoolean(false)

    inline fun <reified T : Any> createResultData(result: Result<T>): Result<T> {

        var data: Result<T> = result

        when (result) {
            is Result.Loading -> setLoading(result.isLoading)
            is Result.Success -> {
                data = Result.Success(result.data)
                setLoading(false)
            }
            is Result.Error -> setLoading(false)

        }

        return data;
    }

    fun setLoading(isLoading: Boolean) {
        loading.set(isLoading)
    }

    fun getAppLoading() = loading


}
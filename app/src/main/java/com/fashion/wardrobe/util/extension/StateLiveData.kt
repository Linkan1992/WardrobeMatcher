package com.fashion.wardrobe.util.extension

import com.fashion.wardrobe.util.constUtil.AppConstants


class StateLiveData<T : Any> : SingleLiveEvent<Result<T>>() {
    /**
     * Use this to put the Data on a LOADING Status
     */
    fun postLoading(loading : Boolean = true) {
        postValue(Result.Loading(isLoading = loading))
    }

    /**
     * Use this to put the Data on a ERROR DataStatus
     * @param throwable the error to be handled
     */
    fun postError(
        errorCode: Int = AppConstants.HttpResCodes.STATUS_INTERNAL_ERROR,
        errorMessage: String? = null
    ) {
        postValue(Result.Error(errorCode, message = errorMessage))
    }


    /**
     * Use this to put the Data on a ERROR DataStatus
     * @param throwable the error to be handled
     */
    fun postResultError(errorData : Result<T>) {
        postValue(errorData)
    }


    /**
     * Use this to put the Data on a SUCCESS DataStatus
     * @param data
     */
    fun postSuccess(data: T) {
        postValue(Result.Success(data))
    }


}
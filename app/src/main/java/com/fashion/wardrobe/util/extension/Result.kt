package com.fashion.wardrobe.util.extension

sealed class Result<out T : Any> {

    data class Loading(val isLoading : Boolean) : Result<Nothing>()

    data class Success<out T : Any>(val data : T) : Result<T>()

    data class Error(val errorCode : Int, val message : String?) : Result<Nothing>()

}
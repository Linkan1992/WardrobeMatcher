package com.fashion.wardrobe.util.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations


inline fun <reified T : Any> SingleLiveEvent<Result<T>>.applyResultTransformation(crossinline call: (data: Result<T>) -> Result<T>): LiveData<Result<T>> {

    return Transformations.map(this) { call(it) }
}


inline fun <reified T : Any> SingleLiveEvent<Result<T>>.applyResultSingleEventTransformation(crossinline call: (data: Result<T>) -> Result<T>): SingleLiveEvent<Result<T>> {
    this.addSource(Transformations.map(this) { call(it) }){}
    return this
}






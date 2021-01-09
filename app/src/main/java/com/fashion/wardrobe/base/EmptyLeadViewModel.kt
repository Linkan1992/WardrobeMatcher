package com.fashion.wardrobe.base

import androidx.databinding.ObservableBoolean

class EmptyLeadViewModel {

    val isVisible = ObservableBoolean(true)

    fun setIsVisible(value: Boolean) {
        this.isVisible.set(value)
    }

}
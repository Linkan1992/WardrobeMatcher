package com.fashion.wardrobe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fashion.wardrobe.data.persistence.db.DbHelper
import com.fashion.wardrobe.di.annotation.CoroutineScopeIO
import com.fashion.wardrobe.ui.activity.main.MainViewModel

import com.fashion.wardrobe.ui.activity.splash.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelProviderFactory @Inject
constructor(
    private val dbHelper: DbHelper,
    @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope
) : ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(ioCoroutineScope) as T
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(dbHelper, ioCoroutineScope) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name} " /*+ modelClass.name*/)
        }
    }
}
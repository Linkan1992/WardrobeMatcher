package com.fashion.wardrobe.di.activityBuilder

import com.fashion.wardrobe.ui.activity.main.MainActivity
import com.fashion.wardrobe.ui.activity.main.MainModule
import com.fashion.wardrobe.ui.activity.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun provideSplashActivity(): SplashActivity

    @ContributesAndroidInjector(
        modules = [
            MainModule::class
        ]
    )
    abstract fun provideMainActivity(): MainActivity

}
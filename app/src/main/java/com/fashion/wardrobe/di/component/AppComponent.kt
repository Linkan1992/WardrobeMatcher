package com.fashion.wardrobe.di.component

import android.app.Application
import com.fashion.wardrobe.di.activityBuilder.ActivityBuilder
import com.fashion.wardrobe.di.module.AppModule
import com.fashion.wardrobe.di.module.PersistenceModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        ActivityBuilder::class,
        AppModule::class,
        PersistenceModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent

    }

    override fun inject(instance: DaggerApplication?)

}
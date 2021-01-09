package com.fashion.wardrobe.di.module

import com.fashion.wardrobe.data.persistence.db.AppDbHelper
import com.fashion.wardrobe.data.persistence.db.DbHelper
import com.fashion.wardrobe.di.annotation.CoroutineScopeIO
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @CoroutineScopeIO
    internal fun provideCoroutineScopeIO(): CoroutineScope = CoroutineScope(Dispatchers.IO)

    @Provides
    @Singleton
    internal fun provideDbHelper(appDbHelper: AppDbHelper): DbHelper = appDbHelper

}
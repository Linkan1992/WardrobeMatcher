package com.fashion.wardrobe.di.module


import android.app.Application
import androidx.room.Room
import com.fashion.wardrobe.data.persistence.db.dao.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule{

    @Provides
    @Singleton
    internal fun provideAppDatabase(application : Application) : AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "WardrobeMatcher.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}
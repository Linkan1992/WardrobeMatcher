package com.fashion.wardrobe.data.persistence.db.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fashion.wardrobe.data.entiity.db.*

@Database(entities = [ProductStore::class, FavouritePairStore::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getProductStoreDao() : ProductStoreDao

    abstract fun getFavPairStoreDao() : FavouritePairDao

}
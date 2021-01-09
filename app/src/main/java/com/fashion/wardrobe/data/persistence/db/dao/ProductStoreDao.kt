package com.fashion.wardrobe.data.persistence.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fashion.wardrobe.data.entiity.db.ProductStore

@Dao
interface ProductStoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductInStore(productStore: ProductStore)

    @Query("SELECT * FROM product_store WHERE productType = :prodType")
    fun fetchProductFromStore(prodType : String) : LiveData<List<ProductStore>>

    @Query("SELECT * FROM product_store WHERE productType = :prodType ORDER BY RANDOM() LIMIT 1")
    fun fetchRandomWear(prodType : String) : ProductStore

}
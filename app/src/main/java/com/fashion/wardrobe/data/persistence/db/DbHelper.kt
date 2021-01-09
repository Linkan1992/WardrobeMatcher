package com.fashion.wardrobe.data.persistence.db

import androidx.lifecycle.LiveData
import com.fashion.wardrobe.data.entiity.db.FavouritePairStore
import com.fashion.wardrobe.data.entiity.db.ProductStore

interface DbHelper {

    suspend fun insertProductInStore(productStore: ProductStore)

    fun fetchTopWearList() : LiveData<List<ProductStore>>

    fun fetchBottomWearList() : LiveData<List<ProductStore>>

    suspend fun insertFavPairInStore(favouritePairStore: FavouritePairStore)

    suspend fun removeFavPairFromStore(favouritePairStore: FavouritePairStore)

    suspend fun isFavPairExist(topFavId : Long, BottomFavId : Long) : Boolean

    fun fetchFavPairList() : LiveData<List<FavouritePairStore>>

    suspend fun fetchFavPair(topFavId: Long, bottomFavId: Long) : FavouritePairStore?

    suspend fun fetchRandomTopWear() : ProductStore

    suspend fun fetchRandomBottomWear() : ProductStore

}
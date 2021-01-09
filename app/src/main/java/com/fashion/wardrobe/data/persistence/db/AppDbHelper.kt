package com.fashion.wardrobe.data.persistence.db


import androidx.lifecycle.LiveData
import com.fashion.wardrobe.data.entiity.db.FavouritePairStore
import com.fashion.wardrobe.data.entiity.db.ProductStore
import com.fashion.wardrobe.data.persistence.db.dao.AppDatabase
import com.fashion.wardrobe.util.constUtil.AppConstants
import javax.inject.Inject

class AppDbHelper @Inject constructor(
    private val appDatabase: AppDatabase
) : DbHelper {

    override suspend fun insertProductInStore(productStore: ProductStore)
    = appDatabase.getProductStoreDao().insertProductInStore(productStore)

    override fun fetchTopWearList(): LiveData<List<ProductStore>>
    = appDatabase.getProductStoreDao().fetchProductFromStore(AppConstants.ProductType.TOP_WEAR)

    override fun fetchBottomWearList(): LiveData<List<ProductStore>>
    = appDatabase.getProductStoreDao().fetchProductFromStore(AppConstants.ProductType.BOTTOM_WEAR)

    override suspend fun insertFavPairInStore(favouritePairStore: FavouritePairStore)
    = appDatabase.getFavPairStoreDao().insertFavPairInStore(favouritePairStore)

    override suspend fun removeFavPairFromStore(favouritePairStore: FavouritePairStore)
    = appDatabase.getFavPairStoreDao().removeFavPairFromStore(favouritePairStore.favTopWearId, favouritePairStore.favBottomWearId)

    override suspend fun isFavPairExist(topFavId: Long, bottomFavId: Long): Boolean
    = appDatabase.getFavPairStoreDao().fetchFavPairFromStore(topFavId, bottomFavId) != null

    override fun fetchFavPairList(): LiveData<List<FavouritePairStore>>
    = appDatabase.getFavPairStoreDao().fetchFavPairList()

    override suspend fun fetchFavPair(topFavId: Long, bottomFavId: Long): FavouritePairStore?
    = appDatabase.getFavPairStoreDao().fetchFavPairFromStore(topFavId, bottomFavId)

    override suspend fun fetchRandomTopWear(): ProductStore
    = appDatabase.getProductStoreDao().fetchRandomWear(AppConstants.ProductType.TOP_WEAR)

    override suspend fun fetchRandomBottomWear(): ProductStore
    = appDatabase.getProductStoreDao().fetchRandomWear(AppConstants.ProductType.BOTTOM_WEAR)

}
package com.fashion.wardrobe.data.persistence.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fashion.wardrobe.data.entiity.db.FavouritePairStore

@Dao
interface FavouritePairDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertFavPairInStore(favouritePairStore: FavouritePairStore)

    @Query("SELECT * FROM favourite_pair_store WHERE favTopWearId = :favTopWearId AND favBottomWearId = :favBottomWearId")
     fun fetchFavPairFromStore(favTopWearId : Long, favBottomWearId : Long) : FavouritePairStore?

    @Query("DELETE FROM favourite_pair_store WHERE favTopWearId = :topWearId AND favBottomWearId = :bottomWearId")
     fun removeFavPairFromStore(topWearId : Long, bottomWearId : Long)

    @Query("SELECT * FROM favourite_pair_store")
     fun fetchFavPairList() : LiveData<List<FavouritePairStore>>


}
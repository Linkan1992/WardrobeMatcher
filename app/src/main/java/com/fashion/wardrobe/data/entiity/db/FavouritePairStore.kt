package com.fashion.wardrobe.data.entiity.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Favourite_Pair_Store")
data class FavouritePairStore(

    @PrimaryKey(autoGenerate = true)
    val favPairId: Long = 0,

    var favTopWearId: Long,

    var favBottomWearId: Long

)
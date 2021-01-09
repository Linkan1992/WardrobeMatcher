package com.fashion.wardrobe.data.entiity.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product_Store")
data class ProductStore(

    @PrimaryKey(autoGenerate = true)
    val productId: Long = 0,

    var imagePath: String,

    var productType: String

)
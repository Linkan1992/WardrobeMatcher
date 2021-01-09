package com.fashion.wardrobe.util.constUtil

object ViewType {

    const val VIEW_TYPE_EMPTY = 1

    const val VIEW_TYPE_NORMAL = 2

    object ProductStore{
        const val PRODUCT_TOP_WEAR = 3
        const val PRODUCT_BOTTOM_WEAR = 4
    }

    fun getProductViewType(productType : String) : Int = when(productType){
         AppConstants.ProductType.TOP_WEAR -> ProductStore.PRODUCT_TOP_WEAR
        AppConstants.ProductType.BOTTOM_WEAR -> ProductStore.PRODUCT_BOTTOM_WEAR
        else -> VIEW_TYPE_NORMAL
    }

}
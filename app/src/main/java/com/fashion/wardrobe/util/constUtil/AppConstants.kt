package com.fashion.wardrobe.util.constUtil

object AppConstants {

    const val SPLASH_TIME_IN_MILLIS = 2000L

    val SUCCESS = "Success"

    const val BUTTON_OK = "OK"

    const val BUTTON_CANCEL = "Cancel"

    object ProductType{
        const val TOP_WEAR = "top_wear"
        const val BOTTOM_WEAR = "bottom_wear"
    }

    class HttpResCodes {
        companion object {
            const val STATUS_NO_ITEMS_FOUND = 200
            const val STATUS_NOT_FOUND = 404
            const val STATUS_UNAUTHORIZED = 401
            const val STATUS_SERVER_ERROR = 500
            const val STATUS_NO_INTERNET = -1
            const val STATUS_INTERNAL_ERROR = 0
        }
    }


}
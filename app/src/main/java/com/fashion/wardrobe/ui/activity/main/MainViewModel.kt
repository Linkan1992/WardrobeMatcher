package com.fashion.wardrobe.ui.activity.main

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import com.fashion.wardrobe.base.BaseViewModel
import com.fashion.wardrobe.data.entiity.db.FavouritePairStore
import com.fashion.wardrobe.data.entiity.db.ProductStore
import com.fashion.wardrobe.data.persistence.db.DbHelper
import com.fashion.wardrobe.di.annotation.CoroutineScopeIO
import com.fashion.wardrobe.util.constUtil.AppConstants
import com.fashion.wardrobe.util.extension.StateLiveData
import com.fashion.wardrobe.util.extension.applyResultTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.fashion.wardrobe.util.extension.Result
import com.fashion.wardrobe.util.constUtil.ConstMessage
import kotlinx.coroutines.delay

class MainViewModel(
    private val dbHelper: DbHelper,
    @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope
) : BaseViewModel() {

    val topWearType = AppConstants.ProductType.TOP_WEAR

    val bottomWearType = AppConstants.ProductType.BOTTOM_WEAR

    var topWearId : Long = -1

    var bottomWearId : Long = -2

    var randomTopPosition = 0

    var randomBottomPosition = 0

    var randomSync = false

    val mTopStoreLiveData: LiveData<List<ProductStore>> = dbHelper.fetchTopWearList()

    val mBottomStoreLiveData: LiveData<List<ProductStore>> = dbHelper.fetchBottomWearList()

    val mFavPairListLiveData: LiveData<List<FavouritePairStore>> = dbHelper.fetchFavPairList()

    private val favPairLiveData: StateLiveData<FavouritePairStore> by lazy { StateLiveData<FavouritePairStore>() }

    val mFavPairLiveData: LiveData<Result<FavouritePairStore>>
        get() = favPairLiveData.applyResultTransformation {  createResultData(result = it) }

    private val topWearList = ObservableArrayList<ProductStore>()

    val mTopWearList: ObservableArrayList<ProductStore>
        get() = topWearList

    private val bottomWearList = ObservableArrayList<ProductStore>()

    val mBottomWearList: ObservableArrayList<ProductStore>
        get() = bottomWearList


    fun updateTopWearList(dataList: List<ProductStore>) {
        topWearList.clear()
        topWearList.addAll(dataList)
    }

    fun updateBottomWearList(dataList: List<ProductStore>) {
        bottomWearList.clear()
        bottomWearList.addAll(dataList)
    }

    fun insertProductInStore(productStore: ProductStore) {
        ioCoroutineScope.launch {
            dbHelper.insertProductInStore(productStore)
        }
    }

    fun insertFavPairInStore() {
        ioCoroutineScope.launch {
            val pairExist = dbHelper.isFavPairExist(topWearId, bottomWearId)
            when{
                (topWearId > 0) and (bottomWearId > 0) -> if (pairExist)
                    dbHelper.removeFavPairFromStore(FavouritePairStore(favTopWearId = topWearId, favBottomWearId = bottomWearId))
                    else
                    dbHelper.insertFavPairInStore(FavouritePairStore(favTopWearId = topWearId, favBottomWearId = bottomWearId))
                else -> favPairLiveData.postError(errorMessage = ConstMessage.SELECT_PRODUCT)
            }
        }
    }

    fun checkIsFavPair(topWearId : Long, bottomWearId : Long){
        ioCoroutineScope.launch {
            val result = dbHelper.fetchFavPair(topWearId, bottomWearId)
            when (result) {
                null -> favPairLiveData.postError(errorMessage = ConstMessage.MAKE_PAIR)
                else -> favPairLiveData.postSuccess(result)
            }
        }
    }

    fun shuffleTopBottomPair(){

        ioCoroutineScope.launch {

            if((topWearId < 0) or (bottomWearId < 0))
                return@launch

            randomSync = true

            val randomTop = dbHelper.fetchRandomTopWear()
            val randomBottom = dbHelper.fetchRandomBottomWear()

            updateTopWearList(ArrayList<ProductStore>().apply { add(randomTop) })
            updateBottomWearList(ArrayList<ProductStore>().apply { add(randomBottom) })

            topWearId = randomTop.productId
            bottomWearId = randomBottom.productId

            checkIsFavPair(topWearId, bottomWearId)

            delay(2000)
            randomSync = false
        }
    }

}
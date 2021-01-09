package com.fashion.wardrobe.ui.adapter.productAdapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fashion.wardrobe.R
import com.fashion.wardrobe.base.BaseRecyclerViewAdapter
import com.fashion.wardrobe.data.entiity.db.ProductStore
import com.fashion.wardrobe.databinding.AdapterProductRowBinding
import com.fashion.wardrobe.util.constUtil.ViewType
import java.io.File

class ProductStoreAdapter(
    private val dataList : MutableList<ProductStore>
) : BaseRecyclerViewAdapter<ProductStore, ProductStoreAdapter.ProductStoreHolder>(dataList){

    private val productLiveData : MutableLiveData<ProductStore> by lazy { MutableLiveData<ProductStore>() }

    val mProductLiveData : LiveData<ProductStore>
    get() = productLiveData

    override fun mOnCreateViewHolder(parent: ViewGroup, viewType: Int): ProductStoreAdapter.ProductStoreHolder
    = when(viewType){

        ViewType.ProductStore.PRODUCT_TOP_WEAR -> TopWearHolder(
            AdapterProductRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

        ViewType.ProductStore.PRODUCT_BOTTOM_WEAR -> BottomWearHolder(
            AdapterProductRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

        else -> TopWearHolder(
            AdapterProductRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class TopWearHolder(private val binding: AdapterProductRowBinding) : ProductStoreHolder(binding) {

        override fun onBind(model: ProductStore?) {
            model?.apply {
                binding.imgProduct.apply {
                    hierarchy.setPlaceholderImage(R.drawable.ic_sweater)
                    hierarchy.setFailureImage(R.drawable.ic_sweater)
                }.setImageURI(Uri.fromFile(File(imagePath)), itemView.context)

                productLiveData.postValue(this)
            }
        }

        override fun viewDetachedFromWindow() {}

    }


    inner class BottomWearHolder(private val binding: AdapterProductRowBinding) : ProductStoreHolder(binding) {

        override fun onBind(model: ProductStore?) {
            model?.apply {
                binding.imgProduct.apply {
                    hierarchy.setPlaceholderImage(R.drawable.ic_trousers)
                    hierarchy.setFailureImage(R.drawable.ic_trousers)
                }.setImageURI(Uri.fromFile(File(imagePath)), itemView.context)

                productLiveData.postValue(this)
            }
        }

        override fun viewDetachedFromWindow() {}

    }

    open inner class ProductStoreHolder(private val binding: ViewDataBinding) : BaseViewHolder(binding.root) {

        override fun onBind(model: ProductStore?) {
            // implement common code like click action
            binding.executePendingBindings()
            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
        }

        override fun viewDetachedFromWindow() {}

    }

    override fun getItemViewType(position: Int): Int
            = if(this.dataList.isNotEmpty()) ViewType.getProductViewType(this.dataList[position].productType) else super.getItemViewType(position)

}
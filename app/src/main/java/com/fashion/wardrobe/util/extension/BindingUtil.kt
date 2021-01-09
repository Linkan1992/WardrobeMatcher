package com.fashion.wardrobe.util.extension

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fashion.wardrobe.R
import com.fashion.wardrobe.data.entiity.db.ProductStore
import com.fashion.wardrobe.ui.adapter.productAdapter.ProductStoreAdapter
import com.fashion.wardrobe.util.constUtil.AppConstants


@BindingAdapter("productAdapter", "productType", "isRandomSync", "viewIndex")
fun bindProfileAdapter(
    recyclerView: RecyclerView, dataList : List<ProductStore>, productType : String, isRandomSync : Boolean, viewIndexPos : Int
) {
    val adapter = recyclerView.adapter as ProductStoreAdapter?
    adapter?.let {

        if(isRandomSync){
                it.itemInsertedAtIndex(viewIndexPos, dataList)
            return
        }

       // if(dataList.isNotEmpty()){

        it.clearItems()
      //  it.itemRangeChanged( 1 , ArrayList<ProductStore>().apply { add(dataList[dataList.size - 1]) })
        it.addItems(dataList)
      //  }
    }
}

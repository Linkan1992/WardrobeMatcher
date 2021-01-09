package com.fashion.wardrobe.ui.activity.main

import androidx.recyclerview.widget.LinearLayoutManager
import com.fashion.wardrobe.ui.adapter.productAdapter.ProductStoreAdapter
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideProductAdapter() : ProductStoreAdapter = ProductStoreAdapter(ArrayList())

    @Provides
    fun provideLayoutManager(activity: MainActivity) : LinearLayoutManager = LinearLayoutManager(activity)

}
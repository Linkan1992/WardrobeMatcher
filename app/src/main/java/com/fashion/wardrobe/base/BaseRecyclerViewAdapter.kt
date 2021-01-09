package com.fashion.wardrobe.base


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fashion.wardrobe.databinding.LayoutEmptyLeadRowBinding
import com.fashion.wardrobe.util.constUtil.ViewType

abstract class BaseRecyclerViewAdapter<T, K : BaseRecyclerViewAdapter<T, K>.BaseViewHolder>(private val data: MutableList<T>) :
    RecyclerView.Adapter<K>() {

    var mEmptyBinding : LayoutEmptyLeadRowBinding? = null

    protected abstract fun mOnCreateViewHolder(parent: ViewGroup, viewType: Int): K

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K =
        when (viewType) {

            ViewType.VIEW_TYPE_NORMAL -> mOnCreateViewHolder(parent, viewType)

            ViewType.VIEW_TYPE_EMPTY -> {

                val emptyRowLayoutBinding = LayoutEmptyLeadRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                 EmptyLeadViewHolder(emptyRowLayoutBinding) as K
            }

            else ->  mOnCreateViewHolder(parent, viewType)
        }



    override fun onBindViewHolder(holder: K, position: Int) {
        if (holder != null && data.isNotEmpty())
            holder.onBind(data[position])
        else if (data.isEmpty())
            holder.onBind(null)

    }

    override fun getItemCount(): Int = if (data.isNotEmpty()) data.size else 1

    override fun getItemViewType(position: Int): Int = if (data.isNotEmpty()) ViewType.VIEW_TYPE_NORMAL else  ViewType.VIEW_TYPE_EMPTY

    open fun addItems(repoList: List<T>) {
        data.addAll(repoList)
        notifyDataSetChanged()
    }

    fun itemRangeChanged(
        itemCountChanged: Int,
        child: List<T>
    ) {
        val start = itemCount
        data.addAll(child)
        notifyItemRangeChanged(start, itemCountChanged)
    }


    fun itemInsertedAtIndex(
        insertionIndex: Int,
        child: List<T>
    ) {

   /*     data.set(insertionIndex, child[0])
       // notifyItemInserted(insertionIndex)
        notifyItemChanged(insertionIndex)*/

        val product = data[insertionIndex]
        val indexOfItem = data.indexOf(child[0])

        data.set(insertionIndex, child[0])
        data.set(indexOfItem, product)

        notifyItemChanged(insertionIndex)
        notifyItemChanged(indexOfItem)
    }


    fun clearItems() {
        data.clear()
    }

    fun getData(): List<T?> {
        return data
    }


    override fun onViewDetachedFromWindow(holder: K) {
        holder.viewDetachedFromWindow()
        super.onViewDetachedFromWindow(holder)
    }


    // Empty ViewHolder
     inner class EmptyLeadViewHolder(private val mBinding: LayoutEmptyLeadRowBinding) :
        BaseViewHolder(mBinding.root) {

        override fun onBind(model: T?) {
            mEmptyBinding = mBinding
            mBinding.executePendingBindings()
        }

        override fun viewDetachedFromWindow() {}

    }


    abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        abstract fun onBind(model: T?)

        abstract fun viewDetachedFromWindow()

    }

}
package com.wa82bj.ads_mvvm_github.ui.main.allAds

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.wa82bj.ads_mvvm_github.AppExecutors
import com.wa82bj.ads_mvvm_github.R
import com.wa82bj.ads_mvvm_github.data.model.AdsModel
import com.wa82bj.ads_mvvm_github.databinding.ItemRowLeftBinding
import com.wa82bj.ads_mvvm_github.databinding.ItemRowRightOldBinding
import com.wa82bj.ads_mvvm_github.databinding.ItemSyriaLinkBinding
import com.wa82bj.ads_mvvm_github.ui.common.RetryAndWebWiewListener
import com.wa82bj.ads_mvvm_github.ui.common.adapter.DataBoundListCustomAdapter



class HomeAdapter(

    appExecutors: AppExecutors,
    private val retryAndWebWiewListener: RetryAndWebWiewListener,
    private val callback: ((AdsModel) -> Unit)?

) : DataBoundListCustomAdapter<AdsModel, ViewDataBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<AdsModel>() {
        override fun areItemsTheSame(oldItem: AdsModel, newItem: AdsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AdsModel, newItem: AdsModel): Boolean {
            return oldItem == newItem
        }
    }
) {
    private enum class ItemType(val value: Int) {
        MORE(2),
        LAYOUT_RIGHT(3),
        LAYOUT_LEFT(4)
    }

    private var isError = false
    private val limit = 12

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutId = when (viewType) {

            ItemType.LAYOUT_RIGHT.value -> R.layout.item_row_right_old

            ItemType.MORE.value -> R.layout.item_syria_link

            ItemType.LAYOUT_LEFT.value -> R.layout.item_row_left

            else -> R.layout.item_syria_link
        }

        return DataBindingUtil.inflate(
            layoutInflater, layoutId,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: AdsModel) {
        when (binding) {
            is ItemRowRightOldBinding -> {
                binding.root.setOnClickListener {
                    binding.productsModel?.let { product ->
                        callback?.invoke(product)
                    }
                }
                binding.productsModel = item

                if (item.fav == true){
                    binding.container.setBackgroundResource(R.color.favoritedColor)
                }else{
                    binding.container.setBackgroundResource(R.color.white)
                }


            }
            is ItemSyriaLinkBinding -> {
                binding.retry = retryAndWebWiewListener
                binding.isError = isError
            }
        }

        when (binding) {
            is ItemRowLeftBinding -> {
                binding.root.setOnClickListener {
                    binding.productsModel?.let { ads ->
                        callback?.invoke(ads)
                    }
                }
                binding.productsModel = item
                if (item.fav == true){
                    binding.container.setBackgroundResource((R.color.favoritedColor))
                }else{
                    binding.container.setBackgroundResource(R.color.white)
                }

            }

        }
    }

    fun onFailure(isError: Boolean) {
        this.isError = isError
        notifyItemChanged(itemCount - 1)
    }

    override fun getItemCount() = getCurrentList().size + 1

    override fun getItem(position: Int): AdsModel =
        getCurrentList().getOrElse(position) {
            AdsModel()
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getItemViewType(position: Int): Int {

        val available = getItem(position).fav

        if (available == false){
            return ItemType.LAYOUT_RIGHT.value
        }
        else{
            if (position == itemCount - 1) {
                return ItemType.MORE.value
            } else{
                return  ItemType.LAYOUT_LEFT.value
            }

        }



    }

}
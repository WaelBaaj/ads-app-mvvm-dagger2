package com.wa82bj.ads_mvvm_github.ui.main.offers

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.wa82bj.ads_mvvm_github.AppExecutors
import com.wa82bj.ads_mvvm_github.R
import com.wa82bj.ads_mvvm_github.data.model.OfferModel
import com.wa82bj.ads_mvvm_github.databinding.ItemOfferBinding
import com.wa82bj.ads_mvvm_github.databinding.ItemSyriaLinkBinding
import com.wa82bj.ads_mvvm_github.ui.common.RetryAndWebWiewListener
import com.wa82bj.ads_mvvm_github.ui.common.adapter.DataBoundListCustomAdapter

class OfferAdapter(

    appExecutors: AppExecutors,
    private val retryAndWebWiewListener: RetryAndWebWiewListener,
    private val callback: ((OfferModel) -> Unit)?

) : DataBoundListCustomAdapter<OfferModel, ViewDataBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<OfferModel>() {
        override fun areItemsTheSame(oldItem: OfferModel, newItem: OfferModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OfferModel, newItem: OfferModel): Boolean {
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

            ItemType.LAYOUT_RIGHT.value -> R.layout.item_offer

            ItemType.MORE.value -> R.layout.item_syria_link

            ItemType.LAYOUT_LEFT.value -> R.layout.item_offer

            else -> R.layout.item_syria_link
        }

        return DataBindingUtil.inflate(
            layoutInflater, layoutId,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: OfferModel) {
        when (binding) {
            is ItemOfferBinding -> {
                binding.root.setOnClickListener {
                    binding.offerModel?.let { product ->
                        callback?.invoke(product)
                    }
                }
                binding.offerModel = item

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
            is ItemOfferBinding -> {
                binding.root.setOnClickListener {
                    binding.offerModel?.let { ads ->
                        callback?.invoke(ads)
                    }
                }
                binding.offerModel = item
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

    override fun getItem(position: Int): OfferModel =
        getCurrentList().getOrElse(position) {
            OfferModel()
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
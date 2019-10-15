package com.wa82bj.ads_mvvm_github.ui.main.offers

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.wa82bj.ads_mvvm_github.AppExecutors
import com.wa82bj.ads_mvvm_github.R
import com.wa82bj.ads_mvvm_github.data.model.OfferModel
import com.wa82bj.ads_mvvm_github.databinding.ItemOfferSmallBinding
import com.wa82bj.ads_mvvm_github.databinding.ItemOfferSmallMoreBinding
import com.wa82bj.ads_mvvm_github.ui.common.RetryAndWebWiewListener
import com.wa82bj.ads_mvvm_github.ui.common.adapter.DataBoundListCustomAdapter


class OffersAdapterHor(

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
        MORE(1),
        LAYOUT_OFFER(2)

    }

    private var isError = false


    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutId = when (viewType) {

            ItemType.LAYOUT_OFFER.value -> R.layout.item_offer_small

            else -> R.layout.item_offer_small_more
        }

        return DataBindingUtil.inflate(
            layoutInflater, layoutId,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: OfferModel) {
        when (binding) {
            is ItemOfferSmallBinding -> {
                binding.root.setOnClickListener {
                    binding.offerModel?.let { product ->
                        callback?.invoke(product)
                    }
                }
                binding.offerModel = item

            }
            is ItemOfferSmallMoreBinding -> {

                binding.cardView.setOnClickListener {
                    it.findNavController().navigate(R.id.action_ToOffersFragment)
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

            if (position == itemCount - 1) {

                return  ItemType.MORE.value
            } else{
                return ItemType.LAYOUT_OFFER.value
            }

        }

}
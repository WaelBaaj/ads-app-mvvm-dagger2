package com.wa82bj.ads_mvvm_github.ui.main.news

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.wa82bj.ads_mvvm_github.AppExecutors
import com.wa82bj.ads_mvvm_github.R
import com.wa82bj.ads_mvvm_github.data.model.NewsModel
import com.wa82bj.ads_mvvm_github.databinding.ItemNewsBinding
import com.wa82bj.ads_mvvm_github.databinding.ItemSyriaLinkBinding
import com.wa82bj.ads_mvvm_github.ui.common.RetryAndWebWiewListener
import com.wa82bj.ads_mvvm_github.ui.common.adapter.DataBoundListCustomAdapter

class NewsAdapter(

    appExecutors: AppExecutors,
    private val retryAndWebWiewListener: RetryAndWebWiewListener,
    private val callback: ((NewsModel) -> Unit)?

) : DataBoundListCustomAdapter<NewsModel, ViewDataBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<NewsModel>() {
        override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem == newItem
        }
    }
) {
    private enum class ItemType(val value: Int) {
        MORE(2),
        LAYOUT_NEWS(3),
    }

    private var isError = false


    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutId = when (viewType) {

            ItemType.LAYOUT_NEWS.value -> R.layout.item_news

            else -> R.layout.item_syria_link
        }

        return DataBindingUtil.inflate(
            layoutInflater, layoutId,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: NewsModel) {
        when (binding) {
            is ItemNewsBinding -> {
                binding.root.setOnClickListener {
                    binding.newsModel?.let { news ->
                        callback?.invoke(news)
                    }
                }
                binding.newsModel = item

            }
            is ItemSyriaLinkBinding -> {
                binding.retry = retryAndWebWiewListener
                binding.isError = isError
            }
        }
    }

    fun onFailure(isError: Boolean) {
        this.isError = isError
        notifyItemChanged(itemCount - 1)
    }

    override fun getItemCount() = getCurrentList().size + 1

    override fun getItem(position: Int): NewsModel =
        getCurrentList().getOrElse(position) {
            NewsModel()
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getItemViewType(position: Int): Int {


       if (position == itemCount - 1) {
                return ItemType.MORE.value
          } else{
                return  ItemType.LAYOUT_NEWS.value
          }

       }


}
package com.wa82bj.ads_mvvm_github.ui.main.allAds

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wa82bj.ads_mvvm_github.AppExecutors
import com.wa82bj.ads_mvvm_github.R
import com.wa82bj.ads_mvvm_github.autoCleared
import com.wa82bj.ads_mvvm_github.data.model.AdsModel
import com.wa82bj.ads_mvvm_github.data.model.OfferModel
import com.wa82bj.ads_mvvm_github.databinding.ProductsFragmentBinding
import com.wa82bj.ads_mvvm_github.ui.common.Result
import com.wa82bj.ads_mvvm_github.ui.common.RetryAndWebWiewListener
import com.wa82bj.ads_mvvm_github.ui.common.adapter.EndlessScrollListener
import com.wa82bj.ads_mvvm_github.ui.main.offers.OffersAdapterHor
import com.wa82bj.ads_mvvm_github.util.toast
import com.wa82bj.ads_mvvm_github.vmFactory.ViewModelFactory
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : DaggerFragment(), RetryAndWebWiewListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var appExecutors: AppExecutors
    private var adapter by autoCleared<HomeAdapter>()
    private var offerAdapter by autoCleared<OffersAdapterHor>()
    private lateinit var binding: ProductsFragmentBinding
    private lateinit var loadingAdsType: LiveData<Result<List<AdsModel>>>
    private lateinit var loadingOfferType: LiveData<Result<List<OfferModel>>>

    private val productViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    companion object {
        private const val SPAN_COUNT = 1

        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ProductsFragmentBinding.inflate(layoutInflater, container, false)
        binding.productsModel = productViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initRecyclerViewHor()

        if (adapter.itemCount <= 1){
            loadingAdsType = productViewModel.product
            loadAds()
        }else{
            loadingAdsType = productViewModel.productDB
            loadAds()
        }

        if (offerAdapter.itemCount <= 1){
            loadingOfferType = productViewModel.offer
            loadOffers()
        }else{
            loadingOfferType = productViewModel.offerDB
            loadOffers()
        }

        productViewModel.isLoadingAds.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading.let {
                adapter.onFailure(!it)
            }
        })

    }

    private fun loadAds() {
        loadingAdsType.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    adapter.submitList(result.data)
                    adapter.onFailure(false)
                }
                is Result.Failure -> {
                    adapter.onFailure(true)
                    Timber.wtf(result.errorMessage)
                }
            }
        })
    }

    private fun loadOffers() {
        loadingOfferType.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    offerAdapter.submitList(result.data)
                    offerAdapter.onFailure(false)
                }
                is Result.Failure -> {
                    offerAdapter.onFailure(true)
                    Timber.wtf(result.errorMessage)
                }
            }
        })
    }

    @SuppressLint("LogNotTimber")
    private fun initRecyclerView() {

        val adapter = HomeAdapter(appExecutors, this) {

            val bundle = bundleOf("KEY_ADS_ID" to it.id)
            view?.findNavController()?.navigate(R.id.action_ToDetailActivity, bundle)

            Log.d("TAG","KEY_ADS_ID IS : " + bundle)
        }

        this.adapter = adapter

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    binding.recyclerView.layoutManager?.scrollToPosition(0)
                }
            }
        })

        val layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        binding.recyclerView.layoutManager = layoutManager

        val scrollListener = object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(currentPage: Int) {

                productViewModel.loadProducts(currentPage)
            }
        }
        productViewModel.refresh.observe(viewLifecycleOwner, Observer {
            scrollListener.reset()

        })

        binding.recyclerView.addOnScrollListener(scrollListener)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setNestedScrollingEnabled(false);
    }

    @SuppressLint("LogNotTimber", "WrongConstant")
    private fun initRecyclerViewHor() {

        val offerAdapter = OffersAdapterHor(appExecutors, this) {

            val bundle = bundleOf("KEY_OFFER_ID" to it.id)
            view?.findNavController()?.navigate(R.id.action_ToDetailActivity, bundle)

            Log.d("TAG","KEY_OFFER_ID IS : " + bundle)
        }

        this.offerAdapter = offerAdapter

        offerAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    binding.recyclerViewHor.layoutManager?.scrollToPosition(0)

                }
            }
        })
        val layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        binding.recyclerViewHor.layoutManager = layoutManager

        productViewModel.loadProducts()

        productViewModel.refresh.observe(viewLifecycleOwner, Observer {

        })

        binding.recyclerViewHor.adapter = offerAdapter
        binding.recyclerViewHor.setNestedScrollingEnabled(false)

    }

    override fun retry() {
        productViewModel.retry()

    }


}

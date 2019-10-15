package com.wa82bj.ads_mvvm_github.ui.main.offers

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wa82bj.ads_mvvm_github.AppExecutors
import com.wa82bj.ads_mvvm_github.R
import com.wa82bj.ads_mvvm_github.autoCleared
import com.wa82bj.ads_mvvm_github.data.model.OfferModel
import com.wa82bj.ads_mvvm_github.databinding.OfferFragmentBinding
import com.wa82bj.ads_mvvm_github.ui.common.Result
import com.wa82bj.ads_mvvm_github.ui.common.RetryAndWebWiewListener
import com.wa82bj.ads_mvvm_github.ui.common.adapter.EndlessScrollListener
import com.wa82bj.ads_mvvm_github.vmFactory.ViewModelFactory
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class OfferFragment : DaggerFragment(), RetryAndWebWiewListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var appExecutors: AppExecutors
    private var adapter by autoCleared<OfferAdapter>()
    private lateinit var binding: OfferFragmentBinding
    private lateinit var loadingType: LiveData<Result<List<OfferModel>>>

    private val offerViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(OfferViewModel::class.java)
    }

    companion object {
        private const val SPAN_COUNT = 1

        fun newInstance(): OfferFragment = OfferFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = OfferFragmentBinding.inflate(layoutInflater, container, false)
        binding.offerModel = offerViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        if (adapter.itemCount <= 1 ){
            loadingType = offerViewModel.offer
            loadOffer()
        }else{
            loadingType = offerViewModel.offerDB
            loadOffer()
        }


        offerViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading.let {
                adapter.onFailure(!it)
            }
        })

    }

    private fun loadOffer() {
        loadingType.observe(viewLifecycleOwner, Observer { result ->
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


    @SuppressLint("LogNotTimber")
    private fun initRecyclerView() {

        val adapter = OfferAdapter(appExecutors, this) {

            val bundle = bundleOf("KEY_OFFER_ID" to it.id)
            view?.findNavController()?.navigate(R.id.action_ToDetailActivity, bundle)

            Log.d("TAG","KEY_OFFER_ID IS : " + bundle)
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

                offerViewModel.loadProducts(currentPage)
            }
        }
        offerViewModel.refresh.observe(viewLifecycleOwner, Observer {
            scrollListener.reset()

        })

        binding.recyclerView.addOnScrollListener(scrollListener)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setNestedScrollingEnabled(false);
    }


    override fun retry() {
        offerViewModel.retry()

    }


}

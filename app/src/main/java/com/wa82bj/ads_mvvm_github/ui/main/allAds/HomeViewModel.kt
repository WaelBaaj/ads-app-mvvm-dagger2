package com.wa82bj.ads_mvvm_github.ui.main.allAds

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.wa82bj.ads_mvvm_github.R
import com.wa82bj.ads_mvvm_github.data.repository.ads.AdsRepository
import com.wa82bj.ads_mvvm_github.data.repository.offer.OfferRepository
import com.wa82bj.ads_mvvm_github.ui.common.toResult
import com.wa82bj.ads_mvvm_github.util.ext.map
import com.wa82bj.ads_mvvm_github.util.ext.toLiveData
import com.wa82bj.ads_mvvm_github.util.onSNACK
import com.wa82bj.ads_mvvm_github.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val adsRepository: AdsRepository,
    private val offerepository: OfferRepository,
    private val application: Application,
    private val schedulerProvider: SchedulerProvider
) : ViewModel(), LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()
    private val page = MutableLiveData<Int>()
    private val _isLoadingRefresh = ObservableBoolean()
    val isLoadingRefresh: ObservableBoolean = _isLoadingRefresh
    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean> = _refresh

    val cm =  application.applicationContext
        .getSystemService(Context.CONNECTIVITY_SERVICE)
            as (ConnectivityManager)
    val activeNetwork = cm.activeNetworkInfo

    private val _product = Transformations
        .switchMap(page) { page ->
            return@switchMap adsRepository.loadAllAdsApi()
                // Use It for search
                //return@switchMap adsRepository.searchAdsFromApi("","","","")
                .toResult(schedulerProvider)
                .toLiveData()
        }
    val product = _product

    private val _productDB = Transformations
        .switchMap(page) { page ->
            return@switchMap adsRepository.loadAllAdsFromDb()
                .toResult(schedulerProvider)
                .toLiveData()
        }

    val productDB = _productDB


    private val _offer = Transformations
        .switchMap(page) { page ->
            return@switchMap offerepository.loadAllOfferApi()
                .toResult(schedulerProvider)
                .toLiveData()
        }
    val offer = _offer

    private val _offerDB = Transformations
        .switchMap(page) { page ->
            return@switchMap offerepository.loadAllOfferFromDb()
                .toResult(schedulerProvider)
                .toLiveData()
        }

    val offerDB = _offerDB

    private val _isLoadingAds: LiveData<Boolean> by lazy {
        _product.map {
            _isLoadingRefresh.set(it.inProgress)
            it.inProgress
        }
    }

    val isLoadingAds = _isLoadingAds

    private val _isLoadingOffer: LiveData<Boolean> by lazy {
        _offer.map {
            _isLoadingRefresh.set(it.inProgress)
            it.inProgress
        }
    }

    val isLoadingOffer = _isLoadingOffer


    fun loadProducts(page: Int = 1) {
        this.page.value = page
    }

    fun onRefresh() {
        _refresh.value = true
        page.value = 1
    }

    fun retry() {
        _refresh.value = true
        page.value = 1
    }

    fun isConnected():Boolean {
        val isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting
        return isConnected
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun onReloadClick(view: View) {

        application.onSNACK(view,"Check Your Internet Connection !","Ok")
        // val context = view.context
        // context.startActivity(Intent(context, MainActivity::class.java))

    }

    fun onRestaurantClick(view: View) {

        val bundle = bundleOf("RESTAURANT_KEY" to 3)
        view.findNavController().navigate(R.id.action_ToSearchResultFragment,bundle)

    }

}

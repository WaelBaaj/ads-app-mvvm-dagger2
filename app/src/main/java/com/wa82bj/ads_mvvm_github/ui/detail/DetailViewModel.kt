package com.wa82bj.ads_mvvm_github.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wa82bj.ads_mvvm_github.data.api.response.checkResponse.ads.AdsEntity
import com.wa82bj.ads_mvvm_github.data.api.response.offer.OfferEntity
import com.wa82bj.ads_mvvm_github.data.repository.adsdetail.GetAdsDetailUseCase
import com.wa82bj.ads_mvvm_github.data.repository.adsdetail.GetOfferDetailUseCase
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getAdsDetailUseCase: GetAdsDetailUseCase,
    private val getOfferDetailUseCase: GetOfferDetailUseCase
) : ViewModel() {

    private val TAG = DetailViewModel::class.java.simpleName
    val productData = MutableLiveData<AdsEntity>()
    val offerData = MutableLiveData<OfferEntity>()
    val isLoad = MutableLiveData<Boolean>()
    val isFavorite = MutableLiveData<Int>()

    init {
        isLoad.value = false
    }

    fun getAdsDetail(id: String?) {
        if (id == null) return
        getAdsDetailUseCase.saveProductId(id)
        getAdsDetailUseCase.execute(
            onSuccess = {
                isLoad.value = true
                productData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun getOfferDetail(id: String?) {
        if (id == null) return
        getOfferDetailUseCase.saveOfferId(id)
        getOfferDetailUseCase.execute(
            onSuccess = {
                isLoad.value = true
                offerData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun updateAdsFavoriteStatus() {
        productData.value?.let { product ->
            if (product.fav == true) {

                getAdsDetailUseCase.updateAsFavorite(0 , product.id)

            }else{
                getAdsDetailUseCase.updateAsFavorite(1 , product.id)

            }
            isFavorite.value?.let {
                isFavorite.value = it
            }
        }
    }


    fun checkAdsFavoriteStatus(fav: Int, adsId: String) {
        isFavorite.value = getAdsDetailUseCase.isFavoriteProduct(fav,adsId)
    }

    fun updateOfferFavoriteStatus() {
        offerData.value?.let { product ->
            if (product.fav == true) {

                getOfferDetailUseCase.updateAsFavorite(0 , product.id)

            }else{
                getOfferDetailUseCase.updateAsFavorite(1 , product.id)

            }
            isFavorite.value?.let {
                isFavorite.value = it
            }
        }
    }


    fun checkOfferFavoriteStatus(fav: Int, adsId: String) {
        isFavorite.value = getOfferDetailUseCase.isFavoriteOffer(fav,adsId)
    }


}

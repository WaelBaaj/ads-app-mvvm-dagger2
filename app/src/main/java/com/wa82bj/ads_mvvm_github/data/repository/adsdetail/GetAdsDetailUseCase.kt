package com.wa82bj.ads_mvvm_github.data.repository.adsdetail

import com.wa82bj.ads_mvvm_github.data.api.response.check24Response.ads.AdsEntity
import io.reactivex.Single
import javax.inject.Inject


class GetAdsDetailUseCase @Inject constructor(
    private val adsRepository: DetailAdsRepository
) : SingleUseCase<AdsEntity>() {

    private var productId: String? = null

    fun saveProductId(id: String) {
        productId = id
    }

    override fun buildUseCaseSingle(): Single<AdsEntity> {
        return adsRepository.getAdsDetail(productId)
    }

    fun updateAsFavorite( fav : Int, productId : String) {
        adsRepository.updateFavoriteAds(fav, productId)
    }

    fun isFavorite(productId: String): Boolean {
       return adsRepository.isFavorite(productId)
    }

    fun isFavoriteProduct(fav: Int,productId: String): Int {
        return adsRepository.isFavoriteAds(fav,productId)
    }
}
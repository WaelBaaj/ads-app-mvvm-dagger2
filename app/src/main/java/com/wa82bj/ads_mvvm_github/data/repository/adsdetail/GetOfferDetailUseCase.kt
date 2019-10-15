package com.wa82bj.ads_mvvm_github.data.repository.adsdetail

import com.wa82bj.ads_mvvm_github.data.api.response.offer.OfferEntity
import io.reactivex.Single
import javax.inject.Inject

class GetOfferDetailUseCase @Inject constructor(
    private val offerRepository: DetailOfferRepository
) : SingleUseCase<OfferEntity>() {

    private var offerId: String? = null

    fun saveOfferId(id: String) {
        offerId = id
    }

    override fun buildUseCaseSingle(): Single<OfferEntity> {
        return offerRepository.getOfferDetail(offerId)
    }

    fun updateAsFavorite( fav : Int, offerId : String) {
        offerRepository.updateFavoriteOffer(fav, offerId)
    }

    fun isFavorite(offerId: String): Boolean {
        return offerRepository.isFavorite(offerId)
    }

    fun isFavoriteOffer(fav: Int,offerId: String): Int {
        return offerRepository.isFavoriteOffer(fav,offerId)
    }
}
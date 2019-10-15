package com.wa82bj.ads_mvvm_github.data.repository.adsdetail

import com.wa82bj.ads_mvvm_github.data.api.response.offer.OfferEntity
import io.reactivex.Single

interface DetailOfferRepository {

    fun getOfferDetail(offerId: String?): Single<OfferEntity>

    fun deleteOffer(offer: OfferEntity)

    fun addOffer(offer: OfferEntity)

    fun updateFavoriteOffer(fav : Int, offerId : String)

    fun isFavoriteOffer (fav : Int ,offerId : String): Int

    fun isFavorite(offerId: String): Boolean

}
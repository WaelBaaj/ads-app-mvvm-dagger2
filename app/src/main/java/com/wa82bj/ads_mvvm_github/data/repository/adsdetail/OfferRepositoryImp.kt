package com.wa82bj.ads_mvvm_github.data.repository.adsdetail

import com.wa82bj.ads_mvvm_github.data.api.response.offer.OfferEntity
import com.wa82bj.ads_mvvm_github.data.db.AppDatabase
import io.reactivex.Single

class OfferRepositoryImp(
    private val database: AppDatabase
) : DetailOfferRepository {


    override fun deleteOffer(offer: OfferEntity) {
        database.OfferDao().deleteAll()   }

    override fun addOffer(offer: OfferEntity) {

        database.OfferDao().insert(offer)
    }

    override fun updateFavoriteOffer(fav: Int, offerId : String) {
        database.OfferDao().update(fav , offerId)    }

    override fun isFavorite(offerId: String): Boolean {
        val loadOneByOfferId = database.OfferDao().loadOneByOfferId(offerId)
        return loadOneByOfferId != null  }

    override fun isFavoriteOffer(fav : Int, offerId : String) : Int {
        return fav  }


    override fun getOfferDetail(offerId: String?): Single<OfferEntity> {
        return database.OfferDao().loadOneByOfferId(offerId!!)
    }



}
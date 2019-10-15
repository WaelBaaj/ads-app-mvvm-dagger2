package com.wa82bj.ads_mvvm_github.data.repository.offer

import com.wa82bj.ads_mvvm_github.data.api.response.offer.OfferEntity
import com.wa82bj.ads_mvvm_github.data.model.OfferModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface OfferRepository {

    val offer: Flowable<List<OfferModel>>
    fun loadAllOfferApi(): Single<List<OfferModel>>
    fun loadAllOfferFromDb(): Single<List<OfferModel>>
    fun loadOfferFromApi(): Single<List<OfferModel>>
    fun saveOffer(offer: List<OfferEntity>): Completable

}
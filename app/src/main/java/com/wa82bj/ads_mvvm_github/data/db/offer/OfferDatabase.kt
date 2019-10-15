package com.wa82bj.ads_mvvm_github.data.db.offer

import com.wa82bj.ads_mvvm_github.data.api.response.offer.OfferEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface OfferDatabase {

    fun saveOfferEntities(ads: List<OfferEntity>)
    fun getAllOffer(): Flowable<List<OfferEntity>>
    fun getOfferLessThanAndEqualPage(): Single<List<OfferEntity>>

}
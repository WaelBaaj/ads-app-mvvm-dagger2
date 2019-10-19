package com.wa82bj.ads_mvvm_github.data.repository.offer

import com.wa82bj.ads_mvvm_github.data.api.AdsApi
import com.wa82bj.ads_mvvm_github.data.api.response.offer.OfferEntity
import com.wa82bj.ads_mvvm_github.data.db.offer.OfferDatabase
import com.wa82bj.ads_mvvm_github.util.toOffer
import com.wa82bj.ads_mvvm_github.data.model.OfferModel
import com.wa82bj.ads_mvvm_github.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import timber.log.Timber
import javax.inject.Inject


class OfferDataRepository @Inject constructor(
    private val api: AdsApi,
    private val offerDatabase: OfferDatabase,
    private val schedulerProvider: SchedulerProvider

) : OfferRepository {


    override val offer: Flowable<List<OfferModel>>
        get() = offerDatabase.getAllOffer()
            .toOffer()

    override fun loadAllOfferApi(): Single<List<OfferModel>> =
        Single.zip(loadOfferFromApi(), loadOfferDb(), BiFunction { t1, t2 ->
            if (t1.isNotEmpty()) {
                val offer = ArrayList<OfferModel>()
                offer.addAll(t1)
                return@BiFunction offer.toList()
            } else return@BiFunction t2
        })

    override fun loadAllOfferFromDb(): Single<List<OfferModel>> =
        Single.zip(loadOfferDb(), loadOfferDb(), BiFunction { t1, t2 ->
            if (t1.isNotEmpty()) {
                val offer = ArrayList<OfferModel>()
                offer.addAll(t1)
                return@BiFunction offer.toList()
            } else return@BiFunction t2
        })

    private fun loadOfferDb(): Single<List<OfferModel>> =
        offerDatabase.getOfferLessThanAndEqualPage()
            .map {
                return@map it.toOffer()
            }
            .subscribeOn(schedulerProvider.io())

    override fun loadOfferFromApi(): Single<List<OfferModel>> =
        api.loadAllOffers()
            .map {
                val newOffer = it.data
                saveOffer(newOffer).subscribe()
                return@map newOffer.toOffer()

            }
            .onErrorReturn { error ->
                Timber.e(error.toString())

                return@onErrorReturn emptyList<OfferModel>()
            }
            .subscribeOn(schedulerProvider.io())


    override fun saveOffer (offer: List<OfferEntity>): Completable =
        Completable.create {
            offerDatabase.saveOfferEntities(offer)
            it.onComplete()
        }


}
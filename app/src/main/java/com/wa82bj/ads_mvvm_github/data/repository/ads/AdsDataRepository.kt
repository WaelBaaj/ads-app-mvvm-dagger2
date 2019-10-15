package com.wa82bj.ads_mvvm_github.data.repository.ads

import com.wa82bj.ads_mvvm_github.data.api.AdsApi
import com.wa82bj.ads_mvvm_github.data.api.response.check24Response.ads.AdsEntity
import com.wa82bj.ads_mvvm_github.data.db.ads.AdsDatabase
import com.wa82bj.ads_mvvm_github.data.db.toProducts
import com.wa82bj.ads_mvvm_github.data.model.AdsModel
import com.wa82bj.ads_mvvm_github.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import timber.log.Timber
import javax.inject.Inject

class AdsDataRepository @Inject constructor(
    private val api: AdsApi,
    private val adsDatabase: AdsDatabase,
    private val schedulerProvider: SchedulerProvider

) : AdsRepository {

    override val ads: Flowable<List<AdsModel>>
        get() = adsDatabase.getAllAds()
            .toProducts()

    override fun loadAllAdsApi(): Single<List<AdsModel>> =
        Single.zip(loadAdsFromApi(), loadAdsDb(), BiFunction { t1, t2 ->
            if (t1.isNotEmpty()) {
                val products = ArrayList<AdsModel>()
                products.addAll(t1)
                return@BiFunction products.toList()
            } else return@BiFunction t2
        })

    override fun loadAllAdsFromDb(): Single<List<AdsModel>> =
        Single.zip(loadAdsDb(), loadAdsDb(), BiFunction { t1, t2 ->
            if (t1.isNotEmpty()) {
                val products = ArrayList<AdsModel>()
                products.addAll(t1)
                return@BiFunction products.toList()
            } else return@BiFunction t2
        })

    private fun loadAdsDb(): Single<List<AdsModel>> =
        adsDatabase.getAdsLessThanAndEqualPage()
            .map {
                return@map it.toProducts()
            }
            .subscribeOn(schedulerProvider.io())

    override fun loadAdsFromApi(): Single<List<AdsModel>> =
        api.loadAllAds()
            .map {
                val newProducts = it.data
                saveAds(newProducts).subscribe()
                return@map newProducts.toProducts()

            }
            .onErrorReturn { error ->
                Timber.e(error.toString())

                return@onErrorReturn emptyList<AdsModel>()
            }
            .subscribeOn(schedulerProvider.io())

    override fun searchAdsFromApi(
        categoryId: String,
        subCate: String,
        city: String,
        keyword: String
    ): Single<List<AdsModel>> =
        api.searchAds(categoryId,subCate,city,keyword)
            .map {
                val newProducts = it.data
                saveAds(newProducts).subscribe()
                return@map newProducts.toProducts()

            }
            .onErrorReturn { error ->
                Timber.e(error.toString())

                return@onErrorReturn emptyList<AdsModel>()
            }
            .subscribeOn(schedulerProvider.io())


    override fun saveAds (ads: List<AdsEntity>): Completable =
        Completable.create {
            adsDatabase.saveAdsEntities(ads)
            it.onComplete()
        }


    override fun loadFavoriteAds(): Single<List<AdsModel>> =
        Single.zip(loadFavoriteAdsDb(), loadFavoriteAdsDb()
            , BiFunction { t1, t2 ->
                if (t1.isNotEmpty()) {
                    val products = ArrayList<AdsModel>()
                    products.addAll(t1)
                    return@BiFunction products.toList()
                } else return@BiFunction t2
            })

    private fun loadFavoriteAdsDb(): Single<List<AdsModel>> =
        adsDatabase.getFavoriteAds()
            .map {
                return@map it.toProducts()
            }
            .subscribeOn(schedulerProvider.io())


}
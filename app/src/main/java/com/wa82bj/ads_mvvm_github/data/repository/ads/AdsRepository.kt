package com.wa82bj.ads_mvvm_github.data.repository.ads

import com.wa82bj.ads_mvvm_github.data.api.response.checkResponse.ads.AdsEntity
import com.wa82bj.ads_mvvm_github.data.model.AdsModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


interface AdsRepository {

    val ads: Flowable<List<AdsModel>>
    fun loadAllAdsApi(): Single<List<AdsModel>>
    fun loadAllAdsFromDb(): Single<List<AdsModel>>
    fun loadAdsFromApi(): Single<List<AdsModel>>
    fun saveAds(ads: List<AdsEntity>): Completable
    fun loadFavoriteAds(): Single<List<AdsModel>>

    fun searchAdsFromApi(categoryId: String,subCate: String,
                         city: String,keyword: String): Single<List<AdsModel>>

}
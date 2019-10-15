package com.wa82bj.ads_mvvm_github.data.db.ads

import com.wa82bj.ads_mvvm_github.data.api.response.check24Response.ads.AdsEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface AdsDatabase {

        fun saveAdsEntities(ads: List<AdsEntity>)
        fun getAllAds(): Flowable<List<AdsEntity>>
        fun getAdsLessThanAndEqualPage(): Single<List<AdsEntity>>

        fun updateFavoriteAds(fav:Int , adsId : String)

        fun getFavoriteAds(): Single<List<AdsEntity>>

    }
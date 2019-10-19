package com.wa82bj.ads_mvvm_github.data.repository.adsdetail

import com.wa82bj.ads_mvvm_github.data.api.response.checkResponse.ads.AdsEntity
import io.reactivex.Single


interface DetailAdsRepository {

    fun getAdsDetail(adsId: String?): Single<AdsEntity>

    fun deleteAds(ads: AdsEntity)

    fun addAds(ads: AdsEntity)

    fun updateFavoriteAds(fav : Int, adsId : String)

    fun isFavoriteAds (fav : Int, adsId : String): Int

    fun isFavorite(adsId: String): Boolean

}
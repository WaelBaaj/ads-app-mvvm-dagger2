package com.wa82bj.ads_mvvm_github.data.repository.adsdetail

import com.wa82bj.ads_mvvm_github.data.api.response.check24Response.ads.AdsEntity
import com.wa82bj.ads_mvvm_github.data.db.AppDatabase
import io.reactivex.Single


class AdsRepositoryImp(
    private val database: AppDatabase
) : DetailAdsRepository {


    override fun deleteAds(ads: AdsEntity) {
        database.AdsDao().deleteAll()   }

    override fun addAds(ads: AdsEntity) {

        database.AdsDao().insert(ads)
    }

    override fun updateFavoriteAds(fav: Int, adsId : String) {
        database.AdsDao().update(fav , adsId)    }

    override fun isFavorite(adsId: String): Boolean {
        val loadOneByPhotoId = database.AdsDao().loadOneByProductId(adsId)
        return loadOneByPhotoId != null  }

    override fun isFavoriteAds(fav : Int, adsId : String) : Int {
        return fav  }


    override fun getAdsDetail(adsId: String?): Single<AdsEntity> {
        return database.AdsDao().loadOneByProductId(adsId!!)
    }



}
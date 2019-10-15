package com.wa82bj.ads_mvvm_github.data.db.ads

import androidx.room.RoomDatabase
import com.wa82bj.ads_mvvm_github.data.api.response.check24Response.ads.AdsEntity
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

@Suppress("NAME_SHADOWING")
class AdsRoomDatabase @Inject constructor(
    private val database: RoomDatabase,
    private val adsDao: AdsDao
) : AdsDatabase {

    override fun getAdsLessThanAndEqualPage(): Single<List<AdsEntity>> =
        Flowable.just(adsDao.getAdsFromRoomDB())
            .flatMapIterable {
                return@flatMapIterable it
            }
            .map {
                return@map it
           }
           .toList()

    override fun getFavoriteAds(): Single<List<AdsEntity>> =
        Flowable.just(adsDao.getFavoriteProducts())
            .flatMapIterable {
                return@flatMapIterable it
            }
            .map {

                return@map it
            }
            .toList()

    override fun saveAdsEntities(ads: List<AdsEntity>) {
        database.runInTransaction {
            for (products in ads) {

                adsDao.insert(ads)
            }
        }
    }

    override fun getAllAds(): Flowable<List<AdsEntity>> =
        adsDao.getAllProduct()

    override fun updateFavoriteAds(fav : Int, adsId : String) =
        adsDao.update(fav , adsId)




}
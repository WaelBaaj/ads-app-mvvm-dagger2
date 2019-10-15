package com.wa82bj.ads_mvvm_github.data.db.offer

import androidx.room.RoomDatabase
import com.wa82bj.ads_mvvm_github.data.api.response.offer.OfferEntity
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject


@Suppress("NAME_SHADOWING")
class OfferRoomDatabase @Inject constructor(
    private val database: RoomDatabase,
    private val offerDao: OfferDao
) : OfferDatabase {

    override fun getOfferLessThanAndEqualPage(): Single<List<OfferEntity>> =
        Flowable.just(offerDao.getOfferFromRoomDB())
            .flatMapIterable {
                return@flatMapIterable it
            }
            .map {
                return@map it
            }
            .toList()

    override fun saveOfferEntities(offer: List<OfferEntity>) {
        database.runInTransaction {
            for (offer in offer) {

                offerDao.insert(offer)
            }
        }
    }

    override fun getAllOffer(): Flowable<List<OfferEntity>> =
        offerDao.getAllOffer()





}
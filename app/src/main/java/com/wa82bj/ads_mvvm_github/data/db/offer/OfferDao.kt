package com.wa82bj.ads_mvvm_github.data.db.offer

import androidx.room.*
import com.wa82bj.ads_mvvm_github.data.api.response.offer.OfferEntity
import io.reactivex.Flowable
import io.reactivex.Single

    @Dao
    abstract class OfferDao {

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        abstract fun insert(ads: OfferEntity)

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        abstract fun insert(offer: List<OfferEntity>)

        @Query("SELECT * FROM offer_table")
        abstract fun getAllOffer(): Flowable<List<OfferEntity>>

        @Query("SELECT * FROM offer_table ")
        abstract fun getOfferFromRoomDB(): List<OfferEntity>


        @Query("SELECT * FROM offer_table where id = :offerId")
        abstract fun loadOneByOfferId(offerId: String): Single<OfferEntity>


        @Query("DELETE FROM offer_table")
        abstract fun deleteAll()



        // Favorite Offer
        @Query("SELECT * FROM offer_table where fav = 1")
        abstract fun getFavoriteOffer(): List<OfferEntity>

        @Update
        abstract fun update(offer: OfferEntity)

        @Query("UPDATE offer_table SET fav = :fav  WHERE id =:offerId")
        abstract fun update( fav : Int ,offerId : String)

        @Query("SELECT * FROM offer_table  where fav = :fav and id = :offerId")
        abstract fun isFavoriteOffer(fav : Int ,offerId: String): Single<OfferEntity>



    }
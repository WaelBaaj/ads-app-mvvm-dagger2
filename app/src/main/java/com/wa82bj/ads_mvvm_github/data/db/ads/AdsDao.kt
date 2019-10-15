package com.wa82bj.ads_mvvm_github.data.db.ads

import androidx.room.*
import com.wa82bj.ads_mvvm_github.data.api.response.check24Response.ads.AdsEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class AdsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(ads: AdsEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(ads: List<AdsEntity>)

    @Query("SELECT * FROM fahras_table")
    abstract fun getAllProduct(): Flowable<List<AdsEntity>>

    @Query("SELECT * FROM fahras_table ")
    abstract fun getAdsFromRoomDB(): List<AdsEntity>


    @Query("SELECT * FROM fahras_table where id = :productId")
    abstract fun loadOneByProductId(productId: String): Single<AdsEntity>


    @Query("DELETE FROM fahras_table")
    abstract fun deleteAll()



    // Favorite Products
    @Query("SELECT * FROM fahras_table where fav = 1")
    abstract fun getFavoriteProducts(): List<AdsEntity>

    @Update
    abstract fun update(ads: AdsEntity)

    @Query("UPDATE fahras_table SET fav = :fav  WHERE id =:productId")
    abstract fun update( fav : Int ,productId : String)

    @Query("SELECT * FROM fahras_table  where fav = :fav and id = :productId")
    abstract fun isFavoriteProuduct(fav : Int ,productId : String): Single<AdsEntity>



}
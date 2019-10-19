package com.wa82bj.ads_mvvm_github.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wa82bj.ads_mvvm_github.data.api.response.checkResponse.ads.AdsEntity
import com.wa82bj.ads_mvvm_github.data.api.response.news.NewsEntity
import com.wa82bj.ads_mvvm_github.data.api.response.offer.OfferEntity
import com.wa82bj.ads_mvvm_github.data.api.response.user.UserEntity
import com.wa82bj.ads_mvvm_github.data.db.ads.AdsDao
import com.wa82bj.ads_mvvm_github.data.db.news.NewsDao
import com.wa82bj.ads_mvvm_github.data.db.offer.OfferDao
import com.wa82bj.ads_mvvm_github.data.db.user.UserDao
import com.wa82bj.ads_mvvm_github.util.Converters

@Database(
    entities = [
        AdsEntity::class,
        NewsEntity::class,
        OfferEntity::class,
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun AdsDao(): AdsDao

    abstract fun NewsDao(): NewsDao

    abstract fun OfferDao(): OfferDao

    abstract fun UserDao(): UserDao
}
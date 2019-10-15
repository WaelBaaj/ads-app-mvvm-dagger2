package com.wa82bj.ads_mvvm_github.di

import android.app.Application
import androidx.room.Room
import com.wa82bj.ads_mvvm_github.data.db.*
import com.wa82bj.ads_mvvm_github.data.db.ads.AdsDao
import com.wa82bj.ads_mvvm_github.data.db.ads.AdsDatabase
import com.wa82bj.ads_mvvm_github.data.db.ads.AdsRoomDatabase
import com.wa82bj.ads_mvvm_github.data.db.news.NewsDao
import com.wa82bj.ads_mvvm_github.data.db.news.NewsDatabase
import com.wa82bj.ads_mvvm_github.data.db.news.NewsRoomDatabase
import com.wa82bj.ads_mvvm_github.data.db.offer.OfferDao
import com.wa82bj.ads_mvvm_github.data.db.offer.OfferDatabase
import com.wa82bj.ads_mvvm_github.data.db.offer.OfferRoomDatabase
import com.wa82bj.ads_mvvm_github.data.db.user.UserDao
import com.wa82bj.ads_mvvm_github.data.db.user.UserDatabase
import com.wa82bj.ads_mvvm_github.data.db.user.UserRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "ads_app.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()


    @Singleton
    @Provides
    fun provideAdsDao(db: AppDatabase): AdsDao = db.AdsDao()

    @Singleton
    @Provides
    fun provideProductsDatabase(db: AppDatabase, adsDao: AdsDao): AdsDatabase =
        AdsRoomDatabase(db, adsDao)

    @Singleton
    @Provides
    fun provideNewsDao(db: AppDatabase): NewsDao = db.NewsDao()

    @Singleton
    @Provides
    fun provideOfferDao(db: AppDatabase): OfferDao = db.OfferDao()

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.UserDao()


    @Singleton
    @Provides
    fun provideNewsDatabase(db: AppDatabase, newsDao: NewsDao): NewsDatabase =
        NewsRoomDatabase(db, newsDao)

    @Singleton
    @Provides
    fun provideOfferDatabase(db: AppDatabase, offerDao: OfferDao): OfferDatabase =
        OfferRoomDatabase(db, offerDao)


    @Singleton
    @Provides
    fun provideUserDatabase(db: AppDatabase, userDao: UserDao): UserDatabase =
        UserRoomDatabase(db, userDao)

}
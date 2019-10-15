package com.wa82bj.ads_mvvm_github.di

import android.app.Application
import android.content.Context
import com.wa82bj.ads_mvvm_github.data.api.AdsApi
import com.wa82bj.ads_mvvm_github.data.db.AppDatabase
import com.wa82bj.ads_mvvm_github.data.db.ads.AdsDatabase
import com.wa82bj.ads_mvvm_github.data.db.news.NewsDatabase
import com.wa82bj.ads_mvvm_github.data.db.offer.OfferDatabase
import com.wa82bj.ads_mvvm_github.data.db.user.UserDatabase
import com.wa82bj.ads_mvvm_github.data.repository.ads.AdsDataRepository
import com.wa82bj.ads_mvvm_github.data.repository.ads.AdsRepository
import com.wa82bj.ads_mvvm_github.data.repository.adsdetail.DetailAdsRepository
import com.wa82bj.ads_mvvm_github.data.repository.adsdetail.DetailOfferRepository
import com.wa82bj.ads_mvvm_github.data.repository.adsdetail.AdsRepositoryImp
import com.wa82bj.ads_mvvm_github.data.repository.adsdetail.OfferRepositoryImp
import com.wa82bj.ads_mvvm_github.data.repository.news.NewsDataRepository
import com.wa82bj.ads_mvvm_github.data.repository.news.NewsRepository
import com.wa82bj.ads_mvvm_github.data.repository.offer.OfferDataRepository
import com.wa82bj.ads_mvvm_github.data.repository.offer.OfferRepository
import com.wa82bj.ads_mvvm_github.data.repository.user.UserDataRepository
import com.wa82bj.ads_mvvm_github.data.repository.user.UserRepository
import com.wa82bj.ads_mvvm_github.util.rx.AppSchedulerProvider
import com.wa82bj.ads_mvvm_github.util.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
object AppModule {
    @Singleton
    @Provides
    @JvmStatic
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    @JvmStatic
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()


    @Singleton
    @Provides
    @JvmStatic
    fun provideAdsDetailRepository(appDatabase: AppDatabase): DetailAdsRepository {
        return AdsRepositoryImp(appDatabase)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideOfferDataRepository(appDatabase: AppDatabase): DetailOfferRepository {
        return OfferRepositoryImp(appDatabase)
    }



    @Singleton
    @Provides
    @JvmStatic
    fun provideAdsRepository(
        adsApi: AdsApi,
        adsDatabase: AdsDatabase,
        schedulerProvider: SchedulerProvider
    ): AdsRepository =
        AdsDataRepository(
            adsApi,
            adsDatabase,
            schedulerProvider
        )

    @Singleton
    @Provides
    @JvmStatic
    fun provideNewsRepository(
        adsApi: AdsApi,
        newsDatabase: NewsDatabase,
        schedulerProvider: SchedulerProvider
    ): NewsRepository =
        NewsDataRepository(
            adsApi,
            newsDatabase,
            schedulerProvider
        )


    @Singleton
    @Provides
    @JvmStatic
    fun provideOfferRepository(
        adsApi: AdsApi,
        offerDatabase: OfferDatabase,
        schedulerProvider: SchedulerProvider
    ): OfferRepository =
        OfferDataRepository(
            adsApi,
            offerDatabase,
            schedulerProvider
        )


    @Singleton
    @Provides
    @JvmStatic
    fun provideUserRepository(
        adsApi: AdsApi,
        userDatabase: UserDatabase,
        schedulerProvider: SchedulerProvider
    ): UserRepository =
        UserDataRepository(
            adsApi,
            userDatabase,
            schedulerProvider
        )

}
package com.wa82bj.ads_mvvm_github.di

import androidx.appcompat.app.AppCompatActivity
import com.wa82bj.ads_mvvm_github.ui.main.MainActivity
import com.wa82bj.ads_mvvm_github.ui.main.allAds.HomeFragment
import com.wa82bj.ads_mvvm_github.ui.main.news.NewsFragment
import com.wa82bj.ads_mvvm_github.ui.main.favorite.FavoriteFragment
import com.wa82bj.ads_mvvm_github.ui.main.offers.OfferFragment
import com.wa82bj.ads_mvvm_github.ui.main.serachResult.SearchResultFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityModule {

    @Binds
    fun providesAppCompatActivity(mainActivity: MainActivity): AppCompatActivity


    @ContributesAndroidInjector
    fun contributeAdsFragment(): HomeFragment

    @ContributesAndroidInjector
    fun contributeNewsFragment(): NewsFragment

    @ContributesAndroidInjector
    fun contributeFavoriteFragment(): FavoriteFragment

    @ContributesAndroidInjector
    fun contributeOfferFragment(): OfferFragment

    @ContributesAndroidInjector
    fun contributeSearchResultFragment(): SearchResultFragment


}
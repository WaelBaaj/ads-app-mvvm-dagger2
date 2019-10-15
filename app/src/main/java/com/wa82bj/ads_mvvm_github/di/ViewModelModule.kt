package com.wa82bj.ads_mvvm_github.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wa82bj.ads_mvvm_github.ui.main.allAds.HomeViewModel
import com.wa82bj.ads_mvvm_github.ui.main.news.NewsViewModel
import com.wa82bj.ads_mvvm_github.ui.main.favorite.FavoriteViewModel
import com.wa82bj.ads_mvvm_github.ui.main.offers.OfferViewModel
import com.wa82bj.ads_mvvm_github.ui.main.serachResult.SearchResultViewModel
import com.wa82bj.ads_mvvm_github.vmFactory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindProductsViewModel(
        homeViewModel: HomeViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    fun bindAvailableViewModel(
        newsViewModel: NewsViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    fun bindFavoriteViewModel(
        favoriteViewModel: FavoriteViewModel
    ): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(OfferViewModel::class)
    fun bindOfferViewModel(
        offerViewModel: OfferViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchResultViewModel::class)
    fun bindSearchResultViewModel(
        searchResultViewModel: SearchResultViewModel
    ): ViewModel


    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
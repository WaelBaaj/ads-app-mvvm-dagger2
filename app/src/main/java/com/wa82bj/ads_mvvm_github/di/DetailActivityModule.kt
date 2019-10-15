package com.wa82bj.ads_mvvm_github.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.wa82bj.ads_mvvm_github.ui.detail.DetailActivity
import com.wa82bj.ads_mvvm_github.ui.detail.DetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface DetailActivityModule {

    @Binds
    fun providesDetailAppCompatActivity(detailActivity: DetailActivity): AppCompatActivity


    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    fun bindDetailViewModel(detailViewModel: DetailViewModel): ViewModel

}
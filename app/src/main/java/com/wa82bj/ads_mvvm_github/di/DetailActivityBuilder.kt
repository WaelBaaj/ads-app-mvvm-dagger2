package com.wa82bj.ads_mvvm_github.di

import com.wa82bj.ads_mvvm_github.ui.detail.DetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface DetailActivityBuilder {

    @ContributesAndroidInjector(modules = [
        DetailActivityModule::class
    ])
    fun contributeDetailActivity(): DetailActivity

}
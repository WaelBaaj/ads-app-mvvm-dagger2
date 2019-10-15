package com.wa82bj.ads_mvvm_github.di

import com.wa82bj.ads_mvvm_github.ui.auth.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AuthActivityBuilder {

    @ContributesAndroidInjector(modules = [
        AuthActivityModule::class
    ])
    fun contributeLoginActivity(): LoginActivity
}
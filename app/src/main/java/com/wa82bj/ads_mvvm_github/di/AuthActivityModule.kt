package com.wa82bj.ads_mvvm_github.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.wa82bj.ads_mvvm_github.ui.auth.LoginActivity
import com.wa82bj.ads_mvvm_github.ui.auth.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface AuthActivityModule {

    @Binds
    fun providesAuthAppCompatActivity(loginActivity: LoginActivity): AppCompatActivity


    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindAuthViewModel(loginViewModel: LoginViewModel): ViewModel
}
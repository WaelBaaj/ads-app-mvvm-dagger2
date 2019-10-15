package com.wa82bj.ads_mvvm_github.di

import android.app.Application
import com.wa82bj.ads_mvvm_github.SyriAdsApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    MainActivityBuilder::class,
    DetailActivityBuilder::class,
    AuthActivityBuilder::class
])
interface AppComponent : AndroidInjector<SyriAdsApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: SyriAdsApp)
}
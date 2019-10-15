package com.wa82bj.ads_mvvm_github.data.db.news

import com.wa82bj.ads_mvvm_github.data.api.response.news.NewsEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface NewsDatabase {

    fun saveNewsEntities(news: List<NewsEntity>)
    fun getAllNews(): Flowable<List<NewsEntity>>
    fun getNewsLessThanAndEqualPage(): Single<List<NewsEntity>>

}
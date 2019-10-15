package com.wa82bj.ads_mvvm_github.data.repository.news

import com.wa82bj.ads_mvvm_github.data.api.response.news.NewsEntity
import com.wa82bj.ads_mvvm_github.data.model.NewsModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface NewsRepository {

    val ads: Flowable<List<NewsModel>>
    fun loadAllNewsApi(): Single<List<NewsModel>>
    fun loadAllNewsFromDb(): Single<List<NewsModel>>
    fun loadNewsFromApi(): Single<List<NewsModel>>
    fun saveNews(news: List<NewsEntity>): Completable
}
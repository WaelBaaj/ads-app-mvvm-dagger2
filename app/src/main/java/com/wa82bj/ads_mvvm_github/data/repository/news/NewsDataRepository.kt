package com.wa82bj.ads_mvvm_github.data.repository.news

import com.wa82bj.ads_mvvm_github.data.api.AdsApi
import com.wa82bj.ads_mvvm_github.data.api.response.news.NewsEntity
import com.wa82bj.ads_mvvm_github.data.db.news.NewsDatabase
import com.wa82bj.ads_mvvm_github.util.toNews
import com.wa82bj.ads_mvvm_github.data.model.NewsModel
import com.wa82bj.ads_mvvm_github.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import timber.log.Timber
import javax.inject.Inject


class NewsDataRepository @Inject constructor(
    private val api: AdsApi,
    private val newsDatabase: NewsDatabase,
    private val schedulerProvider: SchedulerProvider

) : NewsRepository {


    override val ads: Flowable<List<NewsModel>>
        get() = newsDatabase.getAllNews()
            .toNews()

    override fun loadAllNewsApi(): Single<List<NewsModel>> =
        Single.zip(loadNewsFromApi(), loadNewsDb(), BiFunction { t1, t2 ->
            if (t1.isNotEmpty()) {
                val news = ArrayList<NewsModel>()
                news.addAll(t1)
                return@BiFunction news.toList()
            } else return@BiFunction t2
        })

    override fun loadAllNewsFromDb(): Single<List<NewsModel>> =
        Single.zip(loadNewsDb(), loadNewsDb(), BiFunction { t1, t2 ->
            if (t1.isNotEmpty()) {
                val news = ArrayList<NewsModel>()
                news.addAll(t1)
                return@BiFunction news.toList()
            } else return@BiFunction t2
        })




    private fun loadNewsDb(): Single<List<NewsModel>> =
        newsDatabase.getNewsLessThanAndEqualPage()
            .map {
                return@map it.toNews()
            }
            .subscribeOn(schedulerProvider.io())

    override fun loadNewsFromApi(): Single<List<NewsModel>> =
        api.loadAllNews()
            .map {
                val newNews = it.data
                saveNews(newNews).subscribe()
                return@map newNews.toNews()

            }
            .onErrorReturn { error ->
                Timber.e(error.toString())

                return@onErrorReturn emptyList<NewsModel>()
            }
            .subscribeOn(schedulerProvider.io())


    override fun saveNews (news: List<NewsEntity>): Completable =
        Completable.create {
            newsDatabase.saveNewsEntities(news)
            it.onComplete()
        }



}
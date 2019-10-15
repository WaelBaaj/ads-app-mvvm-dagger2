package com.wa82bj.ads_mvvm_github.data.db.news

import androidx.room.RoomDatabase
import com.wa82bj.ads_mvvm_github.data.api.response.news.NewsEntity
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

@Suppress("NAME_SHADOWING")
class NewsRoomDatabase @Inject constructor(
    private val database: RoomDatabase,
    private val newsDao: NewsDao
) : NewsDatabase {

    override fun getNewsLessThanAndEqualPage(): Single<List<NewsEntity>> =
        Flowable.just(newsDao.getNewsFromRoomDB())
            .flatMapIterable {
                return@flatMapIterable it
            }
            .map {
                return@map it
            }
            .toList()

    override fun saveNewsEntities(news: List<NewsEntity>) {
        database.runInTransaction {
            for (news in news) {

                newsDao.insert(news)
            }
        }
    }

    override fun getAllNews(): Flowable<List<NewsEntity>> =
        newsDao.getAllNews()

}
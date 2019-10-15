package com.wa82bj.ads_mvvm_github.data.db.news

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wa82bj.ads_mvvm_github.data.api.response.news.NewsEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(news: NewsEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(news: List<NewsEntity>)

    @Query("SELECT * FROM news_table")
    abstract fun getAllNews(): Flowable<List<NewsEntity>>

    @Query("SELECT * FROM news_table ")
    abstract fun getNewsFromRoomDB(): List<NewsEntity>


    @Query("SELECT * FROM news_table where id = :productId")
    abstract fun loadOneByNewsId(productId: String): Single<NewsEntity>


    @Query("DELETE FROM news_table")
    abstract fun deleteAllNews()




}
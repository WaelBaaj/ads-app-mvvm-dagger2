package com.wa82bj.ads_mvvm_github.data.db.user

import androidx.room.RoomDatabase
import com.wa82bj.ads_mvvm_github.data.api.response.news.NewsEntity
import com.wa82bj.ads_mvvm_github.data.api.response.user.UserEntity
import com.wa82bj.ads_mvvm_github.data.db.news.NewsDao
import com.wa82bj.ads_mvvm_github.data.db.news.NewsDatabase
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject


@Suppress("NAME_SHADOWING")
class UserRoomDatabase @Inject constructor(
    private val database: RoomDatabase,
    private val userDao: UserDao
) : UserDatabase {



    override fun saveUserEntities(user: UserEntity) {
        database.runInTransaction {

            userDao.insert(user)

        }
    }

    override fun getUser(): Flowable<UserEntity> =
        userDao.getUser()

}
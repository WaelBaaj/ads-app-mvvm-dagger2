package com.wa82bj.ads_mvvm_github.data.db.user

import com.wa82bj.ads_mvvm_github.data.api.response.user.UserEntity
import io.reactivex.Flowable

interface UserDatabase {

    fun saveUserEntities(user: UserEntity)
    fun getUser(): Flowable<UserEntity>
}
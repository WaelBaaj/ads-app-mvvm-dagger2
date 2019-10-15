package com.wa82bj.ads_mvvm_github.data.repository.user

import com.wa82bj.ads_mvvm_github.data.api.response.user.UserEntity
import com.wa82bj.ads_mvvm_github.data.api.response.user.UserResponse
import com.wa82bj.ads_mvvm_github.data.model.UserModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface UserRepository {

    val user: Flowable<UserModel>
    fun getUserApi(user_name : String, password:String): Single<UserModel>
    fun getUserResponse(user_name : String, password:String): Single<UserResponse>
    //fun loadUserFromDb(): Single<UserModel>
    fun saveUser(user: UserEntity): Completable
}
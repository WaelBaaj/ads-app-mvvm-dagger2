package com.wa82bj.ads_mvvm_github.data.repository.user

import com.wa82bj.ads_mvvm_github.data.api.AdsApi
import com.wa82bj.ads_mvvm_github.data.api.response.user.UserEntity
import com.wa82bj.ads_mvvm_github.data.api.response.user.UserResponse
import com.wa82bj.ads_mvvm_github.util.toUser
import com.wa82bj.ads_mvvm_github.data.db.user.UserDatabase
import com.wa82bj.ads_mvvm_github.data.model.UserModel
import com.wa82bj.ads_mvvm_github.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val api: AdsApi,
    private val userDatabase: UserDatabase,
    private val schedulerProvider: SchedulerProvider

) : UserRepository {
    override val user: Flowable<UserModel>
        get()  = userDatabase.getUser()
    .toUser()

    override fun getUserApi(user_name: String, password: String): Single<UserModel> =
        api.getLoginUser(user_name , password)
            .map {
                val newOffer = it.data
                saveUser(newOffer).subscribe()
                return@map newOffer.toUser()

            }
            .onErrorReturn { error ->
                Timber.e(error.toString())

                return@onErrorReturn UserModel()
            }
            .subscribeOn(schedulerProvider.io())

    override fun getUserResponse(user_name: String, password: String): Single<UserResponse> =
        api.getLoginUser(user_name , password)



    override fun saveUser (user: UserEntity): Completable =
        Completable.create {
            userDatabase.saveUserEntities(user)
            it.onComplete()
        }


}
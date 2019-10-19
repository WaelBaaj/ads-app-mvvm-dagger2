package com.wa82bj.ads_mvvm_github.util

import com.wa82bj.ads_mvvm_github.data.api.response.checkResponse.ads.AdsEntity
import com.wa82bj.ads_mvvm_github.data.api.response.news.NewsEntity
import com.wa82bj.ads_mvvm_github.data.api.response.offer.OfferEntity
import com.wa82bj.ads_mvvm_github.data.api.response.user.UserEntity
import com.wa82bj.ads_mvvm_github.data.model.AdsModel
import com.wa82bj.ads_mvvm_github.data.model.NewsModel
import com.wa82bj.ads_mvvm_github.data.model.OfferModel
import com.wa82bj.ads_mvvm_github.data.model.UserModel
import io.reactivex.Flowable

fun Flowable<List<AdsEntity>>.toProducts(): Flowable<List<AdsModel>> = map {
    return@map it.toProducts()
}

fun List<AdsEntity>.toProducts(): List<AdsModel> =
    map { AdsModel(it.city ,it.id, it.fav,it.image, it.address, it.price ,it.description ,
        it.condition,it.title,it.latitude,it.longitude,it.views,it.subCate,it.subCateId,
        it.cityId,it.bookmark,it.category,it.categoryId,it.accountId,it.dateCreated) }

fun Flowable<List<OfferEntity>>.toOffer(): Flowable<List<OfferModel>> = map {
    return@map it.toOffer()
}

fun List<OfferEntity>.toOffer(): List<OfferModel> =
    map { OfferModel(it.city ,it.id, it.fav,it.image, it.address, it.price ,it.description ,
        it.condition,it.title,it.latitude,it.longitude,it.views,it.subCate,it.subCateId,
        it.cityId,it.bookmark,it.category,it.categoryId,it.accountId,it.dateCreated) }


fun Flowable<List<NewsEntity>>.toNews(): Flowable<List<NewsModel>> = map {
    return@map it.toNews()
}

fun List<NewsEntity>.toNews(): List<NewsModel> =
    map { NewsModel(it.dateCreated ,it.description, it.id,it.image, it.title, it.url) }

fun Flowable<UserEntity>.toUser(): Flowable<UserModel> = map {
    return@map it.toUser()
}

fun UserEntity.toUser(): UserModel =
      UserModel ( address , email , userId , image , individual , isVerified , name
      , phone , sex  , longitude, type , userName)






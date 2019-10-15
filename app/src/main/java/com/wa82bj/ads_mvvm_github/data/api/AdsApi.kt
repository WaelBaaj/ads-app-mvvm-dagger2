package com.wa82bj.ads_mvvm_github.data.api

import com.wa82bj.ads_mvvm_github.data.api.response.check24Response.ads.AdsResponse
import com.wa82bj.ads_mvvm_github.data.api.response.news.NewsResponse
import com.wa82bj.ads_mvvm_github.data.api.response.offer.OfferResponse
import com.wa82bj.ads_mvvm_github.data.api.response.user.UserResponse
import io.reactivex.Single
import retrofit2.http.*


interface AdsApi {

    @GET("showAds")
    fun loadAllAds(): Single<AdsResponse>

    @GET("showOffer")
    fun loadAllOffers(): Single<OfferResponse>

    @GET("showNews")
    fun loadAllNews(): Single<NewsResponse>

    @GET("searchAds")
    fun searchAds (@Query("categoryId") categoryId: String?,
                   @Query("subCate") subCate: String?,
                   @Query("city") city: String?,
                   @Query("keyword") keyword: String?): Single<AdsResponse>


    @FormUrlEncoded
    @POST("login")
    fun getLoginUser (
        @Field("user_name") user_name : String,
        @Field("password") password : String
    ): Single<UserResponse>

}
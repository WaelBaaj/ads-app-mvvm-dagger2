package com.wa82bj.ads_mvvm_github.data.api.response.check24Response.ads


data class AdsResponse(

    val allpage: Int,
    val `data`: List<AdsEntity>,
    val message: String,
    val status: String
)
package com.wa82bj.ads_mvvm_github.data.api.response.offer

import com.wa82bj.ads_mvvm_github.data.api.response.check24Response.ads.AdsEntity

class OfferResponse (

    val allpage: Int,
    val `data`: List<OfferEntity>,
    val message: String,
    val status: String

    )



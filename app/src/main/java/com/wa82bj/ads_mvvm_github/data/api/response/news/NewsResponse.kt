package com.wa82bj.ads_mvvm_github.data.api.response.news


data class NewsResponse(
    val `data`: List<NewsEntity>,
    val message: String,
    val numpage: Int,
    val status: String
)
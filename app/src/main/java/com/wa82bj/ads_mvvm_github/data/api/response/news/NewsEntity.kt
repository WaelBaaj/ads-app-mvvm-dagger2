package com.wa82bj.ads_mvvm_github.data.api.response.news


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class NewsEntity(
    val dateCreated: String,
    val description: String,
    @PrimaryKey
    val id: String,
    val image: String,
    val title: String,
    val url: String
)
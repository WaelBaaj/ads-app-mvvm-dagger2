package com.wa82bj.ads_mvvm_github.data.api.response.offer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offer_table")
data class OfferEntity (

    val city: String,
    @PrimaryKey
    val id: String,
    val fav: Boolean,
    val image: String,
    val address: String,
    val price: String,
    val description: String,
    val condition: String,// condition for telephone number
    val title: String,
    val latitude: String,
    val longitude: String,
    //val gallery: List<Gallery>,
    val views: String,
    val subCate: String,
    val subCateId: String,
    val cityId: String,
    val bookmark: Int,
    val category: String,
    val categoryId: String,
    val accountId: String,
    val dateCreated: String
)

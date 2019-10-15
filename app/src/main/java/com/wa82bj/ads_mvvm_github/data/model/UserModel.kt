package com.wa82bj.ads_mvvm_github.data.model

import com.google.gson.annotations.SerializedName

data class UserModel (

    val address: String? = null,
    val email: String? = null,
    @SerializedName("id")
    val userId: String? = null,
    val image: String? = null,
    val individual: String? = null,
    val isVerified: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val sex: String? = null,
    //@SerializedName("skype")
    //val latitude: Any? = null,
    @SerializedName("website")
    val longitude: String? = null,
    val type: String? = null,
    @SerializedName("user_name")
    val userName: String? = null
)

package com.wa82bj.ads_mvvm_github.data.api.response.user


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.wa82bj.ads_mvvm_github.util.Constants.USER_ID

@Entity(tableName = "user_table")
data class UserEntity(

    val address: String,
    val email: String,
    @SerializedName("id")
    val userId: String,
    val image: String,
    val individual: String,
    val isVerified: String,
    val name: String,
    val phone: String,
    val sex: String,
    //@SerializedName("skype")
    //val latitude: Any,
    @SerializedName("website")
    val longitude: String,
    val type: String,
    @SerializedName("user_name")
    val userName: String
){
    @PrimaryKey(autoGenerate = false)
    var user_id: Int = USER_ID
}
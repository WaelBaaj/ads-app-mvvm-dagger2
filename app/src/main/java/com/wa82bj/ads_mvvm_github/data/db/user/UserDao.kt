package com.wa82bj.ads_mvvm_github.data.db.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wa82bj.ads_mvvm_github.data.api.response.user.UserEntity
import com.wa82bj.ads_mvvm_github.util.Constants.USER_ID
import io.reactivex.Flowable

@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(user: UserEntity)

    @Query("select * from user_table where user_id = $USER_ID")
    abstract fun getUser (): Flowable<UserEntity>

}
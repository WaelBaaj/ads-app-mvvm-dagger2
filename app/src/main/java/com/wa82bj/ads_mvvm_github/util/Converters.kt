package com.wa82bj.ads_mvvm_github.util

import androidx.room.TypeConverter
import org.threeten.bp.Instant

object Converters {
    @JvmStatic
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? =
        if (value == null) {
            null
        } else {
            Instant.ofEpochSecond(value)
        }

    @JvmStatic
    @TypeConverter
    fun dateToTimestamp(date: Instant?): Long? =
        date?.epochSecond
}
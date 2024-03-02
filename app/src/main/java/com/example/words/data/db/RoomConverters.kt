package com.example.words.data.db

import androidx.room.TypeConverter
import com.example.words.utils.moshi.MoshiUtils
import com.example.words.utils.moshi.MoshiUtils.moshi
import com.example.words.utils.moshi.listAdapter
import java.util.Date

class RoomConverters {

    @TypeConverter
    fun fromTimestamp(value: Long?) = value?.let { Date(value) }

    @TypeConverter
    fun dateToTimestamp(date: Date?) = date?.time

    @TypeConverter
    fun fromLongList(value: List<Long>): String {
        return moshi.listAdapter<Long>().toJson(value)
    }

    @TypeConverter
    fun toLongList(value: String): List<Long> = moshi.listAdapter<Long>().fromJson(value)
        ?: emptyList()

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return moshi.listAdapter<String>().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> = moshi.listAdapter<String>().fromJson(value)
        ?: emptyList()


    private inline fun <reified T> listToJson(value: List<T>?) =
        value?.takeIf { it.isNotEmpty() }?.let { MoshiUtils.listToJson(it) }

    private inline fun <reified T> listFromJson(value: String?) =
        value?.takeIf { it.isNotEmpty() }?.let { MoshiUtils.listFromJson<T>(it) } ?: emptyList()


}
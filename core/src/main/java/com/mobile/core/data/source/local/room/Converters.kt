package com.mobile.core.data.source.local.room

import androidx.room.TypeConverter
import com.mobile.core.domain.model.Genre

class Converters {
    @TypeConverter
    fun fromGenreList(value: List<Genre>?): String {
        var result = ""
        value?.map {
            result += "${it.id}~${it.name}*"
        }
        result.slice(0..result.length - 2)
        return result
    }

    @TypeConverter
    fun stringToGenreList(value: String?): List<Genre> {
        val result = arrayListOf<Genre>()
        val pairs = value?.split("*")
        pairs?.forEach {
            if (it == "") return@forEach
            val pair = it.split("~")
            result.add(
                Genre(pair[0].toInt(), pair[1])
            )
        }
        return result
    }
}
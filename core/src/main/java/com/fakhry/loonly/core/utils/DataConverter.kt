package com.fakhry.loonly.core.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type

class DataConverter : Serializable {
    @TypeConverter
    fun fromIntList(ints: List<Int?>?): String? {
        if (ints == null) {
            return null
        }
        val gson = Gson()
        val type : Type = object : TypeToken<List<Int?>?>() {}.type
        return gson.toJson(ints, type)
    }

    @TypeConverter
    fun toIntList(int: String?): List<Int>? {
        if (int == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Int?>?>() {}.type
        return gson.fromJson(int, type)
    }
}
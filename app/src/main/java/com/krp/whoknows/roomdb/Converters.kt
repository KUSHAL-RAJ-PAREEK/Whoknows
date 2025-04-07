package com.krp.whoknows.roomdb

import android.util.Log
import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

/**
 * Created by KUSHAL RAJ PAREEK on 12,March,2025
 */


class Converters {

    @TypeConverter
    fun fromList(value: List<String>?): String {
        return value?.let { Gson().toJson(it) } ?: "[]"
    }
    @TypeConverter
    fun toList(value: String?): List<String> {
        return if (value.isNullOrEmpty()) {
            emptyList()
        } else {
            Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)
        }
    }
}

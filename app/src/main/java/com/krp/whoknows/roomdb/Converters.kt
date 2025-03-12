package com.krp.whoknows.roomdb

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

/**
 * Created by KUSHAL RAJ PAREEK on 12,March,2025
 */


class Converters {
    @TypeConverter
    fun fromList(value: List<String>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toList(value: String): List<String> {
        return Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)
    }
}

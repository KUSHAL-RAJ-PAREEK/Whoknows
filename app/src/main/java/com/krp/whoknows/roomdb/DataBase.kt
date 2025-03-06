package com.krp.whoknows.roomdb
import android.content.Context
import androidx.room.Database
import androidx.room.Room
/**
 * Created by KUSHAL RAJ PAREEK on 03,February,2025
 */

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.krp.whoknows.Utils.Converters
import com.krp.whoknows.roomdb.entity.InterUserDetail
import com.krp.whoknows.roomdb.entity.JWTToken
import com.krp.whoknows.roomdb.entity.UserPhoneNumber

@Database(entities = [JWTToken::class, UserPhoneNumber::class,InterUserDetail::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DataBase : RoomDatabase() {
    abstract fun dao(): Dao
}

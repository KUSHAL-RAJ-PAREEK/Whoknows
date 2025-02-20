package com.krp.whoknows.roomdb
import android.content.Context
import androidx.room.Database
import androidx.room.Room
/**
 * Created by KUSHAL RAJ PAREEK on 03,February,2025
 */

import androidx.room.RoomDatabase
import com.krp.whoknows.roomdb.entity.JWTToken
import com.krp.whoknows.roomdb.entity.UserPhoneNumber

@Database(entities = [JWTToken::class, UserPhoneNumber::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun dao(): Dao
}

package com.krp.whoknows.roomdb
import android.content.Context
import androidx.room.Database
import androidx.room.Room
/**
 * Created by KUSHAL RAJ PAREEK on 03,February,2025
 */

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.krp.whoknows.roomdb.entity.FcmEntity
import com.krp.whoknows.roomdb.entity.GalleryImageEntity
import com.krp.whoknows.roomdb.entity.InterUserDetail
import com.krp.whoknows.roomdb.entity.JWTToken
import com.krp.whoknows.roomdb.entity.MatchFcmEntity
import com.krp.whoknows.roomdb.entity.MatchUserEntity
import com.krp.whoknows.roomdb.entity.ProfileImageEntity
import com.krp.whoknows.roomdb.entity.UserGalleryImageEntity
import com.krp.whoknows.roomdb.entity.UserMatch
import com.krp.whoknows.roomdb.entity.UserPhoneNumber
import com.krp.whoknows.roomdb.entity.UserProfileImage
import com.krp.whoknows.roomdb.entity.UserResponseEntity

@Database(entities = [FcmEntity::class , MatchFcmEntity::class,UserProfileImage::class,UserGalleryImageEntity::class,MatchUserEntity::class,UserMatch::class,UserResponseEntity::class,JWTToken::class, UserPhoneNumber::class,InterUserDetail::class, ProfileImageEntity::class, GalleryImageEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DataBase : RoomDatabase() {
    abstract fun dao(): Dao
}

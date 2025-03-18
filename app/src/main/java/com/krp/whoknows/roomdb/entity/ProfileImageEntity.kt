package com.krp.whoknows.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.krp.whoknows.roomdb.Converters

/**
 * Created by KUSHAL RAJ PAREEK on 14,March,2025
 */

@Entity(tableName = "profile_image")
data class ProfileImageEntity(
    @PrimaryKey val id: Int = 1,
    val imageString: String
)
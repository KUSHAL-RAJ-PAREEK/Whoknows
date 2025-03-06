package com.krp.whoknows.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.krp.whoknows.Utils.Converters

/**
 * Created by KUSHAL RAJ PAREEK on 10,February,2025
 */


@Entity(tableName = "user_detail")
@TypeConverters(Converters::class)
data class InterUserDetail(
    @PrimaryKey val uid: Int = 1,
    val id: String,
    val username : String,
    val gender: String,
    val dob : String,
    val latitude : String,
    val longitude : String,
    val bio : String,
    val interests : List<String>,
    val posts : List<String>,
    val geoRadiusRange: String,
    val preferredGender : String,
    val ageGap : String,
    val pnumber : String
)
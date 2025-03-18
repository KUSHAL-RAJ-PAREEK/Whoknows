package com.krp.whoknows.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.krp.whoknows.roomdb.Converters
import kotlinx.serialization.Serializable

/**
 * Created by KUSHAL RAJ PAREEK on 12,March,2025
 */
@Serializable
@Entity(tableName = "userdetails")
@TypeConverters(Converters::class)
data class UserResponseEntity(
    @PrimaryKey val id: String,
    val imgUrl: String?,
    val ageGap: String?,
    val bio: String?,
    val dob: String?,
    val gender: String?,
    val geoRadiusRange: Int?,
    val latitude: String?,
    val longitude: String?,
    val pnumber: String?,
    val preferredGender: String?,
    val username: String?,
    val interests: List<String>?,
    val posts: List<String>?
)

package com.krp.whoknows.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.krp.whoknows.roomdb.Converters
import kotlinx.serialization.Serializable

/**
 * Created by Kushal Raj Pareek on 22-03-2025 22:21
 */

@Serializable
@Entity(tableName = "matchuser")
@TypeConverters(Converters::class)
data class MatchUserEntity(
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

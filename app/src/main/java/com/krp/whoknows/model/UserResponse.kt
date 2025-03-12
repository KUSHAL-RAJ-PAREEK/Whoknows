package com.krp.whoknows.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class UserResponse(
    val imgUrl: String?= null,
    val ageGap: String?= null,
    val bio: String?= null,
    val dob: String?= null,
    val gender: String?= null,
    val geoRadiusRange: Int?= null,
    @PrimaryKey val id: String,
    val interests: List<String>?= null,
    val latitude: String?= null,
    val longitude: String?= null,
    val pnumber: String?= null,
    val posts: List<String>?= null,
    val preferredGender: String?= null,
    val username: String?= null
)
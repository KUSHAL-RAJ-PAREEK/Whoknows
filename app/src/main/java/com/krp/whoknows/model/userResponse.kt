package com.krp.whoknows.model

import kotlinx.serialization.Serializable

@Serializable
data class userResponse(
    val ageGap: String?,
    val bio: String?,
    val dob: String?,
    val gender: String?,
    val geoRadiusRange: Int?,
    val id: String?,
    val interests: List<String>?,
    val latitude: String?,
    val longitude: String?,
    val pnumber: String?,
    val posts: List<String>?,
    val preferredGender: String?,
    val username: String?
)
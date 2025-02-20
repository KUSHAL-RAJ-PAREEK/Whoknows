package com.krp.whoknows.model

import com.krp.whoknows.Utils.LocalDateSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class User(
    val geoRadiusRange: String,
    val latitude: String,
    val longitude: String,
    val pNumber: String,
    val preferredAgeRange: String,
    val preferredGender: String,
    val userGender: String,
    @Serializable(with = LocalDateSerializer::class)
    val userDob: LocalDate
)
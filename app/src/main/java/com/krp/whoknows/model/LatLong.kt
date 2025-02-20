package com.krp.whoknows.model

import kotlinx.serialization.Serializable

/**
 * Created by KUSHAL RAJ PAREEK on 14,February,2025
 */

@Serializable
data class LatLongs(
    val latitude: String,
    val longitude: String
)
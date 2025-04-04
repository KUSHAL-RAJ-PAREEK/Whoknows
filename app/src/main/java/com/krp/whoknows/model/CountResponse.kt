package com.krp.whoknows.model

import kotlinx.serialization.Serializable

/**
 * Created by Kushal Raj Pareek on 04-04-2025 15:06
 */

@Serializable
data class CountResponse(
    val statusCode : Int,
    val count : Int
)
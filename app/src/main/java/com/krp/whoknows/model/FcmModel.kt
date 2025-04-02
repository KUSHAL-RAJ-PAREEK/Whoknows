package com.krp.whoknows.model

import kotlinx.serialization.Serializable

/**
 * Created by Kushal Raj Pareek on 01-04-2025 21:23
 */

@Serializable
data class FcmModel(
    val statusCode : Int?= null,
    val token : String?= null
)
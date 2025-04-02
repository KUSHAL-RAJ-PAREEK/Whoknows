package com.krp.whoknows.model

import kotlinx.serialization.Serializable

/**
 * Created by Kushal Raj Pareek on 01-04-2025 21:51
 */

@Serializable
data class TokenModel(
    val userId : String,
   val fcmToken: String
)
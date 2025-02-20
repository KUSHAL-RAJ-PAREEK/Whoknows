package com.krp.whoknows.model

import kotlinx.serialization.Serializable

/**
 * Created by KUSHAL RAJ PAREEK on 30,January,2025
 */

@Serializable
data class SendOTP(
    val countryCode: String,
    val pNumber: String,
    val otp : String
)
package com.krp.whoknows.model

import kotlinx.serialization.Serializable

@Serializable
data class OtpDetail(
    val countryCode: String ,
    val pNumber: String,

)
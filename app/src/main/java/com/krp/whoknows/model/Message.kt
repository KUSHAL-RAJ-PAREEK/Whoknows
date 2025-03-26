package com.krp.whoknows.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okio.Buffer

/**
* Created by Kushal Raj Pareek on 24-03-2025 00:17
*/

@Serializable
data class Message(
    @SerialName("_id") val _id: String? = null,
    val message: String? = null,
    val imgUrl: String? = null,
    val senderId: String,
    val receiverId: String,
    val timeStamp: String? = null,
    val imgStr : String? = null
)

package com.krp.whoknows.model

import kotlinx.serialization.Serializable

/**
* Created by Kushal Raj Pareek on 24-03-2025 00:17
*/
@Serializable
data class Message(
    val message: String,
    val senderId: String,
    val receiverId: String,
    val timeStamp: String ? = null
)
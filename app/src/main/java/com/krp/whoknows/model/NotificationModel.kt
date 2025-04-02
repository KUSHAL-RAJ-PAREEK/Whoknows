package com.krp.whoknows.model

import kotlinx.serialization.Serializable

/**
 * Created by Kushal Raj Pareek on 02-04-2025 09:43
 */

@Serializable
data class NotificationModel(
    val fcmToken: String,
    val title: String,
    val body: String,
    val type: String,
    val imgUrl : String
)

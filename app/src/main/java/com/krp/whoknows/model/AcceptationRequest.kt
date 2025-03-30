package com.krp.whoknows.model

import kotlinx.serialization.Serializable

/**
 * Created by Kushal Raj Pareek on 28-03-2025 19:11
 */

@Serializable
data class AcceptationRequest(
    val count: Int,
    val userId: String
)
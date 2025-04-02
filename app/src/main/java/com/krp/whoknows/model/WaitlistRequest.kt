package com.krp.whoknows.model

import kotlinx.serialization.Serializable

/**
 * Created by Kushal Raj Pareek on 02-04-2025 11:18
 */

@Serializable
data class WaitlistRequest(val userId: String)
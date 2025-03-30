package com.krp.whoknows.model

import kotlinx.serialization.Serializable

/**
 * Created by Kushal Raj Pareek on 28-03-2025 15:28
 */

@Serializable
data class AcceptationModel(val status: Int, val count: Int)
package com.krp.whoknows.model

import com.krp.whoknows.roomdb.entity.MatchUserEntity
import com.krp.whoknows.roomdb.entity.UserResponseEntity
import kotlinx.serialization.Serializable

/**
 * Created by Kushal Raj Pareek on 22-03-2025 21:40
 */

@Serializable
data class checkMatchModel(
    val statusCode : Int,
    val user : MatchUserEntity
)
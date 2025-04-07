package com.krp.whoknows.Appui.MatchingScreen.presentation

import com.krp.whoknows.roomdb.entity.MatchUserEntity
import com.krp.whoknows.roomdb.entity.UserResponseEntity

/**
 * Created by Kushal Raj Pareek on 21-03-2025 23:44
 */

data class CheckMatchState(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var errorMessage: String? = null,
    var statusCode: Int? = null,
    var user: MatchUserEntity? = null
)
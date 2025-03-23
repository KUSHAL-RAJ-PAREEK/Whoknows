package com.krp.whoknows.Appui.MatchingScreen.presentation

import com.krp.whoknows.roomdb.entity.UserResponseEntity

/**
 * Created by Kushal Raj Pareek on 23-03-2025 22:03
 */

sealed class CancelMatchEvent {
    data class CancelMatch(val user: UserResponseEntity, val jwt: String) : CancelMatchEvent()
}
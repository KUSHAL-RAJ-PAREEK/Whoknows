package com.krp.whoknows.Appui.MatchingScreen.presentation

import com.krp.whoknows.Appui.Profile.presentation.UpdateUserEvent
import com.krp.whoknows.roomdb.entity.UserResponseEntity

/**
 * Created by Kushal Raj Pareek on 21-03-2025 23:23
 */

sealed class CreateMatchEvent {
    data class CreateMatch(val user: UserResponseEntity, val jwt: String) : CreateMatchEvent()
}
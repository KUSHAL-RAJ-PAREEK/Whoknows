package com.krp.whoknows.Appui.MatchingScreen.presentation


/**
 * Created by Kushal Raj Pareek on 21-03-2025 23:43
 */

sealed class CheckMatchEvent {
    data class CheckMatch(val id: String, val jwt: String) : CheckMatchEvent()
}
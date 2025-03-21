package com.krp.whoknows.Appui.Profile.presentation

/**
 * Created by Kushal Raj Pareek on 20-03-2025 16:08
 */

sealed class UpdateMatchEvent{
    data class UpdateMatch(val id : String,val jwt :String): UpdateMatchEvent()
}
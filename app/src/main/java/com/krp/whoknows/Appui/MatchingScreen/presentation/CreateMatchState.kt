package com.krp.whoknows.Appui.MatchingScreen.presentation

/**
 * Created by Kushal Raj Pareek on 21-03-2025 23:25
 */

data class CreateMatchState(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val statusCode : Int?= null,
)
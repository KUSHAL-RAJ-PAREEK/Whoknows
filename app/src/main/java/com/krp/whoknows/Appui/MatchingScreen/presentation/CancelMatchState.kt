package com.krp.whoknows.Appui.MatchingScreen.presentation

/**
 * Created by Kushal Raj Pareek on 23-03-2025 22:02
 */
data class CancelMatchState(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var errorMessage: String? = null,
    var statusCode: Int? = null,
)
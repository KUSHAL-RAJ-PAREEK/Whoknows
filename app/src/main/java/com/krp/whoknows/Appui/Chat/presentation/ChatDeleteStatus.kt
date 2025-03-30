package com.krp.whoknows.Appui.Chat.presentation

/**
 * Created by Kushal Raj Pareek on 29-03-2025 15:04
 */

data class ChatDeleteStatus(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val statusCode : Int? = null,
    val errorMessage: String? = null,
)

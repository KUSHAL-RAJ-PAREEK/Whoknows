package com.krp.whoknows.Appui.Chat.presentation

import com.krp.whoknows.model.UserResponse

/**
* Created by Kushal Raj Pareek on 24-03-2025 13:45
*/

data class SendMessageState (
    var isLoading : Boolean = false,
    var isSuccess: Boolean = false,
    var statusCode : Int? = null,
    val errorMessage: String? = null,
    )
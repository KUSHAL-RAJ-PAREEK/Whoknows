package com.krp.whoknows.Appui.Chat.presentation

import com.krp.whoknows.model.Message
import com.krp.whoknows.model.UserResponse

/**
 * Created by Kushal Raj Pareek on 24-03-2025 00:51
 */

data class ChatState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    var messageList: MutableList<Message> = mutableListOf(),
    val successMessage: UserResponse? = null,
    val errorMessage: String? = null,
)

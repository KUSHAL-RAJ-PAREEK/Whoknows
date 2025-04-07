package com.krp.whoknows.Appui.GreetingScreen.Presentation

import com.krp.whoknows.model.UserResponse

/**
 * Created by KUSHAL RAJ PAREEK on 11,March,2025
 */

data class GetUserState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: UserResponse? = null,
)
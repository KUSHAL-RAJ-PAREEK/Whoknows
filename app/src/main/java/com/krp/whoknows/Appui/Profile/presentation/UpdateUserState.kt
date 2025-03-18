package com.krp.whoknows.Appui.Profile.presentation

import com.krp.whoknows.model.UserResponse

/**
 * Created by KUSHAL RAJ PAREEK on 16,March,2025
 */

data class UpdateUserState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val statusCode : Int?= null,
)
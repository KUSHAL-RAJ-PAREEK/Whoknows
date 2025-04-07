package com.krp.whoknows.Appui.userInfo

import com.krp.whoknows.model.UserResponse

/**
 * Created by KUSHAL RAJ PAREEK on 14,February,2025
 */

data class CreateUserState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: UserResponse? = null,
)
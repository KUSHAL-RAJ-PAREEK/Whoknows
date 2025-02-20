package com.krp.whoknows.Appui.userInfo

import com.krp.whoknows.model.User
import com.krp.whoknows.model.userResponse

/**
 * Created by KUSHAL RAJ PAREEK on 14,February,2025
 */

data class CreateUserState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: userResponse?= null,
)
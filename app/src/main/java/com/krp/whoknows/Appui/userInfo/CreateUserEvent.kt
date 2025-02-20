package com.krp.whoknows.Appui.userInfo

import com.krp.whoknows.model.User

/**
 * Created by KUSHAL RAJ PAREEK on 14,February,2025
 */

sealed class CreateUserEvent {
    data class CreateUser(val user: User, val jwt: String) : CreateUserEvent()
}
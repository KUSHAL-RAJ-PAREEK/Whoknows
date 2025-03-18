package com.krp.whoknows.Appui.Profile.presentation

import com.krp.whoknows.Appui.userInfo.CreateUserEvent
import com.krp.whoknows.model.User
import com.krp.whoknows.roomdb.entity.UserResponseEntity

/**
 * Created by KUSHAL RAJ PAREEK on 16,March,2025
 */
sealed class UpdateUserEvent {
    data class UpdateUser(val user: UserResponseEntity, val jwt: String) : UpdateUserEvent()
}
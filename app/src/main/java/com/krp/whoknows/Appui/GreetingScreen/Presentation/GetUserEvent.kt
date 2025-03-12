package com.krp.whoknows.Appui.GreetingScreen.Presentation

import com.krp.whoknows.Appui.userInfo.CreateUserEvent
import com.krp.whoknows.model.User

/**
 * Created by KUSHAL RAJ PAREEK on 11,March,2025
 */

sealed class GetUserEvent {
    data class GetUser(val pnumber: String, val jwt: String) : GetUserEvent()
}
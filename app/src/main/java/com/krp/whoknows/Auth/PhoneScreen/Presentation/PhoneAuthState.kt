package com.krp.whoknows.Auth.PhoneScreen.Presentation

/**
 * Created by KUSHAL RAJ PAREEK on 31,January,2025
 */

data class PhoneAuthState(
    val isLoading: Boolean = false,
    val isOtpSent: Boolean = false,
    val errorMessage: String? = null,
    val phoneNumber: String? = null
)
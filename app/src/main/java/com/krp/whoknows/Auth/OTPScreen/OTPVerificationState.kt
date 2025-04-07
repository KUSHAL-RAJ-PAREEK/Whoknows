package com.krp.whoknows.Auth.OTPScreen

import androidx.annotation.BoolRes

/**
 * Created by KUSHAL RAJ PAREEK on 01,February,2025
 */
data class OTPVerificationState(
    val isLoading: Boolean = false,
    val statusCode: Int? = null,
    val isOtpVerified: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    var isResend: Boolean? = false
)
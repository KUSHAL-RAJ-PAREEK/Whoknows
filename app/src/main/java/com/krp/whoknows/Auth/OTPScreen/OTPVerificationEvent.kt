package com.krp.whoknows.Auth.OTPScreen

import com.krp.whoknows.model.OtpDetail
import com.krp.whoknows.model.SendOTP

/**
 * Created by KUSHAL RAJ PAREEK on 01,February,2025
 */
sealed  class OTPVerificationEvent {
    data class VerifyOtp(val otpDetails: SendOTP) : OTPVerificationEvent()
    data class ResendOtp(val otpSend: OtpDetail) : OTPVerificationEvent()
}
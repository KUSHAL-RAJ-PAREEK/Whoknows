package com.krp.whoknows.Auth.PhoneScreen.Presentation

import com.krp.whoknows.model.OtpDetail

/**
 * Created by KUSHAL RAJ PAREEK on 31,January,2025
 */
sealed class PhoneAuthEvent {
    data class SendOtp(val otpDetail: OtpDetail) : PhoneAuthEvent()
}


package com.krp.whoknows.Auth.OTPScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krp.whoknows.Auth.PhoneScreen.Presentation.PhoneAuthState
import com.krp.whoknows.ktorclient.KtorClient
import com.krp.whoknows.model.OtpDetail
import com.krp.whoknows.model.SendOTP
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by KUSHAL RAJ PAREEK on 01,February,2025
 */

class OTPVerificationViewModel : ViewModel(), KoinComponent {

    private val ktorClient: KtorClient by inject()

    private val _state = MutableStateFlow(OTPVerificationState())
    val state: StateFlow<OTPVerificationState> = _state

    fun onEvent(event: OTPVerificationEvent) {
        when (event) {
            is OTPVerificationEvent.VerifyOtp -> verifyOtp(event.otpDetails.otp, event.otpDetails.pNumber, event.otpDetails.countryCode)
           is OTPVerificationEvent.ResendOtp -> resendOtp(otpDetail = OtpDetail(
                event.otpSend.countryCode,
               event.otpSend.pNumber
            ))
        }
    }

    private fun verifyOtp(otp: String, phoneNumber: String, countryCode: String) {

        viewModelScope.launch {
            _state.value = OTPVerificationState(isLoading = true)
            try {
                val otpToVerify = SendOTP(countryCode, phoneNumber, otp)
                val response = ktorClient.verifyOtp(otpToVerify)
                _state.value = OTPVerificationState(isOtpVerified = true, successMessage = response)
            } catch (e: Exception) {
                _state.value = OTPVerificationState(errorMessage = e.localizedMessage ?: "An error occurred")
            }
        }
    }

    private fun resendOtp(otpDetail: OtpDetail) {
        viewModelScope.launch {
            try {
                ktorClient.sendOtp(OtpDetail(otpDetail.countryCode, otpDetail.pNumber))
                _state.value = OTPVerificationState(isResend = true)
            } catch (e: Exception) {
                _state.value =
                    OTPVerificationState(errorMessage = e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
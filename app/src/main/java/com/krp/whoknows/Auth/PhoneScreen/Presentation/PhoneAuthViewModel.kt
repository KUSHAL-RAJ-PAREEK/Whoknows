package com.krp.whoknows.Auth.PhoneScreen.Presentation

/**
 * Created by KUSHAL RAJ PAREEK on 31,January,2025
 */

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krp.whoknows.ktorclient.KtorClient
import com.krp.whoknows.model.OtpDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PhoneAuthViewModel : ViewModel(), KoinComponent {

    private val ktorClient: KtorClient by inject()

    private val _state = MutableStateFlow(PhoneAuthState())
    val state: StateFlow<PhoneAuthState> = _state

    fun onEvent(event: PhoneAuthEvent) {
        when (event) {
            is PhoneAuthEvent.SendOtp -> sendOtp(event.otpDetail)
        }
    }

    private fun sendOtp(otpDetail: OtpDetail) {
        viewModelScope.launch {
            _state.value = PhoneAuthState(isLoading = true)
            try {
                ktorClient.sendOtp(OtpDetail(otpDetail.countryCode,otpDetail.pNumber))
                _state.value = PhoneAuthState(isOtpSent = true, phoneNumber =otpDetail.pNumber)
            } catch (e: Exception) {
                _state.value = PhoneAuthState(errorMessage = e.localizedMessage ?: "An error occurred")
            }
        }
    }
}

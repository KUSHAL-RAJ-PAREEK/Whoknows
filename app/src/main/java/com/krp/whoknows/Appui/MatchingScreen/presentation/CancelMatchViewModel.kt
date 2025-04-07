package com.krp.whoknows.Appui.MatchingScreen.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krp.whoknows.ktorclient.KtorClient
import com.krp.whoknows.roomdb.entity.UserResponseEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Kushal Raj Pareek on 23-03-2025 22:04
 */

class CancelMatchViewModel : ViewModel(), KoinComponent {
    private val ktorClient: KtorClient by inject()

    private val _state = MutableStateFlow(CancelMatchState())
    val state: StateFlow<CancelMatchState> = _state

    fun onEvent(event: CancelMatchEvent) {
        when (event) {
            is CancelMatchEvent.CancelMatch -> cancelMatch(event.user, event.jwt)
        }
    }

    private fun cancelMatch(user: UserResponseEntity, jwt: String) {
        viewModelScope.launch {
            _state.value = CancelMatchState(isLoading = true)
            try {
                val response = ktorClient.cancelMatch(user, jwt)
                _state.value = CancelMatchState(isSuccess = true, statusCode = response)
            } catch (e: Exception) {
                _state.value =
                    CancelMatchState(errorMessage = e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
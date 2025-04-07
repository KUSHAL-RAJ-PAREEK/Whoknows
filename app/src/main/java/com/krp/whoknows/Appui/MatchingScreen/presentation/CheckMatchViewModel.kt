package com.krp.whoknows.Appui.MatchingScreen.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krp.whoknows.Appui.Profile.presentation.UpdateMatchEvent
import com.krp.whoknows.Appui.Profile.presentation.UpdateMatchState
import com.krp.whoknows.ktorclient.KtorClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Kushal Raj Pareek on 21-03-2025 23:44
 */

class CheckMatchViewModel : ViewModel(), KoinComponent {

    private val ktorClient: KtorClient by inject()

    private val _state = MutableStateFlow(CheckMatchState())
    val state: MutableStateFlow<CheckMatchState> = _state


    fun onEvent(event: CheckMatchEvent) {
        when (event) {
            is CheckMatchEvent.CheckMatch -> checkMatch(event.id, event.jwt)
        }
    }

    private fun checkMatch(id: String, jwt: String) {
        viewModelScope.launch {
            _state.value = CheckMatchState(isLoading = true)
            try {
                val response = ktorClient.checkMatch(id, jwt)
                _state.value = CheckMatchState(
                    isSuccess = true,
                    statusCode = response.statusCode,
                    isLoading = false,
                    user = response.user
                )
            } catch (e: Exception) {
                _state.value =
                    CheckMatchState(errorMessage = e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
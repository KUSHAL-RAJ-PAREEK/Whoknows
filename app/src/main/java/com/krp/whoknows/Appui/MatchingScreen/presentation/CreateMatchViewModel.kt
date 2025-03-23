package com.krp.whoknows.Appui.MatchingScreen.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krp.whoknows.Appui.Profile.presentation.UpdateUserEvent
import com.krp.whoknows.Appui.Profile.presentation.UpdateUserState
import com.krp.whoknows.ktorclient.KtorClient
import com.krp.whoknows.roomdb.entity.UserResponseEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Kushal Raj Pareek on 21-03-2025 23:33
 */

class CreateMatchViewModel : ViewModel(), KoinComponent {
    private val ktorClient: KtorClient by inject()

    private val _state = MutableStateFlow(CreateMatchState())
    val state: StateFlow<CreateMatchState> = _state

    fun onEvent(event: CreateMatchEvent) {
        when (event) {
            is CreateMatchEvent.CreateMatch -> createMatch(event.user, event.jwt)
        }
    }

    private fun createMatch(user: UserResponseEntity, jwt: String) {
        viewModelScope.launch {
            _state.value = CreateMatchState(isLoading = true)
            try {
                val response = ktorClient.createMatch(user, jwt)
                _state.value = CreateMatchState(isSuccess = true, statusCode = response)
                Log.d("GKJADNKJGNDJGNDKJ", state.value.toString())
            } catch (e: Exception) {
                _state.value = CreateMatchState(errorMessage = e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
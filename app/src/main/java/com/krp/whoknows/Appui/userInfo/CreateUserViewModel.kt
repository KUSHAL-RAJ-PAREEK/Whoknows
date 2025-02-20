package com.krp.whoknows.Appui.userInfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krp.whoknows.ktorclient.KtorClient
import com.krp.whoknows.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by KUSHAL RAJ PAREEK on 14,February,2025
 */

class CreateUserViewModel : ViewModel(), KoinComponent {

    private val ktorClient: KtorClient by inject()

    private val _state = MutableStateFlow(CreateUserState())
    val state: StateFlow<CreateUserState> = _state

    fun onEvent(event: CreateUserEvent) {
        when (event) {
            is CreateUserEvent.CreateUser -> createUser(event.user, event.jwt)
        }
    }

    private fun createUser(user: User, jwt: String) {
        viewModelScope.launch {
            _state.value = CreateUserState(isLoading = true)
            try {
                val response = ktorClient.createUser(user, jwt)
                    _state.value = CreateUserState(isSuccess = true, successMessage = response)
                Log.d("GKJADNKJGNDJGNDKJ", state.value.successMessage.toString())
            } catch (e: Exception) {
                _state.value = CreateUserState(errorMessage = e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
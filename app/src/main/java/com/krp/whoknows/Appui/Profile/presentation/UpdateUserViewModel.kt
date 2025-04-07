package com.krp.whoknows.Appui.Profile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krp.whoknows.Appui.userInfo.CreateUserEvent
import com.krp.whoknows.Appui.userInfo.CreateUserState
import com.krp.whoknows.ktorclient.KtorClient
import com.krp.whoknows.model.User
import com.krp.whoknows.roomdb.entity.UserResponseEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

/**
 * Created by KUSHAL RAJ PAREEK on 16,March,2025
 */


class UpdateUserViewModel : ViewModel(), KoinComponent {

    private val ktorClient: KtorClient by inject()

    private val _state = MutableStateFlow(UpdateUserState())
    val state: StateFlow<UpdateUserState> = _state

    fun onEvent(event: UpdateUserEvent) {
        when (event) {
            is UpdateUserEvent.UpdateUser -> updateUser(event.user, event.jwt)
        }
    }

    private fun updateUser(user: UserResponseEntity, jwt: String) {
        viewModelScope.launch {
            _state.value = UpdateUserState(isLoading = true)
            try {
                val response = ktorClient.updateUser(user, jwt)
                _state.value = UpdateUserState(isSuccess = true, statusCode = response)
            } catch (e: Exception) {
                _state.value =
                    UpdateUserState(errorMessage = e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
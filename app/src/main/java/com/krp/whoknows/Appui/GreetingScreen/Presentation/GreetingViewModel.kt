package com.krp.whoknows.Appui.GreetingScreen.Presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krp.whoknows.ktorclient.KtorClient
import com.krp.whoknows.model.UserResponse
import com.krp.whoknows.roomdb.UserRepository
import com.krp.whoknows.roomdb.entity.UserResponseEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Created by KUSHAL RAJ PAREEK on 11,March,2025
 */
class GreetingViewModel(
    private val userRepository: UserRepository,
    private val ktorClient: KtorClient
) : ViewModel() {


    private val _state = MutableStateFlow(GetUserState())
    val state: StateFlow<GetUserState> = _state

    private val _jwtToken = MutableStateFlow<String?>(null)
    val jwtToken: StateFlow<String?> get() = _jwtToken



    init {
        loadJwtToken()
    }

    fun saveUser(user: UserResponseEntity?) {
        viewModelScope.launch {
            userRepository.saveUser(user!!)
        }
    }

    suspend fun getUser(): UserResponseEntity? {
        return userRepository.getUser()
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            userRepository.deleteUser(userId)
        }
    }


    private fun loadJwtToken() {
        viewModelScope.launch {
            _jwtToken.value = userRepository.getToken()
        }
    }

    fun onEvent(event: GetUserEvent) {
        when (event) {
            is GetUserEvent.GetUser -> {
                viewModelScope.launch {
                    val token = jwtToken.value
                    if (token != null) {
                        getUser(event.pnumber, token)
                    } else {
                        _state.value = GetUserState(errorMessage = "JWT Token not found")
                    }
                }
            }
        }
    }

    private fun getUser(pnumber: String, jwt: String) {
        viewModelScope.launch {
            _state.value = GetUserState(isLoading = true)
            try {
                Log.d("iamingetUser","$pnumber  $jwt")
                val response = ktorClient.getUser(pnumber, jwt)
                _state.value = GetUserState(isSuccess = true, successMessage = response)
                Log.d("GreetingViewModel", "User response: ${state.value.successMessage}")
            } catch (e: Exception) {
                _state.value = GetUserState(errorMessage = e.localizedMessage ?: "An error occurred")
            }
        }
    }
}



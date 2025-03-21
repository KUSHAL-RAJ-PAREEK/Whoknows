package com.krp.whoknows.Appui.Profile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.krp.whoknows.ktorclient.KtorClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Kushal Raj Pareek on 20-03-2025 16:15
 */
class UpdateMatchViewModel : ViewModel(), KoinComponent {

    private val ktorClient: KtorClient by inject()

    private val _state = MutableStateFlow(UpdateMatchState())
    val state: MutableStateFlow<UpdateMatchState> = _state

    private val _called = MutableStateFlow(false)
    val called = _called

    fun updateCalled(flag : Boolean){
        called.value = flag
    }
    fun onEvent(event: UpdateMatchEvent) {
        when (event) {
            is UpdateMatchEvent.UpdateMatch -> updateMatch(event.id, event.jwt)
        }
    }

    private fun updateMatch(id: String, jwt: String) {
        Log.d("afsdsffs","$id $jwt")

        viewModelScope.launch {
            _state.value = UpdateMatchState(isLoading = true)
            try {
                val response = ktorClient.updateMatch(id, jwt)
                state.value =
                    UpdateMatchState(isSuccess = true, statusCode = response, isLoading = false)
            } catch (e: Exception) {
                _state.value =
                    UpdateMatchState(errorMessage = e.localizedMessage ?: "An error occurred")
            }
        }
    }

}
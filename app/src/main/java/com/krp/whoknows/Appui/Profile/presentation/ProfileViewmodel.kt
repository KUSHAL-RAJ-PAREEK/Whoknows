package com.krp.whoknows.Appui.Profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krp.whoknows.roomdb.UserRepository
import com.krp.whoknows.roomdb.entity.UserResponseEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn

/**
 * Created by KUSHAL RAJ PAREEK on 12,March,2025
 */

class ProfileViewModel(userRepository: UserRepository) : ViewModel() {
    val user: StateFlow<UserResponseEntity?> = userRepository.getUser().distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}
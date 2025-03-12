package com.krp.whoknows.roomdb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.krp.whoknows.roomdb.entity.InterUserDetail
import com.krp.whoknows.roomdb.entity.JWTToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init

/**
 * Created by KUSHAL RAJ PAREEK on 03,February,2025
 */

class JWTViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = DataStoreManager(application)
    private val userRepository: UserRepository
    private val _jwtToken = MutableStateFlow<String?>(null)
    val jwtToken: StateFlow<String?> get() = _jwtToken

    private val _phoneNumber = MutableStateFlow<String?>(null)
    val phoneNumber: StateFlow<String?> get() = _phoneNumber

    private val _userDetails = MutableStateFlow<InterUserDetail?>(null)
    val userDetail: StateFlow<InterUserDetail?> get() = _userDetails

    init {
        val database = Room.databaseBuilder(
            application,
            DataBase::class.java,
            "userDB"
        ).build()
        val dao = database.dao()
        userRepository = UserRepository(dao)
        loadToken()
        loadPhoneNumber()
        loadUserDetails()
    }

    private fun loadToken() {
        viewModelScope.launch {
            _jwtToken.value = dataStore.jwtToken.first()
        }
    }

    private fun loadPhoneNumber() {
        viewModelScope.launch {
            _phoneNumber.value = dataStore.phoneNumber.first()
        }
    }

    private fun loadUserDetails() {
        viewModelScope.launch {
            dataStore.userDetails.collect { user ->
                _userDetails.value = user
            }
        }
    }


    fun saveToken(token: String) {
        viewModelScope.launch {
            dataStore.saveToken(token)
            userRepository.saveToken(token)
            _jwtToken.value = token
        }
    }

    fun deleteToken() {
        viewModelScope.launch {
            dataStore.deleteToken()
            _jwtToken.value = null
        }
    }

    suspend fun savePhoneNumber(number: String) {

        dataStore.savePhoneNumber(number)
        _phoneNumber.value = number

    }

    fun deletePhoneNumber() {
        viewModelScope.launch {
            dataStore.deletePhoneNumber()
            _phoneNumber.value = null
        }
    }

    fun saveUser(user: InterUserDetail) {
        viewModelScope.launch {
            dataStore.saveUser(user)
            _userDetails.value = user
        }
    }


    fun updateUser(user: InterUserDetail) {
        viewModelScope.launch {
            dataStore.updateUser(user)
            _userDetails.value = user
        }
    }


    fun deleteUser() {
        viewModelScope.launch {
            dataStore.deleteUser()
            _userDetails.value = null
        }
    }

}
package com.krp.whoknows.Appui.GreetingScreen.Presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krp.whoknows.ktorclient.KtorClient
import com.krp.whoknows.model.FcmModel
import com.krp.whoknows.model.UserResponse
import com.krp.whoknows.roomdb.UserRepository
import com.krp.whoknows.roomdb.entity.FcmEntity
import com.krp.whoknows.roomdb.entity.MatchFcmEntity
import com.krp.whoknows.roomdb.entity.MatchUserEntity
import com.krp.whoknows.roomdb.entity.UserResponseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _userState = MutableStateFlow<UserResponseEntity?>(null)
    val userState: StateFlow<UserResponseEntity?> = _userState.asStateFlow()


    private val _pNumber = MutableStateFlow<String?>(null)
    val pNumber: StateFlow<String?> get() = _pNumber

    private val _fcmToken = MutableStateFlow<String?>(null)
    val fcmToken: StateFlow<String?> get() = _fcmToken

    private val _matchFcmToken = MutableStateFlow<String?>(null)
    val matchFcmToken: StateFlow<String?> get() = _matchFcmToken


    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> get() = _userId

    init {
        loadJwtToken()
        loadPNumber()
        viewModelScope.launch {
            userRepository.getUser().collectLatest { user ->
                _userState.value = user
            }

        }
        viewModelScope.launch {
            userRepository.getFcm().collect { entity ->
                _fcmToken.value = entity?.fcm_token
            }
        }

        viewModelScope.launch {
            userRepository.getMatchFcm().collect { entity ->
                _matchFcmToken.value = entity?.fcm_token
            }
        }

//        viewModelScope.launch {
//            userRepository.getMatchUser().collectLatest { user ->
//                _matchUserState.value = user
//            }
//        }

    }

    fun saveUser(user: UserResponseEntity?) {
        viewModelScope.launch {
            userRepository.saveUser(user!!)
        }
    }

//    suspend fun getUser(): UserResponseEntity? {
//        return userRepository.getUser()
//    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            userRepository.deleteUser(userId)
        }
    }

    fun deleteUsers() {
        viewModelScope.launch {
            userRepository.deleteUsers()
        }
    }

    fun deleteMatchUser() {
        viewModelScope.launch {
            userRepository.deleteMatchUser()
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            userRepository.saveToken(token)
        }
    }

    fun savePnumber(pnumber: String) {
        viewModelScope.launch {
            userRepository.savePhone(pnumber)
        }
    }

    fun loadJwtToken() {
        viewModelScope.launch {
            userRepository.getToken()
                .collect { token ->
                    _jwtToken.value = token?.token
                }
        }
    }

    fun loadPNumber() {
        viewModelScope.launch {
            userRepository.getPnumber()
                .collect { phone ->
                    _pNumber.value = phone?.userPhoneNumber
                }
        }
    }

    fun saveMatchFcm(matchFcm: MatchFcmEntity) {
        viewModelScope.launch {
            userRepository.saveMatchFcm(matchFcm)
        }
    }

    fun getMatchFcm(): Flow<MatchFcmEntity?> {
        return userRepository.getMatchFcm()
    }

    fun deleteMatchFcm() {
        viewModelScope.launch {
            userRepository.deleteMatchFcm()
        }
    }

    fun saveFcm(fcm: FcmEntity) {
        viewModelScope.launch {
            userRepository.saveFcm(fcm)
        }
    }

    fun getFcm(): Flow<FcmEntity?> {
        return userRepository.getFcm()
    }

    fun deleteFcm() {
        viewModelScope.launch {
            userRepository.deleteFcm()
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
                Log.d("iamingetUser", "$pnumber  $jwt")
                val response = ktorClient.getUser(pnumber, jwt)
                _state.value =
                    GetUserState(isSuccess = true, successMessage = response, isLoading = false)
                Log.d("GreetingViewModel", "User response: ${state.value.successMessage}")
            } catch (e: Exception) {
                _state.value =
                    GetUserState(errorMessage = e.localizedMessage ?: "An error occurred")
            }
        }
    }


    suspend fun uploadToken(id: String, token: String): Int {
        return try {
            Log.d("iamheretoupload", "$id $token")
            val response = ktorClient.uploadToken(id = id, token = token)
            response
        } catch (e: Exception) {
            Log.d("GreetingViewModeltoken", e.message.toString())
            -1
        }
    }

    suspend fun getToken(id: String): FcmModel {
        return try {
            Log.d("iamheretoupload", "$id")
            val response = ktorClient.getToken(id = id)
            response
        } catch (e: Exception) {
            Log.d("GreetingViewModeltoken", e.message.toString())
            FcmModel(-1, "")
        }
    }

    suspend fun getId(phone: String, jwt: String): String {
        return try {
            Log.d("asdasdasdasdasds", "$phone")
            val response = ktorClient.getUser(pnumber = phone, jwt = jwt)

            _userId.value = response!!.id.toString()
            Log.d("asdasdasdasdasds", "${_userId.value}")
            return response!!.id.toString()
        } catch (e: Exception) {
            Log.d("GreetingViewModeltoken", e.message.toString())
            _userId.value = ""
            return ""
        }


    }


}



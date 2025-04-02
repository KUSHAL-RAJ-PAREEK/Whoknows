package com.krp.whoknows.Appui.MatchingScreen.presentation

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.xr.compose.testing.toDp
import com.krp.whoknows.Appui.Profile.presentation.UpdateMatchState
import com.krp.whoknows.ktorclient.KtorClient
import com.krp.whoknows.roomdb.UserRepository
import com.krp.whoknows.roomdb.entity.MatchUserEntity
import com.krp.whoknows.roomdb.entity.UserResponseEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDate
import kotlin.getValue

/**
 * Created by Kushal Raj Pareek on 22-03-2025 23:04
 */

class MatchUserViewModel(private val userRepository: UserRepository,):ViewModel(),KoinComponent{

    private val ktorClient: KtorClient by inject()

    private val _matchUserState = MutableStateFlow<MatchUserEntity?>(null)
    val matchUserState: StateFlow<MatchUserEntity?> = _matchUserState.asStateFlow()


    private val _dob = MutableStateFlow(LocalDate.now())
    val dob: StateFlow<LocalDate> = _dob

    private val _fcmToken= MutableStateFlow("")
    val fcmToken: StateFlow<String> = _fcmToken

    private val _vis = MutableStateFlow<Boolean?>(false)
    val vis: StateFlow<Boolean?> = _vis

    private val _dobs = MutableStateFlow("")
    val dobs: StateFlow<String> = _dobs

    private val _isMatch = MutableStateFlow(false)
    val isMatch: StateFlow<Boolean> = _isMatch

    private val _id = MutableStateFlow("")
    val id: StateFlow<String> = _id

    private val _mImage = MutableStateFlow("")
    val mImage: StateFlow<String> = _mImage

    private  val _fImage = MutableStateFlow("")
    val fImage : StateFlow<String> = _fImage

    private  val _sImage = MutableStateFlow("")
    val sImage : StateFlow<String> = _sImage

    private  val _tImage = MutableStateFlow("")
    val tImage : StateFlow<String> = _tImage

    private val _pnumber = MutableStateFlow("")
    val pnumber: StateFlow<String> = _pnumber

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _imgUrl = MutableStateFlow("")
    val imgUrl: StateFlow<String> = _imgUrl

    private val _jwt = MutableStateFlow("")
    val jwt: StateFlow<String> = _jwt

    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> = _gender

    private val _preGender = MutableStateFlow("")
    val preGender: StateFlow<String> = _preGender

    private val _geoRadiusRange = MutableStateFlow("")
    val geoRadiusRange: StateFlow<String> = _geoRadiusRange

    private val _longitude = MutableStateFlow("")
    val longitude: StateFlow<String> = _longitude

    private val _latitude = MutableStateFlow("")
    val latitude: StateFlow<String> = _latitude

    private val _preAgeRange = MutableStateFlow("")
    val preAgeRange: StateFlow<String> = _preAgeRange

    private val _preAgeFRange = MutableStateFlow("")
    val preAgeFRange: StateFlow<String> = _preAgeFRange

    private val _preAgeTRange = MutableStateFlow("")
    val preAgeTRange: StateFlow<String> = _preAgeTRange

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location

    private val _bio = MutableStateFlow("")
    val bio: StateFlow<String> = _bio

    private val _interests = MutableStateFlow<List<String>?>(null)
    val interests: StateFlow<List<String>?> = _interests


    private val _posts =  MutableStateFlow<List<String>?>(null)
    val posts: StateFlow<List<String>?> = _posts

    private  val _acceptStatus = MutableStateFlow(0)
    val acceptStatus  = _acceptStatus

    private  val _clicked = MutableStateFlow(false)
    val clicked  = _clicked



    fun updateInterest(interests : List<String>) {
        _interests.value = interests
    }

    fun updatePosts(posts : List<String>) {
        _posts.value = posts
    }

    fun updateBio(bio: String) {
        _bio.value = bio
    }

    fun updateDobs(dobs: String) {
        _dobs.value = dobs
    }


    fun updatePnumber(pno: String) {
        _pnumber.value = pno
    }

    fun updateDOB(dob: LocalDate) {
        _dob.value = dob
    }

    fun updateUsername(username: String) {
        _username.value = username
    }

    fun updateImgUrl(imgUrl: String) {
        _imgUrl.value = imgUrl
    }


    fun updateGender(gender: String) {
        _gender.value = gender
    }

    fun updateLatitude(lat: String) {
        _latitude.value = lat
    }

    fun updateLongitude(long: String) {
        _longitude.value = long
    }

    fun updateMatch(flag : Boolean) {
        _isMatch.value = flag
    }

    fun updateJwt(jwt : String){
        _jwt.value = jwt
    }
    fun updateLocation(location: String) {
        _location.value = location
    }
    fun updateId(id: String) {
        _id.value = id
    }

    fun updatemImage(img: String) {
        _mImage.value = img
    }
    fun updatefImage(img: String) {
        _fImage.value = img
    }
    fun updatesImage(img: String) {
        _sImage.value = img
    }
    fun updatetImage(img: String) {
        _tImage.value = img
    }

    fun updatePreGender(preGender: String) {
        _preGender.value = preGender
    }
    fun updatePreAgeRange(preAgeRange: String) {
        Log.d("prefreefe",preAgeRange)
        _preAgeRange.value = preAgeRange
    }
    fun updateFPreAgeRange(preAgeFRange: String) {
        _preAgeFRange.value = preAgeFRange
    }
    fun updateTPreAgeRange(preAgeTRange: String) {
        _preAgeTRange.value = preAgeTRange
    }
    fun updateGeoRadiusRange(geoRadiusRange: String) {
        _geoRadiusRange.value = geoRadiusRange
    }

    fun updateAcceptStatus(count : Int){
        _acceptStatus.value = count
    }

    fun updateFcm(fcm: String){
        _fcmToken.value = fcm
    }

    fun updateClicked(flag: Boolean){
        _clicked.value = flag
    }

    fun saveMatchUser(user: MatchUserEntity?) {
        viewModelScope.launch {
            userRepository.saveMatchUser(user!!)
        }
    }

    init{
        viewModelScope.launch{
            _matchUserState.value = getMUser()
        }
    }

    suspend fun getMUser(): MatchUserEntity? {
        return userRepository.getMatchUser().firstOrNull()
    }

    fun deleteUser() {
        viewModelScope.launch {
            userRepository.deleteMatchUser()
        }
    }

    fun getStatus(id : String) {
        Log.d("afsdsffs","$id")
        viewModelScope.launch {
            try {
                val response = ktorClient.getAcceptation(id = id)

                if(response.status == 200){
                    Log.d("hellogotStatus",response.status.toString())
                    updateAcceptStatus(response.count)
                }
            } catch (e: Exception) {
                Log.d("erroringetstatus",e.message.toString())

            }
        }
    }

    fun updateStatus(id : String, count : Int,userId : String) {
        Log.d("afsdsffs","$id")
        viewModelScope.launch {
            try {
                val response = ktorClient.updateAcceptation(id = id,count = count, userId = userId)
                if(response == 200){
                   updateAcceptStatus(acceptStatus.value+1)
                    updateClicked(true)
                    Log.d("gotthething",response.toString())
                }
            } catch (e: Exception) {
                Log.d("erroringetstatus",e.message.toString())

            }
        }
    }


    fun updateVis(flag : Boolean){
        _vis.value = flag
    }
    fun updateClicked(accid: String,id : String) {
        Log.d("afsdsffs","$id")
        viewModelScope.launch {
            try {
                val response = ktorClient.getClickStatus(accid = accid, id = id)
                if(response == 200){
                    updateClicked(true)
                }else{
                    updateClicked(false)
                }
            } catch (e: Exception) {
                Log.d("erroringetstatus",e.message.toString())

            }
        }
    }


    fun clearAll() {
        _dob.value = LocalDate.now()
        _dobs.value = ""
        _isMatch.value = false
        _id.value = ""
        _mImage.value = ""
        _fImage.value = ""
        _sImage.value = ""
        _tImage.value = ""
        _pnumber.value = ""
        _username.value = ""
        _imgUrl.value = ""
        _jwt.value = ""
        _gender.value = ""
        _preGender.value = ""
        _geoRadiusRange.value = ""
        _longitude.value = ""
        _latitude.value = ""
        _preAgeRange.value = ""
        _preAgeFRange.value = ""
        _preAgeTRange.value = ""
        _location.value = ""
        _bio.value = ""
        _interests.value = null
        _posts.value = null
        _acceptStatus.value = 0
        _clicked.value = false
    }



}
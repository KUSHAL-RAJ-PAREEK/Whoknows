package com.krp.whoknows.Appui.Profile.presentation

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krp.whoknows.ktorclient.KtorClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDate
import kotlin.getValue

/**
 * Created by KUSHAL RAJ PAREEK on 13,March,2025
 */


class ProfileDetailViewModel: ViewModel(), KoinComponent {

    private val ktorClient: KtorClient by inject()

    private val _dob = MutableStateFlow(LocalDate.now())
    val dob: StateFlow<LocalDate> = _dob

    private val _dobs = MutableStateFlow("")
    val dobs: StateFlow<String> = _dobs

    private val _fcmToken= MutableStateFlow("")
    val fcmToken: StateFlow<String> = _fcmToken

    private val _isMatch = MutableStateFlow(false)
    val isMatch: StateFlow<Boolean> = _isMatch


    private val _inWait = MutableStateFlow(false)
    val inWait: StateFlow<Boolean> = _inWait

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

    private  val _defaultImg = MutableStateFlow<ImageBitmap?>(null)
    val  defaultImg: StateFlow<ImageBitmap?> = _defaultImg

    private val _interests = MutableStateFlow<List<String>?>(null)
    val interests: StateFlow<List<String>?> = _interests


    private val _posts =  MutableStateFlow<List<String>?>(null)
    val posts: StateFlow<List<String>?> = _posts

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


    fun updateDefaultImg(bitmap : ImageBitmap?){
        _defaultImg.value = bitmap
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

    fun updateFcm(fcm: String){
        _fcmToken.value = fcm
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

    fun updateInWaitInternal(flag : Boolean){
        _inWait.value = flag
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


    suspend fun updateInWait(id : String): Int{

            return try{
                val response = ktorClient.getWait(id)

                if(response == 200){
                    _inWait.value = true
                }else{
                    _inWait.value = false
                }
                response
            }catch (e : Exception){
                Log.d("postit","${e.message}")
                500
            }

    }





}
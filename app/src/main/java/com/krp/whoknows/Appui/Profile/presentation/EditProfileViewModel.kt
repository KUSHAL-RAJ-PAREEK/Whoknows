package com.krp.whoknows.Appui.Profile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

/**
 * Created by KUSHAL RAJ PAREEK on 12,March,2025
 */

class EditProfileViewModel : ViewModel() {

    private val _id = MutableStateFlow("")
    val id: StateFlow<String> = _id

    private val _dob = MutableStateFlow(LocalDate.now())
    val dob: StateFlow<LocalDate> = _dob

    private val _dobs = MutableStateFlow("")
    val dobs: StateFlow<String> = _dobs

    private val _mImage = MutableStateFlow("")
    val mImage: StateFlow<String> = _mImage

    private val _fImage = MutableStateFlow("")
    val fImage: StateFlow<String> = _fImage

    private val _sImage = MutableStateFlow("")
    val sImage: StateFlow<String> = _sImage

    private val _tImage = MutableStateFlow("")
    val tImage: StateFlow<String> = _tImage

    private val _pnumber = MutableStateFlow("")
    val pnumber: StateFlow<String> = _pnumber


    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location

    private val _longitude = MutableStateFlow("")
    val longitude: StateFlow<String> = _longitude

    private val _latitude = MutableStateFlow("")
    val latitude: StateFlow<String> = _latitude


    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> = _gender

    private val _preGender = MutableStateFlow("")
    val preGender: StateFlow<String> = _preGender

    private val _geoRadiusRange = MutableStateFlow("")
    val geoRadiusRange: StateFlow<String> = _geoRadiusRange

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username


    private val _preAgeRange = MutableStateFlow("")
    val preAgeRange: StateFlow<String> = _preAgeRange

    private val _preAgeFRange = MutableStateFlow("")
    val preAgeFRange: StateFlow<String> = _preAgeFRange

    private val _preAgeTRange = MutableStateFlow("")
    val preAgeTRange: StateFlow<String> = _preAgeTRange

    private val _bio = MutableStateFlow("")
    val bio: StateFlow<String> = _bio

    private val _interests = MutableStateFlow<List<String>?>(null)
    val interests: StateFlow<List<String>?> = _interests


    private val _posts = MutableStateFlow<List<String>?>(null)
    val posts: StateFlow<List<String>?> = _posts

    fun updateInterest(interests: List<String>) {
        _interests.value = interests.toList()
    }

    fun updatePosts(posts: List<String>) {
        _posts.value = posts
    }

    fun removeInterest(interestToRemove: String) {
        _interests.value = _interests.value?.filterNot { it == interestToRemove }?.toList()
    }

    fun addInterest(newInterest: String) {
        _interests.value = (_interests.value ?: emptyList()) + newInterest
    }

    fun updateBio(bio: String) {
        _bio.value = bio
    }

    fun updateUsername(username: String) {
        _username.value = username
    }

    fun updateDobs(dobs: String) {
        _dobs.value = dobs
    }

    fun updateDOB(dob: LocalDate) {
        _dob.value = dob
    }

    fun updateGender(gender: String) {
        _gender.value = gender
    }

    fun updatePnumber(pno: String) {
        _pnumber.value = pno
    }

    fun updateLocation(location: String) {
        _location.value = location
    }

    fun updateId(id: String) {
        _id.value = id
    }

    fun updatePreGender(preGender: String) {
        _preGender.value = preGender
    }

    fun updateLatitude(lat: String) {
        _latitude.value = lat
    }

    fun updateLongitude(long: String) {
        _longitude.value = long
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


    fun updatePreAgeRange(preAgeRange: String) {
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

}
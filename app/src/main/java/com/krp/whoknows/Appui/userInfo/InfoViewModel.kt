package com.krp.whoknows.Appui.userInfo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

/**
 * Created by KUSHAL RAJ PAREEK on 07,February,2025
 */
class InfoViewModel : ViewModel() {


    private val _dob = MutableStateFlow(LocalDate.now())
    val dob: StateFlow<LocalDate> = _dob

    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> = _gender

    private val _preGender = MutableStateFlow("")
    val preGender: StateFlow<String> = _preGender

    private val _geoRadiusRange = MutableStateFlow("")
    val geoRadiusRange: StateFlow<String> = _geoRadiusRange


    private val _preAgeRange = MutableStateFlow("")
    val preAgeRange: StateFlow<String> = _preAgeRange

    private val _interests = MutableStateFlow<List<String>?>(null)
    val interests: StateFlow<List<String>?> = _interests


    fun updateInterest(interests: List<String>) {
        _interests.value = interests
    }


    fun removeInterest(interestToRemove: String) {
        _interests.value = _interests.value?.filterNot { it == interestToRemove }?.toList()
    }

    fun addInterest(newInterest: String) {
        _interests.value = (_interests.value ?: emptyList()) + newInterest
    }

    fun updateDOB(dob: LocalDate) {
        _dob.value = dob
    }

    fun updateGender(gender: String) {
        _gender.value = gender
    }

    fun updatePreGender(preGender: String) {
        _preGender.value = preGender
    }

    fun updatePreAgeRange(preAgeRange: String) {
        _preAgeRange.value = preAgeRange
    }

    fun updateGeoRadiusRange(geoRadiusRange: String) {
        _geoRadiusRange.value = geoRadiusRange
    }

    fun resetData() {
        _dob.value = LocalDate.now()
        _gender.value = ""
        _preGender.value = ""
        _geoRadiusRange.value = ""
        _preAgeRange.value = ""
        _interests.value = null
    }

}
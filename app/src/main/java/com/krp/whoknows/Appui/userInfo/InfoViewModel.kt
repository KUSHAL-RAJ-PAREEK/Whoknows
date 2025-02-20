package com.krp.whoknows.Appui.userInfo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

/**
 * Created by KUSHAL RAJ PAREEK on 07,February,2025
 */
class InfoViewModel: ViewModel() {


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

}
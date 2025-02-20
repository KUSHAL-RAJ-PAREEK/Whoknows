package com.krp.whoknows.Navigation

import kotlinx.serialization.Serializable

/**
 * Created by KUSHAL RAJ PAREEK on 28,January,2025
 */

@Serializable
data object WelcomeScreen

@Serializable
data object LoginScreen

@Serializable
data object PhoneScreen

@Serializable
data object HomeScreen

@Serializable
data object DOBScreen

@Serializable
data object PreferredGender

@Serializable
data object UserGender

@Serializable
data object PreferredAgeRange

@Serializable
data object GeoRadiusRange

@Serializable
data class LatLong(val latitude : String?, val longitude : String?)

@Serializable
data object MapScreen

@Serializable
data class OTPScreen(val phoneNumber: String)
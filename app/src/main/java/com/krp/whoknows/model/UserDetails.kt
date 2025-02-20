package com.krp.whoknows.model

import java.time.LocalDate


/**
 * Created by KUSHAL RAJ PAREEK on 09,February,2025
 */

data class UserDetails (
     val pNumber: String,
    val preferredAgeRange: String,
    val  preferredGender: String,
    val  geoRadiusRange : Double,
    val  userGender: String,
    val  userDob : LocalDate,
    val  latitude : Double,
    val  longitude : Double
    )
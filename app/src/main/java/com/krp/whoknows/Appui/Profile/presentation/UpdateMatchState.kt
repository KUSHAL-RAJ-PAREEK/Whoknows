package com.krp.whoknows.Appui.Profile.presentation

import com.krp.whoknows.roomdb.entity.MatchUserEntity

/**
 * Created by Kushal Raj Pareek on 20-03-2025 16:10
 */


data class UpdateMatchState(
    val isLoading:Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val statusCode : Int?= null,
    var user : MatchUserEntity? = null

)
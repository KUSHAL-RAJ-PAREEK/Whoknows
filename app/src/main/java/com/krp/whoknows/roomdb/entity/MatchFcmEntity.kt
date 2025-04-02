package com.krp.whoknows.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Kushal Raj Pareek on 01-04-2025 21:58
 */

@Entity(tableName = "matchfcm")
data class MatchFcmEntity(
    @PrimaryKey val id: Int = 1,
    val fcm_token: String
)
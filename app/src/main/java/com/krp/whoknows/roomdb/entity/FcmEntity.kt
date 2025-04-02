package com.krp.whoknows.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Kushal Raj Pareek on 01-04-2025 21:57
 */

@Entity(tableName = "userfcm")
data class FcmEntity(
    @PrimaryKey val id: Int = 1,
    val fcm_token: String
)
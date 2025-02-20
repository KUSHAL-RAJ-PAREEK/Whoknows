package com.krp.whoknows.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by KUSHAL RAJ PAREEK on 10,February,2025
 */

@Entity(tableName = "user_phone")
data class UserPhoneNumber(
    @PrimaryKey val id: Int = 1,
    val userPhoneNumber: String
)
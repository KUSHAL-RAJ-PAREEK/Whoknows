package com.krp.whoknows.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by KUSHAL RAJ PAREEK on 03,February,2025
 */

@Entity(tableName = "jwt_token")
data class JWTToken(
    @PrimaryKey val id: Int = 1,
    val token: String
)
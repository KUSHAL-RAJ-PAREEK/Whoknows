package com.krp.whoknows.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Kushal Raj Pareek on 23-03-2025 00:04
 */
@Entity(tableName = "userprofile_image")
data class UserProfileImage(
    @PrimaryKey val id: Int = 1,
    val imageString: String
)

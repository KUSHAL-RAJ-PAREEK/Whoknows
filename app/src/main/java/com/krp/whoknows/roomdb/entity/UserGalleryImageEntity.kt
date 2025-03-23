package com.krp.whoknows.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Kushal Raj Pareek on 22-03-2025 23:59
 */
@Entity(tableName = "usergallery_images")
data class UserGalleryImageEntity(
    @PrimaryKey val id: String,
    val imageString: String
)
package com.krp.whoknows.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by KUSHAL RAJ PAREEK on 14,March,2025
 */

@Entity(tableName = "gallery_images")
data class GalleryImageEntity(
    @PrimaryKey val id: String,
    val imageString: String
)
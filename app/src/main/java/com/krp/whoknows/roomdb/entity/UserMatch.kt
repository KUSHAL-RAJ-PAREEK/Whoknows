package com.krp.whoknows.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matchTable")
data class UserMatch(
    @PrimaryKey val id: Int = 1,
    val match: Boolean
)
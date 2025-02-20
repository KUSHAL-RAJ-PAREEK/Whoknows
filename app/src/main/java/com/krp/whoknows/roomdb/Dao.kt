package com.krp.whoknows.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krp.whoknows.roomdb.entity.JWTToken
import com.krp.whoknows.roomdb.entity.UserPhoneNumber

/**
 * Created by KUSHAL RAJ PAREEK on 03,February,2025
 */

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToken(token: JWTToken)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNumber(number: UserPhoneNumber)

    @Query("SELECT * FROM jwt_token WHERE id = 1")
    suspend fun getToken(): JWTToken?

    @Query("DELETE FROM jwt_token")
    suspend fun deleteToken()

    @Query("SELECT * FROM user_phone WHERE id = 1")
    suspend fun getPhoneNumber(): UserPhoneNumber?

}
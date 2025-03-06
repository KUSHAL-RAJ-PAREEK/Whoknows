package com.krp.whoknows.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krp.whoknows.roomdb.entity.InterUserDetail
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: InterUserDetail)

    @Query("SELECT * FROM jwt_token WHERE id = 1")
    suspend fun getToken(): JWTToken?

    @Query("DELETE FROM jwt_token")
    suspend fun deleteToken()

    @Query("SELECT * FROM user_phone WHERE id = 1")
    suspend fun getPhoneNumber(): UserPhoneNumber?

    @Query("UPDATE user_detail SET id = :id, username = :username, gender = :gender, dob = :dob, latitude = :latitude, longitude = :longitude, bio = :bio, interests = :interests, posts = :posts, geoRadiusRange = :geoRadiusRange, preferredGender = :preferredGender, ageGap = :ageGap, pnumber = :pnumber WHERE uid = 1")
    suspend fun updateUser(
        id: String,
        username: String,
        gender: String,
        dob: String,
        latitude: String,
        longitude: String,
        bio: String,
        interests: List<String>,
        posts: List<String>,
        geoRadiusRange: String,
        preferredGender: String,
        ageGap: String,
        pnumber: String
    )

    @Query("SELECT * FROM user_detail WHERE uid = 1")
    suspend fun getUserById(): InterUserDetail?

    @Query("DELETE FROM user_detail WHERE uid = 1")
    suspend fun deleteUser()


}
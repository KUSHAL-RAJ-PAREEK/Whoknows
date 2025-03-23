package com.krp.whoknows.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.krp.whoknows.model.UserResponse
import com.krp.whoknows.roomdb.entity.GalleryImageEntity
import com.krp.whoknows.roomdb.entity.InterUserDetail
import com.krp.whoknows.roomdb.entity.JWTToken
import com.krp.whoknows.roomdb.entity.MatchUserEntity
import com.krp.whoknows.roomdb.entity.ProfileImageEntity
import com.krp.whoknows.roomdb.entity.UserGalleryImageEntity
import com.krp.whoknows.roomdb.entity.UserMatch
import com.krp.whoknows.roomdb.entity.UserPhoneNumber
import com.krp.whoknows.roomdb.entity.UserProfileImage
import com.krp.whoknows.roomdb.entity.UserResponseEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by KUSHAL RAJ PAREEK on 03,February,2025
 */

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProfileImage(profileImage: ProfileImageEntity)

    @Query("SELECT * FROM profile_image LIMIT 1")
    suspend fun getProfileImage(): ProfileImageEntity?

    @Query("DELETE FROM profile_image")
    suspend fun deleteProfileImage()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertGalleryImage(galleryImage: GalleryImageEntity)

    @Query("SELECT * FROM gallery_images WHERE id = :id")
    suspend fun getGalleryImageById(id: String): GalleryImageEntity?

    @Query("SELECT * FROM gallery_images ORDER BY id DESC")
    suspend fun getGalleryImages(): List<GalleryImageEntity>

    @Query("DELETE FROM gallery_images WHERE id = :id")
    suspend fun deleteGalleryImageById(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMatchUserProfileImage(profileImage: UserProfileImage)

    @Query("SELECT * FROM userprofile_image LIMIT 1")
    suspend fun getMatchUserProfileImage(): UserProfileImage?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMatchUserGalleryImage(galleryImage: UserGalleryImageEntity)

    @Query("SELECT * FROM usergallery_images WHERE id = :id")
    suspend fun geMatchUsertGalleryImageById(id: String): UserGalleryImageEntity?

    @Query("SELECT * FROM usergallery_images ORDER BY id DESC")
    suspend fun getMatchUserGalleryImages(): List<UserGalleryImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserResponseEntity)

    @Query("SELECT * FROM userdetails LIMIT 1")
    fun getUser(): Flow<UserResponseEntity?>

    @Query("SELECT * FROM matchTable WHERE id = 1")
    fun getMatch():Flow<UserMatch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMatch(match: UserMatch)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMatchUser(user: MatchUserEntity)

    @Query("SELECT * FROM matchuser LIMIT 1")
    fun getMatchUser(): Flow<MatchUserEntity?>

    @Query("DELETE FROM matchuser")
    suspend fun deleteAllMatchUsers()


    @Query("DELETE FROM userdetails WHERE id = :userId")
    suspend fun deleteUser(userId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToken(token: JWTToken)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNumber(number: UserPhoneNumber)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: InterUserDetail)

    @Query("SELECT * FROM jwt_token WHERE id = 1")
     fun getToken():  Flow<JWTToken?>

    @Query("DELETE FROM jwt_token")
    suspend fun deleteToken()

    @Query("SELECT * FROM user_phone WHERE id = 1")
     fun getPhoneNumber(): Flow<UserPhoneNumber?>

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
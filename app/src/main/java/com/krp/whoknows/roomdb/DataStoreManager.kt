package com.krp.whoknows.roomdb

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.krp.whoknows.roomdb.entity.InterUserDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
/**
 * Created by KUSHAL RAJ PAREEK on 04,February,2025
 */

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        private val JWT_TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val PHONE_NUMBER_KEY = stringPreferencesKey("phone_number")
        private val ID_KEY = stringPreferencesKey("id")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val GENDER_KEY = stringPreferencesKey("gender")
        private val DOB_KEY = stringPreferencesKey("dob")
        private val LATITUDE_KEY = stringPreferencesKey("latitude")
        private val LONGITUDE_KEY = stringPreferencesKey("longitude")
        private val BIO_KEY = stringPreferencesKey("bio")
        private val INTERESTS_KEY = stringPreferencesKey("interests")
        private val POSTS_KEY = stringPreferencesKey("posts")
        private val GEO_RADIUS_RANGE_KEY = stringPreferencesKey("geo_radius_range")
        private val PREFERRED_GENDER_KEY = stringPreferencesKey("preferred_gender")
        private val AGE_GAP_KEY = stringPreferencesKey("age_gap")
    }

    val jwtToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[JWT_TOKEN_KEY]
        }

    val phoneNumber: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PHONE_NUMBER_KEY]
        }

    val userDetails: Flow<InterUserDetail?> = context.dataStore.data.map { preferences ->
        val id = preferences[ID_KEY] ?: return@map null
        InterUserDetail(
            id = id,
            username = preferences[USERNAME_KEY] ?: "",
            gender = preferences[GENDER_KEY] ?: "",
            dob = preferences[DOB_KEY] ?: "",
            latitude = preferences[LATITUDE_KEY] ?: "",
            longitude = preferences[LONGITUDE_KEY] ?: "",
            bio = preferences[BIO_KEY] ?: "",
            interests = emptyList(),
            posts = emptyList(),
            geoRadiusRange = preferences[GEO_RADIUS_RANGE_KEY] ?: "",
            preferredGender = preferences[PREFERRED_GENDER_KEY] ?: "",
            ageGap = preferences[AGE_GAP_KEY] ?: "",
            pnumber = preferences[PHONE_NUMBER_KEY] ?: ""
        )
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[JWT_TOKEN_KEY] = token
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(JWT_TOKEN_KEY)
        }
    }

    suspend fun savePhoneNumber(number: String) {
        context.dataStore.edit { preferences ->
            preferences[PHONE_NUMBER_KEY] = number
        }
    }

    suspend fun deletePhoneNumber() {
        context.dataStore.edit { preferences ->
            preferences.remove(PHONE_NUMBER_KEY)
        }
    }

    suspend fun saveUser(user: InterUserDetail) {
        context.dataStore.edit { preferences ->
            preferences[ID_KEY] = user.id
            preferences[USERNAME_KEY] = user.username
            preferences[GENDER_KEY] = user.gender
            preferences[DOB_KEY] = user.dob
            preferences[LATITUDE_KEY] = user.latitude
            preferences[LONGITUDE_KEY] = user.longitude
            preferences[BIO_KEY] = user.bio
            preferences[INTERESTS_KEY] = user.interests.joinToString(",")
            preferences[POSTS_KEY] = user.posts.joinToString(",")
            preferences[GEO_RADIUS_RANGE_KEY] = user.geoRadiusRange
            preferences[PREFERRED_GENDER_KEY] = user.preferredGender
            preferences[AGE_GAP_KEY] = user.ageGap
            preferences[PHONE_NUMBER_KEY] = user.pnumber
        }
    }

    suspend fun updateUser(user: InterUserDetail) {
        saveUser(user)
    }
    suspend fun deleteUser() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}
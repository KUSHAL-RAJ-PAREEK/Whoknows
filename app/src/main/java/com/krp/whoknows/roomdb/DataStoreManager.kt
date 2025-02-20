package com.krp.whoknows.roomdb

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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
    }

    val jwtToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[JWT_TOKEN_KEY]
        }

    val phoneNumber: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PHONE_NUMBER_KEY]
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
}
package com.krp.whoknows.roomdb

import com.krp.whoknows.model.UserResponse
import com.krp.whoknows.roomdb.entity.JWTToken
import com.krp.whoknows.roomdb.entity.UserResponseEntity

/**
 * Created by KUSHAL RAJ PAREEK on 11,March,2025
 */
class UserRepository(private val dao: Dao) {

    suspend fun saveUser(user: UserResponseEntity) {
        dao.saveUser(user!!)
    }

    suspend fun getUser(): UserResponseEntity? {
        return dao.getUser()
    }
    suspend fun deleteUser(userId: String) {
        dao.deleteUser(userId)
    }

    suspend fun saveToken(token: String) {
        dao.saveToken(JWTToken(id = 1, token = token))
    }

    suspend fun getToken(): String? {
        return dao.getToken()?.token
    }

    suspend fun deleteToken() {
        dao.deleteToken()
    }
}

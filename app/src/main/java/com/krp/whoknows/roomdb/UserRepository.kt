package com.krp.whoknows.roomdb

import com.krp.whoknows.model.UserResponse
import com.krp.whoknows.roomdb.entity.JWTToken
import com.krp.whoknows.roomdb.entity.UserPhoneNumber
import com.krp.whoknows.roomdb.entity.UserResponseEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by KUSHAL RAJ PAREEK on 11,March,2025
 */
class UserRepository(private val dao: Dao) {

    suspend fun saveUser(user: UserResponseEntity) {
        dao.saveUser(user!!)
    }

    fun getUser(): Flow<UserResponseEntity?> = dao.getUser()

    suspend fun deleteUser(userId: String) {
        dao.deleteUser(userId)
    }

    suspend fun saveToken(token: String) {
        dao.saveToken(JWTToken(id = 1, token = token))
    }

    suspend fun savePhone(pNumber: String) {
        dao.saveNumber(UserPhoneNumber(id = 1,userPhoneNumber = pNumber))
    }


     fun getPnumber() : Flow<UserPhoneNumber?>{
        return dao.getPhoneNumber()
    }

     fun getToken(): Flow<JWTToken?> {
        return dao.getToken()
    }

    suspend fun deleteToken() {
        dao.deleteToken()
    }
}

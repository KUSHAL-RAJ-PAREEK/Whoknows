package com.krp.whoknows.roomdb

import android.util.Log
import com.krp.whoknows.model.UserResponse
import com.krp.whoknows.roomdb.entity.FcmEntity
import com.krp.whoknows.roomdb.entity.JWTToken
import com.krp.whoknows.roomdb.entity.MatchFcmEntity
import com.krp.whoknows.roomdb.entity.MatchUserEntity
import com.krp.whoknows.roomdb.entity.UserMatch
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

    suspend fun deleteUsers() {
        dao.deleteUsers()
    }

    suspend fun deleteUser(userId: String) {
        dao.deleteUser(userId)
    }

    suspend fun saveToken(token: String) {
        dao.saveToken(JWTToken(id = 1, token = token))
    }

    suspend fun savePhone(pNumber: String) {
        dao.saveNumber(UserPhoneNumber(id = 1, userPhoneNumber = pNumber))
    }

    suspend fun saveMatchUser(user: MatchUserEntity) {
        dao.saveMatchUser(user)
    }

    suspend fun deleteMatchUser() {

        dao.deleteAllMatchUsers()
    }

    fun getMatchUser(): Flow<MatchUserEntity?> = dao.getMatchUser()


    fun getPnumber(): Flow<UserPhoneNumber?> {
        return dao.getPhoneNumber()
    }

    fun getToken(): Flow<JWTToken?> {
        return dao.getToken()
    }

    suspend fun deleteToken() {
        dao.deleteToken()
    }

    suspend fun saveMatchFcm(matchFcm: MatchFcmEntity) {
        dao.saveMatchFcm(matchFcm)
    }

    fun getMatchFcm(): Flow<MatchFcmEntity?> {
        return dao.getMatchFcm()
    }

    suspend fun deleteMatchFcm() {
        dao.deleteMatchFcm()
    }

    suspend fun saveFcm(fcm: FcmEntity) {
        dao.saveFcm(fcm)
    }

    fun getFcm(): Flow<FcmEntity?> {
        return dao.getFcm()
    }

    suspend fun deleteFcm() {
        dao.deleteFcm()
    }
}

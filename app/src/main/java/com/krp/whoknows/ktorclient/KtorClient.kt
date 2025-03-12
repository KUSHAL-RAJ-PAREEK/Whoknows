package com.krp.whoknows.ktorclient

/**
 * Created by KUSHAL RAJ PAREEK on 31,January,2025
 */

import android.util.Log
import com.krp.whoknows.model.OtpDetail
import com.krp.whoknows.model.SendOTP
import com.krp.whoknows.model.User
import com.krp.whoknows.model.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode


class KtorClient : KoinComponent {
    private val client: HttpClient by inject()

    suspend fun sendOtp(otpDetail: OtpDetail): String {
        val response = client.post {
            url("/public/send-otp")
            contentType(ContentType.Application.Json)
            setBody(otpDetail)
        }

        val statusCode = response.status.value
        Log.d("KtorClient", "Status Code: $statusCode")
      if (statusCode == 200) {
            return response.body()
        } else {
            throw Exception("Failed to send OTP. Status code: $statusCode")
        }
    }

    suspend fun verifyOtp(sendOTP: SendOTP): String {
        val response = client.post {
            url("/public/verify-otp")
            contentType(ContentType.Application.Json)
            setBody(sendOTP)
        }
        val statusCode = response.status.value

        Log.d("KtorClientotp", response.bodyAsText())

        return when (statusCode) {
            404 -> {
                return response.body()
            }
            200 -> {
                return response.body()
            }
            else -> {
                throw Exception("Failed to verify OTP. Status code: ${response.status.value}")
            }
        }
    }


    suspend fun createUser(user: User,jwt : String): UserResponse {
        val jwtToken =
            "Bearer $jwt"
        Log.d("jwtisthere", jwtToken)
        Log.d("KtorClient", "Sending Authorization Header: ${user}")

        val response = client.post("/users/create-user") {
            contentType(ContentType.Application.Json)
//            bearerAuth(jwtToken)
            header(HttpHeaders.Authorization, jwtToken)
            setBody(user)
        }

        val statusCode = response.status.value
        Log.d("sstttsss", statusCode.toString())

        Log.d("KtorClientotp", response.bodyAsText())
        return when (statusCode) {
            401 -> {
                Log.d("dkasdadasd", response.toString())
                response.body()
            }
            404 -> response.body()
            in 200 ..299 -> response.body()
            else -> throw Exception("Failed to verify OTP. Status code: ${response.status.value}")
        }
    }


    suspend fun getUser(pnumber: String, jwt: String): UserResponse? {
        return try {
            Log.d("iaminrequest","$pnumber $jwt")
            val response = client.get("/users/user-details") {
                contentType(ContentType.Application.Json)
                  bearerAuth(jwt)
                url {
                    parameters.append("phone", pnumber)
                }
            }
            val statusCode = response.status.value
            Log.d("API_STATUS", "Status Code: $statusCode")
            Log.d("API_RESPONSE", "Response Body: ${response.bodyAsText()}")

            return when (statusCode) {
                in 200..299 -> response.body<UserResponse>()
                401 -> {
                    Log.e("AUTH_ERROR", "Unauthorized - Invalid token")
                    null
                }
                404 -> {
                    Log.e("NOT_FOUND", "User not found")
                    null
                }
                else -> {
                    Log.e("API_ERROR", "Unexpected error: $statusCode - ${response.bodyAsText()}")
                    throw Exception("Unexpected error: $statusCode")
                }
            }
        } catch (e: Exception) {
            Log.e("NETWORK_ERROR", "Exception: ${e.localizedMessage}")
            null
        }
    }
}
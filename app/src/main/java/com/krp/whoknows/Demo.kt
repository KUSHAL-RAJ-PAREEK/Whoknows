import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.krp.whoknows.R

//package com.krp.whoknows
//
//import android.util.Log
//import com.krp.whoknows.model.OtpDetail
//import com.krp.whoknows.model.SendOTP
//import com.krp.whoknows.model.User
//import io.ktor.client.HttpClient
//import io.ktor.client.call.body
//import io.ktor.client.plugins.DefaultRequest
//import io.ktor.client.plugins.HttpTimeout
//import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
//import io.ktor.client.plugins.logging.LogLevel
//import io.ktor.client.plugins.logging.Logger
//import io.ktor.client.plugins.logging.Logging
//import io.ktor.client.request.header
//import io.ktor.client.request.post
//import io.ktor.client.request.setBody
//import io.ktor.client.statement.HttpResponse
//import io.ktor.http.ContentType
//import io.ktor.http.HttpHeaders
//import io.ktor.http.URLProtocol
//import io.ktor.http.contentType
//import io.ktor.http.path
//import io.ktor.serialization.kotlinx.json.json
//import kotlinx.serialization.json.Json
//
///**
// * Created by KUSHAL RAJ PAREEK on 30,January,2025
// */
//
//class demo {
//
//
//    companion object {
//        fun getClient(): HttpClient = HttpClient {
//            install(ContentNegotiation) {
//                json(json = Json {
//                    ignoreUnknownKeys = true
//                })
//            }
//
//            install(HttpTimeout) {
//                socketTimeoutMillis = 10000
//                requestTimeoutMillis = 10000
//                connectTimeoutMillis = 10000
//            }
//
////            install(Auth){
////                bearer {
////                    loadTokens {
////                        BearerTokens("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlaHd0a2h1aGx1IiwiaWF0IjoxNzM4MjU4NDM2LCJleHAiOjE3MzgyNjIwMzZ9.n0i6KbgYxhxbQRbYozS0MNxe_Uvx71vGDfulY110CXg","")
////                    }
////                }
////            }
//            install(DefaultRequest) {
//                url {
//                    host = "java-spring-boot-production-2c58.up.railway.app"
//                    protocol = URLProtocol.HTTPS
////                    headers {
////                        append(HttpHeaders.Authorization,"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlaHd0a2h1aGx1IiwiaWF0IjoxNzM4MjU4NDM2LCJleHAiOjE3MzgyNjIwMzZ9.n0i6KbgYxhxbQRbYozS0MNxe_Uvx71vGDfulY110CXg")
////                        append(HttpHeaders.ContentType, "application/json")
////                        append(HttpHeaders.Accept, "application/json")
////                    }
//                }
//            }
//
//
//            install(Logging) {
//                logger = object : Logger {
//                    override fun log(message: String) {
//                        Log.d("demo", message)
//                    }
//                }
//                level = LogLevel.ALL
//            }
//        }
//    }
//
////    suspend fun getPosts(): List<Post> {
////        return getClient().get("/Posts")
////            .body<List<Post>>()
////    }
//
//    suspend fun postPost(otp: OtpDetail): String {
//
//        return getClient().post {
//            url {
//                path("/public/send-otp")
//            }
//
//            contentType(ContentType.Application.Json)
//            setBody(otp)
//        }.body<String>()
//    }
//
//    suspend fun postPost1(otp: SendOTP): String {
//        return getClient().post {
//            url {
//                path("/public/verify-otp")
//            }
//            contentType(ContentType.Application.Json)
//            setBody(otp)
//        }.body<String>()
//    }
//
//    suspend fun createUser(user: User): HttpResponse {
//        val client = getClient()
//
//        val jwtToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlaHd0a2h1aGx1IiwiaWF0IjoxNzM4MjU4NDM2LCJleHAiOjE3MzgyNjIwMzZ9.n0i6KbgYxhxbQRbYozS0MNxe_Uvx71vGDfulY110CXg"
//        Log.d("demo", "Sending Authorization Header: $jwtToken")
//
//        val response = client.post {
//            url { path("/users/create-user") }
////            header("Authorization", "Bearer abc")
////            headers {
////                append(HttpHeaders.Authorization, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlaHd0a2h1aGx1IiwiaWF0IjoxNzM4MjU4NDM2LCJleHAiOjE3MzgyNjIwMzZ9.n0i6KbgYxhxbQRbYozS0MNxe_Uvx71vGDfulY110CXg")
////                append(HttpHeaders.ContentType, "application/json")
////                append(HttpHeaders.Accept, "application/json")
////            }
////            bearerAuth("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlaHd0a2h1aGx1IiwiaWF0IjoxNzM4MjU4NDM2LCJleHAiOjE3MzgyNjIwMzZ9.n0i6KbgYxhxbQRbYozS0MNxe_Uvx71vGDfulY110CXg")
//            contentType(ContentType.Application.Json)
//            header(HttpHeaders.Authorization, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlaHd0a2h1aGx1IiwiaWF0IjoxNzM4MjU4NDM2LCJleHAiOjE3MzgyNjIwMzZ9.n0i6KbgYxhxbQRbYozS0MNxe_Uvx71vGDfulY110CXg")
//            setBody(user)
//        }
//
//
//        return response
//    }
//
////
////
////    suspend fun patch(map: Map<String, String>, id: Int): Post {
////        return getClient().patch {
////            url {
////                path("/posts/${id}")
////                contentType(ContentType.Application.Json)
////                setBody(map)
////            }
////        }.body<Post>()
////    }
////
////    suspend fun put(post: Post, id: Int): Post {
////        return getClient().patch {
////            url {
////                path("/posts/${id}")
////                contentType(ContentType.Application.Json)
////                setBody(post)
////            }
////        }.body<Post>()
////    }
////
////    suspend fun delete(id: Int): HttpResponse {
////        return getClient().delete {
////            url {
////                path(".posts/${id}")
////            }
////        }
////    }
//
////    suspend fun getComments(id: Int): List<Comment> {
////        return getClient().get {
////            url {
////                path("/comments")
////                parameter("postId", id)
////            }
////        }.body<List<Comment>>()
////    }
//
//
//
//
//}

//
//@Preview(showSystemUi = true)
//@Composable
//private fun run() {
//    UploadingDialog(true)
//}
//
//@Composable
//fun UploadingDialog(showDialog: Boolean) {
//    if (showDialog) {
//        Dialog(onDismissRequest = { },
//            properties = DialogProperties(
//                dismissOnBackPress = false,
//                dismissOnClickOutside = false,
//                usePlatformDefaultWidth = false
//            )) {
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.Black.copy(alpha = 0.5f))
//            ) {
//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.uploading_anim))
//                    LottieAnimation(
//                        composition,
//                        iterations = 1,
////                        modifier = Modifier.size(150.dp),
//                        renderMode = RenderMode.HARDWARE
//                    )
//                }
//
//            }
//        }
//    }
//}

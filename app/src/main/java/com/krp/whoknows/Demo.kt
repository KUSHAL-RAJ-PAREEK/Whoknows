import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.krp.whoknows.Appui.GreetingScreen.Presentation.GreetingViewModel
import com.krp.whoknows.Auth.OTPScreen.OTPVerificationEvent
import com.krp.whoknows.Auth.OTPScreen.OTPVerificationState
import com.krp.whoknows.Auth.OTPScreen.componenets.OTPInputField
import com.krp.whoknows.Navigation.GreetingScreen
import com.krp.whoknows.Navigation.LatLong
import com.krp.whoknows.Navigation.PhoneScreen
import com.krp.whoknows.R
import com.krp.whoknows.model.OtpDetail
import com.krp.whoknows.model.SendOTP
import com.krp.whoknows.roomdb.JWTViewModel
import com.krp.whoknows.ui.theme.Cyan
import com.krp.whoknows.ui.theme.Purple
import com.krp.whoknows.ui.theme.ordColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

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

//@Preview
//@Composable
//private fun run() {
//    OTPScreen()
//}
//
//@Composable
//fun OTPScreen() {
//    var otp by remember { mutableStateOf("") }
//    var counter by remember { mutableStateOf(30) }
//    var enable by remember { mutableStateOf(false) }
////    val user by greetingViewModel.userState.collectAsState()
//
//
//    LaunchedEffect (enable) {
//        while (counter > 0) {
//            delay(1000L)
//            counter--
//        }
//        enable = false
//    }
//
//    Box(modifier = Modifier
//        .fillMaxSize()
//        .statusBarsPadding()
//        .background(Color.White)) {
//
//        Column(
//            modifier = Modifier ,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 25.dp, start = 10.dp, bottom = 16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(
//                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back arrow",
//                    Modifier.size(35.dp).clickable{
//
//                    },
//                    tint = ordColor,
//                )
//            }
//
//
//            Column( modifier = Modifier
//                .fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center) {
//
//                Text(text = "OTP Verification",
//                    fontFamily = FontFamily(Font(R.font.noto_sans_khanada)),
//                    fontSize = 20.sp)
//                //Spacer(modifier = Modifier.height(15.dp))
//
//                Text(text = "We Will send you a one time password on\n"+
//                        " this  Mobile Number",
//                    fontSize = 14.sp,
//                    textAlign = TextAlign.Center,
//                    fontFamily = FontFamily(Font(R.font.noto_sans_khanada))
//                )
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                Text(text = "+91"+
//                        "-$9414696844",
//                    fontSize = 16.sp,
//                    textAlign = TextAlign.Center,
//                    fontFamily = FontFamily(Font(R.font.outfit_semibold))
//                )
//                Spacer(modifier = Modifier.height(20.dp))
//
//                OTPInputField(
//                    otpText = otp,
//                    onOtpTextChange = { newOtp ->
//                        otp = newOtp
//                    }
//                )
//                Spacer(modifier = Modifier.height(15.dp))
//
//                if (enable) {
//                    Text(
//                        text = "Resend OTP in $counter sec",
//                        fontSize = 16.sp,
//                        color = Color.Gray
//                    )
//                } else {
//                    Text(
//                        text = "Resend OTP",
//                        fontSize = 14.sp,
//                        color = ordColor,
//                        modifier = Modifier
//                            .clickable {
//
//
//                                counter = 30
//                                enable = true
//                            }
//                    )
//                }
//            }
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(20.dp),
//                contentAlignment = Alignment.BottomEnd
//            ) {
//                FloatingActionButton(
//                    onClick = {
//
//                    },
//                    shape = CircleShape,
//                    containerColor = ordColor,
//                    modifier = Modifier
//                        .size(56.dp)
//                        .shadow(8.dp, CircleShape)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowForward,
//                        contentDescription = "Next",
//                        tint = Color.White
//                    )
//                }
//            }
//        }
//    }
//}


@Composable
fun ShinyText(text : String) {
    val shimmerColors = listOf(
        Cyan.copy(alpha = 0.4f),
        ordColor,
        Cyan.copy(alpha = 0.3f)
    )

    val transition = rememberInfiniteTransition()
    val shimmerOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(shimmerOffset, 0f),
        end = Offset(shimmerOffset + 500f, 0f)
    )

    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp),
        style = TextStyle(brush = brush)
    )
}

//@Preview
//@Composable
//private fun run() {
//}
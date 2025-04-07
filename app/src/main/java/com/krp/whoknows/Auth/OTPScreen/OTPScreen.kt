package com.krp.whoknows.Auth.OTPScreen

import android.R.attr.phoneNumber
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.xr.compose.testing.toDp
import com.google.firebase.messaging.FirebaseMessaging
import com.krp.whoknows.Appui.GreetingScreen.Presentation.GreetingViewModel
import com.krp.whoknows.Appui.Profile.presentation.ProfileDetailViewModel
import com.krp.whoknows.Auth.OTPScreen.OTPVerificationEvent
import com.krp.whoknows.Auth.OTPScreen.componenets.OTPInputField
import com.krp.whoknows.Navigation.GreetingScreen
import com.krp.whoknows.Navigation.PhoneScreen
import com.krp.whoknows.Navigation.UserGender
import com.krp.whoknows.Navigation.WelcomeScreen

import com.krp.whoknows.R
import com.krp.whoknows.ktorclient.KtorClient
import com.krp.whoknows.model.OtpDetail
import com.krp.whoknows.model.SendOTP
import com.krp.whoknows.roomdb.Dao
import com.krp.whoknows.roomdb.DataBase
import com.krp.whoknows.roomdb.JWTViewModel
import com.krp.whoknows.roomdb.UserRepository
import com.krp.whoknows.roomdb.entity.FcmEntity
import com.krp.whoknows.roomdb.entity.JWTToken
import com.krp.whoknows.ui.theme.lightOrdColor
import com.krp.whoknows.ui.theme.light_red
import com.krp.whoknows.ui.theme.ordColor
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.koin.compose.koinInject

/**
 * Created by KUSHAL RAJ PAREEK on 31,January,2025
 */


@Composable
fun OTPScreen(
    modifier: Modifier = Modifier,
    event: (OTPVerificationEvent) -> Unit,
    state: OTPVerificationState,
    jwtViewModel: JWTViewModel,
    navController: NavController,
    phoneNumber: String,
    onOTp: () -> Unit,
    profileDetailViewModel: ProfileDetailViewModel,
    greetingViewModel: GreetingViewModel
) {
    var otp by remember { mutableStateOf("") }
    var counter by remember { mutableStateOf(30) }
    var enable by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    BackHandler {
        navController.navigate(PhoneScreen) {
            popUpTo(0) { inclusive = true }
        }
    }
    LaunchedEffect(enable) {
        while (counter > 0) {
            delay(1000L)
            counter--
        }
        enable = false
    }
    LaunchedEffect(state.isLoading) {
        if (state.isOtpVerified) {
            val jwt = state.successMessage.toString()
            jwtViewModel.savePhoneNumber(phoneNumber)
            withContext(Dispatchers.IO) {
                jwtViewModel.saveToken(jwt)
                greetingViewModel.saveToken(jwt)
                greetingViewModel.savePnumber(phoneNumber)
                greetingViewModel.savePnumber(phoneNumber)
                greetingViewModel.loadPNumber()
                greetingViewModel.loadJwtToken()
            }


            coroutineScope.launch {
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val userId = greetingViewModel.getId(phoneNumber, jwt)

                        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val newToken = task.result
                                GlobalScope.launch(Dispatchers.IO) {
                                    try {
                                        greetingViewModel.uploadToken(userId, newToken)
                                        Log.d("FCM", "Token updated in MongoDB")
                                    } catch (e: Exception) {
                                        Log.e(
                                            "FCM",
                                            "Error updating token in MongoDB: ${e.message}"
                                        )
                                    }
                                }

                                greetingViewModel.saveFcm(FcmEntity(id = 1, fcm_token = newToken))
                                profileDetailViewModel.updateFcm(newToken)
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("FCM", "Error fetching user ID: ${e.message}")
                    }
                }

            }

            if (state.statusCode == 200) {
                navController.navigate(GreetingScreen)
            } else if (state.statusCode == 206) {

                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(UserGender)
            } else {
                onOTp()
            }

        }
    }

    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current).toDp()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, start = 10.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back arrow",
                    Modifier
                        .size(35.dp)
                        .clickable {
                            navController.navigate(PhoneScreen) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                    tint = ordColor,
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "OTP Verification",
                    fontFamily = FontFamily(Font(R.font.noto_sans_khanada)),
                    fontSize = 20.sp
                )
                Text(
                    text = "We Will send you a one time password on\n" +
                            " this  Mobile Number",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.noto_sans_khanada))
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "+91" +
                            "-$phoneNumber",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.outfit_semibold))
                )
                Spacer(modifier = Modifier.height(20.dp))

                OTPInputField(
                    otpText = otp,
                    onOtpTextChange = { newOtp ->
                        otp = newOtp
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))

                if (enable) {
                    Text(
                        text = "Resend OTP in $counter sec",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                } else {
                    Text(
                        text = "Resend OTP",
                        fontSize = 14.sp,
                        color = ordColor,
                        modifier = Modifier
                            .clickable {
                                event(
                                    OTPVerificationEvent.ResendOtp(
                                        otpSend = OtpDetail(
                                            "+91",
                                            pNumber = phoneNumber
                                        )
                                    )
                                )
                                counter = 30
                                enable = true
                            }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                FloatingActionButton(
                    onClick = {
                        val notp = otp
                        if (otp.length < 4) {
                            DynamicToast.make(
                                context,
                                "please enter 4-digit OTP",
                                ContextCompat.getDrawable(context, R.drawable.warning)?.mutate(),
                                lightOrdColor.toArgb(),
                                light_red.toArgb()
                            ).show()

                        } else {
                            if (!state.isLoading) {
                                event(
                                    OTPVerificationEvent.VerifyOtp(
                                        SendOTP(
                                            countryCode = "+91",
                                            pNumber = phoneNumber,
                                            otp = notp
                                        )
                                    )
                                )
                            }
                        }

                    },
                    shape = CircleShape,
                    containerColor = ordColor,
                    modifier = Modifier
                        .padding(bottom = if (imeHeight > 0.dp) imeHeight + 20.dp else 40.dp)
                        .size(56.dp)
                        .shadow(8.dp, CircleShape)
                        .clickable(enabled = !state.isLoading) {}

                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Next",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}


package com.krp.whoknows.Auth.OTPScreen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.krp.whoknows.Auth.OTPScreen.componenets.OTPInputField
import com.krp.whoknows.Navigation.PhoneScreen

import com.krp.whoknows.R
import com.krp.whoknows.model.OtpDetail
import com.krp.whoknows.model.SendOTP
import com.krp.whoknows.roomdb.DataBase
import com.krp.whoknows.roomdb.JWTViewModel
import com.krp.whoknows.roomdb.entity.JWTToken
import com.krp.whoknows.ui.theme.ordColor
import kotlinx.coroutines.delay

/**
 * Created by KUSHAL RAJ PAREEK on 31,January,2025
 */

@Composable
fun OTPScreen(
    modifier: Modifier = Modifier,
    event: (OTPVerificationEvent) -> Unit,
    state: OTPVerificationState,
    jwtViewModel : JWTViewModel,
    navController : NavController,
    phoneNumber: String,
    onOTp: () -> Unit) {
    var otp by remember { mutableStateOf("") }
    var counter by remember { mutableStateOf(30) }
    var enable by remember { mutableStateOf(false) }
    BackHandler {
        navController.navigate(PhoneScreen){
            popUpTo(0) { inclusive = true }
        }
    }
    LaunchedEffect (enable) {
            while (counter > 0) {
                delay(1000L)
                counter--
            }
        enable = false
    }
    LaunchedEffect(state.isOtpVerified) {
        if (state.isOtpVerified) {
            val jwt = state.successMessage.toString()
            jwtViewModel.saveToken(jwt)
           val t =  jwtViewModel.savePhoneNumber(phoneNumber)
            onOTp()
        }
    }
        Box(modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)) {
            Canvas(modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(), onDraw = {
                drawCircle(
                    color = ordColor.copy(alpha = 0.4f),
                    radius = 800f,
                    center = Offset(0f, 0f)
                )

                drawCircle(
                    color = ordColor.copy(alpha = 0.5f), radius = 800f,
                    center = Offset(size.width, 0f)
                )

//                val center = Offset(size.width / 2f, size.height / 2f - 300f)
//                val radius = 470f

//                drawCircle(
//                    color = ordColor.copy(alpha = 0.4f),
//                    radius = radius,
//                    center = center
//                )
//                val cutHeight = 100f
//                drawRect(
//                    color = Color.White,
//                    topLeft = Offset(center.x - radius, center.y + radius - cutHeight),
//                    size = androidx.compose.ui.geometry.Size(2 * radius, cutHeight)
//                )
            })


            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.otp_girl),
                    modifier = Modifier
                        .width(350.dp)
                        .offset(x = 13.dp),
                    contentDescription = "otp_girl",
                )

                Column( modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {

                    //Spacer(modifier = Modifier.height(15.dp))

                    Text(text = "OTP Verification",
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_semibold))
                    )


                    Text(text = "We Will send you a one time password on\n"+
                            " this  Mobile Number",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.outfit_medium))
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "+91"+
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

                    Spacer(modifier = Modifier.height(15.dp))

                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 70.dp)
                        .height(45.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor  = ordColor),
                        onClick = {
                            val notp = otp
                            event(OTPVerificationEvent.VerifyOtp(
                                SendOTP(
                                    countryCode = "+91",
                                    pNumber = phoneNumber,
                                    otp = notp
                                )))
                        }) {
                        Text(text = "submit",
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            fontSize = 18.sp)
                    }

                }
            }
    }
}


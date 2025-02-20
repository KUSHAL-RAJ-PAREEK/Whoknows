package com.krp.whoknows.Auth.PhoneScreen.Presentation

/**
 * Created by KUSHAL RAJ PAREEK on 31,January,2025
 */

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.krp.whoknows.R
import com.krp.whoknows.model.OtpDetail
import com.krp.whoknows.ui.theme.ordColor

@Preview
@Composable
private fun run() {
    PhoneScreen(Modifier,{},PhoneAuthState(),{})
}
@Composable
fun PhoneScreen(
    modifier: Modifier = Modifier,
    event: (PhoneAuthEvent) -> Unit,
    state: PhoneAuthState,
    onOtpSent: () -> Unit
) {
    LaunchedEffect(state.isOtpSent) {
        if (state.isOtpSent) {
            onOtpSent()
        }
    }

    var phoneNumber by remember { mutableStateOf("") }

    val isValid = phoneNumber.length == 10
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
        })


        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phone ->
                    phoneNumber = phone
                },
                label = { Text("Enter Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (!isValid && phoneNumber.isNotEmpty()) {
                Text(
                    text = "Phone number must be exactly 10 digits",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading) {
                Text(text = "Sending OTP...", color = Color.Gray)
            }

            if (state.errorMessage != null) {
                Text(
                    text = "Error: ${state.errorMessage}",
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (state.isOtpSent) {
                Text(
                    text = "OTP sent successfully!",
                    color = Color.Green
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isValid) {
                        event(PhoneAuthEvent.SendOtp(otpDetail = OtpDetail(
                            "+91", pNumber = phoneNumber
                        )))
                    }
                },
//            enabled = isValid && !state.isOtpSent,

            ) {
                Text("Submit")
            }
        }
    }

}

package com.krp.whoknows.Auth.PhoneScreen.Presentation

/**
 * Created by KUSHAL RAJ PAREEK on 31,January,2025
 */

import android.R.attr.maxLines
import android.R.attr.text
import android.R.attr.textStyle
import android.graphics.PorterDuff
import android.widget.Toast
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
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.LocalTextStyle
import androidx.xr.compose.testing.isFocused
import androidx.xr.compose.testing.toDp
import com.krp.whoknows.Navigation.LatLong
import com.krp.whoknows.R
import com.krp.whoknows.Utils.CustomToast
import com.krp.whoknows.model.OtpDetail
import com.krp.whoknows.ui.theme.lightOrdColor
import com.krp.whoknows.ui.theme.light_green
import com.krp.whoknows.ui.theme.light_red
import com.krp.whoknows.ui.theme.light_yellow
import com.krp.whoknows.ui.theme.ordColor
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import okhttp3.internal.wait



@Preview
@Composable
private fun run() {
    PhoneScreen(Modifier,{},PhoneAuthState(),{})
}

@OptIn(ExperimentalMaterial3Api::class)
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
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current).toDp()


    var phoneNumber by remember { mutableStateOf("") }
    val isValid = phoneNumber.length == 10
val context = LocalContext.current

    if (state.isLoading) {
        DynamicToast.make(context,"Sending otp...",
            ContextCompat.getDrawable(context, R.drawable.paper_plane)?.mutate(),lightOrdColor.toArgb(),light_yellow.toArgb()).show()
    }

    if (state.errorMessage != null) {
        DynamicToast.make(context,"Something went wrong",
            ContextCompat.getDrawable(context, R.drawable.warning)?.mutate(),lightOrdColor.toArgb(),light_red.toArgb()).show()
    }

    if (state.isOtpSent) {
        DynamicToast.make(context,"OTP sent successfully!",
            ContextCompat.getDrawable(context, R.drawable.success)?.mutate(),lightOrdColor.toArgb(),light_green.toArgb()).show()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp,top = 50.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "What's your Phone number?",
                fontFamily = FontFamily(Font(R.font.noto_sans_khanada)),
                fontSize = 20.sp,
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null,  tint = Color.Gray)
                },
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, color = Color.Black),
                placeholder = { Text("Enter your number") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = ordColor,
                    unfocusedBorderColor = Color.Gray
                ),
                maxLines = 1,
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
            


        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {
                    if (!state.isLoading) {

                        if (phoneNumber.isEmpty()) {
                            DynamicToast.make(context,"Please Enter Phone Number",
                                ContextCompat.getDrawable(context, R.drawable.warning)?.mutate(),lightOrdColor.toArgb(),light_red.toArgb()).show()

                        } else if (!isValid && phoneNumber.isNotEmpty()) {

                            DynamicToast.make(context,"number must be 10 digits",
                                ContextCompat.getDrawable(context, R.drawable.warning)?.mutate(),lightOrdColor.toArgb(),light_red.toArgb()).show()

                        }
                        if (isValid) {
                            event(PhoneAuthEvent.SendOtp(OtpDetail("+91", pNumber = phoneNumber)))
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

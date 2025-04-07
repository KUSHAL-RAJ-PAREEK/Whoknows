package com.krp.whoknows.Appui.userInfo

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.wear.compose.material.Icon
import androidx.xr.compose.testing.toDp
import com.krp.whoknows.Navigation.DOBScreen
import com.krp.whoknows.Navigation.PhoneScreen
import com.krp.whoknows.Navigation.UserGender
import com.krp.whoknows.R
import com.krp.whoknows.Utils.DropDownMenu
import com.krp.whoknows.ui.theme.lightOrdColor
import com.krp.whoknows.ui.theme.light_red
import com.krp.whoknows.ui.theme.light_yellow
import com.krp.whoknows.ui.theme.ordColor
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import kotlin.math.log


/**
 * Created by KUSHAL RAJ PAREEK on 07,February,2025
 */


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferredGender(
    viewModel: InfoViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    var text by remember { mutableStateOf(TextFieldValue("")) }

    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current).toDp()
    LaunchedEffect(Unit) {
        text = TextFieldValue(viewModel.preGender.value)
    }
    BackHandler {
        navController.navigate(UserGender) {
            popUpTo(0) { inclusive = true }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                        navController.navigate(UserGender) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                tint = ordColor
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .padding(top = 10.dp)
        ) {
            Text(
                text = "What's your preferred Gender ?",
                fontFamily = FontFamily(Font(R.font.noto_sans_khanada)),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            DropDownMenu(listOf("MALE", "FEMALE"), viewModel.preGender.value, flag = false) {
                if (viewModel.preGender.value.isBlank()) {
                    viewModel.updatePreGender(it)
                }
                text = TextFieldValue(viewModel.preGender.value)
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
                    if (text.text.isBlank()) {
                        DynamicToast.make(
                            context, "Please enter your Preferred Gender",
                            ContextCompat.getDrawable(context, R.drawable.warning)?.mutate(),
                            com.krp.whoknows.ui.theme.lightOrdColor.toArgb(), light_red.toArgb()
                        ).show()

                    } else if (text.text.toIntOrNull() != null && text.text.toInt() < 18) {
                        DynamicToast.make(
                            context,
                            "You must be at least 18 years old",
                            ContextCompat.getDrawable(context, R.drawable.age_logo)?.mutate(),
                            lightOrdColor.toArgb(),
                            light_yellow.toArgb()
                        ).show()
                    } else {
                        navController.navigate(com.krp.whoknows.Navigation.DOBScreen)
                    }
                },
                shape = CircleShape,
                containerColor = ordColor,
                modifier = Modifier

                    .padding(bottom = if (imeHeight > 0.dp) imeHeight + 20.dp else 40.dp)
                    .size(56.dp)
                    .shadow(8.dp, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next",
                    tint = Color.White
                )
            }
        }
    }
}
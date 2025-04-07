package com.krp.whoknows.Appui.userInfo

import android.app.DatePickerDialog
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.LocalTextStyle
import androidx.xr.compose.testing.toDp
import com.krp.whoknows.R
import com.krp.whoknows.Utils.Dialogs
import com.krp.whoknows.ui.theme.lightOrdColor
import com.krp.whoknows.ui.theme.light_red
import com.krp.whoknows.ui.theme.light_yellow
import com.krp.whoknows.ui.theme.ordColor
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Created by KUSHAL RAJ PAREEK on 07,February,2025
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DOBScreen(
    viewModel: InfoViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var isFocused by remember { mutableStateOf(false) }
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current).toDp()

    BackHandler {
        navController.navigate(com.krp.whoknows.Navigation.PreferredGender) {
            popUpTo(0) { inclusive = true }
        }
    }
    var date by remember {
        mutableStateOf(LocalDate.now())
    }

    LaunchedEffect(Unit) {
        text = TextFieldValue(viewModel.dob.value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
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
                        navController.navigate(com.krp.whoknows.Navigation.PreferredGender) {
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
                text = "What's your date of birth ?",
                fontFamily = FontFamily(Font(R.font.noto_sans_khanada)),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = text,
                onValueChange = { },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {}
                    .onFocusChanged { isFocused = it.isFocused },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = "Calendar Icon",
                        tint = Color.Gray,
                        modifier = Modifier.clickable {
                            val listener =
                                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                                    date = LocalDate.of(year, month + 1, day)
                                    val formattedDate =
                                        date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                                    text = TextFieldValue(formattedDate)
                                    viewModel.updateDOB(date)
                                }
                            Dialogs().openDatePicker(
                                context = context,
                                onDateSetListener = listener,
                                date = date
                            )
                        }
                    )
                },
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, color = Color.Black),
                placeholder = { Text("Enter your Date of Birth") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = ordColor,
                    unfocusedBorderColor = Color.Gray
                ),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
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
                    if (text.text.isBlank()) {

                        DynamicToast.make(
                            context,
                            "Please enter your date of birth",
                            ContextCompat.getDrawable(context, R.drawable.calender_logo)?.mutate(),
                            lightOrdColor.toArgb(),
                            light_red.toArgb()
                        ).show()
                    } else {
                        val today = LocalDate.now()
                        val ageThreshold = today.minusYears(18)
                        if (viewModel.dob.value.isAfter(ageThreshold)) {
                            DynamicToast.make(
                                context,
                                "You must be at least 18 years old",
                                ContextCompat.getDrawable(context, R.drawable.age_logo)?.mutate(),
                                lightOrdColor.toArgb(),
                                light_yellow.toArgb()
                            ).show()
                        } else {
                            navController.navigate(com.krp.whoknows.Navigation.InterestScreen)
                        }
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
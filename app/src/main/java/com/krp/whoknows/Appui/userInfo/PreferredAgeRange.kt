package com.krp.whoknows.Appui.userInfo

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.LocalTextStyle
import androidx.xr.compose.testing.toDp
import com.krp.whoknows.R
import com.krp.whoknows.ui.theme.ordColor
import java.time.LocalDate

/**
 * Created by KUSHAL RAJ PAREEK on 10,February,2025
 */


@Preview
@Composable
private fun Run() {
    PreferredAgeRange(viewModel = viewModel(), navController = rememberNavController())
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferredAgeRange(viewModel: InfoViewModel,
                      navController: NavController
) {
    val context = LocalContext.current

    var text by remember { mutableStateOf("")}
    var text1 by remember { mutableStateOf(TextFieldValue("")) }
    var text2 by remember { mutableStateOf(TextFieldValue("")) }
    var isFocused by remember { mutableStateOf(false) }
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current).toDp()
    BackHandler {
        navController.navigate(com.krp.whoknows.Navigation.DOBScreen){
            popUpTo(0) { inclusive = true }
        }
    }
    LaunchedEffect(Unit) {
        text = viewModel.preAgeRange.value
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
                Modifier.size(35.dp),
                tint = ordColor
            )
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(top = 10.dp)){
            Text(text = "What's your Preferred Age ?",
                fontFamily = FontFamily(Font(R.font.noto_sans_khanada)),
                fontSize = 25.sp)

            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){
                OutlinedTextField(
                    value = text1,
                    onValueChange = { text1 = it
                        viewModel.updatePreAgeRange(it.toString())},
                    modifier = Modifier
                        .clickable {}
                        .weight(1f)
                        .onFocusChanged { isFocused = it.isFocused },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    placeholder = { Text("Min") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ordColor,
                        unfocusedBorderColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done)
                )
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = text2,
                    onValueChange = { text2 = it
                        viewModel.updatePreAgeRange(it.toString())},
                    modifier = Modifier
                        .clickable {}
                        .weight(1f)
                        .onFocusChanged { isFocused = it.isFocused },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    placeholder = { Text("Max") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ordColor,
                        unfocusedBorderColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done)
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
                    text ="${text1.text}-${text2.text}"
                    Log.d("textiti",text)
                    if (text1.text.isBlank() && text2.text.isBlank()) {
                        Toast.makeText(context, "Please enter min and max range", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.updatePreAgeRange(text)
                        navController.navigate(com.krp.whoknows.Navigation.GeoRadiusRange)
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
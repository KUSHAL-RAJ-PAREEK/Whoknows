package com.krp.whoknows.Appui.Profile.presentation.EditProfileComponents

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.LocalTextStyle
import com.krp.whoknows.Appui.userInfo.InfoViewModel
import com.krp.whoknows.R
import com.krp.whoknows.ui.theme.ordColor

/**
 * Created by KUSHAL RAJ PAREEK on 09,March,2025
 */

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.EditBioScreen(modifier: Modifier = Modifier,
                                        text : String,
                                        animatedVisibilityScope : AnimatedVisibilityScope,
                                        viewModel : InfoViewModel,
                                        navController : NavController) {

    Box(){
        OutlinedTextField(
            value = text,
            onValueChange = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {}
                .sharedElement(
                    state = rememberSharedContentState(
                        key = "text/${text}",
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 1000)
                    }
                )
                ,
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
            placeholder = { Text("Enter range in KM") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = ordColor,
                unfocusedBorderColor = Color.Gray
            ),
            shape = RoundedCornerShape(20.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

    }
    }


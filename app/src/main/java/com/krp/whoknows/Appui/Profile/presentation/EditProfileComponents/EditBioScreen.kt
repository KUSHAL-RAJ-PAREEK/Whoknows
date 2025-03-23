package com.krp.whoknows.Appui.Profile.presentation.EditProfileComponents

import android.R.attr.bottom
import android.R.attr.scaleX
import android.R.attr.scaleY
import android.R.attr.text
import android.R.attr.textStyle
import android.R.attr.top
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.LocalTextStyle
import com.google.accompanist.insets.statusBarsPadding
import com.krp.whoknows.Appui.Profile.presentation.EditProfileViewModel
import com.krp.whoknows.Appui.userInfo.InfoViewModel
import com.krp.whoknows.R
import com.krp.whoknows.ui.theme.background_white
import com.krp.whoknows.ui.theme.ordColor
import com.krp.whoknows.ui.theme.text_gray
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Created by KUSHAL RAJ PAREEK on 09,March,2025
 */

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
private fun run() {

    var text by  remember { mutableStateOf("") }

val context = LocalContext.current
    Box(modifier = Modifier){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    background_white
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Edit Bio",
                color = text_gray,
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 30.dp),
                fontFamily = FontFamily(Font(R.font.poppins_bold))
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = text,
                onValueChange = { newText ->
//                    text = newText.take(500)
                    Log.d("newtext", newText)
                    if (newText.length <= 500) {
                        text = newText
                    }else {
                        Toast.makeText(context, "reached the limit", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(550.dp)
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 20.dp)
                    .clickable {},
                supportingText = {
                    Text(
                        text = "${text.length}/500",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                    )
                },
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp,color = Color.Black),
                placeholder = { Text("Enter the Bio") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = ordColor,
                    unfocusedBorderColor = Color.Gray
                ),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )

            androidx.compose.material3.Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick()
                    .padding(horizontal = 25.dp)
                    .height(45.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ordColor),
                onClick = {

                }
            ) {
                Text(
                    text = "Save",
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 18.sp
                )
            }

        }
    }
}
@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.EditBioScreen(modifier: Modifier = Modifier,
                                        trans_text: String,
                                        animatedVisibilityScope : AnimatedVisibilityScope,
                                        viewModel : InfoViewModel,
                                        editProfileViewModel: EditProfileViewModel,
                                        navController : NavController) {

    var text by  remember { mutableStateOf(trans_text) }

    val context = LocalContext.current
    Box(modifier = Modifier){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    background_white
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Edit Bio",
                color = text_gray,
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 30.dp),
                fontFamily = FontFamily(Font(R.font.poppins_bold))
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = text,
                onValueChange = { newText ->
//                    text = newText.take(500)
                    Log.d("newtext", newText)
                    if (newText.length <= 500) {
                        text = newText
                    }else {
                        Toast.makeText(context, "reached the limit", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(550.dp)
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 20.dp)
                    .sharedElement(
                        state = rememberSharedContentState(
                            key = "text/${editProfileViewModel.bio.value}",
                        ),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 1000)
                        }
                    )
                    .clickable {},
                supportingText = {
                    Text(
                        text = "${text.length}/500",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                    )
                },
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, color = Color.Black),
                placeholder = { Text("Enter the Bio") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = ordColor,
                    unfocusedBorderColor = Color.Gray
                ),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )

            androidx.compose.material3.Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick()
                    .padding(horizontal = 25.dp)
                    .height(45.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ordColor),
                onClick = {
                    editProfileViewModel.updateBio(text)
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate("profileEditScreen/123/whoknows")
                }
            ) {
                Text(
                    text = "Save",
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 18.sp
                )
            }

        }
    }
    }


enum class ButtonState { Pressed, Idle }
fun Modifier.bounceClick() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(if (buttonState == ButtonState.Pressed) 0.70f else 1f)

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {  }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}
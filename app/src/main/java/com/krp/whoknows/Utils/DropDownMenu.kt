package com.krp.whoknows.Utils

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.krp.whoknows.Appui.userInfo.InfoViewModel
import com.krp.whoknows.ui.theme.background_white
import com.krp.whoknows.ui.theme.ordColor
import io.ktor.websocket.Frame
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import kotlin.math.log

/**
 * Created by KUSHAL RAJ PAREEK on 09,February,2025
 */


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    list: List<String>,
    value: String,
    flag: Boolean,
    onItemSelected: (String) -> Unit,
) {
    Log.d("insidedropdown", value)
    var selectedItem by remember { mutableStateOf(value) }
    var isFocused by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { },
            readOnly = true,
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
                .fillMaxWidth(),
            trailingIcon = {
                Icon(icon, "", Modifier.clickable {
                    expanded = !expanded
                    isFocused = expanded
                })
            },
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, color = Color.Black),
            placeholder = { if (flag) Text("Enter your Gender") else Text("Enter preferred Gender") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (isFocused) ordColor else Color.Gray,
                unfocusedBorderColor = Color.Gray
            ),
            shape = RoundedCornerShape(20.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                isFocused = false
            },
            modifier = Modifier
                .background(background_white)
                .width(
                    with(LocalDensity.current) { textFieldSize.width.toDp() },
                )
        ) {
            list.forEach { label ->
                MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(40.dp))) {

                    DropdownMenuItem(
                        modifier = Modifier,
                        text = {
                            Text(text = label, color = Color.Black)
                        },
                        onClick = {
                            selectedItem = label
                            onItemSelected(label)
                            expanded = false
                            isFocused = false
                        },
                    )
                }
            }
        }

    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemDropDownMenu(
    list: List<String>,
    value: String,
    onItemSelected: (String) -> Unit
) {
    var selectedItem by remember { mutableStateOf(value) }
    var isFocused by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    LaunchedEffect(value) {
        selectedItem = value
    }
    Column(
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { },
            readOnly = true,
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
                .fillMaxWidth(),
            trailingIcon = {
                Icon(icon, "", Modifier.clickable {
                    expanded = !expanded
                    isFocused = expanded
                })
            },
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, color = Color.Black),
            placeholder = { Text("Enter preferred Gender") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (isFocused) ordColor else Color.Gray,
                unfocusedBorderColor = Color.Gray
            ),
            shape = RoundedCornerShape(20.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                isFocused = false
            },
            modifier = Modifier
                .background(background_white)
                .width(
                    with(LocalDensity.current) { textFieldSize.width.toDp() },
                )
        ) {
            list.forEach { label ->
                MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(40.dp))) {

                    DropdownMenuItem(
                        modifier = Modifier,
                        text = {
                            Text(text = label, color = Color.Black)
                        },
                        onClick = {
                            selectedItem = label
                            onItemSelected(label)
                            expanded = false
                            isFocused = false
                        },
                    )
                }
            }
        }

    }
}


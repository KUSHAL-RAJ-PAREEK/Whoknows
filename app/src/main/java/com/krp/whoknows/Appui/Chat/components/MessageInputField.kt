package com.krp.whoknows.Appui.Chat.components

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.krp.whoknows.Appui.Chat.presentation.ChatState
import com.krp.whoknows.Appui.Chat.presentation.ChatViewModel
import com.krp.whoknows.R
import com.krp.whoknows.roomdb.ImageConverter.base64ToBitmap
import com.krp.whoknows.roomdb.ImageConverter.uriToBase64
import com.krp.whoknows.ui.theme.background_white
import com.krp.whoknows.ui.theme.chat_light
import com.krp.whoknows.ui.theme.ordColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

/**
 * Created by Kushal Raj Pareek on 24-03-2025 00:15
 */
//
//@Preview
//@Composable
//private fun run() {
//    MessageInputField { }
//}

@Composable
fun MessageInputField(
    chatId : String, userId: String,chatViewModel : ChatViewModel, cimage : Uri?, setBimage: (Bitmap?) -> Unit, setUri : (Uri?)-> Unit, onSend: (String) -> Unit) {


    val context = LocalContext.current
    var cImage by remember { mutableStateOf<Uri?>(null) }
val coroutineScope = rememberCoroutineScope()

    var lastTypedTime by remember { mutableStateOf(System.currentTimeMillis()) }
    var cPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            cImage = uri
            uri?.let {
                val base64String = uriToBase64(context, uri)
                val bitmap = base64String?.let { base64ToBitmap(it) }
                setBimage(bitmap)
                Log.d("uriisherer", uri.toString())
                setUri(cImage)
            }
        }
    )

    val message = remember {
        mutableStateOf("")
    }
    val containerColor = chat_light
var cam = remember { mutableStateOf(true) }

    LaunchedEffect(message.value, cimage) {
        if (message.value.isNotEmpty()) {
            cam.value = false
        } else if (cimage != null) {
            cam.value = false
        }else if(cimage == null){
            cam.value = true
        }else{
            cam.value = true
        }
    }
    TextField(
        value = message.value,
        onValueChange = {
            message.value = it

            chatViewModel.sendTypingStatus(chatId, userId, true)

            lastTypedTime = System.currentTimeMillis() // Update last typed time

            coroutineScope.launch {
                delay(2000)
                if (System.currentTimeMillis() - lastTypedTime >= 2000) {
                    chatViewModel.sendTypingStatus(chatId, userId, false)
                }
            }

        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 7.dp)
            .padding( horizontal = 4.dp)
            .navigationBarsPadding()
//            .imePadding()
        ,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.hellix_regular))
        ),
        placeholder = {
            Text(
                text = "Type a message...",
                style = androidx.compose.ui.text.TextStyle(
                    color = Color(0xFFCCCCCC),
                    fontSize = 16.sp,
//                    fontFamily = FontFamily(Font(R.font.pompiere))
                )
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                   if(cam.value){
                       try {
                           cPhotoPickerLauncher.launch(
                               PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                           )
                       } catch (e: Exception) {
                           Toast.makeText(context, "$e", Toast.LENGTH_LONG)
                               .show()
                       }
                   }else{
                       onSend(message.value)
                       message.value = ""
                   }
                },
                imageVector =if(cam.value) Icons.Default.CameraEnhance else  Icons.AutoMirrored.Outlined.Send,
                contentDescription = null,
                tint = ordColor
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        maxLines = 2
    )

}
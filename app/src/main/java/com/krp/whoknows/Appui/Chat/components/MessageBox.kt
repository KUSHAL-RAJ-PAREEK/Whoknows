package com.krp.whoknows.Appui.Chat.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.krp.whoknows.Appui.Profile.presentation.ProfileDetailViewModel
import com.krp.whoknows.Utils.toBitmap
import com.krp.whoknows.model.Message
import com.krp.whoknows.ui.theme.chat_dark
import com.krp.whoknows.ui.theme.chat_light
import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle

/**
 * Created by Kushal Raj Pareek on 24-03-2025 00:14
 */

@Preview
@Composable
private fun run() {
    MessageBox(
        padding = 2.dp,
        Message(
            senderId = "DADSASD",
            receiverId = "dasdasdas",
            timeStamp = "22:30",
            message = "",
            imgStr ="dsadasd",
            imgUrl = null
        ), profileDetailViewModel = ProfileDetailViewModel()
    )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MessageBox(
    padding : Dp,
    message: Message,
    profileDetailViewModel: ProfileDetailViewModel
) {
    val isCurrentUser = profileDetailViewModel.id.value == message.senderId

    val modifier = Modifier
        .padding(
            start = if (isCurrentUser) 16.dp else 8.dp,
            end = if (isCurrentUser) 8.dp else 16.dp,
            top = 3.dp,
            bottom = 3.dp+padding
        )
        .defaultMinSize(minHeight = 40.dp)
        .clip(
            RoundedCornerShape(
                bottomEnd = 10.dp,
                bottomStart = 10.dp,
                topStart = if (isCurrentUser) 10.dp else 0.dp,
                topEnd = if (isCurrentUser) 0.dp else 10.dp
            )
        )
        .background(
//            brush = Brush.linearGradient(
            if (isCurrentUser) {

                if(message.message != "deleted"){
                    chat_dark
                }else{
                    chat_dark.copy(alpha = 0.2f)
                }
            } else {
                if(message.message != "deleted"){
                    chat_light
                }else{
                    chat_light.copy(alpha = 0.2f)
                }
            }
        )

    val boxAlignment = if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        contentAlignment = boxAlignment
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Box(
                modifier = modifier
            ) {
                Column(
                    modifier = Modifier.padding(7.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    if (message.imgUrl != null) {
                        Image(
                            bitmap = message.imgStr!!.toBitmap()!!.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(250.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                    }


              Spacer(modifier = Modifier.height(4.dp))

                    if(message.message != "") {
                        Text(
                            modifier = Modifier.widthIn(max = 250.dp),
                            text = message.message!!,
                            maxLines = Int.MAX_VALUE,
                            overflow = TextOverflow.Visible,
                            style = androidx.compose.ui.text.TextStyle(
                                color = if (isCurrentUser) chat_light else chat_dark,
                                fontSize = 16.sp
                            )
                        )
                    }

                        Spacer(modifier = Modifier.height(4.dp))


                    message.timeStamp?.let {
                        Text(
                            modifier = Modifier.align(if (profileDetailViewModel.id.value == message.senderId) Alignment.End else Alignment.Start),
                            text = formatTimestampToHHMM(it),
                            style = androidx.compose.ui.text.TextStyle(
                                color = if (isCurrentUser) chat_light else chat_dark,
                                fontSize = 11.sp,
                            )
                        )
                    }
                }
            }
        }
    }
}


fun formatTimestampToHHMM(timestamp: String): String {
    return try {
        val instant = Instant.parse(timestamp)
        val zonedDateTime = instant.atZone(ZoneId.of("Asia/Kolkata"))

        val hours = zonedDateTime.hour.toString().padStart(2, '0')
        val minutes = zonedDateTime.minute.toString().padStart(2, '0')

        "$hours:$minutes"
    } catch (e: Exception) {
        "--:--"
    }
}

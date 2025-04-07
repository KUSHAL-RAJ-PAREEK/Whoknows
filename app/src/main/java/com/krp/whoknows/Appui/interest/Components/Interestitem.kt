package com.krp.whoknows.Appui.interest.Components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.krp.whoknows.ui.theme.ordColor

/**
 * Created by KUSHAL RAJ PAREEK on 04,February,2025
 */


@Composable
fun InterestItem(
    modifier: Modifier = Modifier,
    name: String,
    filled: Boolean,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .border(2.dp, ordColor, RoundedCornerShape(13.dp))
            .clip(RoundedCornerShape(13.dp))
            .background(if (filled) ordColor else Color.Transparent)
            .padding(10.dp)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            color = if (filled) Color.White else ordColor
        )
    }
}


package com.krp.whoknows.Appui.Profile.presentation.components

import android.R.attr.maxLines
import android.R.attr.name
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.krp.whoknows.Appui.Profile.presentation.InterestItem
import com.krp.whoknows.ui.theme.Bpink
import com.krp.whoknows.ui.theme.heartColor
import com.krp.whoknows.ui.theme.ordColor

/**
 * Created by KUSHAL RAJ PAREEK on 07,March,2025
 */

@Composable
fun MatchItem(modifier: Modifier = Modifier, icon: ImageVector, status: Boolean) {
    Box(
        modifier = modifier
            .border(1.dp, ordColor, RoundedCornerShape(13.dp))
            .clip(RoundedCornerShape(13.dp))
            .background(ordColor.copy(alpha = 0.2f))
            .padding(6.dp)
            .clickable(enabled = false, onClick = {}),
        contentAlignment = Alignment.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = "match Icon",
                tint = if (status) heartColor else Bpink,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (status) "matched" else "single",
                color = ordColor,
                maxLines = 1
            )
        }
    }
}
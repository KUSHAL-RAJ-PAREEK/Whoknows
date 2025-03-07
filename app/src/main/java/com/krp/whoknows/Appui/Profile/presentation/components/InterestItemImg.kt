package com.krp.whoknows.Appui.Profile.presentation.components

import android.R.attr.contentDescription
import android.media.Image
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.krp.whoknows.Appui.Profile.presentation.InterestItem
import com.krp.whoknows.R
import com.krp.whoknows.ui.theme.ordColor

/**
 * Created by KUSHAL RAJ PAREEK on 07,March,2025
 */

//@Preview
//@Composable
//private fun Run() {
//    InterestItemImg(name = "singing", imageRes = R.drawable.)
//}

@Composable
fun InterestItemImg(modifier: Modifier = Modifier, interest: InterestItem) {
    Box(
        modifier = modifier
            .border(1.dp, ordColor, RoundedCornerShape(13.dp))
            .clip(RoundedCornerShape(13.dp))
            .background(Color.Transparent)
            .padding(6.dp)
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = interest.icon,
                contentDescription = "${interest.name} Icon",
                tint = ordColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = interest.name,
                color = ordColor,
                maxLines = 1
            )
        }
    }
}


package com.krp.whoknows.Appui.Profile.presentation.components

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
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.krp.whoknows.Appui.Profile.presentation.InterestItem
import com.krp.whoknows.ui.theme.ordColor

/**
 * Created by KUSHAL RAJ PAREEK on 13,March,2025
 */

@Composable
fun InterestItemImgCancel(
    modifier: Modifier = Modifier,
    interest: InterestItem,
    selectedInterest: InterestItem?,
    onSelect: (InterestItem) -> Unit,
    onDelete: (InterestItem) -> Unit
) {
    val isSelected = selectedInterest == interest

    Box(
        modifier = modifier
            .border(1.dp, if (isSelected) Color.Red else ordColor, RoundedCornerShape(13.dp))
            .clip(RoundedCornerShape(13.dp))
            .background(Color.Transparent)
            .padding(6.dp)
            .clickable {
                if (isSelected) {
                    onDelete(interest)
                } else {
                    onSelect(interest)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (isSelected) Icons.Default.Remove else interest.icon,
                contentDescription = "${interest.name} Icon",
                tint = if (isSelected) Color.Red else ordColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = interest.name,
                color = if (isSelected) Color.Red else ordColor,
                maxLines = 1
            )
        }
    }
}



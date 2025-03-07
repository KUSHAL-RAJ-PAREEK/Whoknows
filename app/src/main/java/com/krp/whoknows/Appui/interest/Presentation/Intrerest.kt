package com.krp.whoknows.Appui.interest.Presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import com.krp.whoknows.Appui.interest.Components.InterestItem
import com.krp.whoknows.R
import io.ktor.websocket.Frame

/**
 * Created by KUSHAL RAJ PAREEK on 04,February,2025
 */

@Preview
@Composable
fun Interest(modifier: Modifier = Modifier) {
    val list = mutableListOf<String>()

    list.add("hello")
    list.add("hellowdwe")
    list.add("hello")
    list.add("helldeedeo")
    list.add("hello")
    list.add("heledelo")
    list.add("hello")
    val selectedStates = remember { mutableStateListOf<Boolean>().apply { repeat(list.size) { add(false) } } }

   Column(modifier = Modifier.background(Color.White)) {
       Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(top = 25.dp, start = 5.dp,bottom = 16.dp),
           verticalAlignment = Alignment.CenterVertically
       ) {
           Icon(imageVector = Icons.Default.ArrowBack
               , contentDescription = "Back arrow",
               Modifier.size(30.dp),
               tint = Color.Black)
           Spacer(modifier = modifier.width(12.dp))
           Text(text = "Choose Your Interest",
               fontSize = 25.sp,
               fontFamily = FontFamily(Font(R.font.outfit_semibold)))
       }

       LazyVerticalGrid(columns = GridCells.Fixed(3),
       ) {
           items(list.size){i ->
               InterestItem( modifier = Modifier.padding(6.dp),
                   name = list[i], filled = selectedStates[i]) {
                   selectedStates[i] = !selectedStates[i]
               }
           }
       }
   }
}




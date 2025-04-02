package com.krp.whoknows.Auth.LoginScreen.Presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.krp.whoknows.Auth.LoginScreen.Presentation.components.SlideToUnlock
import com.krp.whoknows.Navigation.PhoneScreen
import com.krp.whoknows.R

/**
 * Created by KUSHAL RAJ PAREEK on 28,January,2025
 */
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun LoginScreen(modifier: Modifier = Modifier,onPhoneSlide :() -> Unit) {
   var isLoading by remember { mutableStateOf(false) }
   Box(
      modifier = Modifier
          .fillMaxSize()
          .background(Color.White)
   ) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
Spacer(modifier = modifier.height(90.dp))
        Image(
            painter = painterResource(id = R.drawable.couple),
            contentDescription = "Landing Image",
            modifier = Modifier
                .size(580.dp)
                .fillMaxSize()
        )
        Spacer(modifier = modifier.height(10.dp))
       Text(
          text = "WhoKnows? Love Beyond Looks!",
          fontSize = 20.sp,
          fontFamily = FontFamily(Font(R.font.outfit_medium)),
          modifier = Modifier.fillMaxWidth(),
          textAlign = TextAlign.Center
       )
        Spacer(modifier = modifier.height(20.dp))
      SlideToUnlock(
         isLoading = isLoading,
         onUnlockRequested = {onPhoneSlide() },
          modifier = Modifier.padding(horizontal = 40.dp)
      )
    }
   }
}
//
//@Preview
//@Composable
//private fun run() {
//    LoginScreen(onPhoneSlide = {})
//}



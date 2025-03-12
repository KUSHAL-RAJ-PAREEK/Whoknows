package com.krp.whoknows.Appui.GreetingScreen.Presentation

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.krp.whoknows.Navigation.HomeScreen
import com.krp.whoknows.R
import com.krp.whoknows.Utils.TypewriteText
import com.krp.whoknows.model.User
import com.krp.whoknows.roomdb.entity.UserResponseEntity
import com.krp.whoknows.ui.theme.Cyan
import com.krp.whoknows.ui.theme.LightBlue
import com.krp.whoknows.ui.theme.Purple
import kotlinx.coroutines.delay
import kotlin.math.log

/**
 * Created by KUSHAL RAJ PAREEK on 11,March,2025
 */

@Composable
fun GreetingScreen(modifier: Modifier = Modifier,
                   navController: NavController,
                   greetingViewModel: GreetingViewModel,
                   state : GetUserState,
                   event : (GetUserEvent)-> Unit) {


    val jwtToken = greetingViewModel.jwtToken.collectAsState()


    LaunchedEffect(jwtToken) {
        val user = greetingViewModel.getUser()
        Log.d("maalagaya", user.toString())
        if (user != null) {
            delay(5000)
            navController.popBackStack()
            navController.navigate(HomeScreen)
        } else {
            jwtToken?.let { token ->
                Log.d("tokenaagaya", token.value.toString())
                event(GetUserEvent.GetUser("9414696844", token.value.toString()))
            }
        }
    }


    LaunchedEffect(state.isLoading) {
        Log.d("afkmaskfsmasfsa", state.isSuccess.toString())
        if(state.isSuccess){
            val user = state.successMessage
            val data = UserResponseEntity(
                id = user?.id ?: "",
                imgUrl = user?.imgUrl,
                ageGap = user?.ageGap,
                bio = user?.bio,
                dob = user?.dob,
                gender = user?.gender,
                geoRadiusRange = user?.geoRadiusRange,
                latitude = user?.latitude,
                longitude = user?.longitude,
                pnumber = user?.pnumber,
                preferredGender = user?.preferredGender,
                username = user?.username,
                interests = user?.interests ?: emptyList(),
                posts = user?.posts ?: emptyList()
            )
            greetingViewModel.saveUser(data)
            delay(5000)
            navController.popBackStack()
            navController.navigate(HomeScreen)
        }
    }

    val gradientColors = listOf(Cyan, LightBlue, Purple)

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.welcome)
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val textDuration = ("Welcome".length * 500)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.5f))
                .blur(170.dp),
            contentAlignment = Alignment.Center
        ){
            Image(painter = painterResource(R.drawable.gradient_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop)

            TypewriteText(
                text = "Welcome",
                spec = tween(
                    durationMillis = textDuration,
                    easing = LinearEasing
                ),
                modifier = Modifier
            )
//            LottieAnimation(modifier= Modifier.size(180.dp)
//                .blur(500.dp)
//                ,composition = composition,
//                iterations = 1,
//                renderMode = RenderMode.HARDWARE )
        }
//        TypewriteText(
//            text = "Welcome",
//            spec = tween(
//                durationMillis = textDuration,
//                easing = LinearEasing
//            ),
//            modifier = Modifier
//        )
        LottieAnimation(modifier= Modifier.size(190.dp),composition = composition,
            iterations = 1,
            renderMode = RenderMode.HARDWARE )
    }
}


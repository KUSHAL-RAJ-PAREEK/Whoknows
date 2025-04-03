package com.krp.whoknows.Appui.GreetingScreen.Presentation

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.krp.whoknows.Appui.Profile.presentation.EditProfileViewModel
import com.krp.whoknows.Appui.Profile.presentation.ImageViewModel
import com.krp.whoknows.Appui.Profile.presentation.MainImageViewModel
import com.krp.whoknows.Appui.Profile.presentation.ProfileDetailViewModel
import com.krp.whoknows.Navigation.HomeScreen
import com.krp.whoknows.R
import com.krp.whoknows.Utils.TypewriteText
import com.krp.whoknows.Utils.calculateAge
import com.krp.whoknows.Utils.convertImageUrlToBase64
import com.krp.whoknows.Utils.drawableToBitmap
import com.krp.whoknows.Utils.getLocationCityState
import com.krp.whoknows.model.FcmModel
import com.krp.whoknows.model.User
import com.krp.whoknows.roomdb.JWTViewModel
import com.krp.whoknows.roomdb.entity.FcmEntity
import com.krp.whoknows.roomdb.entity.UserResponseEntity
import com.krp.whoknows.ui.theme.Cyan
import com.krp.whoknows.ui.theme.LightBlue
import com.krp.whoknows.ui.theme.Purple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.koin.compose.koinInject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.log

/**
 * Created by KUSHAL RAJ PAREEK on 11,March,2025
 */

@SuppressLint("StateFlowValueCalledInComposition", "SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun GreetingScreen(modifier: Modifier = Modifier,
                   navController: NavController,
                   greetingViewModel: GreetingViewModel,
                   state : GetUserState,
                   event : (GetUserEvent)-> Unit,
                   profileDetailViewModel : ProfileDetailViewModel,
                   editProfileViewModel : EditProfileViewModel,
                   jwtViewModel: JWTViewModel,
                   imageViewModel: ImageViewModel,
                   mainImageViewModel: MainImageViewModel) {

    val jwtToken = jwtViewModel.jwtToken.value

    val user by greetingViewModel.userState.collectAsState()
    val pNumber by greetingViewModel.pNumber.collectAsState()

    val fcmToken by greetingViewModel.fcmToken.collectAsState()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var animated by remember{ mutableStateOf(false) }


    LaunchedEffect(user?.username) {
        delay(4000)
        Log.d("maalagaya", user.toString())
        Log.d("tokenfsnasfasfa", fcmToken.toString())
        if (user != null) {
//            delay(3000)
            profileDetailViewModel.updateFcm(fcmToken!!)
            navController.popBackStack()
            navController.navigate(HomeScreen)
        } else {
            val jwtToken = jwtViewModel.jwtToken
            jwtToken?.let { token ->
                Log.d("tokenaagaya", token.value.toString())
                event(GetUserEvent.GetUser(pNumber.toString(), token.value.toString()))
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

//            withContext(Dispatchers.IO) {
                val dobString = user?.dob ?: "2004-12-12"
            val localDate = LocalDate.parse(dobString, DateTimeFormatter.ISO_LOCAL_DATE)
            profileDetailViewModel.updateId(user?.id.toString())
            profileDetailViewModel.updateUsername(user?.username.toString())
            profileDetailViewModel.updateImgUrl(user?.imgUrl.toString())
            profileDetailViewModel.updateGeoRadiusRange(user?.geoRadiusRange.toString())
            profileDetailViewModel.updatePreGender(user?.preferredGender.toString())
            profileDetailViewModel.updateGender(user?.gender.toString())
            profileDetailViewModel.updateDobs(calculateAge(localDate.toString()).toString())
            profileDetailViewModel.updateDOB(localDate)
            profileDetailViewModel.updatePreAgeRange(user?.ageGap.toString())
            profileDetailViewModel.updateBio(user?.bio?:"hey, I am using whoknows.")
            profileDetailViewModel.updateInterest(user?.interests ?: emptyList())
            profileDetailViewModel.updatePosts(user?.posts ?: emptyList())
//        profileDetailViewModel.updateFPreAgeRange(parts[0].toString())
//        profileDetailViewModel.updateTPreAgeRange(parts[1].toString())
            profileDetailViewModel.updateLatitude(user?.latitude.toString())
            profileDetailViewModel.updateLongitude(user?.longitude.toString())
            profileDetailViewModel.updatePnumber(user?.pnumber.toString())
            profileDetailViewModel.updateJwt(jwtViewModel.jwtToken.value.toString())
            profileDetailViewModel.updateDefaultImg(drawableToBitmap(context, if(user?.gender.toString() == "MALE") R.drawable.bp_img_placeholder else  R.drawable.p_img_placeholder).asImageBitmap())
            val  location = getLocationCityState(
                user?.latitude?.toDouble() ?: 26.9124,
                user?.longitude?.toDouble() ?: 75.7873,
                context
            )
            profileDetailViewModel.updateLocation(location)

            val imgUrl = user?.imgUrl.toString()
            val list = user?.posts ?: emptyList()


            profileDetailViewModel.updatemImage(imgUrl)

            if (list.isNotEmpty()) {
                val firstElement = list[0]
                profileDetailViewModel.updatefImage(firstElement)
            }

            if (list.size > 1) {
                val secondElement = list[1]
                profileDetailViewModel.updatesImage(secondElement)

            }

            if (list.size > 2) {
                val thirdlement = list[2]
                profileDetailViewModel.updatetImage(thirdlement)

            }

//            coroutineScope.launch {
                mainImageViewModel.saveProfileImage(convertImageUrlToBase64(user?.imgUrl.toString()))
                val size = user?.posts?.size ?: 0
                for (i in 0 until size) {
                    val imgUrl = user?.posts?.get(i) ?: continue
                    val base64String = convertImageUrlToBase64(imgUrl)
                    Log.d("imgusdasdsdrl",imgUrl)
                    if (base64String != null) {
                        Log.d("converted",i.toString())
                        mainImageViewModel.saveGalleryImage("${user?.id}_g${i+1}",base64String)
                    } else {
                        Log.e("ProfileImage", "Failed to convert image to Base64")
                    }
                }
//            }
//            }

            coroutineScope.launch {
                val response = greetingViewModel.getToken(user?.id.toString())
                if(response.statusCode == 200){
                    greetingViewModel.saveFcm(FcmEntity(id = 1, fcm_token = response.token!!))
                    profileDetailViewModel.updateFcm(response.token!!)
                }
            }
            greetingViewModel.saveUser(data)
            delay(4000)
            navController.popBackStack()
            Log.d("itiscomingthere","hello before")

            navController.navigate(HomeScreen)
            Log.d("itiscomingthere","hello after")
        }
    }



    LaunchedEffect(user) {
        user?.let {
            val dobString = user?.dob ?: "2004-12-12"
            val localDate = LocalDate.parse(dobString, DateTimeFormatter.ISO_LOCAL_DATE)
            profileDetailViewModel.updateId(user?.id.toString())
            profileDetailViewModel.updateUsername(user?.username.toString())
            profileDetailViewModel.updateImgUrl(user?.imgUrl.toString())
            profileDetailViewModel.updateGeoRadiusRange(user?.geoRadiusRange.toString())
            profileDetailViewModel.updatePreGender(user?.preferredGender.toString())
            profileDetailViewModel.updateGender(user?.gender.toString())
            profileDetailViewModel.updateDobs(calculateAge(localDate.toString()).toString())
            profileDetailViewModel.updateDOB(localDate)
            profileDetailViewModel.updatePreAgeRange(user?.ageGap.toString())
            profileDetailViewModel.updateBio(user?.bio?:"hey, I am using whoknows.")
            profileDetailViewModel.updateInterest(user?.interests ?: emptyList())
            profileDetailViewModel.updatePosts(user?.posts ?: emptyList())
//        profileDetailViewModel.updateFPreAgeRange(parts[0].toString())
//        profileDetailViewModel.updateTPreAgeRange(parts[1].toString())
            profileDetailViewModel.updateLatitude(user?.latitude.toString())
            profileDetailViewModel.updateLongitude(user?.longitude.toString())
            profileDetailViewModel.updatePnumber(user?.pnumber.toString())
            profileDetailViewModel.updateJwt(jwtViewModel.jwtToken.value.toString())

            val imgUrl = user?.imgUrl.toString()
            val list = user?.posts ?: emptyList()


            val  location = getLocationCityState(
                user?.latitude?.toDouble() ?: 26.9124,
                user?.longitude?.toDouble() ?: 75.7873,
                context
            )
            profileDetailViewModel.updateLocation(location)

            profileDetailViewModel.updatemImage(imgUrl)

            if (list.isNotEmpty()) {
                val firstElement = list[0]
                profileDetailViewModel.updatefImage(firstElement)
            }

            if (list.size > 1) {
                val secondElement = list[1]
                profileDetailViewModel.updatesImage(secondElement)

            }

            if (list.size > 2) {
                val thirdlement = list[2]
                profileDetailViewModel.updatetImage(thirdlement)

            }

//        coroutineScope.launch {
            mainImageViewModel.saveProfileImage(convertImageUrlToBase64(user?.imgUrl.toString()))
            val size = user?.posts?.size ?: 0
            for (i in 0 until size) {
                val imgUrl = user?.posts?.get(i) ?: continue
                val base64String = convertImageUrlToBase64(imgUrl)
                Log.d("imgusdasdsdrl",imgUrl)
                if (base64String != null) {
                    Log.d("converted",i.toString())
                    mainImageViewModel.saveGalleryImage("${user?.id}_g${i+1}",base64String)
                } else {
                    Log.e("ProfileImage", "Failed to convert image to Base64")
                }
//            }
            }
            profileDetailViewModel.updateDefaultImg(drawableToBitmap(context, if(user?.gender.toString() == "MALE") R.drawable.bp_img_placeholder else  R.drawable.p_img_placeholder).asImageBitmap())

            fun logValues() {
                Log.d("ProfileViewModel", """
        Username: ${profileDetailViewModel.username.value}
        Image URL: ${profileDetailViewModel.imgUrl.value}
        Geo Radius Range: ${profileDetailViewModel.geoRadiusRange.value}
        Preferred Gender: ${profileDetailViewModel.preGender.value}
        DOB: ${profileDetailViewModel.dob.value}
        Preferred Age Range: ${profileDetailViewModel.preAgeRange.value}
        Bio: ${profileDetailViewModel.bio.value}
        Interests: ${profileDetailViewModel.interests.value}
        Posts: ${profileDetailViewModel.posts.value}
        Latitude: ${profileDetailViewModel.latitude.value}
        Longitude: ${profileDetailViewModel.longitude.value}
    """.trimIndent())
            }
            logValues()
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



package com.krp.whoknows.Appui.MatchingScreen.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.core.Fit
import com.krp.whoknows.Appui.Profile.presentation.MainImageViewModel
import com.krp.whoknows.Appui.Profile.presentation.ProfileDetailViewModel
import com.krp.whoknows.Appui.Profile.presentation.components.MatchItem
import com.krp.whoknows.Navigation.ChatScreen
import com.krp.whoknows.Navigation.HomeScreen
import com.krp.whoknows.R
import com.krp.whoknows.RiveComponents.ComposableRiveAnimationView
import com.krp.whoknows.Utils.ShinyText
import com.krp.whoknows.Utils.calculateAge
import com.krp.whoknows.Utils.convertImageUrlToBase64
import com.krp.whoknows.Utils.getLocationCityState
import com.krp.whoknows.Utils.noRippleClickable
import com.krp.whoknows.roomdb.entity.UserResponseEntity
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Created by KUSHAL RAJ PAREEK on 02,March,2025

 */

@SuppressLint("StateFlowValueCalledInComposition", "UnrememberedMutableInteractionSource")
@Composable
fun MatchingScreen(
    modifier: Modifier = Modifier,
    match_state: CreateMatchState,
    check_state: CheckMatchState,
    cancel_state : CancelMatchState,
    cancel_event : (CancelMatchEvent) -> Unit,
    match_event: (CreateMatchEvent) -> Unit,
    check_event: (CheckMatchEvent) -> Unit,
    profileDetailViewModel: ProfileDetailViewModel,
    matchUserViewModel : MatchUserViewModel,
    mainImageViewModel : MainImageViewModel,
    navcontroller : NavController
) {


    val value = profileDetailViewModel.isMatch.value

   if(value){
       var animation: RiveAnimationView? = null
       var progress by remember { mutableStateOf(0f) }
       var rprogress by remember { mutableStateOf(0f) }

       var launched by remember { mutableStateOf(false) }
       var reached by remember { mutableStateOf(false) }
var inside by remember { mutableStateOf(false) }
       var backstack by remember { mutableStateOf(false) }
       LaunchedEffect(launched) {
           if (launched) {
               val duration = 2000L
               val steps = 100
               val delayTime = duration / steps
               for (i in 0..steps) {
                   progress = i.toFloat()
                   animation?.setNumberState("Motion", "numDrag", progress)
                   delay(delayTime)
               }
           }
           reached = true
       }

       BackHandler(true) {
           if(backstack){
               Log.d("hellosdasd","sdasdasd")
               navcontroller.popBackStack()
               navcontroller.popBackStack()
               navcontroller.navigate(HomeScreen)
           }
       }

       LaunchedEffect(rprogress) {
           if(rprogress == 100f){
               delay(1500)
               inside = true
               delay(2000)
//               backstack = true
               navcontroller.popBackStack()
               navcontroller.navigate("chatScreen")
           }
       }
       LaunchedEffect(reached) {
           if (reached) {
               val duration = 1000L
               val steps = 100
               val delayTime = duration / steps
               for (i in 0..steps) {
                   rprogress = i.toFloat()
                   animation?.setNumberState("Motion", "numLoad", rprogress)
                   delay(delayTime)
               }
           }
       }


       launched = true

       Box(
           modifier = Modifier.fillMaxSize(),
           contentAlignment = Alignment.Center

       ) {
           ComposableRiveAnimationView(
               modifier = Modifier,
               fit = Fit.COVER, animation = R.raw.pull_to_refresh,
               stateMachineName = "Motion"
           ) { view ->
               animation = view
           }

           val interactionSource = remember { MutableInteractionSource() }


           Box(
               modifier = Modifier
                   .align(Alignment.Center)
                   .size(190.dp)
                   .padding(top = 35.dp)
                   .clickable(
                       interactionSource = interactionSource,
                       indication = null
                   ) {}
                   .background(Color.Transparent)
           )

               Column(
                   modifier = Modifier.padding(top = 50.dp),
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.Center
               ) {
                   if(inside){
                       MatchingMatchItem(
                           modifier = Modifier.padding(top = 20.dp),
                           icon = ImageVector.vectorResource(R.drawable.heart),
                           status = true
                       )
                       Spacer(Modifier.height(10.dp))
                       ShinyText(text = "Stay Loyal")
                   }
                   }
       }
   }else{
       var inMatch by remember { mutableStateOf(false) }

       val user = UserResponseEntity(
           id = profileDetailViewModel.id.value,
           imgUrl = profileDetailViewModel.imgUrl.value,
           ageGap = profileDetailViewModel.preAgeRange.value,
           bio = profileDetailViewModel.bio.value,
           dob = profileDetailViewModel.dob.value.toString(),
           gender = profileDetailViewModel.gender.value,
           geoRadiusRange = profileDetailViewModel.geoRadiusRange.value.toInt(),
           latitude = profileDetailViewModel.latitude.value,
           longitude = profileDetailViewModel.longitude.value,
           pnumber = profileDetailViewModel.pnumber.value,
           preferredGender = profileDetailViewModel.preGender.value,
           username = profileDetailViewModel.username.value,
           interests = profileDetailViewModel.interests.value,
           posts = profileDetailViewModel.posts.value
       )



       val context = LocalContext.current
       var animation: RiveAnimationView? = null
       var launched by remember { mutableStateOf(false) }
       var reached by remember { mutableStateOf(false) }
       LaunchedEffect(match_state.isLoading) {
           if (match_state.isSuccess) {
               if (match_state.statusCode == 200) {
                   inMatch = true
               } else {
                   Log.d("match_error", match_state.isSuccess.toString())
               }
           }
       }
       var progress by remember { mutableStateOf(0f) }
       var rprogress by remember { mutableStateOf(0f) }
       var showMatchItem by remember { mutableStateOf(false) }
       var isMatched by remember { mutableStateOf(false) }
       var breakit by remember { mutableStateOf(false) }
       var backstack by remember { mutableStateOf(true) }


       BackHandler(true) {
           if(backstack){
               Log.d("backstackes", "asfasdfda")
               navcontroller.popBackStack()
               navcontroller.popBackStack()
               navcontroller.navigate(HomeScreen)
           }
       }
//    var showText by remember { mutableStateOf(true) }
//
//    LaunchedEffect(Unit) {
//        delay(3000)
//        showText = false
//    }
//BackHandler {
//
//}


       if(progress > 0){
           BackHandler {}
       }

       LaunchedEffect(rprogress) {
           if (rprogress == 100f) {
               delay(2000)
               showMatchItem = true
           }
       }


       LaunchedEffect(launched) {
           if (launched) {
               val duration = 2000L
               val steps = 100
               val delayTime = duration / steps
               for (i in 0..steps) {
                   progress = i.toFloat()
                   animation?.setNumberState("Motion", "numDrag", progress)
                   delay(delayTime)
               }
           }
       }

       LaunchedEffect(reached) {
           if (reached) {
               val duration = 2000L
               val steps = 100
               val delayTime = duration / steps
               for (i in 0..steps) {
                   rprogress = i.toFloat()
                   animation?.setNumberState("Motion", "numLoad", rprogress)
                   delay(delayTime)
               }
           }
       }

       LaunchedEffect(cancel_state.isLoading) {
           if(cancel_state.statusCode == 200){
               Log.d("helloheyinmatchremove","removed")
               delay(2500)
               backstack = true
               cancel_state.isLoading = false
               cancel_state.isSuccess = false
               cancel_state.statusCode = null
               navcontroller.popBackStack()
               navcontroller.popBackStack()
               navcontroller.navigate(HomeScreen)
           }
       }

       LaunchedEffect(check_state.isLoading) {
           if(check_state.statusCode == 200){
               breakit = true
               delay(2000)
               reached = true
               check_state.isLoading = false
               check_state.isSuccess = false
               match_state.isLoading = false
               match_state.isSuccess = false
               inMatch = false
               isMatched = true

               val matchUser = check_state.user
               matchUserViewModel.saveMatchUser(matchUser)

               val dobString = matchUser?.dob ?: "2004-12-12"
               val localDate = LocalDate.parse(dobString, DateTimeFormatter.ISO_LOCAL_DATE)
               matchUserViewModel.updateId(matchUser?.id.toString())
               matchUserViewModel.updateUsername(matchUser?.username.toString())
               matchUserViewModel.updateImgUrl(matchUser?.imgUrl.toString())
               matchUserViewModel.updateGeoRadiusRange(matchUser?.geoRadiusRange.toString())
               matchUserViewModel.updatePreGender(matchUser?.preferredGender.toString())
               matchUserViewModel.updateGender(matchUser?.gender.toString())
               matchUserViewModel.updateDobs(calculateAge(localDate.toString()).toString())
               matchUserViewModel.updateDOB(localDate)
               matchUserViewModel.updatePreAgeRange(matchUser?.ageGap.toString())
               matchUserViewModel.updateBio(matchUser?.bio?:"hey, I am using whoknows.")
               matchUserViewModel.updateInterest(matchUser?.interests ?: emptyList())
               matchUserViewModel.updatePosts(matchUser?.posts ?: emptyList())
//        profileDetailViewModel.updateFPreAgeRange(parts[0].toString())
//        profileDetailViewModel.updateTPreAgeRange(parts[1].toString())
               matchUserViewModel.updateLatitude(matchUser?.latitude.toString())
               matchUserViewModel.updateLongitude(matchUser?.longitude.toString())
               matchUserViewModel.updatePnumber(matchUser?.pnumber.toString())

               val imgUrl = matchUser?.imgUrl.toString()
               val list = matchUser?.posts ?: emptyList()

               val  location = getLocationCityState(
                   matchUser?.latitude?.toDouble() ?: 26.9124,
                   matchUser?.longitude?.toDouble() ?: 75.7873,
                   context
               )
               matchUserViewModel.updateLocation(location)

               matchUserViewModel.updatemImage(imgUrl)

               if (list.isNotEmpty()) {
                   val firstElement = list[0]
                   matchUserViewModel.updatefImage(firstElement)
               }

               if (list.size > 1) {
                   val secondElement = list[1]
                   matchUserViewModel.updatesImage(secondElement)

               }

               if (list.size > 2) {
                   val thirdlement = list[2]
                   matchUserViewModel.updatetImage(thirdlement)

               }

//        coroutineScope.launch {
               mainImageViewModel.saveMatchProfileImage(convertImageUrlToBase64(matchUser?.imgUrl.toString()))
               val size = matchUser?.posts?.size ?: 0
               for (i in 0 until size) {
                   val imgUrl = matchUser?.posts?.get(i) ?: continue
                   val base64String = convertImageUrlToBase64(imgUrl)
                   Log.d("imgusdasdsdrl",imgUrl)
                   if (base64String != null) {
                       Log.d("converted",i.toString())
                       mainImageViewModel.saveMatchGalleryImage("${matchUser?.id}_g${i+1}",base64String)
                   } else {
                       Log.e("ProfileImage", "Failed to convert image to Base64")
                   }
//            }
               }

               delay(7000)

               navcontroller.popBackStack()
               navcontroller.navigate("chatScreen")

               delay(200)
               profileDetailViewModel.updateMatch(true)
               Log.d("hereiam",check_state.user.toString())
               Log.d("matchornot", "matched")
           }
       }

       LaunchedEffect(inMatch) {
           Log.d("dadasdasdasd", inMatch.toString())
           if (inMatch) {
               var attempts = 0
               while (attempts < 3) {
                   check_event(
                       CheckMatchEvent.CheckMatch(
                           id = profileDetailViewModel.id.value,
                           jwt = profileDetailViewModel.jwt.value
                       )
                   )
                   delay(6000)
                   if (breakit) {
                       Log.d("breaking", "now");
                       break
                   }
                   attempts++
               }
               reached = true
               Log.d("attempted",attempts.toString())
               if(attempts == 3){
                   Log.d("afssasfas","sdadsasdasd")
//                   check_state.isLoading = false
//                   check_state.isSuccess = false
//                   match_state.isLoading = false
//                   match_state.isSuccess = false
//                   inMatch = false
                   isMatched = false
                   delay(4500)
                   cancel_event(CancelMatchEvent.CancelMatch(
                       user = user,
                       jwt = profileDetailViewModel.jwt.value
                   ))
                   Log.d("matchornot", "not matched")
               }
           }
       }


       Box(
           modifier = Modifier.fillMaxSize(),
           contentAlignment = Alignment.Center

       ) {
           ComposableRiveAnimationView(
               modifier = Modifier,
               fit = Fit.COVER, animation = R.raw.pull_to_refresh,
               stateMachineName = "Motion"
           ) { view ->
               animation = view
           }
           val interactionSource = remember { MutableInteractionSource() }


           Box(
               modifier = Modifier
                   .align(Alignment.Center)
                   .size(190.dp)
                   .padding(top = 35.dp)
                   .clickable(
                       interactionSource = interactionSource,
                       indication = null
                   ) {
                       Log.d("clicking", "yes")
                       launched = true
                       backstack = false
                       match_event(
                           CreateMatchEvent.CreateMatch(
                               user = user,
                               jwt = profileDetailViewModel.jwt.value
                           )
                       )
                   }
                   .background(Color.Transparent)
           )

           if (showMatchItem) {
               Column(
                   modifier = Modifier.padding(top = 50.dp),
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.Center
               ) {
                   MatchingMatchItem(
                       modifier = Modifier.padding(top = 20.dp),
                       icon = ImageVector.vectorResource(R.drawable.heart),
                       status = isMatched
                   )
                   if (!isMatched) {
                       Spacer(Modifier.height(10.dp))
                       ShinyText(text = "Love takes time to find")
                   } else {
                       Spacer(Modifier.height(10.dp))
                       ShinyText(text = "Now YouKnows")

                   }
               }
           }
       }
   }
}


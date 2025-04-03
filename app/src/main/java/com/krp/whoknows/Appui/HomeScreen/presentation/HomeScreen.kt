package com.krp.whoknows.Appui.HomeScreen.presentation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.MaterialTheme
import com.krp.whoknows.R
import com.krp.whoknows.ui.theme.WhoknowsTheme
import kotlin.math.PI
import kotlin.math.sin
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.RenderEffect
import android.graphics.Shader
import android.net.Uri
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Scaffold
import app.rive.runtime.kotlin.RiveAnimationView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.krp.whoknows.Appui.Chat.presentation.ChatViewModel
import com.krp.whoknows.Appui.GreetingScreen.Presentation.GreetingViewModel
import com.krp.whoknows.Appui.MatchingScreen.presentation.MatchUserViewModel
import com.krp.whoknows.Appui.Profile.presentation.MainImageViewModel
import com.krp.whoknows.Appui.Profile.presentation.ProfileDetailViewModel
import com.krp.whoknows.Appui.Profile.presentation.UpdateMatchEvent
import com.krp.whoknows.Appui.Profile.presentation.UpdateMatchState
import com.krp.whoknows.Appui.Profile.presentation.UpdateMatchViewModel
import com.krp.whoknows.Appui.Profile.presentation.UpdateUserEvent
import com.krp.whoknows.Appui.userInfo.InfoViewModel
import com.krp.whoknows.Navigation.FAB_KEY
import com.krp.whoknows.Navigation.HomeScreen
import com.krp.whoknows.Navigation.LoginScreen
import com.krp.whoknows.Navigation.PhoneScreen
import com.krp.whoknows.RiveComponents.ComposableRiveAnimationView
import com.krp.whoknows.Utils.ExpandableLogoutFAB
import com.krp.whoknows.Utils.calculateAge
import com.krp.whoknows.Utils.convertDrawableToBase64
import com.krp.whoknows.Utils.convertImageUrlToBase64
import com.krp.whoknows.Utils.createChatRoomId
import com.krp.whoknows.Utils.getLocationCityState
import com.krp.whoknows.Utils.times
import com.krp.whoknows.Utils.transform
import com.krp.whoknows.roomdb.entity.MatchFcmEntity
import com.krp.whoknows.ui.theme.lightOrdColor
import com.krp.whoknows.ui.theme.ordColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQueries.localDate
import kotlin.toString


/**
 * Created by KUSHAL RAJ PAREEK on 03,February,2025
 */

const val DEFAULT_PADDING = 45

@RequiresApi(Build.VERSION_CODES.S)
private fun getRenderEffect(): RenderEffect {
    val blurEffect = RenderEffect
        .createBlurEffect(80f, 80f, Shader.TileMode.MIRROR)

    val alphaMatrix = RenderEffect.createColorFilterEffect(
        ColorMatrixColorFilter(
            ColorMatrix(
                floatArrayOf(
                    1f, 0f, 0f, 0f, 0f,
                    0f, 1f, 0f, 0f, 0f,
                    0f, 0f, 1f, 0f, 0f,
                    0f, 0f, 0f, 50f, -5000f
                )
            )
        )
    )

    return RenderEffect
        .createChainEffect(alphaMatrix, blurEffect)
}


@ExperimentalSharedTransitionApi
@Composable
fun SharedTransitionScope.HomeScreen(
    videoUri : Uri,
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navController: NavController,
    onFabClick: ()-> Unit,
    onProfileClick:()-> Unit,
    onChatClick:()-> Unit,
    greetingViewModel: GreetingViewModel,
    updateMatchViewModel:UpdateMatchViewModel,
    profileDetailViewModel : ProfileDetailViewModel,
    matchUserViewModel : MatchUserViewModel,
    infoViewModel: InfoViewModel,
    state: UpdateMatchState,
    chatViewModel: ChatViewModel,
    mainImageViewModel : MainImageViewModel,
    event :(UpdateMatchEvent) -> Unit){
    val context = LocalContext.current
//    val exoPlayer = remember { context.buildExoPlayer(videoUri) }
//
//    DisposableEffect(
//        AndroidView(
//            factory = {it.buildPlayerView(exoPlayer)},
//            modifier = Modifier.fillMaxSize()
//        )
//    ) {
//        onDispose {
//            exoPlayer.release()
//        }
//    }
    val coroutineScope = rememberCoroutineScope()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val mUser by matchUserViewModel.matchUserState.collectAsState()
    var GoOn by remember { mutableStateOf(false) }
//    val callIt by remember { mutableStateOf(false) }
    val fcmToken by greetingViewModel.matchFcmToken.collectAsState()




    LaunchedEffect(mUser) {

        val m_id : String? = mUser?.id
        if(m_id != null){
            Log.d("optinmize",m_id)


            coroutineScope.launch(Dispatchers.IO) {

                val id = createChatRoomId(profileDetailViewModel.id.value, m_id)

                val messagesDeferred = async { chatViewModel.getMessages(chatRoomID = id) }
                val statusDeferred = async { matchUserViewModel.getStatus(id) }
                val updateDeferred = async { matchUserViewModel.updateClicked(accid = id, id = profileDetailViewModel.id.value) }

                messagesDeferred.await()
                statusDeferred.await()
                updateDeferred.await()
                delay(200)
                Log.d("adasdasdasdasdasdsadsadasd",fcmToken.toString())
                coroutineScope.launch {
                    val response = greetingViewModel.getToken(m_id)
                    Log.d("asdddddddddddddddddd",response.toString()
                    )
                    if(response.statusCode == 200){
                        greetingViewModel.saveMatchFcm(MatchFcmEntity(id = 1, fcm_token = response.token!!))
                        matchUserViewModel.updateFcm(response.token!!)
                        Log.d("adasdasdasdasdasdsssdsdsdsadsadasd",response.token!!)
                    }
                }
            }
        }
    }

    LaunchedEffect(GoOn) {
        Log.d("okokokokokokokoko","yesssss call")

        if(GoOn == true){
            Log.d("okokokokokokokoko","yes call")

            coroutineScope.launch {
                val response = greetingViewModel.getToken(matchUserViewModel.id.value)
                Log.d("asdddddddddddddddddd",response.toString()
                )
                if(response.statusCode == 200){
                    greetingViewModel.saveMatchFcm(MatchFcmEntity(id = 1, fcm_token = response.token!!))
                    matchUserViewModel.updateFcm(response.token!!)
                    Log.d("adasdasdasdasdasdsssdsdsdsadsadasd",response.token!!)

                }
            }
            val id = createChatRoomId(profileDetailViewModel.id.value, matchUserViewModel.id.value)

            val messagesDeferred = async { chatViewModel.getMessages(chatRoomID = id) }
            val statusDeferred = async { matchUserViewModel.getStatus(id) }
            val updateDeferred = async { matchUserViewModel.updateClicked(accid = id, id = profileDetailViewModel.id.value) }

            messagesDeferred.await()
            statusDeferred.await()
            updateDeferred.await()
            Log.d("okokokokokokokoko","yes called")
        }
    }


    Log.d("adasdasdjnfasjvnsjkv",state.toString())
    LaunchedEffect(state.isLoading) {
        if(state.isSuccess){
            Log.d("inhomescreen",state.statusCode.toString())
            if(!updateMatchViewModel.called.value){
                val res = state.statusCode

                if(res == 200){
                    profileDetailViewModel.updateMatch(true)
                    val matchUser = state.user
                    Log.d("adsdasdasdsadasdasdas",matchUser.toString())
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
                    if(matchUser?.imgUrl == null){
                        mainImageViewModel.saveMatchProfileImage(convertDrawableToBase64(context = context,if(matchUser?.gender == "MALE")  R.drawable.bp_img_placeholder else R.drawable.bp_img_placeholder))

                    }else{
                        mainImageViewModel.saveMatchProfileImage(convertImageUrlToBase64(matchUser?.imgUrl.toString()))

                    }
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

//                    val id = createChatRoomId(profileDetailViewModel.id.value, matchUserViewModel.id.value)


                    if(mUser?.id == null){
                        Log.d("okokokokokokokoko","no call")
                        Log.d("notoptimize","yesthis")

                        GoOn = true
                    }
                    profileDetailViewModel.updateInWait(id = profileDetailViewModel.id.value)
//                    val id = createChatRoomId(profileDetailViewModel.id.value,matchUserViewModel.id.value)
//                    chatViewModel.getMessages(chatRoomID = id)
//                    matchUserViewModel.getStatus(id)
//                    matchUserViewModel.updateClicked(accid = id, id =profileDetailViewModel.id.value)
                }else if(res == 204){
                    profileDetailViewModel.updateMatch(false)
                }
                updateMatchViewModel.updateCalled(false)
            }
        }
    }




    LaunchedEffect(currentBackStackEntry) {
        if(!updateMatchViewModel.called.value){
            Log.d("afasfaf","${profileDetailViewModel.id.value}, ${profileDetailViewModel.jwt.value}")
            event(UpdateMatchEvent.UpdateMatch(profileDetailViewModel.id.value,profileDetailViewModel.jwt.value))
        }
    }

    val isMenuExtended = remember { mutableStateOf(false) }

    val fabAnimationProgress by animateFloatAsState(
        targetValue = if (isMenuExtended.value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        )
    )

    val clickAnimationProgress by animateFloatAsState(
        targetValue = if (isMenuExtended.value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 400,
            easing = LinearEasing
        )
    )


    var animation: RiveAnimationView? = null
    var animation1: RiveAnimationView? = null
    var clicked by remember { mutableStateOf(false) }

    LaunchedEffect(clicked) {
        if(!clicked){
            animation?.reset()
        }else{
            Log.d("afsfafasfsafas","fasasfas")
            animation?.setBooleanState(
                "GirlState", "MouseOver",
                clicked)
//            animation?.setNumberState(
//                "GirlState", "Look_up",
//                value = 1f
//            )
        }
    }
    val renderEffect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        getRenderEffect().asComposeRenderEffect()
    } else {
        null
    }


    ComposableRiveAnimationView(modifier = Modifier,animation = R.raw.phone_girl,
        stateMachineName = "GirlState"){view ->
        animation = view
    }



    MainScreen(
        modifier = modifier.sharedBounds(
            sharedContentState = rememberSharedContentState(key = FAB_KEY),
            animatedVisibilityScope = animatedVisibilityScope
        ),
        renderEffect = renderEffect,
        fabAnimationProgress = fabAnimationProgress,
        clickAnimationProgress = clickAnimationProgress,
        onFabClick = onFabClick,
        onProfileClick = onProfileClick,
        onChatClick = onChatClick,
clicked = clicked,
        onToggle = {
            clicked = !clicked
        }
    ) {
        isMenuExtended.value = isMenuExtended.value.not()
    }

    ExpandableLogoutFAB{
       coroutineScope.launch {
           greetingViewModel.deleteUsers()
           greetingViewModel.deleteMatchUser()
//           greetingViewModel.deleteFcm()
           greetingViewModel.deleteMatchFcm()
           infoViewModel.resetData()
       }
        navController.popBackStack()
        navController.navigate(LoginScreen)

    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable

fun AnimatedFab1(
    modifier: Modifier,
    icon: ImageVector? = null,
    opacity: Float = 1f,
    backgroundColor: Color = MaterialTheme.colors.secondary,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
        containerColor = backgroundColor,
        modifier = modifier.scale(1.1f)
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = Color.White.copy(alpha = opacity)
            )
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier,
    renderEffect: androidx.compose.ui.graphics.RenderEffect?,
    fabAnimationProgress: Float = 0f,
    clickAnimationProgress: Float = 0f,
    onToggle: () -> Unit,
    clicked :Boolean,
    onFabClick: () -> Unit,
    onProfileClick:()-> Unit,
    onChatClick:()-> Unit,
    toggleAnimation: () -> Unit = { },
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.BottomCenter
    ) {


        Circle(
            color = MaterialTheme.colors.primary.copy(alpha = 0.5f),
            animationProgress = 0.5f
        )

        FabGroup(modifier = Modifier,renderEffect = renderEffect,   onToggle = onToggle, animationProgress = fabAnimationProgress)
        FabGroup(
           onFabClick= onFabClick,
            onProfileClick = onProfileClick,
            onChatClick = onChatClick,
            modifier = modifier,
            renderEffect = null,
            onToggle = onToggle,
            animationProgress = fabAnimationProgress,
            toggleAnimation = toggleAnimation
        )
        Circle(
            color = Color.White,
            animationProgress = clickAnimationProgress
        )
    }


}

@Composable
fun Circle(color: Color, animationProgress: Float) {
    val animationValue = sin(PI * animationProgress).toFloat()

    Box(
        modifier = Modifier
            .padding(DEFAULT_PADDING.dp)
            .size(56.dp)
            .scale(2 - animationValue)
            .border(
                width = 2.dp,
                color = color.copy(alpha = color.alpha * animationValue),
                shape = CircleShape
            )
    )
}



@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FabGroup(
    modifier: Modifier,
    animationProgress: Float = 0f,
    renderEffect: androidx.compose.ui.graphics.RenderEffect? = null,
    onFabClick: (() -> Unit)? = null,
    onProfileClick:(() -> Unit)? = null,
    onChatClick:(() -> Unit)? = null,
    onToggle :() -> Unit,
    toggleAnimation: () -> Unit = { }
) {
    Box(
        Modifier
            .fillMaxSize()
            .graphicsLayer { this.renderEffect = renderEffect }
            .padding(bottom = DEFAULT_PADDING.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        AnimatedFab1(
            backgroundColor = ordColor,
            icon = Icons.Default.ChatBubble,
            modifier = Modifier
                .padding(
                    PaddingValues(
                        bottom = 72.dp,
                        end = 210.dp
                    ) * FastOutSlowInEasing.transform(0f, 0.8f, animationProgress)
                ),
            opacity = LinearEasing.transform(0.2f, 0.7f, animationProgress)
        ){
            if (onChatClick != null) {
                onChatClick()
            }
        }

       AnimatedFab1(
           backgroundColor = ordColor,
            icon = Icons.Default.Search,
            modifier = modifier.padding(
                PaddingValues(
                    bottom = 88.dp,
                ) * FastOutSlowInEasing.transform(0.1f, 0.9f, animationProgress)
            ),
            opacity = LinearEasing.transform(0.3f, 0.8f, animationProgress)
        ){
           if (onFabClick != null) {
               onFabClick()
           }
       }

        AnimatedFab1(
            backgroundColor = ordColor,
            icon = Icons.Default.Person,
            modifier = Modifier.padding(
                PaddingValues(
                    bottom = 72.dp,
                    start = 210.dp
                ) * FastOutSlowInEasing.transform(0.2f, 1.0f, animationProgress)
            ),
            opacity = LinearEasing.transform(0.4f, 0.9f, animationProgress)
        ){
            if (onProfileClick != null) {
                onProfileClick()
            }
        }

        AnimatedFab(
            backgroundColor = ordColor,
            modifier = Modifier
                .scale(1f - LinearEasing.transform(0.5f, 0.85f, animationProgress)),
        )

        AnimatedFab(
            icon = Icons.Default.FavoriteBorder,
            modifier = Modifier
                .rotate(
                    225 * FastOutSlowInEasing
                        .transform(0.35f, 0.65f, animationProgress)
                ),
            onClick = { toggleAnimation()
                onToggle()},
            backgroundColor = Color.Transparent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedFab(
    modifier: Modifier,
    icon: ImageVector? = null,
    opacity: Float = 1f,
    backgroundColor: Color = MaterialTheme.colors.secondary,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
        containerColor = backgroundColor,
        modifier = modifier.scale(1.1f)
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = Color.White.copy(alpha = opacity)
            )
        }
    }
}



//@Composable
//@Preview(device = "id:pixel_4a", showBackground = true, backgroundColor = 0xFF3A2F6E)
//private fun MainScreenPreview() {
//    WhoknowsTheme {
//        com.krp.whoknows.Navigation.HomeScreen(navController = rememberNavController())
//    }
//}

//
//private fun Context.buildExoPlayer(uri: Uri) =
//    ExoPlayer.Builder(this).build().apply {
//        setMediaItem(MediaItem.fromUri(uri))
//        repeatMode = Player.REPEAT_MODE_ALL
//        playWhenReady = true
//        prepare()
//    }
//
//private fun Context.buildPlayerView(exoPlayer: ExoPlayer) =
//    StyledPlayerView(this).apply {
//        player = exoPlayer
//        layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
//        useController = false
//        resizeMode = RESIZE_MODE_ZOOM
//    }



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
import com.krp.whoknows.Appui.GreetingScreen.Presentation.GreetingViewModel
import com.krp.whoknows.Appui.Profile.presentation.ProfileDetailViewModel
import com.krp.whoknows.Appui.Profile.presentation.UpdateMatchEvent
import com.krp.whoknows.Appui.Profile.presentation.UpdateMatchState
import com.krp.whoknows.Appui.Profile.presentation.UpdateMatchViewModel
import com.krp.whoknows.Appui.Profile.presentation.UpdateUserEvent
import com.krp.whoknows.Navigation.FAB_KEY
import com.krp.whoknows.Navigation.HomeScreen
import com.krp.whoknows.RiveComponents.ComposableRiveAnimationView
import com.krp.whoknows.Utils.times
import com.krp.whoknows.Utils.transform
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQueries.localDate


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
    state: UpdateMatchState,
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
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

Log.d("adasdasdjnfasjvnsjkv",state.toString())
    LaunchedEffect(state.isLoading) {
        if(state.isSuccess){
            Log.d("inhomescreen",state.statusCode.toString())
            if(!updateMatchViewModel.called.value){
                val res = state.statusCode
                if(res == 200){
                    profileDetailViewModel.updateMatch(true)
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
        CustomBottomNavigation()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomNavigation() {
//    Row(
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier
//            .height(80.dp)
//            .paint(
//                painter = painterResource(R.drawable.bottom_navigation),
//                contentScale = ContentScale.FillHeight
//            )
//            .padding(horizontal = 40.dp)
//    ) {
//        listOf(Icons.Filled.ChatBubble, Icons.Filled.Person2).map { image ->
//            IconButton(onClick = {
//
//            }) {
//                androidx.wear.compose.material.Icon(imageVector = image, contentDescription = null, tint = Color.White)
//            }
//        }
//    }
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
            modifier = Modifier
                .scale(1f - LinearEasing.transform(0.5f, 0.85f, animationProgress)),
        )

        AnimatedFab(
            icon = Icons.Default.Add,
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



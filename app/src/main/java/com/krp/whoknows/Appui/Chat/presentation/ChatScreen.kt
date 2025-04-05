package com.krp.whoknows.Appui.Chat.presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.colorspace.TransferParameters
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.room.Database
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.SwipeToDismissBox
import androidx.wear.compose.material.Text
import androidx.xr.compose.testing.toDp
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.core.Fit
import app.rive.runtime.kotlin.core.Loop
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.insets.statusBarsPadding
import com.google.common.collect.Multimaps.index
import com.krp.whoknows.Appui.Chat.components.MessageBox
import com.krp.whoknows.Appui.Chat.components.MessageInputField
import com.krp.whoknows.Appui.GreetingScreen.Presentation.GreetingViewModel
import com.krp.whoknows.Appui.MatchingScreen.presentation.MatchUserViewModel
import com.krp.whoknows.Appui.Profile.presentation.ImageViewModel
import com.krp.whoknows.Appui.Profile.presentation.MainImageViewModel
import com.krp.whoknows.Appui.Profile.presentation.ProfileDetailViewModel
import com.krp.whoknows.Navigation.ChatScreen
import com.krp.whoknows.Navigation.HomeScreen
import com.krp.whoknows.R
import com.krp.whoknows.RiveComponents.ComposableRiveAnimationView
import com.krp.whoknows.Utils.DotsWaveAnimation
import com.krp.whoknows.Utils.MyAlertDialog
import com.krp.whoknows.Utils.MyAlertDialogAcc
import com.krp.whoknows.Utils.MyAlertDialogDel
import com.krp.whoknows.Utils.MyAlertDialogWait
import com.krp.whoknows.Utils.createChatRoomId
import com.krp.whoknows.Utils.downloadImageFromUrl
import com.krp.whoknows.Utils.drawableToBitmap
import com.krp.whoknows.Utils.formatDate
import com.krp.whoknows.Utils.generateUniqueFileName
import com.krp.whoknows.model.Message
import com.krp.whoknows.model.NotificationModel
import com.krp.whoknows.roomdb.Dao
import com.krp.whoknows.roomdb.DataBase
import com.krp.whoknows.roomdb.ImageConverter.base64ToBitmap
import com.krp.whoknows.roomdb.ImageConverter.uriToBase64
import com.krp.whoknows.roomdb.UserRepository
import com.krp.whoknows.ui.theme.Bpink
import com.krp.whoknows.ui.theme.Cyan
import com.krp.whoknows.ui.theme.Lgreen
import com.krp.whoknows.ui.theme.Purple
import com.krp.whoknows.ui.theme.background_white
import com.krp.whoknows.ui.theme.chat_dark
import com.krp.whoknows.ui.theme.chat_light
import com.krp.whoknows.ui.theme.lightOrdColor
import com.krp.whoknows.ui.theme.light_yellow
import com.krp.whoknows.ui.theme.ordColor
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URI

/**
 * Created by KUSHAL RAJ PAREEK on 06,March,2025
 */


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class,
    ExperimentalLayoutApi::class, ExperimentalFoundationApi::class
)
@SuppressLint(
    "UnrememberedMutableState", "StateFlowValueCalledInComposition",
    "SuspiciousIndentation", "AutoboxingStateCreation"
)
@Composable
fun SharedTransitionScope.ChatScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    matchUserViewModel: MatchUserViewModel,
    userDetailViewModel: ProfileDetailViewModel,
    state: SendMessageState,
    chatState: ChatState,
    imageViewModel: ImageViewModel,
    event: (ChatEvent) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    mainImageViewModel: MainImageViewModel,
    chatViewModel: ChatViewModel,
    greetingViewModel: GreetingViewModel
) {

    var showSplash by remember { mutableStateOf(true) }
    var backstack by remember { mutableStateOf(true) }
    val visited by matchUserViewModel.vis.collectAsState()
    Log.d("loginging", visited.toString())



    BackHandler(true) {
        if (backstack) {
            matchUserViewModel.updateVis(true)
            Log.d("backstackes", "asfasdfda")
            navController.popBackStack()
            navController.navigate(HomeScreen)
        }
    }
    LaunchedEffect(Unit) {
        if (visited == false) {
            Log.d("askfjaskfklsamfkasf", "jfaklsjflksahflhas")
            delay(2000)
            showSplash = false
        } else {
            showSplash = false
        }
    }
    if (showSplash) {
        AnimatedVisibility(
            visible = showSplash,
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        lightOrdColor
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                ) {
                    AndroidView(
                        modifier = modifier,
                        factory = { context ->
                            RiveAnimationView(context).also {
                                it.setRiveResource(
                                    resId = R.raw.cat,
                                    alignment = app.rive.runtime.kotlin.core.Alignment.CENTER,
                                    fit = Fit.CONTAIN,
                                    loop = Loop.LOOP
                                )
                            }
                        }
                    )
                    Spacer(modifier.height(10.dp))
                    Text(
                        modifier = modifier.padding(horizontal = 40.dp),
                        text = "We are connecting, please wait a moment.",
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                        fontSize = 15.sp
                    )
                }
            }
        }
    } else {
        if (userDetailViewModel.isMatch.value == false) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                ) {
                    AndroidView(
                        modifier = modifier,
                        factory = { context ->
                            RiveAnimationView(context).also {
                                it.setRiveResource(
                                    resId = R.raw.cat,
                                    alignment = app.rive.runtime.kotlin.core.Alignment.CENTER,
                                    fit = Fit.CONTAIN,
                                    loop = Loop.LOOP
                                )
                            }
                        }
                    )
                    Spacer(modifier.height(10.dp))
                    Text(
                        modifier = modifier.padding(horizontal = 40.dp),
                        text = "Oops! The connection isn’t complete yet.\nKeep exploring!",
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                        fontSize = 15.sp
                    )
                }
            }
        } else {


            val context = LocalContext.current
            var bimage by remember { mutableStateOf<Bitmap?>(null) }
            val listState = rememberLazyListState()
            var isCrossVisible by remember { mutableStateOf(false) }
            var cimage by remember { mutableStateOf<Uri?>(null) }
            var id by remember { mutableStateOf<String?>(null) }

            var showDialog by remember { mutableStateOf(false) }
            val coroutineScope = rememberCoroutineScope()

            var imageUrl by remember { mutableStateOf<String?>(null) }
            var isdownload by remember { mutableStateOf(false) }
            var showAccDialog by remember { mutableStateOf(false) }
            var showDelDialog by remember { mutableStateOf(false) }
            var showWaitDialog by remember { mutableStateOf(false) }

            val imeIsShown = WindowInsets.isImeVisible
val updateValue by matchUserViewModel.updation.collectAsState()
            val targetBottomPadding = if (imeIsShown) 0.dp else contentPadding().calculateBottomPadding()
            val bottomPadding by animateDpAsState(
                targetValue = targetBottomPadding,
                animationSpec = tween(durationMillis = 300)
            )

            var mId by remember { mutableStateOf<String?>(null) }

            val inWait by userDetailViewModel.inWait.collectAsState()

            val chatId = createChatRoomId(userDetailViewModel.id.value, matchUserViewModel.id.value)

val acc by matchUserViewModel.acceptStatus.collectAsState()
            var acc_status by remember { mutableIntStateOf(acc)}

//            LaunchedEffect(matchUserViewModel.acceptStatus) {
//                Log.d("inininnininininin",matchUserViewModel.acceptStatus.value.to)
//                matchUserViewModel.acceptStatus.collect { newValue ->
//                    acc_status = newValue
//                }
//            }
//

            val deleteChatState by chatViewModel.deleteState.collectAsState()

            val map by chatViewModel.map.collectAsState()

            val matchRM by chatViewModel.matchRemoved.collectAsState()


            val matchRMm by chatViewModel.chatRemoved.collectAsState()


            var showLottie by remember { mutableStateOf(false) }

            LaunchedEffect(chatState.messageList.size) {
                if (chatState.messageList.isNotEmpty()) {
                    listState.scrollToItem(chatState.messageList.size - 1)
                }
            }


            Log.d("hereititititiititit", matchRM.toString())
            Log.d("hereititititiititit1", matchRMm.toString())

            Log.d("asddddddddddddddethythythy", acc.toString())
//LaunchedEffect(chatViewModel.map.value) {
//    map = chatViewModel.map.value.toMutableMap()
//}

            LaunchedEffect(matchRM) {
                if (matchRM == true) {
                    Log.d("byrrmsssssssss1", matchRM.toString())
                    matchUserViewModel.updateAcceptStatus(0)
                    chatViewModel.removeAcc(chatId)
                    matchUserViewModel.clearAll()
                    mainImageViewModel.clearMatch()
                    matchUserViewModel.deleteUser()

                    delay(1000)



                    navController.popBackStack()
                    navController.navigate(HomeScreen)

                    delay(500)
                    chatViewModel.updateMatchRm()
                    chatViewModel.updateMatchRmm()
                    userDetailViewModel.updateMatch(false)
                    userDetailViewModel.updateMatch(false)
                    matchUserViewModel.updateMatch(false)
                    deleteChatState.isSuccess = false
                    deleteChatState.statusCode = 500
                }
            }

            LaunchedEffect(matchRMm) {
                if (matchRMm == chatId && matchRM == false) {
                    Log.d("byrrmsssssssss2", "m${matchRMm} ${matchRM}")
                    matchUserViewModel.updateAcceptStatus(0)
                    chatViewModel.removeAcc(chatId)
                    matchUserViewModel.clearAll()
                    mainImageViewModel.clearMatch()
                    matchUserViewModel.deleteUser()
                    delay(1000)
//            coroutineScope.launch {

//            }

                    showLottie = true
                    backstack = false
                    Log.d("byrrm", "yes")


                    navController.popBackStack()
                    navController.navigate(HomeScreen)

                    delay(500)
                    chatViewModel.updateMatchRm()
                    chatViewModel.updateMatchRmm()
                    userDetailViewModel.updateMatch(false)
                    userDetailViewModel.updateMatch(false)
                    matchUserViewModel.updateMatch(false)
                    deleteChatState.isSuccess = false
                    deleteChatState.statusCode = 500
                }
            }

//            LaunchedEffect(matchUserViewModel.acceptStatus.value) {
//                acc_status = matchUserViewModel.acceptStatus.value
//            }

            val statusState by chatViewModel.statusState.collectAsState()



            LaunchedEffect(statusState) {
                Log.d("changingstates", "yes")
                statusState?.let { status ->
                    if (status.chatId == chatId && updateValue == 0) {
                        Log.d("updatedcountbysocket", "hererere")
                        acc_status = status.count
                        matchUserViewModel.updateUpdation()
                        matchUserViewModel.updateAcceptStatus(status.count)
                    }
                }
            }

            LaunchedEffect(state.isLoading) {

                if (state.isSuccess) {
                    Log.d("messageaagaya", state.statusCode.toString())
                }
            }


            LaunchedEffect(deleteChatState.isLoading) {
                Log.d("hogayi", deleteChatState.statusCode.toString())
                if (deleteChatState.isSuccess && deleteChatState.statusCode == 200) {
                    chatViewModel.removeMatch(
                        id = userDetailViewModel.id.value,
                        jwt = userDetailViewModel.jwt.value
                    )
                    chatViewModel.sendRemoveStatus(chatId)
                }
            }




            val density = LocalDensity.current
            val imeInsets = WindowInsets.ime

            val imePadding by remember {
                derivedStateOf {
                    with(density) { imeInsets.getBottom(this).toDp() }
                }
            }


            val imeVisible = imeInsets.getBottom(density) > 0f



            LaunchedEffect(imeVisible, chatState.messageList) {
                if (imeVisible && chatState.messageList.isNotEmpty()) {
                    listState.animateScrollToItem(chatState.messageList.size - 1)
                }else if(imeVisible){
                    listState.animateScrollToItem(chatState.messageList.size)
                }
            }


            LaunchedEffect(key1 = map[chatId]?.contains(matchUserViewModel.id.value)) {
                val isTyping = map.contains(chatId) && map[chatId]?.contains(matchUserViewModel.id.value) == true
                if (isTyping) {
                    listState.animateScrollToItem(chatState.messageList.size)
                }
            }
            val isScrollingUp = remember { mutableStateOf(false) }

            LaunchedEffect(listState) {
                snapshotFlow { listState.firstVisibleItemIndex }
                    .collect { firstVisibleItemIndex ->
                        isScrollingUp.value = firstVisibleItemIndex > 0
                    }
            }

            Box(

                modifier = Modifier
//            .statusBarsPadding()
                    .fillMaxSize()
                    .background(lightOrdColor)
                    .padding(WindowInsets.statusBars.asPaddingValues())
            ) {


//                Column(modifier = Modifier.fillMaxSize()) {


                Box(
                    modifier = Modifier
//              .statusBarsPadding()
                        .fillMaxWidth()
                        .size(60.dp)
                        .background(lightOrdColor)
                        .zIndex(1f)
//                        .consumeWindowInsets(WindowInsets.ime.exclude(WindowInsets.safeDrawing))
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.CenterStart
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val gender = matchUserViewModel.gender.value ?: "MALE"
                        val profileImage = mainImageViewModel.matchprofileImage.value

                        val bitmapImage = if (acc_status == 2) {
                            profileImage?.asImageBitmap() ?: drawableToBitmap(
                                context,
                                if (gender == "MALE") R.drawable.bp_img_placeholder else R.drawable.p_img_placeholder
                            ).asImageBitmap()
                        } else {
                            drawableToBitmap(
                                context,
                                if (gender == "MALE") R.drawable.bp_img_placeholder else R.drawable.p_img_placeholder
                            ).asImageBitmap()
                        }

                        Image(
                            bitmap = bitmapImage,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .sharedElement(
                                    state = rememberSharedContentState(
                                        key = "image/${R.drawable.p_image}",
                                    ),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = 1000)
                                    }
                                )
                                .clip(CircleShape)
                                .size(50.dp)
                                .border(2.dp, ordColor, shape = CircleShape)
                                .clickable {
                                    if (acc_status == 2) {
                                        matchUserViewModel.updateVis(true)
                                        navController.navigate("matchProfileScreen")
                                    } else {
                                        DynamicToast.make(
                                            context,
                                            "Both concerns need to be addressed to view the profile",
                                            ContextCompat.getDrawable(
                                                context,
                                                R.drawable.fulll_handshake
                                            )?.mutate(),
                                            Color.White.toArgb(),
                                            lightOrdColor.toArgb()
                                        ).show()
                                    }
                                }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = matchUserViewModel.username.value,
                            fontSize = 20.sp,
                            modifier = Modifier,
                            fontFamily = FontFamily(Font(R.font.vesperlibre_medium))
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {


                        val handshake = if (acc_status == 0) {
                            R.drawable.empty_handshake
                        } else if (acc_status == 1) {
                            R.drawable.half_handshake
                        } else {
                            R.drawable.fulll_handshake
                        }
                        Image(
                            painter = painterResource(R.drawable.breakup),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    showDelDialog = true
                                })

                        Spacer(modifier = Modifier.width(10.dp))
                        Image(
                            painter = painterResource(handshake),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    if (matchUserViewModel.clicked.value == false) {
                                        showAccDialog = true
                                    } else {
                                        DynamicToast.make(
                                            context, "You have already taken your side step",
                                            ContextCompat.getDrawable(
                                                context,
                                                R.drawable.half_circle
                                            )?.mutate(), Color.White.toArgb(),
                                            lightOrdColor.toArgb()
                                        ).show()

                                    }
                                })

                        Spacer(modifier = Modifier.width(10.dp))

                        Image(
                            painter = painterResource(R.drawable.ping_pong),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {

                                        coroutineScope.launch {
                                            val responses =
                                                userDetailViewModel.getInWait(userDetailViewModel.id.value)
                                            if (responses == 404) {
                                                showWaitDialog = true
                                            }else{
                                                DynamicToast.make(
                                                    context,
                                                    "You need to wait for 5 minutes before making another request.",
                                                    ContextCompat.getDrawable(context, R.drawable.clock)
                                                        ?.mutate(),
                                                    Color.White.toArgb(),
                                                    lightOrdColor.toArgb()
                                                ).show()
                                            }


                                    }
                                }
                        )
                    }
                }


//                    Box(modifier = Modifier
//                        .fillMaxSize()
//                       ){
                Image(
                    painter = painterResource(id = R.drawable.chat_background),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(alpha = 0.2f),
                    contentScale = ContentScale.Crop
                )
//
//                        Box(modifier = Modifier.fillMaxSize()
//                         ){

                Column(
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .fillMaxSize()
                        .padding(top = 60.dp)
                        .imePadding()
//                        .animateContentSize()
                ) {


//                    Spacer(modifier = Modifier.height(10.dp))


                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
//                            .animateContentSize()
//                            .padding(bottom = if (imeVisible) 5.dp else 0.dp)
//                            .padding(bottom = if (isScrollingUp.value) 0.dp else imePadding)

//                                        .windowInsetsPadding(WindowInsets.ime)
//                                        .padding(bottom = imePadding)
//                            .imePadding()

                                .weight(1f),
                            state = listState,
                            reverseLayout = false,
//                        verticalArrangement = Arrangement.Bottom
                        ) {
                            Log.d("listrgfaasdfa", chatState.messageList.toString())
                            itemsIndexed(chatState.messageList) { index, message ->

                                val isLastMessage = index == chatState.messageList.size - 1
                                val bottomPadding = if (isLastMessage) 5.dp else 0.dp

                                val currentDate = message.timeStamp!!.substring(0, 10)

                                val shouldShowDateHeader = index == 0 ||
                                        chatState.messageList[index - 1].timeStamp!!.substring(
                                            0,
                                            10
                                        ) != currentDate

                                if (shouldShowDateHeader) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = formatDate(currentDate),
                                            color = background_white,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }


                                if (message.message != "deleted") {
                                    var dismissState = rememberSwipeToDismissBoxState()
                                    Log.d("heiehefhjdnkfsaf", message.toString())
                                    val canSwipeRight = message.imgUrl != null
                                    dismissState = rememberSwipeToDismissBoxState(
                                        confirmValueChange = {
                                            if (it == SwipeToDismissBoxValue.EndToStart && message.senderId == matchUserViewModel.id.value) {
                                                false
                                            } else if (it == SwipeToDismissBoxValue.StartToEnd && message.senderId == userDetailViewModel.id.value) {
                                                false
                                            } else if (it == SwipeToDismissBoxValue.StartToEnd && !canSwipeRight) {
                                                false
                                            } else if (it == SwipeToDismissBoxValue.EndToStart) {
                                                coroutineScope.launch {
                                                    dismissState.reset()
                                                }
                                                mId = message._id!!
                                                isdownload = false
                                                showDialog = true
                                                true
                                            } else if (it == SwipeToDismissBoxValue.StartToEnd) {
                                                coroutineScope.launch {
                                                    dismissState.reset()
                                                }
                                                imageUrl = message.imgUrl!!
                                                isdownload = true
                                                showDialog = true
                                                true
                                            } else {
                                                true
                                            }
                                        }
                                    )
                                    androidx.compose.material3.SwipeToDismissBox(
                                        state = dismissState,
                                        backgroundContent = {
                                            when (dismissState.targetValue) {
                                                SwipeToDismissBoxValue.EndToStart -> {
                                                    if (message.senderId == userDetailViewModel.id.value) {
                                                        Box(
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .padding(end = 16.dp),
                                                            contentAlignment = Alignment.CenterEnd
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Default.Delete,
                                                                contentDescription = "Delete",
                                                                tint = Color.Red,
                                                                modifier = Modifier.size(32.dp)
                                                            )

                                                        }
                                                    }
                                                }

                                                SwipeToDismissBoxValue.StartToEnd -> {
                                                    if (canSwipeRight && message.senderId == matchUserViewModel.id.value) {
                                                        Box(
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .padding(start = 16.dp),
                                                            contentAlignment = Alignment.CenterStart
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Default.Download,
                                                                contentDescription = "Download",
                                                                tint = Color.Green,
                                                                modifier = Modifier.size(32.dp)
                                                            )
                                                        }
                                                    }
                                                }

                                                else -> {}
                                            }
                                        }
                                    ) {
                                        MessageBox(
                                            padding = bottomPadding,
                                            message = message,
                                            profileDetailViewModel = userDetailViewModel,
                                        )
                                    }
                                } else {
                                    MessageBox(
                                        padding = bottomPadding,
                                        message = message,
                                        profileDetailViewModel = userDetailViewModel
                                    )
                                }

                                if (index == chatState.messageList.lastIndex) {
                                    if (map.contains(chatId) && map[chatId]?.contains(
                                            matchUserViewModel.id.value
                                        ) == true
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        ) {
                                            DotsWaveAnimation(chat_dark)
//                                            Spacer(modifier = Modifier.height(7.dp))
                                        }
                                    }
                                }

                            }

                        }


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
//                            .animateContentSize()
//                            .imePadding()
//                            .windowInsetsPadding(WindowInsets.ime)
                    ) {
                        MessageInputField(
                            chatId = chatId,
                            userId = userDetailViewModel.id.value,
                            chatViewModel = chatViewModel,
                            cimage = cimage,
                            setBimage = { bimage = it },
                            setUri = { cimage = it }) { message ->
                            var imgStr: String? = null;

                            if (bimage != null) {
                                id = generateUniqueFileName()

                                val localUri = cimage
                                localUri?.let {
                                    imgStr = uriToBase64(context, localUri)

                                }
                                Log.d("uriisherersss", localUri.toString())
                                coroutineScope.launch {
                                    Log.d("insideuri", localUri.toString())
                                    if (localUri != null) {
                                        imageViewModel.saveChatImagetoSupabase(
                                            context = context,
                                            uri = localUri,
                                            id = id!!
                                        )
                                    } else {
                                        Log.e(
                                            "UploadError",
                                            "Image URI is null inside coroutine"
                                        )
                                    }
                                }
                            }


                            Log.d("uriisherersss", cimage.toString())
                            event(
                                ChatEvent.SendMessage(
                                    Message(
                                        senderId = userDetailViewModel.id.value,
                                        receiverId = matchUserViewModel.id.value,
                                        message = message,
                                        imgUrl = if (cimage != null) "https://dtgatrenwhgxvicpbxre.supabase.co/storage/v1/object/public/chat-images//${id}.jpg" else null,
                                        imgStr = imgStr
                                    )

                                )
                            )
                            coroutineScope.launch {
                                val notification = NotificationModel(
                                    fcmToken = matchUserViewModel.fcmToken.value,
                                    title = matchUserViewModel.username.value,
                                    body = message.toString(),
                                    type = "chat",
                                    imgUrl = "https://dtgatrenwhgxvicpbxre.supabase.co/storage/v1/object/public/chat-images//${id}.jpg" ?: "no"
                                )

                                chatViewModel.sendNotification(notification)
                            }
                            bimage = null
                            cimage = null
                        }
                    }
                }
//                        }


                if (bimage != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .windowInsetsPadding(WindowInsets.ime)
                            .padding(
                                start = 20.dp,
                                bottom = if (imeVisible) 70.dp else 95.dp
                            )
                            .animateContentSize()
                            .clip(shape = RoundedCornerShape(16.dp))
                            .background(if (isCrossVisible) Bpink else ordColor)
                            .padding(7.dp)
                    ) {
                        Image(
                            bitmap = bimage!!.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(140.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {
                                            isCrossVisible = true
                                        },
                                        onTap = {
                                            if (isCrossVisible) {
                                                bimage = null
                                                cimage = null
                                                isCrossVisible = false
                                            }
                                        }
                                    )
                                },
                            contentScale = ContentScale.Crop
                        )
                    }


                }
//                    }
            }

            if (showAccDialog) {
                MyAlertDialogAcc(onDismiss = { showAccDialog = false }, onConfirm = {
                   coroutineScope.launch {
                       matchUserViewModel.updateStatus(
                           id = chatId,
                           count = matchUserViewModel.acceptStatus.value + 1,
                           userId = userDetailViewModel.id.value
                       )
                       acc_status = matchUserViewModel.acceptStatus.value + 1
Log.d("asdasdasdasdas","${matchUserViewModel.acceptStatus.value} $acc_status")
                       chatViewModel.sendCountUpdate(chatRoomId = chatId, count = acc_status)
                   }
                })
            }

            if (showDelDialog) {
                MyAlertDialogDel(onDismiss = { showDelDialog = false }, onConfirm = {
                  coroutineScope.launch {
                      chatViewModel.deleteChatRoom(chatId)
                      greetingViewModel.deleteMatchUser()
                      greetingViewModel.deleteMatchFcm()
                  }
                    showLottie = true
                    backstack = false
                })
            }


            if (showWaitDialog) {
                MyAlertDialogWait(onDismiss = { showWaitDialog = false }, onConfirm = {
                    coroutineScope.launch {
                        val response = chatViewModel.postWait(userDetailViewModel.id.value)
                        if (response == 200) {
                            val notification = NotificationModel(
                                fcmToken = matchUserViewModel.fcmToken.value,
                                title = matchUserViewModel.username.value,
                                body = "❤ Don't leave me hanging—start chatting please!",
                                type = "buzz",
                                imgUrl = "no"
                            )
                            chatViewModel.sendNotification(notification)
                            userDetailViewModel.updateInWaitInternal(flag = true)
                        }
                    }

                })
            }

            if (showDialog) {
                MyAlertDialog(
                    onDismiss = { showDialog = false }, isdownload, onConfirmD = {
                        downloadImageFromUrl(context = context, imageUrl = imageUrl!!)
                        isdownload = false
                    },
                    onConfirmDe = {
                        coroutineScope.launch {
                            event(ChatEvent.EditMessage(mId!!))
                        }
                    })
            }

            if (showLottie) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ordColor.copy(alpha = 0.4f))
                ) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.breakup))
                    LottieAnimation(
                        composition,
                        iterations = LottieConstants.IterateForever,
//                        modifier = Modifier.size(150.dp),
                        renderMode = RenderMode.HARDWARE
                    )
                }
            }
        }
    }
}
//}

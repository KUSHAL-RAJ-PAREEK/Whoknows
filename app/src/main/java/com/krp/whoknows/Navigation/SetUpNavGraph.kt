package com.krp.whoknows.Navigation

import android.R.attr.text
import android.content.Context
import android.icu.text.IDNA.Info
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.google.android.gms.maps.model.LatLng
import com.krp.whoknows.Appui.Chat.presentation.ChatScreen
import com.krp.whoknows.Appui.Chat.presentation.ChatViewModel
import com.krp.whoknows.Appui.GreetingScreen.Presentation.GreetingScreen
import com.krp.whoknows.Appui.GreetingScreen.Presentation.GreetingViewModel
import com.krp.whoknows.Appui.HomeScreen.presentation.HomeScreen
import com.krp.whoknows.Appui.MatchingScreen.presentation.CancelMatchViewModel
import com.krp.whoknows.Appui.MatchingScreen.presentation.CheckMatchState
import com.krp.whoknows.Appui.MatchingScreen.presentation.CheckMatchViewModel
import com.krp.whoknows.Appui.MatchingScreen.presentation.CreateMatchState
import com.krp.whoknows.Appui.MatchingScreen.presentation.CreateMatchViewModel
import com.krp.whoknows.Appui.MatchingScreen.presentation.MatchUserViewModel
import com.krp.whoknows.Appui.MatcjhUserProfile.presentation.MatchProfileScreen
import com.krp.whoknows.Appui.Profile.presentation.EditProfileComponents.EditBioScreen
import com.krp.whoknows.Appui.Profile.presentation.EditProfileComponents.EditProfileMapScreen
import com.krp.whoknows.Appui.Profile.presentation.EditProfileViewModel
import com.krp.whoknows.Appui.Profile.presentation.ImageViewModel
import com.krp.whoknows.Appui.Profile.presentation.MainImageViewModel
import com.krp.whoknows.Appui.Profile.presentation.ProfileDetailViewModel
import com.krp.whoknows.Appui.Profile.presentation.ProfileEditScreen
import com.krp.whoknows.Appui.Profile.presentation.ProfileScreen
import com.krp.whoknows.Appui.Profile.presentation.ProfileViewModel
import com.krp.whoknows.Appui.Profile.presentation.UpdateMatchViewModel
import com.krp.whoknows.Appui.Profile.presentation.UpdateUserViewModel
import com.krp.whoknows.Appui.userInfo.CreateUserViewModel
import com.krp.whoknows.Appui.userInfo.DOBScreen
import com.krp.whoknows.Appui.userInfo.InfoViewModel
import com.krp.whoknows.Appui.userInfo.InterestScreen
import com.krp.whoknows.Auth.LoginScreen.Presentation.LoginScreen
import com.krp.whoknows.Auth.OTPScreen.OTPVerificationViewModel
import com.krp.whoknows.Auth.PhoneScreen.Presentation.PhoneAuthViewModel
import com.krp.whoknows.Auth.PhoneScreen.Presentation.PhoneScreen
import com.krp.whoknows.Auth.WelcomeScreen.presentation.WelcomeScreen
import com.krp.whoknows.Utils.scaleIntoContainer
import com.krp.whoknows.Utils.scaleOutOfContainer
import com.krp.whoknows.model.LatLongs
import com.krp.whoknows.roomdb.ImageRepository
import com.krp.whoknows.roomdb.JWTViewModel
import com.krp.whoknows.ui.theme.ordColor
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import kotlin.math.log

/**
 * Created by KUSHAL RAJ PAREEK on 28,January,2025
 */


const val FAB_KEY = "FAB_KEY"

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SetUpNavGraph(modifier: Modifier = Modifier, startDest: Any) {

    val InfoViewModel: InfoViewModel = koinViewModel()
    val profileDetailViewModel: ProfileDetailViewModel = koinViewModel()
    val editProfileViewModel: EditProfileViewModel = koinViewModel()
    val jwtViewModel: JWTViewModel = koinViewModel()
    val imageViewModel: ImageViewModel = koinViewModel()
    val mainImageViewModel: MainImageViewModel = koinViewModel()
    val greetingViewModel: GreetingViewModel = koinViewModel()
    val updateMatchViewModel: UpdateMatchViewModel = koinViewModel()
    val checkMatchViewModel: CheckMatchViewModel = koinViewModel()
    val createMatchViewModel: CreateMatchViewModel = koinViewModel()
    val matchUserViewModel: MatchUserViewModel = koinViewModel()
    val cancelMatchViewModel: CancelMatchViewModel = koinViewModel()
    val chatViewModel: ChatViewModel = koinViewModel()

    SharedTransitionLayout {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = startDest
        ) {

            composable<WelcomeScreen> {
                WelcomeScreen(modifier = Modifier) { onLandingButtonClick(navController) }
            }

            composable<LoginScreen> {
                LoginScreen(modifier = Modifier) { onPhoneSlide(navController) }
            }

            composable<PhoneScreen> {
                val viewModel: PhoneAuthViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                PhoneScreen(
                    modifier = Modifier,
                    event = viewModel::onEvent,
                    state = state,
                    onOtpSent = { onPhoneVerify(navController, state.phoneNumber!!) }
                )
            }

            composable<OTPScreen> {
                var args = it.toRoute<OTPScreen>()
                val viewModel: OTPVerificationViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val jwtViewModel: JWTViewModel = koinViewModel()
                com.krp.whoknows.Auth.OTPScreen.OTPScreen(
                    modifier = Modifier,
                    event = viewModel::onEvent,
                    state = state,
                    jwtViewModel,
                    navController = navController,
                    phoneNumber = args.phoneNumber,
                    profileDetailViewModel = profileDetailViewModel,
                    onOTp = { onOTp(navController) },
                    greetingViewModel = greetingViewModel
                )
            }

            composable<PreferredGender> {
                com.krp.whoknows.Appui.userInfo.PreferredGender(
                    viewModel = InfoViewModel,
                    navController = navController
                )
            }

            composable<DOBScreen> {
                com.krp.whoknows.Appui.userInfo.DOBScreen(
                    viewModel = InfoViewModel,
                    navController = navController
                )
            }
            composable<UserGender> {
                com.krp.whoknows.Appui.userInfo.UserGender(
                    viewModel = InfoViewModel,
                    navController = navController
                )
            }
            composable<PreferredAgeRange> {
                com.krp.whoknows.Appui.userInfo.PreferredAgeRange(
                    viewModel = InfoViewModel,
                    navController = navController
                )
            }
            composable<GeoRadiusRange> {
                com.krp.whoknows.Appui.userInfo.GeoRadiusRange(
                    viewModel = InfoViewModel,
                    navController = navController
                )
            }

            composable<LatLong> {
                val userViewModel: CreateUserViewModel = koinViewModel()
                val state by userViewModel.state.collectAsStateWithLifecycle()
                val args = it.toRoute<LatLong>()
                com.krp.whoknows.Appui.userInfo.LatLong(
                    viewModel = InfoViewModel,
                    greetingViewModel = greetingViewModel,
                    event = userViewModel::onEvent,
                    state = state,
                    jwtViewModel = jwtViewModel,
                    navController = navController,
                    latLong = LatLongs(
                        args.latitude ?: "",
                        args.longitude ?: ""
                    )
                )
            }

            composable<MapScreen> {
                com.krp.whoknows.Appui.userInfo.MapScreen(
                    navController = navController,
                    context = LocalContext.current
                )
            }

            composable<InterestScreen>{
                InterestScreen(
                    infoViewModel = InfoViewModel,
                    navController = navController,
                )
            }

            composable<HomeScreen> {
                val state by updateMatchViewModel.state.collectAsStateWithLifecycle()
                HomeScreen(
                    videoUri = getVideoUri(),
                    navController = navController, animatedVisibilityScope = this, onFabClick = {
                        navController.navigate(MatchingScreen)
                    },
                    onProfileClick = {
                        navController.navigate("profileScreen")
                    },
                    onChatClick = {
                        navController.navigate("chatScreen")
                    },
                    greetingViewModel = greetingViewModel,
                    updateMatchViewModel = updateMatchViewModel,
                    profileDetailViewModel = profileDetailViewModel,
                    matchUserViewModel = matchUserViewModel,
                    mainImageViewModel = mainImageViewModel,
                    chatViewModel = chatViewModel,
                    infoViewModel = InfoViewModel,
                    state = state,
                    event = updateMatchViewModel::onEvent
                )
            }

            composable<MatchingScreen> {
                val match_state by createMatchViewModel.state.collectAsStateWithLifecycle()
                val check_state by checkMatchViewModel.state.collectAsStateWithLifecycle()
                val cancel_state by cancelMatchViewModel.state.collectAsStateWithLifecycle()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ordColor)
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = FAB_KEY
                            ),
                            animatedVisibilityScope = this
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    com.krp.whoknows.Appui.MatchingScreen.presentation
                        .MatchingScreen(
                            check_state = check_state,
                            match_state = match_state,
                            cancel_state = cancel_state,
                            cancel_event = cancelMatchViewModel::onEvent,
                            check_event = checkMatchViewModel::onEvent,
                            match_event = createMatchViewModel::onEvent,
                            profileDetailViewModel = profileDetailViewModel,
                            matchUserViewModel = matchUserViewModel,
                            mainImageViewModel = mainImageViewModel,
                            greetingViewModel = greetingViewModel,
                            navcontroller = navController
                        )
                }
            }


            composable("profileScreen") {
                var matrix by remember { mutableStateOf(ColorMatrix()) }
                val profileViewModel: ProfileViewModel = koinViewModel()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ordColor)
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = FAB_KEY
                            ),
                            animatedVisibilityScope = this
                        ),
                ) {
                    ProfileScreen(
                        matrix = matrix,
                        navController = navController,
                        onITemClick = { resId, text ->
                            navController.navigate("profileEditScreen/$resId/$text")
                        },
                        animatedVisibilityScope = this@composable,
                        profileViewModel = profileViewModel,
                        profileDetailViewModel = profileDetailViewModel,
                        editProfileViewModel = editProfileViewModel,
                        mainImageViewModel = mainImageViewModel,
                        imageViewModel = imageViewModel
                    )
                }
            }

            composable("chatScreen"){
                val state by chatViewModel.state.collectAsStateWithLifecycle()
                val chatState by chatViewModel.Liststate.collectAsStateWithLifecycle()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ordColor)
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = FAB_KEY
                            ),
                            animatedVisibilityScope = this
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    ChatScreen(
                        navController = navController,
                        matchUserViewModel = matchUserViewModel,
                        userDetailViewModel = profileDetailViewModel,
                        state = state,
                        chatState = chatState,
                        imageViewModel = imageViewModel,
                        event = chatViewModel::onEvent,
                        animatedVisibilityScope = this@composable,
                        mainImageViewModel = mainImageViewModel,
                        chatViewModel = chatViewModel,
                        greetingViewModel = greetingViewModel
                    )
                }
            }

            composable(
                "profileEditScreen/{resId}/{text}",
                arguments = listOf(
                    navArgument("resId") {
                        type = NavType.IntType
                    },
                    navArgument("text") {
                        type = NavType.StringType
                    }
                )) {
                val resId = it.arguments?.getInt("resId") ?: 0
                val text = it.arguments?.getString("text") ?: ""
                val profileViewModel: ProfileViewModel = koinViewModel()
                val updateUserViewModel: UpdateUserViewModel = koinViewModel()
                val state by updateUserViewModel.state.collectAsStateWithLifecycle()

                ProfileEditScreen(
                    resId = resId,
                    text = text,
                    animatedVisibilityScope = this,
                    navController = navController,
                    onBioClick = {
                        navController.navigate("EditBioScreen/${editProfileViewModel.bio.value}")
                    },
                    onMapClick = { t ->
                        navController.navigate("EditProfileMapScreen/$t")
                    },
                    profileViewModel = profileViewModel,
                    editProfileViewModel = editProfileViewModel,
                    mainImageViewModel = mainImageViewModel,
                    profileDetailViewModel = profileDetailViewModel,
                    imageViewModel = imageViewModel,
                    updateUserViewModel = updateUserViewModel,
                    state = state,
                    event = updateUserViewModel::onEvent,
                    greetingViewModel = greetingViewModel
                )

            }

            composable(
                "EditProfileMapScreen/{t}",
                arguments = listOf(
                    navArgument("t") {
                        type = NavType.StringType
                    }
                )) {
                val text = it.arguments?.getString("t") ?: ""
                EditProfileMapScreen(
                    text = text,
                    animatedVisibilityScope = this@composable,
                    viewModel = InfoViewModel,
                    editProfileViewModel = editProfileViewModel,
                    navController = navController
                )

            }

            composable(
                "EditBioScreen/{t}",
                arguments = listOf(
                    navArgument("t") {
                        type = NavType.StringType
                    }
                )) {
                val text = it.arguments?.getString("t") ?: ""
                EditBioScreen(
                    trans_text = text,
                    animatedVisibilityScope = this@composable,
                    viewModel = InfoViewModel,
                    editProfileViewModel = editProfileViewModel,
                    navController = navController
                )

            }

            composable<GreetingScreen>(
                popEnterTransition = {
                    scaleIntoContainer()
                },
                popExitTransition = {
                    scaleOutOfContainer()
                }
            ) {
                val viewModel: GreetingViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                GreetingScreen(
                    navController = navController, greetingViewModel = viewModel,
                    state = state,
                    event = viewModel::onEvent,
                    profileDetailViewModel = profileDetailViewModel,
                    editProfileViewModel = editProfileViewModel,
                    jwtViewModel = jwtViewModel,
                    imageViewModel = imageViewModel,
                    mainImageViewModel = mainImageViewModel
                )
            }

            composable("matchProfileScreen") {
                var matrix by remember { mutableStateOf(ColorMatrix()) }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ordColor)
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = FAB_KEY
                            ),
                            animatedVisibilityScope = this
                        ),
                ) {
                    MatchProfileScreen(
                        matrix = matrix,
                        animatedVisibilityScope = this@composable,
                        matchUserViewModel = matchUserViewModel,
                        mainImageViewModel = mainImageViewModel,
                        imageViewModel = imageViewModel
                    )
                }
            }


        }

    }
}

fun onLandingButtonClick(navController: NavController) {
    navController.popBackStack()
    navController.navigate(LoginScreen)
}

fun onPhoneSlide(navController: NavController) {
    navController.popBackStack()
    navController.navigate(PhoneScreen)
}

fun onOTp(navController: NavController) {
    navController.popBackStack()
    navController.navigate(UserGender)
}

fun onPhoneVerify(navController: NavController, phoneNumber: String) {

    navController.navigate(OTPScreen(phoneNumber)) {
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun getVideoUri(): Uri {
    val context = LocalContext.current
    val rawId = context.resources.getIdentifier("background_video", "raw", context.packageName)

    return Uri.parse("android.resource://${context.packageName}/$rawId")
}


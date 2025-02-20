package com.krp.whoknows.Navigation

import android.content.Context
import android.icu.text.IDNA.Info
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.android.gms.maps.model.LatLng
import com.krp.whoknows.Appui.HomeScreen.presentation.HomeScreen
import com.krp.whoknows.Appui.userInfo.CreateUserViewModel
import com.krp.whoknows.Appui.userInfo.DOBScreen
import com.krp.whoknows.Appui.userInfo.InfoViewModel
import com.krp.whoknows.Auth.LoginScreen.Presentation.LoginScreen
import com.krp.whoknows.Auth.OTPScreen.OTPVerificationViewModel
import com.krp.whoknows.Auth.PhoneScreen.Presentation.PhoneAuthViewModel
import com.krp.whoknows.Auth.PhoneScreen.Presentation.PhoneScreen
import com.krp.whoknows.Auth.WelcomeScreen.presentation.WelcomeScreen
import com.krp.whoknows.model.LatLongs
import com.krp.whoknows.roomdb.JWTViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.math.log

/**
 * Created by KUSHAL RAJ PAREEK on 28,January,2025
 */

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SetUpNavGraph(modifier: Modifier = Modifier,startDest : Any) {

    val navController = rememberNavController()
    val InfoViewModel: InfoViewModel = koinViewModel()
    NavHost(
        navController = navController,
        startDestination = startDest
    ) {

        composable<WelcomeScreen>{
            WelcomeScreen(modifier= Modifier) { onLandingButtonClick(navController) }
        }

        composable<LoginScreen>{
            LoginScreen(modifier = Modifier){onPhoneSlide(navController)}
        }

        composable<PhoneScreen>{
            val viewModel: PhoneAuthViewModel = koinViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            PhoneScreen(
                modifier = Modifier,
                event = viewModel::onEvent,
                state = state,
                onOtpSent = { onPhoneVerify(navController,state.phoneNumber!!) }
            )
        }

        composable<OTPScreen>{
            var args = it.toRoute<OTPScreen>()
            val viewModel: OTPVerificationViewModel = koinViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val jwtViewModel : JWTViewModel = koinViewModel()
com.krp.whoknows.Auth.OTPScreen.OTPScreen(  modifier = Modifier,
    event = viewModel::onEvent,
    state = state,
    jwtViewModel,
    navController= navController,
    phoneNumber = args.phoneNumber,
    onOTp = {onOTp(navController)}
)
        }

        composable<HomeScreen>{
            com.krp.whoknows.Appui.HomeScreen.presentation.HomeScreen()
        }
        composable<PreferredGender>{
            com.krp.whoknows.Appui.userInfo.PreferredGender(viewModel = InfoViewModel,navController = navController)
        }
        
        composable<DOBScreen> { 
            com.krp.whoknows.Appui.userInfo.DOBScreen(viewModel = InfoViewModel,navController = navController)
        }
        composable<UserGender> {
            com.krp.whoknows.Appui.userInfo.UserGender(viewModel = InfoViewModel,navController = navController)
        }
        composable<PreferredAgeRange> {
            com.krp.whoknows.Appui.userInfo.PreferredAgeRange(viewModel = InfoViewModel,navController = navController)
        }
        composable<GeoRadiusRange> {
            com.krp.whoknows.Appui.userInfo.GeoRadiusRange(viewModel = InfoViewModel,navController = navController)
        }

        composable<LatLong> {
            val userViewModel: CreateUserViewModel = koinViewModel()
            val jwtViewModel : JWTViewModel = koinViewModel()
            val state by userViewModel.state.collectAsStateWithLifecycle()
            val args = it.toRoute<LatLong>()
            com.krp.whoknows.Appui.userInfo.LatLong(viewModel = InfoViewModel,event = userViewModel::onEvent,state = state,
                jwtViewModel = jwtViewModel,navController = navController,latLong = LatLongs(
                args.latitude ?: "",
                args.longitude ?: ""
            ))
        }

        composable<MapScreen> {
            com.krp.whoknows.Appui.userInfo.MapScreen(navController = navController, context = LocalContext.current)
        }

    }
}

fun onLandingButtonClick( navController : NavController){
    navController.popBackStack()
    navController.navigate(LoginScreen)
//    navController.navigate(DOBScreen)
}

fun onPhoneSlide( navController : NavController){
    navController.popBackStack()
    navController.navigate(PhoneScreen)
}

fun onOTp( navController : NavController){
    navController.popBackStack()
    navController.navigate(UserGender)
}

fun onPhoneVerify(navController: NavController, phoneNumber: String) {

    navController.navigate(OTPScreen(phoneNumber)){
        launchSingleTop = true
        restoreState = true
    }
}
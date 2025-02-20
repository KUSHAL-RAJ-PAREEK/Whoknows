package com.krp.whoknows

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.krp.whoknows.Navigation.SetUpNavGraph
import com.krp.whoknows.Auth.WelcomeScreen.presentation.WelcomeScreen
import com.krp.whoknows.roomdb.DataStoreManager
import com.krp.whoknows.roomdb.JWTViewModel
import com.krp.whoknows.ui.theme.WhoknowsTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel
import org.koin.core.KoinApplication.Companion.init
import kotlin.math.log

sealed class StartDestination {
    object Loading : StartDestination()
    object Welcome : StartDestination()
    object UserGender : StartDestination()
}

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        setContent {
            val jwtViewModel: JWTViewModel by inject()
            val startDestination = rememberStartDestination(jwtViewModel)

            splashScreen.setKeepOnScreenCondition {
                startDestination is StartDestination.Loading
            }

            if (startDestination !is StartDestination.Loading) {
                WhoknowsTheme {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = colorResource(id = R.color.splashScreenColor))
                    ) {
                        SetUpNavGraph(
                            startDest = when (startDestination) {
                                is StartDestination.Welcome -> com.krp.whoknows.Navigation.WelcomeScreen
                                is StartDestination.UserGender -> com.krp.whoknows.Navigation.UserGender
                                else -> com.krp.whoknows.Navigation.WelcomeScreen
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun rememberStartDestination(
    jwtViewModel: JWTViewModel
): StartDestination {
    val token by jwtViewModel.jwtToken.collectAsState(initial = null)
    var destination by remember { mutableStateOf<StartDestination>(StartDestination.Loading) }

    LaunchedEffect(token) {
        destination = when {
            token == null -> StartDestination.Loading
            token!!.isEmpty() -> StartDestination.Welcome
            else -> StartDestination.UserGender
        }
    }

    return destination
}
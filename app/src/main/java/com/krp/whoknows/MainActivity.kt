package com.krp.whoknows

import android.os.Build
import android.os.Build.VERSION_CODES.P
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
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        setContent {
            val jwtViewModel: JWTViewModel by inject()
//            val token by jwtViewModel.jwtToken.collectAsState(initial = null)
            val p by jwtViewModel.userDetail.collectAsState(initial = null)
            Log.d("userishere1111", p.toString())

            var startDest by remember { mutableStateOf<Any?>(null) }
            var timeoutReached by remember { mutableStateOf(false) }

            splashScreen.setKeepOnScreenCondition { startDest == null && !timeoutReached }

            LaunchedEffect(p) {
                if (p?.username != null) {
                    startDest = if (p?.username!!.isEmpty()) {
                        com.krp.whoknows.Navigation.WelcomeScreen
                    } else {
                        com.krp.whoknows.Navigation.GreetingScreen
                    }
                }
            }

            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(3000)
                if (startDest == null) {
                    timeoutReached = true
                    startDest = com.krp.whoknows.Navigation.WelcomeScreen
                }
            }

            if (startDest != null) {
                WhoknowsTheme {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = colorResource(id = R.color.splashScreenColor))
                    ) {
                        SetUpNavGraph(startDest = startDest!!)
                    }
                }
            }
        }
    }
}
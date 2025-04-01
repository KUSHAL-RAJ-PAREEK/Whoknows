package com.krp.whoknows.Appui.userInfo

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.LocalTextStyle
import androidx.xr.compose.testing.toDp
import com.krp.whoknows.Appui.GreetingScreen.Presentation.GreetingViewModel
import com.krp.whoknows.Navigation.MapScreen
import com.krp.whoknows.R
import com.krp.whoknows.Utils.checkForPermission
import com.krp.whoknows.Utils.getLocationName
import com.krp.whoknows.model.LatLongs
import com.krp.whoknows.model.User
import com.krp.whoknows.roomdb.JWTViewModel
import com.krp.whoknows.roomdb.entity.InterUserDetail
import com.krp.whoknows.ui.theme.ordColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by KUSHAL RAJ PAREEK on 10,February,2025
 */



@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("StateFlowValueCalledInComposition", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LatLong(viewModel: InfoViewModel,
            event : (CreateUserEvent) -> Unit,
            jwtViewModel: JWTViewModel,
            greetingViewModel: GreetingViewModel,
            state :CreateUserState,
            navController: NavController,
            latLong: LatLongs,
) {


    var pNumber= greetingViewModel.pNumber.collectAsState()
    var jwt= greetingViewModel.jwtToken.collectAsState()
    val context = LocalContext.current
    var hasLocationPermission by remember {
        mutableStateOf(checkForPermission(context))
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var isGranted = true
        permissions.entries.forEach {
            if (!it.value) {
                isGranted = false
                return@forEach
            }
        }

        if (isGranted) {

        }
    }

LaunchedEffect(state.isLoading) {
    Log.d("afkmaskfsmasfsa", state.isSuccess.toString())
    if(state.isSuccess){
        val details = state.successMessage
        val user = InterUserDetail(
            id = details?.id!!,
            ageGap = details.ageGap!!,
            username = details.username!!,
            posts = details.posts!!,
            interests = details.interests!!,
            gender = details.gender!!,
            preferredGender = details.preferredGender!!,
            geoRadiusRange = details.geoRadiusRange.toString(),
            dob = details.dob!!,
            bio = details.bio?:"hey",
            latitude = details.latitude!!,
            longitude = details.longitude!!,
            pnumber = details.pnumber!!
        )
        jwtViewModel.saveUser(user)
        navController.popBackStack()
        navController.navigate(com.krp.whoknows.Navigation.GreetingScreen)
    }


}
    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
    }

    var text by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit){
        if(latLong.latitude != "" && latLong.longitude != ""){
            Log.d("latlongcome", "LatLong: $latLong")
          val loc = getLocationName(latLong.latitude.toDouble(), latLong.longitude.toDouble(),context)
            Log.d("latlongcome", loc.toString())
            text = TextFieldValue(loc.toString())
        }
    }
    val coroutineScope = rememberCoroutineScope()

    var isFocused by remember { mutableStateOf(false) }
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current).toDp()
    BackHandler {
        navController.navigate(com.krp.whoknows.Navigation.DOBScreen){
            popUpTo(0) { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, start = 10.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack, contentDescription = "Back arrow",
                Modifier.size(35.dp).clickable{
                    navController.navigate(com.krp.whoknows.Navigation.DOBScreen){
                        popUpTo(0) { inclusive = true }
                    }
                },
                tint = ordColor
            )
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(top = 10.dp)){
            Text(text = "What's your location ?",
                fontFamily = FontFamily(Font(R.font.noto_sans_khanada)),
                fontSize = 20.sp)

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = text,
                readOnly = true,
                onValueChange = {},
                trailingIcon = {
                    Image(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            Log.d("GIVENORNOT", hasLocationPermission.toString())
                            if (hasLocationPermission) {
                                navController.navigate(MapScreen)
                                Log.d("iscomingthere", "LatLong:")

                            } else {
                                locationPermissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                    )
                                )
                                hasLocationPermission = true
                            }
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    }
                    .onFocusChanged { isFocused = it.isFocused },
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp,color = Color.Black),
                placeholder = { Text("Enter your Location") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = ordColor,
                    unfocusedBorderColor = Color.Gray
                ),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {

                    if (text.text.isBlank()) {
                        Toast.makeText(context, "Click to select Location", Toast.LENGTH_SHORT).show()
                    } else {
                        if(pNumber?.value == null){
                            Log.d("asfddffffffffffff", "null")

                        }else{
                            Log.d("asfddffffffffffff", pNumber.value.toString())
                        }
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                delay(1000)
                                Log.d("afnasknfklsnfklasnfkls",viewModel.interests.value.toString())
                                Log.d("asfddffffffffffff", jwt.value.toString())
                                val user = User(pNumber =  pNumber?.value!!,
                                    geoRadiusRange = viewModel.geoRadiusRange.value,
                                    preferredGender = viewModel.preGender.value,
                                    preferredAgeRange = viewModel.preAgeRange.value,
                                    latitude = latLong.latitude,
                                    longitude = latLong.longitude,
                                    userDob = viewModel.dob.value,
                                    interests = viewModel.interests.value,
                                    userGender = viewModel.gender.value)
                                event(CreateUserEvent.CreateUser(user, jwt.value.toString()))
                            }
                        }
                    }
                },
                shape = CircleShape,
                containerColor = ordColor,
                modifier = Modifier
                    .padding(bottom = if (imeHeight > 0.dp) imeHeight + 20.dp else 40.dp)
                    .size(56.dp)
                    .shadow(8.dp, CircleShape)
                    .clickable(enabled = !state.isLoading) {}
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Next",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
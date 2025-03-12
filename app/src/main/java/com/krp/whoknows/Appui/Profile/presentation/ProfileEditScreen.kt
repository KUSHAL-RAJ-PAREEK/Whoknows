package com.krp.whoknows.Appui.Profile.presentation

import android.Manifest
import android.R.attr.contentDescription
import android.R.attr.fontFamily
import android.R.attr.phoneNumber
import android.R.attr.text
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.VerticalAlign
import androidx.core.content.ContentResolverCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.LocalTextStyle
import androidx.xr.compose.testing.isFocused
import androidx.xr.compose.testing.toDp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.krp.whoknows.Appui.Profile.presentation.ProfileEditScreen
import com.krp.whoknows.Appui.Profile.presentation.components.InterestItemImg
import com.krp.whoknows.Appui.userInfo.InfoViewModel
import com.krp.whoknows.Auth.OTPScreen.OTPVerificationEvent
import com.krp.whoknows.Navigation.MapScreen
import com.krp.whoknows.Navigation.ProfileEditScreen

import com.krp.whoknows.R
import com.krp.whoknows.SupabaseClient.supabaseClient
import com.krp.whoknows.Utils.CustomMultilineHintTextField
import com.krp.whoknows.Utils.Dialogs
import com.krp.whoknows.Utils.DropDownMenu
import com.krp.whoknows.Utils.checkForPermission
import com.krp.whoknows.model.SendOTP
import com.krp.whoknows.ui.theme.background_white
import com.krp.whoknows.ui.theme.ordColor
import com.krp.whoknows.ui.theme.text_gray
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.UploadData
import io.github.jan.supabase.storage.storage
import io.ktor.utils.io.core.use
import kotlinx.coroutines.launch
import okio.use
import java.io.InputStream
import org.koin.java.KoinJavaComponent.inject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.contracts.contract
import kotlin.math.log

/**
 * Created by KUSHAL RAJ PAREEK on 08,March,2025
 */


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun SharedTransitionScope.ProfileEditScreen(
    modifier: Modifier = Modifier,
    resId: Int, text: String,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: InfoViewModel,
    navController: NavController,
    onBioClick: (String)-> Unit,
    onMapClick:(String)-> Unit
) {


    val context = LocalContext.current
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var isFocused by remember { mutableStateOf(false) }
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current).toDp()

    val list = mutableListOf(
        InterestItem("Music", Icons.Default.MusicNote),
        InterestItem("Travel", Icons.Default.TravelExplore),
        InterestItem("Sports", Icons.Default.Sports),
        InterestItem("Cooking", Icons.Default.Cookie),
        InterestItem("Gaming", Icons.Default.Games),
        InterestItem("Photography", Icons.Default.Camera)
    )



    var fImage by remember {
        mutableStateOf<Uri?>(null)
    }
    var sImage by remember {
        mutableStateOf<Uri?>(null)
    }
    var tImage by remember {
        mutableStateOf<Uri?>(null)
    }

    var fPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri -> fImage = uri}
    )

    var sPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri -> sImage = uri}
    )
    var tPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri -> tImage = uri}
    )


    var date by remember {
        mutableStateOf(LocalDate.now())
    }

    LaunchedEffect(Unit) {
        text = TextFieldValue(viewModel.dob.value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
    }
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


    var isBioSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()
    if (isBioSheetOpen) {
        val bioSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        ModalBottomSheet(
            sheetState = rememberModalBottomSheetState(),
            onDismissRequest = { isBioSheetOpen = false },
            modifier = Modifier.heightIn(min = 200.dp, max = 600.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var text by remember {
                    mutableStateOf("")
                }
                Column(modifier = Modifier.fillMaxSize()) {
                    CustomMultilineHintTextField(
                        value = text,
                        onValueChanged = {
                            text = it
                        },
                        hintText = "I am using WhoKnows",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20))
                            .background(Color.White)
                            .padding(16.dp),
                        maxLines = 1000
                    )
                }

            }
        }
    }

    val isFocusedList = remember { mutableStateListOf(false, false, false, false, false, false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .background(
                    background_white
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Edit Profile",
                color = text_gray,
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 30.dp),
                fontFamily = FontFamily(Font(R.font.poppins_bold))
            )
            Spacer(modifier = Modifier.height(25.dp))

            Image(
                painter = painterResource(id = R.drawable.p_image),
                contentDescription = null,
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
                    .size(220.dp)
                    .border(4.dp, ordColor, shape = CircleShape))

            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Sabrina Carpainter",
                modifier = Modifier,
                color = text_gray,
                fontFamily = FontFamily(Font(R.font.vesperlibre_medium)),
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 25.dp)) {

                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Date of Birth",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = text,
                    onValueChange = { },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {}
                        .onFocusChanged { isFocusedList[0] = it.isFocused },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = "Calendar Icon",
                            tint = ordColor,
                            modifier = Modifier.clickable {
                                val listener =
                                    DatePickerDialog.OnDateSetListener { _, year, month, day ->
                                        date = LocalDate.of(year, month, day)
                                        val formattedDate =
                                            date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                                        text = TextFieldValue(formattedDate)
                                        viewModel.updateDOB(date)
                                    }
                                Dialogs().openDatePicker(
                                    context = context,
                                    onDateSetListener = listener,
                                    date = date
                                )
                            }
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    placeholder = { Text("Enter your Date of Birth") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ordColor,
                        unfocusedBorderColor = text_gray
                    ),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier.padding(horizontal = 25.dp)) {
                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Preferred Gender",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )
                Spacer(modifier = Modifier.height(10.dp))
                // dropdown
                DropDownMenu(listOf("MALE", "FEMALE"), viewModel.preGender.value) {
                    if (viewModel.preGender.value.isBlank()) {
                        viewModel.updatePreGender(it)
                    }
                    text = TextFieldValue(viewModel.preGender.value)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 25.dp)) {
                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Preferred Age Range",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {
//                            text = it
                            viewModel.updatePreAgeRange(it.toString())
                        },
                        modifier = Modifier
                            .weight(1f)
                            .onFocusChanged { isFocusedList[0] = it.isFocused },
                        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                        placeholder = { Text("Start") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = ordColor,
                            unfocusedBorderColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(20.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .weight(1f)
                            .onFocusChanged { isFocusedList[0] = it.isFocused },
                        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                        placeholder = { Text("End") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = ordColor,
                            unfocusedBorderColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(20.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 25.dp)) {

                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Preferred Range",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier = Modifier.height(10.dp))


                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        viewModel.updateGeoRadiusRange(it.toString())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {}
                        .onFocusChanged { isFocusedList[2] = it.isFocused },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    placeholder = { Text("Enter range in KM") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ordColor,
                        unfocusedBorderColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 25.dp)) {
                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Location",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = text,
                    readOnly = true,
                    onValueChange = {},
                    trailingIcon = {
                        Image(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "",
                            modifier = Modifier.
                            clickable {
                                Log.d("GIVENORNOT", hasLocationPermission.toString())
                                if (hasLocationPermission) {
                                    onMapClick("InMyMap")
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
                        .sharedElement(
                            state = rememberSharedContentState(
                                key = "map/InMyMap",
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 1000)
                            }
                        )
                        .onFocusChanged { isFocusedList[3] = it.isFocused },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    placeholder = { Text("Enter your Location") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ordColor,
                        unfocusedBorderColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 25.dp)) {
                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Bio",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        viewModel.updateGeoRadiusRange(it.toString())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedElement(
                            state = rememberSharedContentState(
                                key = "text/${text.text}",
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 1000)
                            }
                        )
                        .clickable {}
                        .onFocusChanged { isFocusedList[2] = it.isFocused },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.ModeEdit,
                            tint = ordColor,
                            contentDescription = "",
                            modifier = Modifier
                                .clickable {
                                    isBioSheetOpen = true
                                }
                                .clickable {
                                    onBioClick(text.text)
                                }
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    placeholder = { Text("Enter range in KM") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ordColor,
                        unfocusedBorderColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )


            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 25.dp)) {
                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Interest",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier = Modifier.height(3.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        list.forEach { interest ->
                            InterestItemImg(
                                modifier = Modifier.padding(4.dp),
                                interest = interest
                            )
                        }

                        InterestItemImg(
                            modifier = Modifier.padding(4.dp),
                            interest = InterestItem("Add", Icons.Default.Add),
                            color = text_gray
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 25.dp)) {
                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Gallery",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth(),
                ) {
                    AsyncImage(
                        model = fImage ?: R.drawable.img,
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                try{
                                    fPhotoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }catch(e : Exception){
                                    Toast.makeText(context,"$e",Toast.LENGTH_LONG).show()
                                }
                            },
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    AsyncImage(
                        model = sImage ?: R.drawable.add_file_button,
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .then(if (sImage != null) Modifier else Modifier.border(2.dp, ordColor, RoundedCornerShape(16.dp)))
                            .clickable {
                                sPhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                            .padding(10.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(getProfileImageUrl("12345")).build()?: R.drawable.img,
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                tPhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            androidx.compose.material3.Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .height(45.dp),
                shape = RoundedCornerShape(8.dp),

               colors = ButtonDefaults.buttonColors(containerColor = ordColor),
                onClick ={
                    coroutineScope.launch {
                        fImage?.let{ uri ->
                            uploadProfileImage(userId = "12345", imageUri = uri, contentResolver = context.contentResolver)

                        }
                    }
                }
            ) {
                Text(text = "Save",
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Preview
@Composable
private fun ok() {
    Run(InfoViewModel(), navController = rememberNavController())
}

suspend fun uploadProfileImage(imageUri: Uri, userId: String, contentResolver: ContentResolver): Boolean {
    Log.d("cominginside","yes")
    return try {
        contentResolver.openInputStream(imageUri)?.use { inputStream ->
            val byteArray = inputStream.readBytes()
            supabaseClient().storage.from("profile_images").upload(
                path = "$userId.jpg",
                data = byteArray,
                upsert = true
            )
        }
        println("Profile image uploaded successfully!")
        true
    } catch (e: Exception) {
        println("Profile image upload failed: ${e.message}")
        false
    }
}

fun getProfileImageUrl(userId: String): String {

    return supabaseClient().storage
        .from("profile_images").publicUrl("$userId.jpg")
}


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun Run(
    viewModel: InfoViewModel,
    navController: NavController
) {

    var fImage by remember {
        mutableStateOf<Uri?>(null)
    }
    var sImage by remember {
        mutableStateOf<Uri?>(null)
    }
    var tImage by remember {
        mutableStateOf<Uri?>(null)
    }

    var fPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri -> fImage = uri}
    )

    var sPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri -> sImage = uri}
    )
    var tPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri -> tImage = uri}
    )

    val context = LocalContext.current
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var isFocused by remember { mutableStateOf(false) }
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current).toDp()

    var date by remember {
        mutableStateOf(LocalDate.now())
    }

    LaunchedEffect(Unit) {
        text = TextFieldValue(viewModel.dob.value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
    }
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

    val bioSheetState = rememberModalBottomSheetState()
    var isBioSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val list = mutableListOf(
        InterestItem("Music", Icons.Default.MusicNote),
        InterestItem("Travel", Icons.Default.TravelExplore),
        InterestItem("Sports", Icons.Default.Sports),
        InterestItem("Cooking", Icons.Default.Cookie),
        InterestItem("Gaming", Icons.Default.Games),
        InterestItem("Photography", Icons.Default.Camera)
    )



    val isFocusedList = remember { mutableStateListOf(false, false, false, false, false, false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .background(
                    background_white
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Edit Profile",
                color = text_gray,
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 30.dp),
                fontFamily = FontFamily(Font(R.font.poppins_bold))
            )
            Spacer(modifier = Modifier.height(25.dp))

            Image(
                painter = painterResource(id = R.drawable.p_image),
                contentDescription = null,
                modifier = Modifier

                    .clip(CircleShape)
                    .size(220.dp)
                    .border(4.dp, ordColor, shape = CircleShape)
            )

            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Sabrina Carpainter",
                modifier = Modifier,
                color = text_gray,
                fontFamily = FontFamily(Font(R.font.vesperlibre_medium)),
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 25.dp)) {

                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Date of Birth",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = text,
                    onValueChange = { },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {}
                        .onFocusChanged { isFocusedList[0] = it.isFocused },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = "Calendar Icon",
                            tint = ordColor,
                            modifier = Modifier.clickable {
                                val listener =
                                    DatePickerDialog.OnDateSetListener { _, year, month, day ->
                                        date = LocalDate.of(year, month, day)
                                        val formattedDate =
                                            date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                                        text = TextFieldValue(formattedDate)
                                        viewModel.updateDOB(date)
                                    }
                                Dialogs().openDatePicker(
                                    context = context,
                                    onDateSetListener = listener,
                                    date = date
                                )
                            }
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    placeholder = { Text("Enter your Date of Birth") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ordColor,
                        unfocusedBorderColor = text_gray
                    ),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier.padding(horizontal = 25.dp)) {
                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Preferred Gender",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )
                Spacer(modifier = Modifier.height(10.dp))
                // dropdown
                DropDownMenu(listOf("MALE", "FEMALE"), viewModel.preGender.value) {
                    if (viewModel.preGender.value.isBlank()) {
                        viewModel.updatePreGender(it)
                    }
                    text = TextFieldValue(viewModel.preGender.value)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 25.dp)) {
                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Preferred Age Range",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {
//                            text = it
                            viewModel.updatePreAgeRange(it.toString())
                        },
                        modifier = Modifier
                            .weight(1f)
                            .onFocusChanged { isFocusedList[0] = it.isFocused },
                        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                        placeholder = { Text("Start") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = ordColor,
                            unfocusedBorderColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(20.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .weight(1f)
                            .onFocusChanged { isFocusedList[0] = it.isFocused },
                        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                        placeholder = { Text("End") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = ordColor,
                            unfocusedBorderColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(20.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 25.dp)) {

                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Preferred Range",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier = Modifier.height(10.dp))


                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        viewModel.updateGeoRadiusRange(it.toString())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {}
                        .onFocusChanged { isFocusedList[2] = it.isFocused },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    placeholder = { Text("Enter range in KM") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ordColor,
                        unfocusedBorderColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 25.dp)) {
                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Location",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier = Modifier.height(10.dp))

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
                        .onFocusChanged { isFocusedList[3] = it.isFocused },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    placeholder = { Text("Enter your Location") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ordColor,
                        unfocusedBorderColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 25.dp)) {
                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Bio",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        viewModel.updateGeoRadiusRange(it.toString())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {}
                        .onFocusChanged { isFocusedList[2] = it.isFocused },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.ModeEdit,
                            tint = ordColor,
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                isBioSheetOpen = true
                            }
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    placeholder = { Text("Enter range in KM") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ordColor,
                        unfocusedBorderColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 25.dp)) {
                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Interest",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        list.forEach { interest ->
                            InterestItemImg(
                                modifier = Modifier.padding(4.dp),
                                interest = interest
                            )
                        }

                        InterestItemImg(
                            modifier = Modifier.padding(4.dp),
                            interest = InterestItem("Add", Icons.Default.Add),
                            color = text_gray
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 25.dp)) {
                androidx.wear.compose.material.Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "Gallery",
                    color = text_gray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    AsyncImage(
                        model = fImage ?: R.drawable.img,
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                fPhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        contentScale = ContentScale.Crop
                    )

                    AsyncImage(
                        model = sImage ?: R.drawable.img,
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                sPhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        contentScale = ContentScale.Crop
                    )
                    AsyncImage(
                        model = tImage ?: R.drawable.img,
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                tPhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }


        }
    }

}








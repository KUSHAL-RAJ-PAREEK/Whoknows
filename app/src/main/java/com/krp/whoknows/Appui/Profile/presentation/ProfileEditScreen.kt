package com.krp.whoknows.Appui.Profile.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.LocalTextStyle
import androidx.wear.compose.material.placeholder
import androidx.xr.compose.testing.toDp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.krp.whoknows.Appui.GreetingScreen.Presentation.GreetingViewModel
import com.krp.whoknows.Appui.Profile.presentation.components.InterestItemImgCancel
import com.krp.whoknows.Appui.Profile.presentation.components.InterestItemImg
import com.krp.whoknows.Appui.interest.Components.InterestItem
import com.krp.whoknows.Navigation.HomeScreen

import com.krp.whoknows.R
import com.krp.whoknows.SupabaseClient.supabaseClient
import com.krp.whoknows.Utils.CustomMultilineHintTextField
import com.krp.whoknows.Utils.Dialogs
import com.krp.whoknows.Utils.RemDropDownMenu
import com.krp.whoknows.Utils.checkForPermission
import com.krp.whoknows.Utils.drawableToBitmap
import com.krp.whoknows.Utils.getImageBitmapOrPlaceholder
import com.krp.whoknows.Utils.getProfileImageUrl
import com.krp.whoknows.Utils.interestIcons
import com.krp.whoknows.roomdb.ImageConverter.base64ToBitmap
import com.krp.whoknows.roomdb.ImageConverter.uriToBase64
import com.krp.whoknows.roomdb.ImageRepository
import com.krp.whoknows.roomdb.entity.UserResponseEntity
import com.krp.whoknows.ui.theme.background_white
import com.krp.whoknows.ui.theme.ordColor
import com.krp.whoknows.ui.theme.text_gray
import io.github.jan.supabase.storage.storage
import io.ktor.utils.io.core.use
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.toString

/**
 * Created by KUSHAL RAJ PAREEK on 08,March,2025
 */


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(
    ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun SharedTransitionScope.ProfileEditScreen(
    modifier: Modifier = Modifier,
    resId: Int, text: String,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navController: NavController,
    onBioClick: (String) -> Unit,
    onMapClick: (String) -> Unit,
    profileViewModel: ProfileViewModel,
    profileDetailViewModel: ProfileDetailViewModel,
    editProfileViewModel: EditProfileViewModel,
    mainImageViewModel: MainImageViewModel,
    imageViewModel: ImageViewModel,
    updateUserViewModel: UpdateUserViewModel,
    event: (UpdateUserEvent) -> Unit,
    state: UpdateUserState,
    greetingViewModel: GreetingViewModel
) {

    val context = LocalContext.current

    val repository: ImageRepository = koinInject<ImageRepository>()

    val viewModel = viewModel<SearchViewModel>()

    val coroutineScope = rememberCoroutineScope()

    val profileImage by imageViewModel.profileImage.collectAsState()

    val G1 by imageViewModel.firstGalleryImage.collectAsState()


    val G2 by imageViewModel.secondGalleryImage.collectAsState()

    val G3 by imageViewModel.thirdGalleryImage.collectAsState()


    val PimageBitmap = getImageBitmapOrPlaceholder(profileImage)

    val FimageBitmap = getImageBitmapOrPlaceholder(G1)
    val SimageBitmap = getImageBitmapOrPlaceholder(G2)
    val TimageBitmap = getImageBitmapOrPlaceholder(G3)

    Log.d("herehree", FimageBitmap.toString())
    Log.d("herehree", SimageBitmap.toString())
    Log.d("herehree", TimageBitmap.toString())

    var text by remember { mutableStateOf(TextFieldValue("")) }
    var preferredGender by remember { mutableStateOf(TextFieldValue("")) }
    var preferredRange by remember { mutableStateOf(TextFieldValue("")) }
    var dob by remember { mutableStateOf(TextFieldValue("")) }
    var location by remember { mutableStateOf(TextFieldValue("")) }
    var bio by remember { mutableStateOf(TextFieldValue("")) }
    var fPre by remember { mutableStateOf(TextFieldValue("")) }
    var tPre by remember { mutableStateOf(TextFieldValue("")) }
    var preferredAgeRange by remember { mutableStateOf("20-25") }
    val interest by editProfileViewModel.interests.collectAsState()
    var posts by remember { mutableStateOf<List<String>?>(null) }

    LaunchedEffect(Unit) {
        interest?.forEach { existingInterest ->
            viewModel.toggleSelection(existingInterest)
        }
    }

//    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current).toDp()
    val user = profileViewModel.user.collectAsState()

    var username by remember { mutableStateOf("Whoknows") }

    LaunchedEffect(Unit) {
        username = editProfileViewModel.username.value
        val parts = preferredAgeRange.split("-").map { it.toInt() }
        preferredGender =
            TextFieldValue((editProfileViewModel.preGender.value ?: "Whoknows").toString())
        preferredRange =
            TextFieldValue((editProfileViewModel.geoRadiusRange.value ?: "Whoknows").toString())

        val dobStateFlow = editProfileViewModel.dob
        fun formatDate(date: LocalDate): String {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            return date.format(formatter)
        }

        val dateOfBirth = formatDate(dobStateFlow.value)
        dob = TextFieldValue(dateOfBirth)
        bio = TextFieldValue(editProfileViewModel.bio.value ?: "whoknows")
        preferredAgeRange =
            if (editProfileViewModel.preAgeRange.value == null) editProfileViewModel.preAgeRange.value else "20-25"

        location = TextFieldValue(editProfileViewModel.location.value)
        Log.d("partsarethere", "${parts[0]} ${parts[1]}")

        fPre = TextFieldValue((editProfileViewModel.preAgeFRange.value ?: "Whoknows").toString())
        tPre = TextFieldValue((editProfileViewModel.preAgeTRange.value ?: "Whoknows").toString())
//        interest = editProfileViewModel.interests.value
        posts = editProfileViewModel.posts.value
    }

    val defaultIcon = Icons.Default.Star

//    val interests: MutableList<String> =  editProfileViewModel.interests.value?.toMutableList() ?: mutableListOf()

    var isCrossVisible by remember { mutableStateOf(false) }
    var isCrossVisible1 by remember { mutableStateOf(false) }
    var isCrossVisible2 by remember { mutableStateOf(false) }
    var isCrossVisible3 by remember { mutableStateOf(false) }

    var isProfileDeleted by remember { mutableStateOf(false) }
    var isG1Deleted by remember { mutableStateOf(false) }

    var isG2Deleted by remember { mutableStateOf(false) }
    var isG3Deleted by remember { mutableStateOf(false) }

    var selectedInterest by remember { mutableStateOf<InterestItem?>(null) }

    val jwt by greetingViewModel.jwtToken.collectAsState()

    var mImage by remember {
        mutableStateOf<Uri?>(null)
    }

    var fImage by remember {
        mutableStateOf<Uri?>(null)
    }
    var sImage by remember {
        mutableStateOf<Uri?>(null)
    }
    var tImage by remember {
        mutableStateOf<Uri?>(null)
    }


    var Pimg_url by remember { mutableStateOf<String?>(null) }
    var g1img_url by remember { mutableStateOf<String?>(null) }
    var g2img_url by remember { mutableStateOf<String?>(null) }
    var g3img_url by remember { mutableStateOf<String?>(null) }


    var mPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            mImage = uri
            uri?.let {
                val base64String = uriToBase64(context, uri)
                val img = base64String?.let { base64ToBitmap(it) }
                imageViewModel.updateProfile(img!!)
            }
        }
    )


    var fPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            fImage = uri
            uri?.let {
                val base64String = uriToBase64(context, uri)
                val bm = base64String?.let { base64ToBitmap(it) }
                imageViewModel.updateG1(bm!!)
            }
        }
    )

    var sPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            sImage = uri
            uri?.let {
                val base64String = uriToBase64(context, it)
                val bm = base64String?.let { base64ToBitmap(it) }
                imageViewModel.updateG2(bm!!)
            }
        }
    )
    var tPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            tImage = uri
            uri?.let {
                val base64String = uriToBase64(context, it)
                val bm = base64String?.let { base64ToBitmap(it) }
                imageViewModel.updateG3(bm!!)
            }
        }
    )


    var date by remember {
        mutableStateOf(LocalDate.now())
    }

    LaunchedEffect(Unit) {
        text =
            TextFieldValue(editProfileViewModel.dob.value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
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


    var isInterestSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
//    val selectedStates =
//        remember { mutableStateListOf<Boolean>().apply { repeat(205) { add(false) } } }

    if (isInterestSheetOpen) {
        val searchText by viewModel.searchText.collectAsState()
        val interestSea by viewModel.interest.collectAsState()
        val isSearch by viewModel.isSearching.collectAsState()
        val selectedStates by viewModel.selectedStates.collectAsState()
        val bioSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            sheetState = bioSheetState,
            onDismissRequest = { isInterestSheetOpen = false },
            modifier = Modifier.heightIn(min = 200.dp, max = 600.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = searchText,
                    onValueChange = viewModel::onSearchTextChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Search") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    interestSea.forEachIndexed { i, interest ->
                        val isSelected = selectedStates[interest.interest] ?: false
                        InterestItem(
                            modifier = Modifier.padding(6.dp),
                            name = interest.interest, filled = isSelected
                        ) {
                            viewModel.toggleSelection(interest.interest)
                            if (!isSelected) {
                                editProfileViewModel.addInterest(interest.interest)
                            } else {
                                editProfileViewModel.removeInterest(interest.interest)
                            }
                        }
                    }
                }
            }
        }
    }

    val isFocusedList = remember { mutableStateListOf(false, false, false, false, false, false) }

    var galleryList by remember { mutableStateOf<List<String>?>(null) }

    Pimg_url = editProfileViewModel.mImage.value
    g1img_url = editProfileViewModel.fImage.value
    g2img_url = editProfileViewModel.sImage.value
    g3img_url = editProfileViewModel.tImage.value


    LaunchedEffect(state.isLoading) {
        if (state.isSuccess) {
            Log.d("sucesssssss", "dasdssadsd")
        }
        if (state.statusCode == 200) {
            Log.d("dasdasdasd",Pimg_url.toString())
            Log.d("dasdasdasd",g1img_url.toString())
            Log.d("dasdasdasd",g2img_url.toString())
            Log.d("dasdasdasd",g3img_url.toString())
            val user = UserResponseEntity(
                imgUrl = Pimg_url,
                posts = galleryList,
                id = editProfileViewModel.id.value,
                ageGap = "${editProfileViewModel.preAgeFRange.value}-${editProfileViewModel.preAgeTRange.value}",
                bio = editProfileViewModel.bio.value,
                dob = editProfileViewModel.dob.value.toString(),
                gender = editProfileViewModel.gender.value,
                geoRadiusRange = editProfileViewModel.geoRadiusRange.value.toInt(),
                latitude = editProfileViewModel.latitude.value,
                longitude = editProfileViewModel.longitude.value,
                pnumber = editProfileViewModel.pnumber.value,
                preferredGender = editProfileViewModel.preGender.value,
                username = editProfileViewModel.username.value,
                interests = editProfileViewModel.interests.value,
            )
            Log.d("saveUser", user.toString())

//            withContext(Dispatchers.IO) {
                greetingViewModel.saveUser(user)

                profileDetailViewModel.updatePnumber(editProfileViewModel.pnumber.value)
                profileDetailViewModel.updateId(editProfileViewModel.id.value)
                profileDetailViewModel.updateImgUrl(Pimg_url.toString())
                profileDetailViewModel.updatePosts(galleryList ?: emptyList())
                profileDetailViewModel.updatePreAgeRange("${editProfileViewModel.preAgeFRange.value}-${editProfileViewModel.preAgeTRange.value}")
                profileDetailViewModel.updateBio(editProfileViewModel.bio.value)
                profileDetailViewModel.updateDOB(editProfileViewModel.dob.value)
                profileDetailViewModel.updateGender(editProfileViewModel.gender.value)
                profileDetailViewModel.updateGeoRadiusRange(editProfileViewModel.geoRadiusRange.value)
                profileDetailViewModel.updateLatitude(editProfileViewModel.latitude.value)
                profileDetailViewModel.updateLongitude(editProfileViewModel.longitude.value)
                profileDetailViewModel.updatePnumber(editProfileViewModel.pnumber.value)
                profileDetailViewModel.updatePreGender(editProfileViewModel.preGender.value)
                profileDetailViewModel.updateUsername(editProfileViewModel.username.value)
                profileDetailViewModel.updateInterest(editProfileViewModel.interests.value!!)
                Log.d("saveUser", "User saved successfully in background")
//            }
            Log.d("asdasdasdsadsa", galleryList.toString())
            Toast.makeText(context, "data saved", Toast.LENGTH_SHORT).show()
//            navController.navigate(HomeScreen)
        } else {
            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
        }
    }


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
                bitmap = if (isCrossVisible) {
                    drawableToBitmap(context, R.drawable.cross).asImageBitmap()
                } else {
                    PimageBitmap
                },
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
                    .size(220.dp)
                    .border(4.dp, if (isCrossVisible) Color.Red else ordColor, shape = CircleShape)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                isCrossVisible = true
                            },
                            onTap = {
                                if (isCrossVisible) {
                                    isCrossVisible = false
                                    imageViewModel.updateProfile(
                                        drawableToBitmap(
                                            context,
                                            R.drawable.p_image
                                        )
                                    )
                                    mImage = null
                                    isProfileDeleted = true
                                } else {
                                    try {
                                        mPhotoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "$e", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        )
                    }
            )

            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = username,
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
                    value = dob,
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
                                        dob = TextFieldValue(formattedDate)
                                        editProfileViewModel.updateDOB(date)
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
                RemDropDownMenu(listOf("MALE", "FEMALE"), editProfileViewModel.preGender.value) {
                    if (editProfileViewModel.preGender.value.isBlank()) {
                        editProfileViewModel.updatePreGender(it)
                    }
                    preferredGender = TextFieldValue(editProfileViewModel.preGender.value)
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
                        value = fPre,
                        onValueChange = {
                            fPre = it
                            editProfileViewModel.updatePreAgeRange(it.text)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .onFocusChanged { isFocusedList[0] = it.isFocused },
                        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                        placeholder = { Text("Min") },
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
                        value = tPre,
                        onValueChange = {
                            tPre = it
                            editProfileViewModel.updateTPreAgeRange(it.text)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .onFocusChanged { isFocusedList[0] = it.isFocused },
                        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                        placeholder = { Text("Max") },
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
                    value = preferredRange,
                    onValueChange = {
                        preferredRange = it
                        editProfileViewModel.updateGeoRadiusRange(it.text)
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
                    value = location,
                    readOnly = true,
                    onValueChange = {},
                    trailingIcon = {
                        Image(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "",
                            modifier = Modifier.clickable {
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
                    value = bio,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedElement(
                            state = rememberSharedContentState(
                                key = "text/${bio.text}",
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
                                    onBioClick(bio.text)
                                }
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    placeholder = { Text("Write About yourself") },
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
//                InterestItem(interest,interestIcons[interest]?:defaultIcon)
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        interest?.forEach { interest ->
                            InterestItemImgCancel(
                                modifier = Modifier.padding(4.dp),
                                interest = InterestItem(
                                    interest.toString(),
                                    interestIcons[interest.toString()] ?: defaultIcon
                                ),
                                selectedInterest = selectedInterest,
                                onSelect = { selectedInterest = it },
                                onDelete = { interestToRemove ->
                                    editProfileViewModel.removeInterest(interestToRemove.name)
                                    if (selectedInterest?.name == interestToRemove.name) selectedInterest =
                                        null
                                    viewModel.toggleSelection(interestToRemove.name)
                                }

                            )
                        }
                        InterestItemImg(
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    isInterestSheetOpen = true
                                    selectedInterest = null
                                },
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Image(
                        if (isCrossVisible1) {
                            drawableToBitmap(context, R.drawable.cross).asImageBitmap()
                        } else {
                            FimageBitmap
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .then(
                                if (isCrossVisible1) Modifier
                                    .border(
                                        2.dp,
                                        Color.Red,
                                        RoundedCornerShape(16.dp)
                                    )
                                    .padding(10.dp)
                                else if (G1 != null) Modifier else Modifier
                                    .border(
                                        2.dp,
                                        ordColor,
                                        RoundedCornerShape(16.dp)
                                    )
                                    .padding(10.dp)
                            )
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        isCrossVisible1 = true
                                    },
                                    onTap = {
                                        if (isCrossVisible1) {
                                            isCrossVisible1 = false
                                            imageViewModel.updateG1(null)
                                            isG1Deleted = true
                                            fImage = null
                                        } else {
                                            try {
                                                fPhotoPickerLauncher.launch(
                                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                                )
                                            } catch (e: Exception) {
                                                Toast.makeText(context, "$e", Toast.LENGTH_LONG)
                                                    .show()
                                            }
                                        }
                                    }
                                )
                            },
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(
                        if (isCrossVisible2) {
                            drawableToBitmap(context, R.drawable.cross).asImageBitmap()
                        } else {
                            SimageBitmap
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .then(
                                if (isCrossVisible2) Modifier
                                    .border(
                                        2.dp,
                                        Color.Red,
                                        RoundedCornerShape(16.dp)
                                    )
                                    .padding(10.dp)
                                else if (G2 != null) Modifier else Modifier
                                    .border(
                                        2.dp,
                                        ordColor,
                                        RoundedCornerShape(16.dp)
                                    )
                                    .padding(10.dp)
                            )
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        isCrossVisible2 = true
                                    },
                                    onTap = {
                                        if (isCrossVisible2) {
                                            isCrossVisible2 = false
                                            imageViewModel.updateG2(null)
                                            isG2Deleted = true
                                            sImage = null
                                        } else {
                                            try {
                                                sPhotoPickerLauncher.launch(
                                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                                )
                                            } catch (e: Exception) {
                                                Toast.makeText(context, "$e", Toast.LENGTH_LONG)
                                                    .show()
                                            }
                                        }
                                    }
                                )
                            },
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(
                        if (isCrossVisible3) {
                            drawableToBitmap(context, R.drawable.cross).asImageBitmap()
                        } else {
                            TimageBitmap
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .then(
                                if (isCrossVisible3) Modifier
                                    .border(
                                        2.dp,
                                        Color.Red,
                                        RoundedCornerShape(16.dp)
                                    )
                                    .padding(10.dp)
                                else if (G3 != null) Modifier else Modifier
                                    .border(
                                        2.dp,
                                        ordColor,
                                        RoundedCornerShape(16.dp)
                                    )
                                    .padding(10.dp)
                            )
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        isCrossVisible3 = true
                                    },
                                    onTap = {
                                        if (isCrossVisible3) {
                                            isCrossVisible3 = false
                                            imageViewModel.updateG3(null)
                                            isG3Deleted = true
                                            tImage = null
                                        } else {
                                            try {
                                                tPhotoPickerLauncher.launch(
                                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                                )
                                            } catch (e: Exception) {
                                                Toast.makeText(context, "$e", Toast.LENGTH_LONG)
                                                    .show()
                                            }
                                        }
                                    }
                                )
                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .height(45.dp),
                shape = RoundedCornerShape(8.dp),

                colors = ButtonDefaults.buttonColors(containerColor = ordColor),
                onClick = {
                    coroutineScope.launch {
                        if (isProfileDeleted) {
                            imageViewModel.deleteProfileSupabase(editProfileViewModel.id.value)
                            imageViewModel.deleteProfile()
                            imageViewModel.updateProfile(null)
                        }

                        if (isG1Deleted) {
                            imageViewModel.deleteGallerySupabase("${editProfileViewModel.id.value}_g1")
                            imageViewModel.deleteGalleryImage("${editProfileViewModel.id.value}_g1")
                            imageViewModel.updateG1(null)
                        }
                        if (isG2Deleted) {
                            imageViewModel.deleteGallerySupabase("${editProfileViewModel.id.value}_g2")
                            imageViewModel.deleteGalleryImage("${editProfileViewModel.id.value}_g2")
                            imageViewModel.updateG2(null)
                        }
                        if (isG3Deleted) {
                            imageViewModel.deleteGallerySupabase("${editProfileViewModel.id.value}_g3")
                            imageViewModel.deleteGalleryImage("${editProfileViewModel.id.value}_g3")
                            imageViewModel.updateG3(null)
                        }

                        val profileUpload = async {
                            mImage?.let { uri ->
                                val success = imageViewModel.saveProfiletoSupabase(context, uri, editProfileViewModel.id.value)
                                if (success) {
                                    imageViewModel.saveProfileImage(context, uri)
                                    val base64String = uriToBase64(context, uri)
                                    val bm = base64String?.let { base64ToBitmap(it) }
                                    imageViewModel.updateProfile(bm!!)
                                    mainImageViewModel.updateProfile(bm!!)
                                } else {
                                    Log.e("UploadError", "Profile image upload failed!")
                                }
                                success
                            }
                        }

                        mImage?.let { uri ->
                            imageViewModel.saveProfiletoSupabase(
                                context,
                                uri,
                                editProfileViewModel.id.value
                            )
                            imageViewModel.saveProfileImage(context, uri)
                            uri?.let {
                                val base64String = uriToBase64(context, uri)
                                val bm = base64String?.let { base64ToBitmap(it) }
                                imageViewModel.updateProfile(bm!!)
                                mainImageViewModel.updateProfile(bm!!)
                            }


                        }

                        val Gpload = async {
                            mImage?.let { uri ->
                                val success = imageViewModel.saveProfiletoSupabase(context, uri, editProfileViewModel.id.value)
                                if (success) {
                                    imageViewModel.saveProfileImage(context, uri)
                                    val base64String = uriToBase64(context, uri)
                                    val bm = base64String?.let { base64ToBitmap(it) }
                                    imageViewModel.updateProfile(bm!!)
                                    mainImageViewModel.updateProfile(bm!!)
                                } else {
                                    Log.e("UploadError", "Profile image upload failed!")
                                }
                                success
                            }
                        }

                        val G1Upload = async{
                            fImage?.let { uri ->
                                val success = imageViewModel.saveGallerytoSupabase(
                                    context,
                                    uri,
                                    "${editProfileViewModel.id.value}_g1"
                                )

                                if(success){
                                    imageViewModel.saveGalleryImage(
                                        context,
                                        uri,
                                        "${editProfileViewModel.id.value}_g1"
                                    )
                                        val base64String = uriToBase64(context, uri)
                                        val bm = base64String?.let { base64ToBitmap(it) }
                                        imageViewModel.updateG1(bm!!)
                                        mainImageViewModel.updateG1(bm!!)
                                } else {
                                    Log.e("UploadError", "Profile image upload failed!")
                                }
                                success
                            }
                        }

                        val G2Upload = async{
                            sImage?.let { uri ->
                                val success = imageViewModel.saveGallerytoSupabase(
                                    context,
                                    uri,
                                    "${editProfileViewModel.id.value}_g2"
                                )

                                if(success){
                                    imageViewModel.saveGalleryImage(
                                        context,
                                        uri,
                                        "${editProfileViewModel.id.value}_g2"
                                    )
                                    val base64String = uriToBase64(context, uri)
                                    val bm = base64String?.let { base64ToBitmap(it) }
                                    imageViewModel.updateG2(bm!!)
                                    mainImageViewModel.updateG2(bm!!)
                                } else {
                                    Log.e("UploadError", "Profile image upload failed!")
                                }
                                success
                            }
                        }

                        val G3Upload = async{
                            tImage?.let { uri ->
                                val success = imageViewModel.saveGallerytoSupabase(
                                    context,
                                    uri,
                                    "${editProfileViewModel.id.value}_g3"
                                )

                                if(success){
                                    imageViewModel.saveGalleryImage(
                                        context,
                                        uri,
                                        "${editProfileViewModel.id.value}_g3"
                                    )
                                    val base64String = uriToBase64(context, uri)
                                    val bm = base64String?.let { base64ToBitmap(it) }
                                    imageViewModel.updateG3(bm!!)
                                    mainImageViewModel.updateG3(bm!!)
                                } else {
                                    Log.e("UploadError", "Profile image upload failed!")
                                }
                                success
                            }
                        }

                        if (profileUpload.await() == true) {
                            Pimg_url = imageViewModel.getImageUrlSupabase("profile_images", "${editProfileViewModel.id.value}.jpg")
                        } else {
                            Log.e("UploadError", "Profile image upload failed. Skipping URL fetch.")
                        }

                        if (G1Upload.await() == true) {
                            g1img_url = imageViewModel.getImageUrlSupabase("gallery_images", "${editProfileViewModel.id.value}_g1.jpg")
                        } else {
                            Log.e("UploadError", "Profile image upload failed. Skipping URL fetch.")
                        }

                        if (G2Upload.await() == true) {
                            g2img_url = imageViewModel.getImageUrlSupabase("gallery_images", "${editProfileViewModel.id.value}_g2.jpg")
                        } else {
                            Log.e("UploadError", "Profile image upload failed. Skipping URL fetch.")
                        }

                        if (G3Upload.await() == true) {
                            g3img_url = imageViewModel.getImageUrlSupabase("gallery_images", "${editProfileViewModel.id.value}_g3.jpg")
                        } else {
                            Log.e("UploadError", "Profile image upload failed. Skipping URL fetch.")
                        }


                        Log.d("iadsnmiasmdasd", Pimg_url.toString())

                        galleryList = listOfNotNull(
                            g1img_url,
                            g2img_url,
                            g3img_url
                        ).takeIf { it.isNotEmpty() }
//if(galleryList ==null){
//    galleryList = editProfileViewModel.posts.value
//}
                        Log.d("iadsnmiasmdasd", Pimg_url.toString())
                        val user = UserResponseEntity(
                            imgUrl = Pimg_url,
                            posts = galleryList,
                            id = editProfileViewModel.id.value,
                            ageGap = "${editProfileViewModel.preAgeFRange.value}-${editProfileViewModel.preAgeTRange.value}",
                            bio = editProfileViewModel.bio.value,
                            dob = editProfileViewModel.dob.value.toString(),
                            gender = editProfileViewModel.gender.value,
                            geoRadiusRange = editProfileViewModel.geoRadiusRange.value.toInt(),
                            latitude = editProfileViewModel.latitude.value,
                            longitude = editProfileViewModel.longitude.value,
                            pnumber = editProfileViewModel.pnumber.value,
                            preferredGender = editProfileViewModel.preGender.value,
                            username = editProfileViewModel.username.value,
                            interests = editProfileViewModel.interests.value,
                        )
                        Log.d("jwtinprofile", jwt.toString())
                        event(UpdateUserEvent.UpdateUser(user, jwt.toString()))
                    }
                }
            ) {
                Text(
                    text = "Save",
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}


suspend fun uploadProfileImage(
    imageUri: Uri,
    userId: String,
    contentResolver: ContentResolver
): Boolean {
    Log.d("cominginside", "yes")
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








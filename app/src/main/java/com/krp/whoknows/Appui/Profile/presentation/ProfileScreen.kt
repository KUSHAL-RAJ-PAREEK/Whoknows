package com.krp.whoknows.Appui.Profile.presentation

import android.R.attr.contentDescription
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.common.collect.Multimaps.index
import com.krp.whoknows.Appui.Profile.presentation.EditProfileComponents.ExpandableText
import com.krp.whoknows.Appui.Profile.presentation.components.InterestItemImg
import com.krp.whoknows.Appui.Profile.presentation.components.MatchItem
import com.krp.whoknows.R
import com.krp.whoknows.Utils.calculateAge
import com.krp.whoknows.Utils.drawableToBitmap
import com.krp.whoknows.Utils.getLocationCityState
import com.krp.whoknows.Utils.getLocationName
import com.krp.whoknows.Utils.interestIcons
import com.krp.whoknows.roomdb.JWTViewModel
import com.krp.whoknows.roomdb.entity.UserResponseEntity
import com.krp.whoknows.ui.theme.heartColor
import com.krp.whoknows.ui.theme.ordColor
import com.krp.whoknows.ui.theme.text_gray
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQueries.localDate

/**
 * Created by KUSHAL RAJ PAREEK on 05,March,2025
 */

//@Preview(showSystemUi = true)
//@Composable
//private fun run() {
//    var matrix by remember { mutableStateOf(ColorMatrix()) }
//
//    ProfileScreen(matrix = matrix)
//}

data class InterestItem(val name: String, val icon: ImageVector)


@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalSharedTransitionApi::class
)

@Composable
fun SharedTransitionScope.ProfileScreen(modifier: Modifier = Modifier, matrix: ColorMatrix
                                        ,navController: NavController,onITemClick: (Int, String)-> Unit, animatedVisibilityScope: AnimatedVisibilityScope,
                                        profileViewModel : ProfileViewModel,
                                        profileDetailViewModel : ProfileDetailViewModel,
                                        editProfileViewModel : EditProfileViewModel,
                                        mainImageViewModel: MainImageViewModel,
                                        imageViewModel: ImageViewModel) {
    val jwtViewModel: JWTViewModel = koinViewModel()
    val context =LocalContext.current
    val userDetails by jwtViewModel.userDetail.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        editProfileViewModel.updateId(profileDetailViewModel.id.value)
        editProfileViewModel.updatePnumber(profileDetailViewModel.pnumber.value)
        editProfileViewModel.updateUsername(profileDetailViewModel.username.value)
        editProfileViewModel.updateGeoRadiusRange(profileDetailViewModel.geoRadiusRange.value.toString())
        editProfileViewModel.updatePreGender(profileDetailViewModel.preGender.value.toString())
        editProfileViewModel.updateDOB(profileDetailViewModel.dob.value)
        editProfileViewModel.updateDobs(profileDetailViewModel.dobs.value)
        editProfileViewModel.updatePreAgeRange(profileDetailViewModel.preAgeRange.value.toString())
        editProfileViewModel.updateBio(profileDetailViewModel.bio.value.toString())
        editProfileViewModel.updateInterest(profileDetailViewModel.interests.value ?: emptyList())
        editProfileViewModel.updateLocation(profileDetailViewModel.location.value)
        editProfileViewModel.updatePosts(profileDetailViewModel.posts.value ?: emptyList())
        editProfileViewModel.updateGender(profileDetailViewModel.gender.value)
        editProfileViewModel.updateLatitude(profileDetailViewModel.latitude.value)
        editProfileViewModel.updateLongitude(profileDetailViewModel.longitude.value)
        val preferredAgeRange = editProfileViewModel.preAgeRange.value
        Log.d("preferredAgeRange",preferredAgeRange.toString())
        val parts = preferredAgeRange.split("-").map { it.toInt() }
        editProfileViewModel.updateFPreAgeRange(parts[0].toString())
        editProfileViewModel.updateTPreAgeRange(parts[1].toString())
        imageViewModel.updateProfile(mainImageViewModel.profileImage.value)
        imageViewModel.updateG1(mainImageViewModel.firstGalleryImage.value)
        imageViewModel.updateG2(mainImageViewModel.secondGalleryImage.value)
        imageViewModel.updateG3(mainImageViewModel.thirdGalleryImage.value)
        editProfileViewModel.updatemImage(profileDetailViewModel.mImage.value)
        editProfileViewModel.updatefImage(profileDetailViewModel.fImage.value)
        editProfileViewModel.updatesImage(profileDetailViewModel.sImage.value)
        editProfileViewModel.updatetImage(profileDetailViewModel.tImage.value)

    }
//    val user = profileViewModel.user.collectAsState()



//    LaunchedEffect(profileDetailViewModel.dob) {
//        dob_num = (profileDetailViewModel.dob ?: "2004-12-12").toString()
//
//        dob = calculateAge(dob_num)
//    }
    var ageGap by remember { mutableStateOf("20-30") }
    var location by remember { mutableStateOf("Jaipur") }
    var preferredGender by remember { mutableStateOf("Female") }
    var preferredRange by remember { mutableStateOf("Infinite") }
    var bio by remember { mutableStateOf("Hey, I am using WhoKnows. You never know who you might meet today!") }
    var dob by remember { mutableStateOf("20") }
    var profileImg by remember { mutableStateOf<Bitmap?>(imageViewModel.profileImage.value) }
    var interestList by remember { mutableStateOf(mutableListOf<String?>()) }
    var galleryImages by remember { mutableStateOf<List<Bitmap?>>(listOf(null,null,null)) }


    fun logValues() {
        Log.d("ProfileViewModel", """
        Username: ${profileDetailViewModel.username.value}
        Image URL: ${profileDetailViewModel.imgUrl.value}
        Geo Radius Range: ${profileDetailViewModel.geoRadiusRange.value}
        Preferred Gender: ${profileDetailViewModel.preGender.value}
        DOB: ${profileDetailViewModel.dob.value}
        Preferred Age Range: ${profileDetailViewModel.preAgeTRange.value}
        Bio: ${profileDetailViewModel.bio.value}
        Interests: ${profileDetailViewModel.interests.value}
        Posts: ${profileDetailViewModel.posts.value}
        Latitude: ${profileDetailViewModel.latitude.value}
        Longitude: ${profileDetailViewModel.longitude.value}
    """.trimIndent())
    }
    logValues()


    LaunchedEffect(Unit) {
        location = profileDetailViewModel.location.value
        ageGap = profileDetailViewModel.preAgeRange.value ?: "20-30"
        preferredGender = profileDetailViewModel.preGender.value ?: "Female"
        preferredRange = (profileDetailViewModel.geoRadiusRange.value ?: "Infinite").toString()
//        interestList = profileDetailViewModel.interests.value as MutableList<String?>
        bio = profileDetailViewModel.bio.value
        interestList = profileDetailViewModel.interests.value?.toMutableList() ?: mutableListOf()
        dob = profileDetailViewModel.dobs.value.toString()
        profileImg= mainImageViewModel.profileImage.value
        galleryImages = listOf(
            mainImageViewModel.firstGalleryImage.value,
            mainImageViewModel.secondGalleryImage.value,
            mainImageViewModel.thirdGalleryImage.value
        )
    }




//    LaunchedEffect(user.value?.username) {
//        val dobString = user.value?.dob ?: "2004-12-12"
//        val localDate = LocalDate.parse(dobString, DateTimeFormatter.ISO_LOCAL_DATE)
//        editProfileViewModel.updateGeoRadiusRange(user.value?.geoRadiusRange.toString())
//        editProfileViewModel.updatePreGender(user.value?.preferredGender.toString())
//        editProfileViewModel.updateDOB(localDate)
//        editProfileViewModel.updatePreAgeRange(user.value?.ageGap.toString())
//        editProfileViewModel.updateGeoRadiusRange(user.value?.geoRadiusRange.toString())
//        editProfileViewModel.updateBio(user.value?.bio.toString())
//        editProfileViewModel.updateInterest(user.value?.interests ?: emptyList())
//        editProfileViewModel.updatePosts(user.value?.posts ?: emptyList())
//        val preferredAgeRange = editProfileViewModel.preAgeRange.value
//        val parts = preferredAgeRange.split("-").map { it.toInt() }
//        editProfileViewModel.updateFPreAgeRange(parts[0].toString())
//        editProfileViewModel.updateTPreAgeRange(parts[1].toString())
//    }

//    Log.d("inprofilescreen", user.value.toString())
    val list = mutableListOf(
//        InterestItem("Music", Icons.Default.MusicNote),
//        InterestItem("Travel", Icons.Default.TravelExplore),
//        InterestItem("Sports", Icons.Default.Sports),
//        InterestItem("Cooking", Icons.Default.Cookie),
//        InterestItem("Gaming", Icons.Default.Games),
//        InterestItem("Photography", Icons.Default.Camera),
        "Music",
        "Travel",
        "Sports",
        "Cooking",
        "Gaming",
        "Photography"
    )

//    val images = remember {
//        mutableListOf(
//            "https://lastfm.freetls.fastly.net/i/u/770x0/95f578936a18db26a9a3d9ce7e62439c.jpg#95f578936a18db26a9a3d9ce7e62439c",
//            "https://lastfm.freetls.fastly.net/i/u/770x0/eebfb655fdb21e3133d9fb82741128d0.jpg#eebfb655fdb21e3133d9fb82741128d0",
//            "https://lastfm.freetls.fastly.net/i/u/770x0/46043eaa0841aa679e9398aee2e1cc7c.jpg#46043eaa0841aa679e9398aee2e1cc7c"
//        )
//    }


    val scrollState = rememberScrollState()

    val shouldCollapse by remember {
        derivedStateOf { scrollState.value > 0 }
    }
    val defaultIcon = Icons.Default.Star

    val progress by animateFloatAsState(
        targetValue = if (shouldCollapse) 1f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "Profile Header Progress Animation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFC973FF),
                        Color(0xFFAEBAF8),
                    )
                )
            )

    ) {
        ProfileHeader(progress = progress,animatedVisibilityScope = animatedVisibilityScope,profileDetailViewModel =profileDetailViewModel, img = profileImg, gender = if(profileDetailViewModel.gender.value == "MALE") true else false )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 20.dp)
                .verticalScroll(scrollState)
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 3.dp)
                    .size(width = 40.dp, height = 5.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.5f))
            )
            Spacer(modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = dob.toString(),
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.hellix_medium))
                        )
                        Spacer(modifier.height(3.dp))
                        Text(
                            text = location.trim()
                                .split(Regex("\\s+"))
                                .chunked(5)
                                .joinToString("\n") { it.joinToString(" ") },
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.hellix_medium))
                        )
                    }

                    Row(verticalAlignment  = Alignment.CenterVertically){
                        MatchItem(icon = ImageVector.vectorResource(R.drawable.heart), status = profileDetailViewModel.isMatch.value)
                        Spacer(Modifier.width(5.dp))
                        Icon(modifier = Modifier.clickable{
                            onITemClick(R.drawable.p_image,"Sabrina Carpainter")
                        },imageVector = Icons.Default.Edit, contentDescription = "edit", tint = ordColor)
                    }
                }
                Spacer(modifier.height(20.dp))

                Text(
                    text = "About",
                    color = text_gray,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier.height(15.dp))

                ExpandableText(
                    text = (if(bio != "")bio else "Hey, I am using WhoKnows. You never know who you might meet today!" + ".").toString(),
                    fontSize = 15.sp,
                    font =  FontFamily(Font(R.font.hellix_medium))
                    )

//                Text(
//                    text = (if(bio != "")bio else "Hey, I am using WhoKnows. You never know who you might meet today!" + ".").toString(),
//                    color = Color.Black,
//                    fontFamily = FontFamily(Font(R.font.hellix_medium))
//                )

                Spacer(modifier.height(20.dp))

                Text(
                    text = "Interest",
                    color = text_gray,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )
                Spacer(modifier.height(14.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if(interestList.isEmpty()){
                        InterestItemImg(
                            modifier = Modifier.padding(4.dp),
                            interest = InterestItem("Whoknows",defaultIcon)
                        )
                    }else{
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            interestList.forEach { interest ->
                                InterestItemImg(
                                    modifier = Modifier.padding(4.dp),
                                    interest = InterestItem(interest.toString(),interestIcons[interest.toString()]?:defaultIcon)
                                )
                            }
                        }

                    }

                }

                Spacer(modifier.height(20.dp))

                Text(
                    text = "Preferred Age",
                    color = text_gray,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier.height(15.dp))

                Text(
                    text = ageGap,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.hellix_medium))
                )

                Spacer(modifier.height(20.dp))

                Text(
                    text = "Preferred Gender",
                    color = text_gray,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier.height(15.dp))

                Text(
                    text = preferredGender,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.hellix_medium))
                )

                Spacer(modifier.height(20.dp))

                Text(
                    text = "Preferred Range",
                    color = text_gray,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier.height(15.dp))

                Text(
                    text = "$preferredRange Km",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.hellix_medium))
                )

                Spacer(modifier.height(20.dp))

                Text(
                    text = "Gallery",
                    color = text_gray,
                    fontFamily = FontFamily(Font(R.font.hellix_regular))
                )

                Spacer(modifier.height(15.dp))
                ImageSlider(images = galleryImages, matrix = matrix)


                Spacer(modifier.height(50.dp))

                Row {
                    Text(
                        text = "Made with ",
                        color = text_gray,
                        fontFamily = FontFamily(Font(R.font.poppins_bold))
                    )

                    Icon(
                        painter = painterResource(R.drawable.heart),
                        tint = heartColor,
                        modifier = Modifier.size(20.dp), contentDescription = null
                    )


                    Text(
                        text = " in jaipur",
                        color = text_gray,
                        fontFamily = FontFamily(Font(R.font.poppins_bold))
                    )
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMotionApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ProfileHeader(progress: Float,animatedVisibilityScope : AnimatedVisibilityScope,
                                        profileDetailViewModel : ProfileDetailViewModel,
                                        img : Bitmap?,
                                        gender : Boolean) {

    val gender = gender
    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }

    var username by remember { mutableStateOf("Whoknows") }

    LaunchedEffect(Unit) {
        username = (profileDetailViewModel.username.value ?: "Whoknows").toString()
    }

//    var profileImage by remember { mutableStateOf<Any>(R.drawable.p_image) }
//
//    LaunchedEffect(user) {
//        user?.imgUrl?.let { url ->
//            val request = ImageRequest.Builder(context)
//                .data(url)
//                .memoryCacheKey(url) // Enables faster retrieval
//                .diskCacheKey(url)
//                .crossfade(true)
//                .build()
//            profileImage = request
//        }
//    }


    MotionLayout(
        motionScene = MotionScene(motionScene),
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        val properties = motionProperties(id = "profile_pic")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            properties.value.color("box_background1"),
                            properties.value.color("box_background2"),
                        )
                    )
                )
                .layoutId("box"),
        )

        val bitmapImage = img?.asImageBitmap()
            ?: drawableToBitmap(context, if(gender) R.drawable.bp_img_placeholder else  R.drawable.p_img_placeholder).asImageBitmap()
        Image(
            bitmap = bitmapImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(if (properties.value.int("shape_r") == 0) RectangleShape else CircleShape)
                .layoutId("profile_pic")
                .sharedElement(
                    state = rememberSharedContentState(key = "image/${R.drawable.p_image}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 1000)
                    }
                )
                .graphicsLayer { alpha = 0.99f }
        )
        Text(
            text = username,
            fontSize = 25.sp,
            modifier = Modifier
                .layoutId("username")
                .sharedElement(
                    state = rememberSharedContentState(
                        key = "text/Sabrina Carpainter",
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 1000)
                    }
                ),
            fontFamily = FontFamily(Font(R.font.vesperlibre_medium))
        )
    }
}

@Composable
fun ImageSlider(modifier: Modifier = Modifier, images: List<Bitmap?>, matrix: ColorMatrix) {
    val pagerState = rememberPagerState { 3 }
    val context = LocalContext.current
    HorizontalPager(
        state = pagerState
    ) { index ->
        val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
        val imageSize by animateFloatAsState(
            targetValue = if (pageOffset != 0.0f) 0.75f else 1f,
            animationSpec = tween(durationMillis = 300)
        )


        val bitmapImage = images.getOrNull(index)?.asImageBitmap()
            ?: drawableToBitmap(context, R.drawable.p_image).asImageBitmap()

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .clip(RoundedCornerShape(16.dp))
                .graphicsLayer {
                    scaleX = imageSize
                    scaleY = imageSize
                    shape = RoundedCornerShape(16.dp)
                    clip = true
                },
            bitmap =bitmapImage as ImageBitmap, contentDescription = "image",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.colorMatrix(matrix)
        )
    }
}



package com.krp.whoknows.Utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Anchor
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.CurrencyBitcoin
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.Hiking
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicVideo
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Sailing
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Snowboarding
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.SportsCricket
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.SportsGolf
import androidx.compose.material.icons.filled.SportsHandball
import androidx.compose.material.icons.filled.SportsMma
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.SportsTennis
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.filled.Waves
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.wear.compose.material.ContentAlpha
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.krp.whoknows.Appui.Profile.presentation.InterestItem
import com.krp.whoknows.R
import com.krp.whoknows.SupabaseClient.supabaseClient
import com.krp.whoknows.roomdb.ImageConverter
import com.krp.whoknows.ui.theme.Cyan
import com.krp.whoknows.ui.theme.LightBlue
import com.krp.whoknows.ui.theme.Purple
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.io.ByteArrayInputStream
import java.io.IOException
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import kotlin.collections.mutableListOf
import androidx.core.graphics.createBitmap
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Created by KUSHAL RAJ PAREEK on 11,February,2025
 */




fun checkForPermission(context: Context): Boolean {
    return !(ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED)
}

fun String.capitaliseIt() = this.lowercase().capitalize(Locale.current)

fun calculateDistance(latlngList: List<LatLng>): Double {
    var totalDistance = 0.0

    for (i in 0 until latlngList.size - 1) {
        totalDistance += SphericalUtil.computeDistanceBetween(latlngList[i],latlngList[i + 1])

    }

    return (totalDistance * 0.001)
}

fun calculateSurfaceArea(latlngList: List<LatLng>): Double {
    if (latlngList.size < 3) {
        return 0.0
    }
    return SphericalUtil.computeArea(latlngList)
}

fun formattedValue(value: Double) = String.format("%.2f",value)



@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, onLocationFetched: (location: LatLng) -> Unit) {
    var loc: LatLng
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            
            if (location != null) {

                val latitude = location.latitude
                val longitude = location.longitude
                loc = LatLng(latitude,longitude)
                onLocationFetched(loc)
            }
        }
        .addOnFailureListener { exception: Exception ->
            Log.d("MAP-EXCEPTION",exception.message.toString())
        }

}

fun bitmapDescriptor(
    context: Context,
    resId: Int
): BitmapDescriptor? {

    val drawable = ContextCompat.getDrawable(context, resId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}
 @RequiresApi(Build.VERSION_CODES.TIRAMISU)
 fun getLocationName(latitude: Double, longitude: Double, context: Context): String {
    val geocoder = Geocoder(context)
    try {
//        geocoder.getFromLocation(latitude,longitude,1,object : Geocoder.GeocodeListener{
//            override fun onGeocode(addresses: MutableList<Address>) {
//
//                // code
//            }
//            override fun onError(errorMessage: String?) {
//                super.onError(errorMessage)
//
//            }
//
//        })
        val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses?.isNotEmpty()!!) {
            val address = addresses[0]
            val buildingName = address.featureName ?: ""
            val subBuildingName = address.subThoroughfare ?: ""
            val thoroughfare = address.thoroughfare ?: ""
            val subLocality = address.subLocality ?: ""
            val locality = address.locality ?: ""
            val adminArea = address.adminArea ?: ""
            val countryName = address?.countryName ?: ""
            val postalCode = address?.postalCode ?: ""

            val fullAddress = buildString {
                if (buildingName.isNotBlank()) append("$buildingName, ")
                if (subBuildingName.isNotBlank()) append("$subBuildingName, ")
                if (thoroughfare.isNotBlank()) append("$thoroughfare, ")
                if (subLocality.isNotBlank()) append("$subLocality, ")
                if (locality.isNotBlank()) append("$locality, ")
                if (adminArea.isNotBlank()) append("$adminArea")
                if (countryName.isNotBlank()) append("$countryName")
                if (postalCode.isNotBlank()) append("$postalCode")
            }

            Log.d("nameaddress", fullAddress)
            return fullAddress
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return ""
}

fun getLocationCityState(latitude: Double, longitude: Double, context: Context): String {
    val geocoder = Geocoder(context)

    try {
        val addresses: List<Address>? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(latitude, longitude, 1)
        } else {
            // Suppress warning for older API usage
            @Suppress("DEPRECATION")
            geocoder.getFromLocation(latitude, longitude, 1)
        }

        if (!addresses.isNullOrEmpty()) {
            val address = addresses[0]
            val city = address.locality ?: ""
            val state = address.adminArea ?: ""

            return listOf(city, state).filter { it.isNotBlank() }.joinToString(", ")
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return ""
}


object LocalDateSerializer : KSerializer<LocalDate> {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(formatter)) // Serialize to "yyyy-MM-dd"
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), formatter) // Deserialize from "yyyy-MM-dd"
    }
}


operator fun PaddingValues.times(value: Float): PaddingValues = PaddingValues(
    top = calculateTopPadding() * value,
    bottom = calculateBottomPadding() * value,
    start = calculateStartPadding(LayoutDirection.Ltr) * value,
    end = calculateEndPadding(LayoutDirection.Ltr) * value
)

fun Easing.transform(from: Float, to: Float, value: Float): Float {
    return transform(((value - from) * (1f / (to - from))).coerceIn(0f, 1f))
}

@Composable
fun CustomMultilineHintTextField(modifier: Modifier = Modifier,
                                 value : String,
                                 onValueChanged:(String)-> Unit,
                                 hintText : String = "",
                                 maxLines:Int = 4) {
BasicTextField(value = value, onValueChange = onValueChanged,
    maxLines = maxLines,
    decorationBox = {innerTextField ->
        Box(modifier = modifier){
            if(value.isEmpty()){
                Text(
                    text = hintText,
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                )
            }
            innerTextField()
        }
    }
    )

}


@Composable
fun TypewriteText(
    text: String,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    spec: AnimationSpec<Int> = tween(durationMillis = text.length * 100, easing = LinearEasing),
    preoccupySpace: Boolean = true
) {
    val gradientColors = listOf(Cyan, LightBlue, Purple)
    var textToAnimate by remember { mutableStateOf("") }

    val index = remember {
        Animatable(initialValue = 0, typeConverter = Int.VectorConverter)
    }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            textToAnimate = text
            index.animateTo(text.length, spec)
        } else {
            index.snapTo(0)
        }
    }

    LaunchedEffect(text) {
        if (isVisible) {
            index.snapTo(0)
            textToAnimate = text
            index.animateTo(text.length, spec)
        }
    }

    Box(modifier = modifier) {
        if (preoccupySpace && index.isRunning) {

            Text(
                text = text,
                fontSize = 40.sp,
                fontFamily = FontFamily(Font(R.font.yellowtail)),
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                ),
                fontWeight = FontWeight.Bold,
                modifier = modifier.alpha(0f)
            )
        }

        Text(
            text = textToAnimate.substring(0, index.value),
            fontSize = 190.sp,
            fontFamily = FontFamily(Font(R.font.twinklestar)),
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = gradientColors
                )
            )
        )
    }
}

enum class ScaleTransitionDirection {
    INWARDS, OUTWARDS
}

fun scaleIntoContainer(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.INWARDS,
    initialScale: Float = if (direction == ScaleTransitionDirection.OUTWARDS) 0.9f else 1.1f
): EnterTransition {
    return scaleIn(
        animationSpec = tween(220, delayMillis = 90),
        initialScale = initialScale
    ) + fadeIn(animationSpec = tween(220, delayMillis = 90))
}

fun scaleOutOfContainer(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.OUTWARDS,
    targetScale: Float = if (direction == ScaleTransitionDirection.INWARDS) 0.9f else 1.1f
): ExitTransition {
    return scaleOut(
        animationSpec = tween(
            durationMillis = 220,
            delayMillis = 90
        ), targetScale = targetScale
    ) + fadeOut(tween(delayMillis = 90))
}

fun calculateAge(dob: String): Int {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val birthDate = LocalDate.parse(dob, formatter)
    val currentDate = LocalDate.now()
    return Period.between(birthDate, currentDate).years
}

val interestIcons = mapOf(
    // Fitness & Health
    "Gyming" to Icons.Default.FitnessCenter,
    "Working Out" to Icons.Default.FitnessCenter,
    "Fitness" to Icons.Default.FitnessCenter,
    "Weightlifting" to Icons.Default.FitnessCenter,
    "Cardio Training" to Icons.Default.FavoriteBorder,
    "Running" to Icons.Default.DirectionsRun,
    "Jogging" to Icons.Default.DirectionsRun,
    "Walking" to Icons.Default.DirectionsWalk,
    "Cycling" to Icons.Default.DirectionsBike,
    "Stretching" to Icons.Default.SelfImprovement,
    "Calisthenics" to Icons.Default.Accessibility,
    "Zumba" to Icons.Default.MusicNote,
    "Aerobics" to Icons.Default.SelfImprovement,
    "Swimming" to Icons.Default.Pool,
    "Hiking" to Icons.Default.Terrain,
    "Meditation" to Icons.Default.Spa,
    "Yoga" to Icons.Default.SelfImprovement,
    "Pilates" to Icons.Default.SelfImprovement,

    // Strength & Combat Sports
    "Bodybuilding" to Icons.Default.FitnessCenter,
    "Powerlifting" to Icons.Default.FitnessCenter,
    "CrossFit" to Icons.Default.FitnessCenter,
    "Martial Arts" to Icons.Default.SportsMma,
    "Judo" to Icons.Default.SportsMma,
    "Karate" to Icons.Default.SportsMma,
    "Taekwondo" to Icons.Default.SportsMma,
    "Boxing" to Icons.Default.SportsMma,
    "Kickboxing" to Icons.Default.SportsMma,
    "Wrestling" to Icons.Default.SportsMma,
    "MMA" to Icons.Default.SportsMma,
    "Brazilian Jiu-Jitsu" to Icons.Default.SportsMma,
    "Fencing" to Icons.Default.SportsHandball,
    "Archery" to Icons.Default.SportsHandball,

    // Technology & Development
    "Competitive Programming" to Icons.Default.Code,
    "Software Development" to Icons.Default.DeveloperMode,
    "Mobile App Development" to Icons.Default.Smartphone,
    "Game Development" to Icons.Default.SportsEsports,
    "Web Development" to Icons.Default.Laptop,
    "Data Science" to Icons.Default.PieChart,
    "Machine Learning" to Icons.Default.Memory,
    "Artificial Intelligence" to Icons.Default.Computer,
    "Deep Learning" to Icons.Default.Memory,
    "Cybersecurity" to Icons.Default.Shield,
    "Ethical Hacking" to Icons.Default.Lock,
    "Cloud Computing" to Icons.Default.Cloud,
    "Blockchain" to Icons.Default.MonetizationOn,
    "Smart Contracts" to Icons.Default.AttachMoney,
    "Cryptocurrency" to Icons.Default.CurrencyBitcoin,

    // Finance & Investing
    "Investing" to Icons.Default.AccountBalance,
    "Trading" to Icons.Default.MonetizationOn,
    "Forex Trading" to Icons.Default.AttachMoney,
    "Options Trading" to Icons.Default.TrendingUp,
    "Real Estate" to Icons.Default.Home,

    // Entrepreneurship
    "Entrepreneurship" to Icons.Default.Business,
    "Side Hustles" to Icons.Default.WorkOutline,
    "Freelancing" to Icons.Default.WorkOutline,
    "Startup Culture" to Icons.Default.Business,
    "Small Business" to Icons.Default.Store,

    // Digital & Content Creation
    "E-commerce" to Icons.Default.ShoppingCart,
    "Dropshipping" to Icons.Default.LocalShipping,
    "Affiliate Marketing" to Icons.Default.TrendingUp,
    "Social Media Marketing" to Icons.Default.Share,
    "Digital Marketing" to Icons.Default.Public,
    "SEO" to Icons.Default.Search,
    "Content Creation" to Icons.Default.Create,
    "Blogging" to Icons.Default.Article,
    "Vlogging" to Icons.Default.Videocam,
    "Podcasting" to Icons.Default.Mic,

    "Travel" to Icons.Default.TravelExplore,
    "Music" to Icons.Default.MusicNote,
    "Sports" to Icons.Default.Sports,
    "Cooking" to Icons.Default.Cookie,
    "Gaming" to  Icons.Default.Games,
    "Photography" to Icons.Default.Camera,

    // Communication & Media
    "Public Speaking" to Icons.Default.RecordVoiceOver,
    "Debating" to Icons.Default.Gavel,
    "Writing" to Icons.Default.Edit,
    "Copywriting" to Icons.Default.Edit,
    "Technical Writing" to Icons.Default.Article,
    "Poetry" to Icons.Default.MenuBook,
    "Screenwriting" to Icons.Default.Movie,
    "Journalism" to Icons.Default.Newspaper,

    // Science & Academics
    "Astronomy" to Icons.Default.Public,
    "Astrophysics" to Icons.Default.Public,
    "Space Exploration" to Icons.Default.RocketLaunch,
    "Rocket Science" to Icons.Default.Science,
    "Quantum Physics" to Icons.Default.Science,
    "Biotechnology" to Icons.Default.Science,
    "Genetics" to Icons.Default.Biotech,
    "Neuroscience" to Icons.Default.Psychology,
    "Psychology" to Icons.Default.Psychology,
    "Philosophy" to Icons.Default.Book,
    "Sociology" to Icons.Default.Groups,
    "Anthropology" to Icons.Default.AccountCircle,

    // History & Politics
    "History" to Icons.Default.Book,
    "Archaeology" to Icons.Default.Landscape,
    "Political Science" to Icons.Default.Gavel,
    "Economics" to Icons.Default.TrendingUp,
    "Personal Finance" to Icons.Default.AttachMoney,

    // Lifestyle & Self-Improvement
    "Minimalism" to Icons.Default.AllInbox,
    "Mindfulness" to Icons.Default.Spa,
    "Self-Improvement" to Icons.Default.SelfImprovement,
    "Spirituality" to Icons.Default.Spa,

    // Esports & Gaming
    "Esports" to Icons.Default.SportsEsports,
    "Video Gaming" to Icons.Default.SportsEsports,
    "Board Games" to Icons.Default.Extension,
    "Card Games" to Icons.Default.Collections,
    "Chess" to Icons.Default.SportsEsports,
    "Go" to Icons.Default.SportsEsports,
    "Scrabble" to Icons.Default.SportsEsports,
    "Sudoku" to Icons.Default.Calculate,
    "Puzzles" to Icons.Default.Extension,

    // Art & Creativity
    "Magic Tricks" to Icons.Default.AutoAwesome,
    "Origami" to Icons.Default.Create,
    "Calligraphy" to Icons.Default.Create,
    "Sketching" to Icons.Default.Brush,
    "Drawing" to Icons.Default.Brush,
    "Painting" to Icons.Default.Palette,
    "Graffiti Art" to Icons.Default.Brush,
    "Graphic Design" to Icons.Default.DesignServices,
    "3D Modeling" to Icons.Default.SmartToy,

    // Performing Arts
    "Photography" to Icons.Default.Camera,
    "Cinematography" to Icons.Default.Movie,
    "Filmmaking" to Icons.Default.Movie,
    "Acting" to Icons.Default.TheaterComedy,
    "Theater" to Icons.Default.TheaterComedy,
    "Dancing" to Icons.Default.MusicNote,
    "Singing" to Icons.Default.MusicNote,
    "Playing Guitar" to Icons.Default.MusicNote,
    "Playing Piano" to Icons.Default.MusicNote,

    // Reading & Media
    "Reading Books" to Icons.Default.MenuBook,
    "Science Fiction" to Icons.Default.Book,
    "Fantasy Books" to Icons.Default.Book,
    "Mystery Books" to Icons.Default.Book,
    "Thrillers" to Icons.Default.Book,
    "Classic Literature" to Icons.Default.Book,

    // Food & Drink
    "Cooking" to Icons.Default.Restaurant,
    "Baking" to Icons.Default.Cake,
    "Wine Tasting" to Icons.Default.LocalBar,
    "Coffee Brewing" to Icons.Default.Coffee,

    // Travel & Adventure
    "Camping" to Icons.Default.Terrain,
    "Backpacking" to Icons.Default.Hiking,
    "Hiking" to Icons.Default.Terrain,
    "Wildlife Watching" to Icons.Default.Forest,

    // Miscellaneous
    "Tattoo Art" to Icons.Default.ColorLens,
    "ASMR" to Icons.Default.Hearing,
    "Memes" to Icons.Default.EmojiEmotions
)

fun getProfileImageUrl(userId: String): String {
    return supabaseClient().storage
        .from("profile_images").publicUrl("$userId.webp")
}

fun String.toBitmap(): Bitmap? {
    return try {
        val byteArray = ImageConverter.base64ToByteArray(this)
        val inputStream = ByteArrayInputStream(byteArray)
      BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
fun getImageBitmapOrPlaceholder(image: Bitmap?, gender :String? = null): ImageBitmap {
    return image?.asImageBitmap() ?: run {
        val context = LocalContext.current
var placeholder: Int? = null

        if(gender == "MALE"){
            placeholder = R.drawable.bp_img_placeholder
        }else if(gender == "FEMALE"){
            placeholder = R.drawable.p_img_placeholder
        }else{
            placeholder = R.drawable.add_file_button
        }
        val drawable = ContextCompat.getDrawable(context, placeholder)

        if (drawable is BitmapDrawable) {
            drawable.bitmap.asImageBitmap()
        } else {
            val bitmap = createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap.asImageBitmap()
        }
    }
}

//fun drawableToBitmap(drawable: Drawable): Bitmap {
//    val bitmap = createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight)
//    val canvas = Canvas(bitmap)
//    drawable.setBounds(0, 0, canvas.width, canvas.height)
//    drawable.draw(canvas)
//    return bitmap
//}

suspend fun convertImageUrlToBase64(imageUrl: String): String? {
    return withContext(Dispatchers.IO) {
        try {
            val client = HttpClient()
            val byteArray = client.get(imageUrl).readBytes()

            Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}


fun drawableToBitmap(context: Context, drawableId: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId)
        ?: return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}
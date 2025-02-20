package com.krp.whoknows.Utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


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
            // Handle failure to get location
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
 fun getLocationName(latitude: Double, longitude: Double, context : Context): String {
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



package com.krp.whoknows.Appui.userInfo

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.wear.compose.material.Icon
import androidx.xr.compose.testing.toDp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.rememberCameraPositionState
import com.krp.whoknows.Utils.getCurrentLocation
import com.krp.whoknows.model.LatLongs
import com.krp.whoknows.model.LineType
import com.krp.whoknows.ui.theme.ordColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

/**
 * Created by KUSHAL RAJ PAREEK on 11,February,2025
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavController, context: Context) {
    var showMap by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var location by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    val mapProperties by remember { mutableStateOf(MapProperties()) }
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current).toDp()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 15f)
    }

    LaunchedEffect(searchQuery.text) {
        if (searchQuery.text.isNotBlank()) {
            val result = geocodeLocation(context, searchQuery.text)
            result?.let {
                Log.d("hellolat", it.toString())
                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(it, 16f))
                location = it
                Log.d("hellolat", location.toString())
            }
        }
    }



    getCurrentLocation(context) {
        location = it
        showMap = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        MyMap(
            context = context,
            cameraPositionState = cameraPositionState,
            mapProperties = mapProperties,
        ) {
            location = it
        }

        SearchBar(searchQuery = searchQuery, onSearch = { newValue -> searchQuery = newValue })

        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .size(35.dp), imageVector = Icons.Default.LocationOn,
            colorFilter = ColorFilter.tint(ordColor),
            contentDescription = ""
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
                navController.popBackStack()
                navController.navigate(
                    com.krp.whoknows.Navigation.LatLong(
                        location.latitude.toString(),
                        location.longitude.toString()
                    )
                )
            },
            shape = CircleShape,
            containerColor = ordColor,
            modifier = Modifier
                .padding(bottom = if (imeHeight > 0.dp) imeHeight + 20.dp else 40.dp)
                .size(56.dp)
                .shadow(8.dp, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next",
                tint = Color.White
            )
        }
    }
}

suspend fun geocodeLocation(context: Context, query: String): LatLng? {
    return withContext(Dispatchers.IO) {
        try {
            val geocoder = Geocoder(context)
            val addressList = geocoder.getFromLocationName(query, 1)
            if (!addressList.isNullOrEmpty()) {
                val location = addressList[0]
                return@withContext LatLng(location.latitude, location.longitude)
            }
        } catch (e: IOException) {
            Log.e("MapSearch", "Geocoding failed: ${e.localizedMessage}")
        }
        return@withContext null
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchQuery: TextFieldValue, onSearch: (TextFieldValue) -> Unit) {
    var localQuery by remember { mutableStateOf(searchQuery) }
    OutlinedTextField(
        value = localQuery,
        onValueChange = {
            localQuery = it
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = ordColor,
            unfocusedBorderColor = Color.Gray
        ),
        shape = RoundedCornerShape(20.dp),
        label = { Text(text = "Search Location") },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .onKeyEvent { keyEvent ->
                if (keyEvent.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                    onSearch(localQuery)
                    true
                } else {
                    false
                }
            }
    )
}
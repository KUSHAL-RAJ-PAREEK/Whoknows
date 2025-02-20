package com.krp.whoknows.Appui.userInfo


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.krp.whoknows.Utils.calculateDistance
import com.krp.whoknows.Utils.calculateSurfaceArea
import com.krp.whoknows.Utils.capitaliseIt
import com.krp.whoknows.Utils.formattedValue
import com.krp.whoknows.model.LineType
import io.ktor.websocket.Frame

/**
 * Created by KUSHAL RAJ PAREEK on 13,February,2025
 */


@Composable
fun MyMap(
    context: Context,
    cameraPositionState: CameraPositionState,
    mapProperties: MapProperties = MapProperties(),
    onLocationChange: (LatLng) -> Unit
) {

    val mapStyleOptions = remember {
        MapStyleOptions.loadRawResourceStyle(context, com.krp.whoknows.R.raw.map)
    }



    LaunchedEffect(cameraPositionState.position) {
        snapshotFlow { cameraPositionState.position.target }
            .collect { newLatLng ->
                onLocationChange(newLatLng)
            }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false),
            properties = mapProperties.copy(mapStyleOptions = mapStyleOptions)
        )


        }
}
package com.example.mobile_etno.views.components

import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun GoogleMapSection(latitude: String, longitude: String, title: String, section: String){

    val placeInGoogleMap = LatLng(latitude.toDouble(), longitude.toDouble())
    val cameraPositionState = rememberCameraPositionState{ position = CameraPosition.fromLatLngZoom(placeInGoogleMap, 12f) }

    GoogleMap(
        modifier = Modifier
            .width(350.dp)
            .height(300.dp),
        cameraPositionState = cameraPositionState
    ){
        Marker(
            state = MarkerState(position = placeInGoogleMap),
            title = title,
            snippet = "$section $title"
        )
    }
}
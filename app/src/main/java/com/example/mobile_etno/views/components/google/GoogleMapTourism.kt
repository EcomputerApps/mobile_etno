package com.example.mobile_etno.views.components.google

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mobile_etno.R
import com.example.mobile_etno.models.Tourism
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun GoogleMapTourism(
    listTourism: MutableList<Tourism>
){
    val currentContext = LocalContext.current
    val properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
    val uiSettings by remember { mutableStateOf(MapUiSettings()) }

    GoogleMap(
        uiSettings = uiSettings,
        properties = properties,
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
        cameraPositionState = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(
            LatLng(42.13202335670619,-0.40816585603218675),
            14f
        ) }
    ){
        listTourism.forEach {
            tourism ->

            val tourismType: Int  = when(tourism.type){
                "Hotel" -> R.drawable.hotel
                "Monumento" -> R.drawable.monumental
                "Museo" -> R.drawable.museo
                else -> { R.drawable.hotel }
            }

            Marker(
                icon = bitmapDescriptor(currentContext, tourismType),
                state = MarkerState(
                    position = LatLng(
                        tourism.longitude!!.toDouble(),
                        tourism.latitude!!.toDouble()
                    )
                ),
                title = tourism.title,
                snippet = "Turismo - ${tourism.type}"
            )
        }
    }
}
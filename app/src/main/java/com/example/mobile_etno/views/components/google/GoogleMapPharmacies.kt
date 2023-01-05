package com.example.mobile_etno.views.components.google

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.mobile_etno.R
import com.example.mobile_etno.models.Pharmacy
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun GoogleMapPharmacies(
    listPharmacies: MutableList<Pharmacy>,
    click: (name: String) -> Unit
){
    val currentContext = LocalContext.current
    val properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
    val uiSettings by remember { mutableStateOf(MapUiSettings()) }

    GoogleMap(
        uiSettings = uiSettings,
        properties = properties,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        cameraPositionState = rememberCameraPositionState{ position = CameraPosition.fromLatLngZoom(LatLng(42.13202335670619,-0.40816585603218675), 14f) }
    ){
        listPharmacies.forEach {
            pharmacy ->

            Marker(
                onInfoWindowClick = {
                                    click.invoke(pharmacy.name!!)
                },
                icon = if (pharmacy.type == "Normal") bitmapDescriptor(currentContext, R.drawable.blue_pharmacy) else bitmapDescriptor(currentContext, R.drawable.red_pharmacy),
                state = MarkerState(
                    position = LatLng(
                        pharmacy.longitude!!.toDouble(),
                        pharmacy.latitude!!.toDouble()
                    )
                ),
                title = pharmacy.name,
                snippet = "Horario: ${pharmacy.schedule}",
            )
        }
    }
}

fun bitmapDescriptor(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, 120, 120)

    val bm = Bitmap.createBitmap(
        120,
        120,
        Bitmap.Config.ARGB_8888
    )
    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}
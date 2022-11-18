package com.example.mobile_etno.components

import android.content.res.Resources
import android.os.Build
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobile_etno.R
import com.example.mobile_etno.internalization.LocaleHelper
import com.example.mobile_etno.viewmodels.MenuViewModel
import java.util.*


@Composable
fun TopBarHome(){
    var currentContext = LocalContext.current
    var resources: Resources

    TopAppBar(
        title = { Text("Etno", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), color = Color.White) },
        navigationIcon = {
            IconButton(onClick = { Toast.makeText(currentContext, "menu", Toast.LENGTH_SHORT).show() }) {
                Icon(Icons.Filled.Menu, tint = Color.White, contentDescription = null)
            }
        },
        backgroundColor = Color.Red,
        actions = {
            // RowScope here, so these icons will be placed horizontally
            IconButton(onClick = {

            }) {
                Row() {
                    Icon(painterResource(id = R.drawable.internalization_icon), contentDescription = "Localized description", modifier = Modifier.size(25.dp), tint = Color.White)
                    Text(text = "EN", color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.padding(2.dp))
                }
            }
        }
    )
}

package com.example.mobile_etno.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_etno.R

@Composable
fun TopBarScreen(screenName: String, navHostController: NavHostController){
    TopAppBar(title = { Text(text = screenName, textAlign = TextAlign.Center, modifier = Modifier.width(270.dp), color = Color.White )},
    navigationIcon = {
        IconButton(onClick = { navHostController.navigate("Home")}) {
            Icon(painter = painterResource(id = R.drawable.back_arrow), tint = Color.White, contentDescription = "back to Home screen", modifier = Modifier.size(30.dp))
        }
    },
        backgroundColor = Color.Red
    )
}
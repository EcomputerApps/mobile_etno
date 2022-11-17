package com.example.mobile_etno.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobile_etno.R

@Preview
@Composable
fun TopBarHome(){
    TopAppBar(
        title = { Text("Etno", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), color = Color.White) },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Menu, tint = Color.White, contentDescription = null)
            }
        },
        backgroundColor = Color.Red,
        actions = {
            // RowScope here, so these icons will be placed horizontally
            IconButton(onClick = { /* doSomething() */ }) {
                Row() {
                    Icon(painterResource(id = R.drawable.internalization_icon), contentDescription = "Localized description", modifier = Modifier.size(25.dp), tint = Color.White)
                    Text(text = "ES", color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.padding(2.dp))
                }
            }
        }
    )
}
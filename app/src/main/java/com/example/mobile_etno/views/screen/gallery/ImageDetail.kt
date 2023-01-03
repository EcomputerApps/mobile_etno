package com.example.mobile_etno.views.screen.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImageDetail(
    link: String
){
    Surface(
        color = Color.Black.copy(alpha = 0.9f),
        modifier = Modifier
        .fillMaxSize()
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = link),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )
    }
}
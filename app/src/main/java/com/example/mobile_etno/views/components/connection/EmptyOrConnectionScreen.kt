package com.example.mobile_etno.views.components.connection

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mobile_etno.R

@Composable
fun EmptyOrConnectionScreen(
    @DrawableRes
    icon: Int,
    prop: String
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = "")
            Text(
                text = prop,
                fontWeight = FontWeight.W700,
                fontSize = 14.sp,
            )
        }
    }
}
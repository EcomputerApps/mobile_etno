package com.example.mobile_etno.views.screen.incident

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IncidentCard(
    title: String,
    description: String,
    onClick: () -> Unit
){
    Card(
        backgroundColor = Color.Red,
        contentColor = Color.White,
        elevation = 3.dp,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                    Icon(imageVector = Icons.Filled.Warning, contentDescription = "Warning", modifier = Modifier.size(14.dp), tint = Color.White)
                    Text(text = "INCIDENTE - $title", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(painter = painterResource(id = com.example.mobile_etno.R.drawable.right), contentDescription = "right", modifier = Modifier.align(
                        Alignment.BottomEnd))
                }
            }
        }
    }
}
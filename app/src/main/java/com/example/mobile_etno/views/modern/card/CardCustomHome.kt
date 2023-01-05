package com.example.mobile_etno.views.modern.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_etno.R

@Composable
fun CardCustomHome(
    @DrawableRes
    icon: Int,
    section: String,
    title: String,
    description: String,
    cardClick: () -> Unit
){
    Text(text = section, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    Spacer(modifier = Modifier.padding(top = 4.dp))
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                cardClick.invoke()
            }
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = "services", modifier = Modifier.padding(top = 4.dp))
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column() {
                Text(text = title, fontWeight = FontWeight.Bold)
                Text(text = description, color = Color.Gray)
            }
            Spacer(modifier = Modifier.padding(horizontal = 10.dp))
            Icon(painter = painterResource(id = R.drawable.arrow), contentDescription = "arrow", modifier = Modifier
                .size(30.dp)
                .padding(top = 2.dp))
        }
    }
}
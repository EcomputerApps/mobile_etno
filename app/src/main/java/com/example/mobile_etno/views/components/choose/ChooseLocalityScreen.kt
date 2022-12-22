package com.example.mobile_etno.views.components.choose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.R

@Composable
fun ChooseLocalityScreen(
    navController: NavHostController
){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Image(painter = painterResource(id = R.drawable.etno_icon), contentDescription = "", modifier = Modifier.size(200.dp))
            Button(onClick = { navController.navigate(NavItem.Localities.route) }, modifier = Modifier.width(200.dp)) {
                Row() {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                    Text(text = "Busca tu localidad")
                }
            }
        }
    }
}
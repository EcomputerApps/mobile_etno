package com.example.mobile_etno.views.screen.tourism

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mobile_etno.R
import com.example.mobile_etno.models.Tourism
import com.example.mobile_etno.views.ScreenTopBar

@Composable
fun TourismDetails(
    navController: NavHostController?,
    tourism: Tourism?
){
    val currentContext = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        Scaffold(
            topBar = { ScreenTopBar(nameScreen = "Detalles Farmacia", navController = navController!!) }
        ) {
            Box(modifier = Modifier
                .padding(it)
                .fillMaxSize()) {
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = if (tourism?.imageUrl != "null") rememberAsyncImagePainter(
                                model = tourism?.imageUrl
                            ) else painterResource(
                                id = R.drawable.tourism_icon
                            ), contentDescription = "", modifier = Modifier.height(300.dp)
                        )
                    }
                    Column() {

                        Spacer(modifier = Modifier.padding(vertical = 5.dp))

                        Text(text = "Tipo", fontWeight = FontWeight.Bold)
                        Text(text = tourism?.type!!)

                        Spacer(modifier = Modifier.padding(vertical = 5.dp))

                        Text(text = "Localidad", fontWeight = FontWeight.Bold)
                        Text(text = tourism.username!!)

                        Spacer(modifier = Modifier.padding(vertical = 5.dp))

                        Text(text = "Nombre", fontWeight = FontWeight.Bold)
                        Text(text = tourism.title!!)

                        Spacer(modifier = Modifier.padding(vertical = 5.dp))

                        Text(text = "Descripción", fontWeight = FontWeight.Bold)
                        Text(text = tourism.description!!)
                    }
                }
            }
        }
    }
}
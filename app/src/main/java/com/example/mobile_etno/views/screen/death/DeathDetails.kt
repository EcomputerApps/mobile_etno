package com.example.mobile_etno.views.screen.death

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mobile_etno.models.Death
import com.example.mobile_etno.views.ScreenTopBar

@Composable
fun DeathDetails(
    navController: NavHostController?,
    death: Death?
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        Scaffold(
            topBar = { ScreenTopBar(nameScreen = "Detalles Fallecimiento", navController = navController!!) }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier
                        .fillMaxSize()
                    ) {
                      Image(painter = if (death?.imageUrl!! != "null") rememberAsyncImagePainter(
                          model = death.imageUrl!!
                      ) else painterResource(id = com.example.mobile_etno.R.drawable.fallecimiento), contentDescription = "", modifier = Modifier.height(300.dp).width(300.dp))
                    }
                        Column() {

                            Spacer(modifier = Modifier.padding(vertical = 5.dp))

                            Text(text = "Localidad", fontWeight = FontWeight.Bold)
                            Text(text = death?.username!!)

                            Spacer(modifier = Modifier.padding(vertical = 5.dp))

                            Text(text = "Nombre", fontWeight = FontWeight.Bold)
                            Text(text = death.name!!)

                            Spacer(modifier = Modifier.padding(vertical = 5.dp))

                            Text(text = "description", fontWeight = FontWeight.Bold)
                            Text(text = death.description!!)

                            Spacer(modifier = Modifier.padding(vertical = 5.dp))

                            Text(text = "deathDate", fontWeight = FontWeight.Bold)
                            Text(text = death.deathDate!!)
                        }
                    }
                
            }
        }
    }
}
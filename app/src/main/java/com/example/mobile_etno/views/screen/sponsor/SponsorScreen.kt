package com.example.mobile_etno.views.screen.sponsor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.R
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.components.connection.EmptyOrConnectionScreen
import com.example.mobile_etno.views.modern.navigationbottom.BottomNavigationCustom

@Composable
fun SponsorScreen(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
){
    val sponsors = userVillagerViewModel.userSponsors.collectAsState()
    val connection = userVillagerViewModel.connection.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationCustom(
            navController = navController,
            stateNavigationButton = -1,
            userVillagerViewModel = userVillagerViewModel
        ) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                if (connection.value){
                    Text(
                        text = "Patrocinadores", fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                    if(sponsors.value.isNotEmpty()){
                        LazyColumn(){
                            items(sponsors.value){ sponsor ->
                                Card(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    elevation = 4.dp
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(8.dp)
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                Icon(painter = painterResource(id = com.example.mobile_etno.R.drawable.business), contentDescription = "", modifier = Modifier.size(20.dp), tint = Color.Red)
                                                Text(text = sponsor.title!!, fontWeight = FontWeight.Bold, color = Color.Red)
                                            }
                                            Text(text = sponsor.description!!, color = Color.Red)
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                Icon(imageVector = Icons.Filled.Phone, contentDescription = "", modifier = Modifier.size(20.dp), tint = Color.Red)
                                                Text(text = sponsor.phone!!, color = Color.Red)
                                            }
                                            com.skydoves.landscapist.glide.GlideImage(
                                                imageModel = { sponsor.urlImage },
                                                loading = {
                                                    Box(
                                                        modifier = Modifier
                                                            .height(250.dp)
                                                            .fillMaxWidth()
                                                            .background(Color.LightGray)
                                                    ) {
                                                        CircularProgressIndicator(
                                                            color = Color.White,
                                                            modifier = Modifier.align(Alignment.Center)
                                                        )
                                                    }
                                                },
                                                success = { imageState ->
                                                    Image(bitmap = imageState.imageBitmap!!, contentDescription = "")
                                                },
                                                failure = {
                                                    Box(
                                                        modifier = Modifier
                                                            .height(250.dp)
                                                            .fillMaxWidth()
                                                            .background(Color.LightGray)
                                                    ) {
                                                        CircularProgressIndicator(
                                                            color = Color.White,
                                                            modifier = Modifier.align(Alignment.Center)
                                                        )
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }else{
                        EmptyOrConnectionScreen(icon = R.drawable.no_backpack, prop = "No hay patrocinadores en este momento")
                    }
                }
            }
            if (!connection.value){
                EmptyOrConnectionScreen(icon = R.drawable.wifi_off, prop = "Por favor, comprueba tu conexión a internet")
            }
        }
    }
}
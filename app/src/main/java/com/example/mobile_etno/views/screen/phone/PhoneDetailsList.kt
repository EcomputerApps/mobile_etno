package com.example.mobile_etno.views.screen.phone

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_etno.R
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.components.connection.EmptyOrConnectionScreen
import com.example.mobile_etno.views.modern.navigationbottom.BottomNavigationCustom
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PhoneDetailsList(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
){
    val currentContext = LocalContext.current
    val phones = userVillagerViewModel.userVillagerPhone.collectAsState()
    val phoneCategory = userVillagerViewModel.saveStatePhoneCategory.collectAsState()
    val dialPhoneIntent = Intent(Intent.ACTION_DIAL)
    val connection = userVillagerViewModel.connection.collectAsState()

Scaffold(
    topBar = {},
    bottomBar = { BottomNavigationCustom(
        navController = navController,
        stateNavigationButton = -1,
        userVillagerViewModel = userVillagerViewModel
    ) }
) {
    Column(
        modifier = Modifier
            .padding(it)
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(modifier = Modifier
                .padding(16.dp)
            ) {
                if(connection.value){
                    if (phones.value.isEmpty()){
                        EmptyOrConnectionScreen(prop = "No hay servicios disponibles en este momento", icon = R.drawable.no_backpack)
                    }else{
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ){
                            items(phones.value) {
                                    phone ->
                                Card(elevation = 7.dp, modifier = Modifier
                                    .clickable { }
                                    .padding(8.dp)){
                                    Box(modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxSize()
                                    ) {
                                        Column() {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                GlideImage(
                                                    imageModel = { phone.imageUrl },
                                                    success = { imageState ->
                                                        Image(
                                                            bitmap = imageState.imageBitmap!!,
                                                            contentDescription = "",
                                                            modifier = Modifier
                                                                .width(50.dp)
                                                                .height(50.dp)
                                                                .clip(CircleShape),
                                                            contentScale = ContentScale.FillBounds
                                                        )
                                                    },
                                                    loading = {
                                                        Box(
                                                            modifier = Modifier
                                                                .width(50.dp)
                                                                .height(50.dp)
                                                                .clip(CircleShape)
                                                        ) {
                                                            CircularProgressIndicator(
                                                                modifier = Modifier.align(Alignment.Center)
                                                            )
                                                        }
                                                    },
                                                    failure = {
                                                        Box(
                                                            modifier = Modifier
                                                                .width(50.dp)
                                                                .height(50.dp)
                                                                .background(Color.LightGray)
                                                                .clip(
                                                                    CircleShape
                                                                )
                                                        ) {
                                                            CircularProgressIndicator(
                                                                color = Color.White,
                                                                modifier = Modifier
                                                                    .align(Alignment.Center)
                                                                    .clip(
                                                                        CircleShape
                                                                    )
                                                            )
                                                        }
                                                    }
                                                )

                                                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                                                Text(text = phone.owner!!, fontWeight = FontWeight.Bold)
                                                Spacer(modifier = Modifier.padding(horizontal = 40.dp))
                                                Box(modifier = Modifier.fillMaxWidth()) {
                                                    Row(
                                                        modifier = Modifier
                                                            .align(Alignment.BottomEnd),
                                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                                    ) {
                                                        Icon(imageVector = Icons.Filled.Phone, contentDescription = "")
                                                        Text(text = phone.number!!)
                                                    }
                                                }
                                            }
                                            Spacer(modifier = Modifier.padding(vertical = 5.dp))
                                            Divider()
                                            Spacer(modifier = Modifier.padding(vertical = 5.dp))
                                            Box(modifier = Modifier.fillMaxSize()) {
                                                Text(text = phone.schedule!!, color = Color.Gray, modifier = Modifier.padding(vertical = 12.dp))
                                                Button(onClick = {
                                                    dialPhoneIntent.data = Uri.parse("tel:${phone.number}")
                                                    currentContext.startActivity(dialPhoneIntent)
                                                }, modifier = Modifier.align(
                                                    Alignment.BottomEnd), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)) {
                                                    Text(text = "Llamar", color = Color.White)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(!connection.value){
                EmptyOrConnectionScreen(icon = R.drawable.wifi_off, prop = "Por favor, comprueba tu conexión a internet")
            }
        }
    }
}
}
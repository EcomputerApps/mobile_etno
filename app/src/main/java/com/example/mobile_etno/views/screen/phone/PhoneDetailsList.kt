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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.ScreenTopBar

@Composable
fun PhoneDetailsList(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
){
    val currentContext = LocalContext.current
    val phones = userVillagerViewModel.userVillagerPhone.collectAsState()
    val phoneCategory = userVillagerViewModel.saveStatePhoneCategory.collectAsState()
    val dialPhoneIntent = Intent(Intent.ACTION_DIAL)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        Scaffold(
            topBar = { ScreenTopBar(nameScreen = "Teléfonos de ${phoneCategory.value}", navController = navController) }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Column(modifier = Modifier
                    .padding(16.dp)
                ) {
                    LazyColumn{
                       items(phones.value) {
                           phone ->
                           Card(elevation = 7.dp, modifier = Modifier.clickable {  }){
                               Box(modifier = Modifier
                                   .padding(10.dp)
                                   .fillMaxSize()
                               ) {
                                   Column() {
                                       Row(verticalAlignment = Alignment.CenterVertically) {
                                           Image(painter = painterResource(id = com.example.mobile_etno.R.drawable.panda_contact), contentDescription = "", modifier = Modifier
                                               .width(50.dp)
                                               .height(50.dp)
                                               .clip(CircleShape),
                                               contentScale = ContentScale.FillBounds
                                           )
                                           Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                                           Text(text = phone.owner!!, fontWeight = FontWeight.Bold)
                                           Spacer(modifier = Modifier.padding(horizontal = 40.dp))
                                           Box(modifier = Modifier.fillMaxSize()) {
                                               Row() {
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
                           Spacer(modifier = Modifier.padding(vertical = 8.dp))
                       }
                    }
                }
            }
        }
    }
}
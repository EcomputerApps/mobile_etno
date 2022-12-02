package com.example.mobile_etno.views.screen

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mobile_etno.models.Event
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.MenuViewModel
import com.example.mobile_etno.views.Drawer
import com.example.mobile_etno.views.ScreenTopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EventNameScreen(menuViewModel: MenuViewModel?, navController: NavHostController?, event: Event, imageEvent: String){
    val eventState = remember {event}

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    
    val list = listOf(
        Event(
            title = event.title,
            address = event.address,
            description = event.description,
            organization = event.organization,
            link = event.link,
            startDate = event.startDate,
            endDate = event.endDate,
            publicationDate = event.publicationDate,
            time = event.time,
            lat = event.lat,
            long = event.long,
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.backgroundEtno)
            .wrapContentSize(Alignment.Center)
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { ScreenTopBar(menuViewModel =  menuViewModel!!, navController = navController!!, nameScreen = "Nombre de Evento") },
            drawerBackgroundColor = Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController!!, menuViewModel!!)
            },
            backgroundColor = Color.Red
        ){
            Surface(color = Colors.backgroundEtno,
                modifier = Modifier.fillMaxSize()) {
               Column(
                   Modifier
                       .verticalScroll(rememberScrollState())
                       .padding(16.dp)) {
                   Image(painter = rememberAsyncImagePainter(imageEvent), contentDescription = "", modifier = Modifier.height(300.dp))
                   Row() {
                       Column() {
                           Text(text = "Título", fontWeight = FontWeight.Bold)
                           Text(text = event.title!!)
                       }
                       Spacer(modifier = Modifier.padding(horizontal = 70.dp))
                       Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red), shape = CircleShape) {
                           Text(text = "Subscribirse", color = Color.White)
                       }
                   }
                      Text(text = "Lugar", fontWeight = FontWeight.Bold)
                      Text(text = event.address!!)
                      Text(text = "Enlace", fontWeight = FontWeight.Bold)
                      Text(text = event.link!!)
                     Row() {
                         Column() {
                             Text(text = "Fecha inicial", fontWeight = FontWeight.Bold)
                             Text(text = event.startDate!!)
                         }
                         Spacer(modifier = Modifier.padding(horizontal = 70.dp))
                         Column() {
                             Text(text = "Organization", fontWeight = FontWeight.Bold)
                             Text(text = event.organization!!)
                         }
                     }
                      Text(text = "Fecha de publicación", fontWeight = FontWeight.Bold)
                      Text(text = event.publicationDate!!)
                      Text(text = "Description", fontWeight = FontWeight.Bold)
                      Text(text = event.description!!)
                  }
            }
        }
    }
}

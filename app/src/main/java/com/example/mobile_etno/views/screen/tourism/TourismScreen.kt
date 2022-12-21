package com.example.mobile_etno.views.screen.tourism

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.R
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.MenuViewModel
import com.example.mobile_etno.viewmodels.TourismViewModel
import com.example.mobile_etno.views.ScreenTopBar
import com.example.mobile_etno.views.components.google.GoogleMapTourism

@Composable
fun TourismScreen(menuViewModel: MenuViewModel, tourismViewModel: TourismViewModel, navController: NavHostController){

    val currentContext = LocalContext.current
    val tourism = tourismViewModel.tourism.collectAsState()

    BackHandler() {
        navController.navigate(NavItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.backgroundEtno)
            .wrapContentSize(Alignment.Center)
    ) {

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                ScreenTopBar(
                    navController = navController,
                    nameScreen = "Turismo"
                )
            },
            backgroundColor = Color.Red
        ) {
            Box(modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.White)) {

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                ) {
                    GoogleMapTourism(listTourism = tourism.value)
                }
                Column() {
                    Spacer(modifier = Modifier.padding(vertical = 170.dp))
                    LazyColumn(modifier = Modifier.padding(16.dp)){
                        items(tourism.value){
                                item ->
                            Card(modifier = Modifier.padding(vertical = 4.dp).clickable {
                                Toast.makeText(currentContext, item.title, Toast.LENGTH_SHORT).show()
                            }) {
                                Row(modifier = Modifier
                                    .background(Color.White)
                                    .width(400.dp)) {
                                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                                    Spacer(modifier = Modifier.padding(vertical = 16.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.etno_icon),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .height(40.dp)
                                            .width(40.dp)
                                            .clip(
                                                CircleShape
                                            )
                                    )
                                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                        Text(
                                            text = item.title!!,
                                            style = MaterialTheme.typography.h6,
                                        )
                                        (if(item.description!!.length >= 70) item.description!!.substring(0, 37) else item.description)?.let { it -> Text(text = it, color = Color.LightGray) }
                                    }
                                    Icon(
                                        painter = painterResource(id = R.drawable.arrow_rigth),
                                        contentDescription = "",
                                        tint = Color.Red,
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .width(60.dp)
                                            .height(60.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.padding(vertical = 10.dp))
                        }
                    }
                }
            }
        }
    }
}
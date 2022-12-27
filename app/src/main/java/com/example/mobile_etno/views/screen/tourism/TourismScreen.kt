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
import androidx.compose.runtime.*
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
import com.example.mobile_etno.models.Tourism
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.TourismViewModel
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.ScreenTopBar
import com.example.mobile_etno.views.components.google.GoogleMapTourism
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun TourismScreen( userVillagerViewModel: UserVillagerViewModel, navController: NavHostController){

    val currentContext = LocalContext.current
    val tourism = userVillagerViewModel.userVillagerTourism.collectAsState()

    var selectedTabIndex by remember { mutableStateOf(0) }

    BackHandler() {
        navController.navigate(NavItem.Home.route){  }
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
                    .fillMaxHeight()
                ) {
                    GoogleMapTourism(listTourism = tourism.value)
                    CustomScrollableFilterTourism(
                        typeButtonList = listOf(Tourism(type = "Restaurante"), Tourism(type = "Monumento"), Tourism(type = "Museo"), Tourism(type = "Hotel")),
                        selectedTabIndex = selectedTabIndex,
                        userVillagerViewModel = userVillagerViewModel
                    ){
                        index ->
                        selectedTabIndex = index
                    }
                }
                Column() {
                    Spacer(modifier = Modifier.padding(vertical = 220.dp))
                    LazyColumn(modifier = Modifier.padding(16.dp)){
                        items(tourism.value){
                                item ->

                            val imageType: Int = when(item.type){
                                "Museo" -> R.drawable.museo
                                "Hotel" -> R.drawable.hotel
                                "Monumento" -> R.drawable.monumental
                                "Restaurante" -> R.drawable.restaurant
                                else -> {R.drawable.etno_icon}
                            }

                            Card(modifier = Modifier
                                .padding(vertical = 4.dp)
                                .clickable {
                                    val encodeUrlImage: String = if(item.imageUrl != null){
                                        URLEncoder.encode(item.imageUrl, StandardCharsets.UTF_8.toString())
                                    }else{
                                        "null"
                                    }
                                   navController.navigate("${NavItem.DetailTourism.route}?type=${item.type}&username=${item.username}&title=${item.title}&description=${item.description}&imageUrl=$encodeUrlImage")
                                }) {
                                Row(modifier = Modifier
                                    .background(Color.White)
                                    .width(400.dp)) {
                                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                                    Spacer(modifier = Modifier.padding(vertical = 16.dp))
                                    Image(
                                        painter = painterResource(id = imageType),
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

@Composable
fun CustomScrollableFilterTourism(
    typeButtonList: List<Tourism>,
    userVillagerViewModel: UserVillagerViewModel,
    selectedTabIndex: Int,
    onItemClick: (Int) -> Unit
){
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        backgroundColor = Color.Transparent,
        contentColor = Color.Transparent,
        divider = {  }
    ) {
        Button(onClick = { userVillagerViewModel.filterAllTourism() }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
            Text(text = "Todo")
        }
        typeButtonList.forEachIndexed { index, tourism ->
            Tab(selected = selectedTabIndex == index, onClick = { onItemClick.invoke(index) }, modifier = Modifier
                .background(Color.Transparent)
                .padding(horizontal = 5.dp)) {
                Button(onClick = {
                                 when(tourism.type) {
                                     "Restaurante" -> userVillagerViewModel.tourismFilter("Restaurante")
                                     "Museo" -> userVillagerViewModel.tourismFilter("Museo")
                                     "Hotel" -> userVillagerViewModel.tourismFilter("Hotel")
                                     "Monumento" -> userVillagerViewModel.tourismFilter("Monumento")
                                 }
                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                    Row {
                       val typeIcon: Int = when(tourism.type){
                            "Restaurante" -> R.drawable.restaurant
                            "Museo" -> R.drawable.museo
                            "Hotel" -> R.drawable.hotel
                            "Monumento" -> R.drawable.monumental
                           else -> { R.drawable.etno_icon }
                        }
                        Image(painter = painterResource(id = typeIcon), contentDescription = tourism.type, modifier = Modifier.size(20.dp))
                        Text(text = tourism.type!!)
                    }
                }
            }
        }
    }
}
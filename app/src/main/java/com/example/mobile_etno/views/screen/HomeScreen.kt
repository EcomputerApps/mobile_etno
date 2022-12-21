package com.example.mobile_etno.views.screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.viewmodels.EventViewModel
import com.example.mobile_etno.viewmodels.MenuViewModel
import com.example.mobile_etno.views.TopBar
import com.example.mobile_etno.R.*
import com.example.mobile_etno.models.NavigationBottom
import com.example.mobile_etno.viewmodels.PharmacyViewModel
import com.example.mobile_etno.viewmodels.TourismViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    list: List<String>,
    listBottomNavigation: List<NavigationBottom>,
    navController: NavHostController,
    menuViewModel: MenuViewModel,
    eventViewModel: EventViewModel,
    pharmacyViewModel: PharmacyViewModel,
    tourismViewModel: TourismViewModel
) {
    var selectedItem by remember { mutableStateOf(1) }
    //This will let to close the screen ->
    val activity = (LocalContext.current as Activity)

    BackHandler() {
        activity.finish()
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    //then here i have to iterate the list in card

    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = { TopBar(scope = scope, scaffoldState = scaffoldState, menuViewModel = menuViewModel) },
                drawerBackgroundColor = Color.White,
                // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer

                backgroundColor = Color.Red
            ){
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp), modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()){

                    itemsIndexed(list){
                            index ,item ->
                        Card(elevation = 0.dp,modifier = Modifier
                            .fillMaxSize()
                            .fillMaxHeight()
                            .clickable {
                                //Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
                                navController.navigate(list[index]) {
                                    menuViewModel.updateInvisible(false)
                                }

                                when (item) {
                                    "Eventos" -> eventViewModel.getEventRequest()
                                    "Farmacias" -> pharmacyViewModel.getPharmacies()
                                    "Turismo" -> tourismViewModel.getRequestTourism()
                                }
                            },
                            backgroundColor = Color.White) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Spacer(modifier = Modifier.padding(vertical = 16.dp))

                                when(item){
                                    "Eventos" -> Icon(painter = painterResource(id = drawable.events_icon), contentDescription = "events", modifier = Modifier.size(55.dp), tint = Color.Red)
                                    "Reservaciones" -> Icon(painter = painterResource(id = drawable.book_icon), contentDescription = "book", modifier = Modifier.size(55.dp), tint = Color.Red)
                                    "Muertes" -> Icon(painter = painterResource(id = drawable.death), contentDescription = "book", modifier = Modifier.size(55.dp), tint = Color.Red)
                                    "Telefonos" -> Icon(painter = painterResource(id = drawable.phone), contentDescription = "book", modifier = Modifier.size(55.dp), tint = Color.Red)
                                    "Noticias" -> Icon(painter = painterResource(id = drawable.news), contentDescription = "book", modifier = Modifier.size(55.dp), tint = Color.Red)
                                    "Galeria" -> Icon(painter = painterResource(id = drawable.gallery), contentDescription = "book", modifier = Modifier.size(55.dp), tint = Color.Red)
                                    "Farmacias" -> Icon(painter = painterResource(id = drawable.kit_pharmacie), contentDescription = "book", modifier = Modifier.size(55.dp), tint = Color.Red)
                                    "Patrocinadores" -> Icon(painter = painterResource(id = drawable.sponsors), contentDescription = "book", modifier = Modifier.size(55.dp), tint = Color.Red)
                                    "Fiestas" -> Icon(painter = painterResource(id = drawable.festivities), contentDescription = "book", modifier = Modifier.size(55.dp), tint = Color.Red)
                                    "Anuncios" -> Icon(painter = painterResource(id = drawable.ad), contentDescription = "book", modifier = Modifier.size(55.dp), tint = Color.Red)
                                    "Turismo" -> Icon(painter = painterResource(id = drawable.tourism), contentDescription = "book", modifier = Modifier.size(55.dp), tint = Color.Red)
                                    "Servicios" -> Icon(painter = painterResource(id = drawable.service), contentDescription = "book", modifier = Modifier.size(55.dp), tint = Color.Red)
                                    "Incidentes" -> Icon(painter = painterResource(id = drawable.warning), contentDescription = "book", modifier = Modifier.size(55.dp), tint = Color.Red)
                                    "Enlaces" -> Icon(painter = painterResource(id = drawable.links), contentDescription = "book", modifier = Modifier.size(55.dp), tint = Color.Red)
                                    "Bandos" -> Icon(painter = painterResource(id = drawable.speaker), contentDescription = "book", modifier = Modifier.size(55.dp), tint = Color.Red)
                                }
                                Text(text = item)
                                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                            }
                        }
                    }
                }
            }

        }
        BottomNavigation(
            modifier = Modifier.align(Alignment.BottomCenter).height(50.dp),
            backgroundColor = Color.Red,
            contentColor = Color.White
        ) {
            listBottomNavigation.forEachIndexed { index, item ->
                BottomNavigationItem(selected = selectedItem == index, onClick = {
                    when (item.name) {
                        "Noticias" -> {
                            navController.navigate(NavItem.News.route) { }
                        }
                        "Menu" -> {
                            navController.navigate(NavItem.Home.route) { }
                        }
                    }
                    selectedItem = index
                }, icon = {
                    Icon(
                        imageVector = item.icon!!,
                        contentDescription = null
                    )
                }, label = { Text(text = item.name!!) })
            }
        }
    }
}
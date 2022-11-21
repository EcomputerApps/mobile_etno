package com.example.mobile_etno

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.MenuViewModel
import com.example.mobile_etno.views.Drawer
import com.example.mobile_etno.views.ScreenTopBar

@Composable
fun HomeScreen(list: List<String>, navController: NavHostController, menuViewModel: MenuViewModel) {
    //then here i have to iterate the list in card

    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp), modifier = Modifier
        .background(Colors.backgroundEtno)
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

                },
                backgroundColor = Colors.backgroundEtno) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.padding(vertical = 16.dp))
                    Image(painter = painterResource(id = R.drawable.home_test), modifier = Modifier.size(60.dp), contentDescription = item)
                    Text(text = item)
                    Spacer(modifier = Modifier.padding(vertical = 16.dp))
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EventsScreen(menuViewModel: MenuViewModel, navController: NavHostController) {

    BackHandler() {
        navController.navigate(NavDrawerItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.backgroundEtno)
            .wrapContentSize(Alignment.Center)
    ) {

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { ScreenTopBar(menuViewModel =  menuViewModel, navController = navController, nameScreen = "Events") },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController, menuViewModel)
            },
            backgroundColor = Color.Red
        ) {

            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Events View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }


        }

    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReservationsScreen(menuViewModel: MenuViewModel, navController: NavHostController) {

    BackHandler() {
        navController.navigate(NavDrawerItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

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
                    menuViewModel = menuViewModel,
                    navController = navController,
                    nameScreen = "Reservations"
                )
            },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
            backgroundColor = Color.Red
        ) {
            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Reservations View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DeathsScreen(menuViewModel: MenuViewModel, navController: NavHostController) {

    BackHandler() {
        navController.navigate(NavDrawerItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

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
                    menuViewModel = menuViewModel,
                    navController = navController,
                    nameScreen = "Deaths"
                )
            },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
            backgroundColor = Color.Red
        ) {
            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Deaths View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PhoneScreen(menuViewModel: MenuViewModel, navController: NavHostController) {

    BackHandler() {
        navController.navigate(NavDrawerItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

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
                    menuViewModel = menuViewModel,
                    navController = navController,
                    nameScreen = "Telefonos"
                )
            },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
            backgroundColor = Color.Red
        ) {
            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Phones View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewsScreen(menuViewModel: MenuViewModel, navController: NavHostController){

    BackHandler() {
        navController.navigate(NavDrawerItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

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
                    menuViewModel = menuViewModel,
                    navController = navController,
                    nameScreen = "Noticias"
                )
            },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
            backgroundColor = Color.Red
        ) {
            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "News View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GalleryScreen(menuViewModel: MenuViewModel, navController: NavHostController) {

    BackHandler() {
        navController.navigate(NavDrawerItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

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
                    menuViewModel = menuViewModel,
                    navController = navController,
                    nameScreen = "Galería"
                )
            },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
            backgroundColor = Color.Red
        ) {
            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Gallery View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PharmaciesScreen(menuViewModel: MenuViewModel, navController: NavHostController){

    BackHandler() {
        navController.navigate(NavDrawerItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

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
                    menuViewModel = menuViewModel,
                    navController = navController,
                    nameScreen = "Farmacias"
                )
            },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
            backgroundColor = Color.Red
        ) {
            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Pharmacies View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SponsorsScreen(menuViewModel: MenuViewModel, navController: NavHostController){

    BackHandler() {
        navController.navigate(NavDrawerItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

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
                    menuViewModel = menuViewModel,
                    navController = navController,
                    nameScreen = "Patrocinadores"
                )
            },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
            backgroundColor = Color.Red
        ) {
            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Sponsors View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FestivitiesScreen(menuViewModel: MenuViewModel, navController: NavHostController){

    BackHandler() {
        navController.navigate(NavDrawerItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

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
                    menuViewModel = menuViewModel,
                    navController = navController,
                    nameScreen = "Fiestas"
                )
            },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
            backgroundColor = Color.Red
        ) {
            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Festivities View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdvertisementsScreen(menuViewModel: MenuViewModel, navController: NavHostController){

    BackHandler() {
        navController.navigate(NavDrawerItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

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
                    menuViewModel = menuViewModel,
                    navController = navController,
                    nameScreen = "Anuncios"
                )
            },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
            backgroundColor = Color.Red
        ) {
            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Advertisements View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ServicesScreen(menuViewModel: MenuViewModel, navController: NavHostController){

    BackHandler() {
        navController.navigate(NavDrawerItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

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
                    menuViewModel = menuViewModel,
                    navController = navController,
                    nameScreen = "Servicios"
                )
            },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
            backgroundColor = Color.Red
        ) {
            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Services View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TourismScreen(menuViewModel: MenuViewModel, navController: NavHostController){

    BackHandler() {
        navController.navigate(NavDrawerItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

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
                    menuViewModel = menuViewModel,
                    navController = navController,
                    nameScreen = "Turismo"
                )
            },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
            backgroundColor = Color.Red
        ) {
            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Tourism View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun IncidentsScreen(menuViewModel: MenuViewModel, navController: NavHostController){

    BackHandler() {
        navController.navigate(NavDrawerItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

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
                    menuViewModel = menuViewModel,
                    navController = navController,
                    nameScreen = "Incidentes"
                )
            },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
            backgroundColor = Color.Red
        ) {
            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Incidents View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LinksScreen(menuViewModel: MenuViewModel, navController: NavHostController){

    BackHandler() {
        navController.navigate(NavDrawerItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

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
                    menuViewModel = menuViewModel,
                    navController = navController,
                    nameScreen = "Enlaces"
                )
            },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
            backgroundColor = Color.Red
        ) {
            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Links View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BandosScreen(menuViewModel: MenuViewModel, navController: NavHostController){

    BackHandler() {
        navController.navigate(NavDrawerItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

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
                    menuViewModel = menuViewModel,
                    navController = navController,
                    nameScreen = "Bandos"
                )
            },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
            backgroundColor = Color.Red
        ) {
            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Bandos View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }
}
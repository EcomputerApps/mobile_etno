package com.example.mobile_etno.views.screen.pharmacy

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.R
import com.example.mobile_etno.models.Pharmacy
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.MenuViewModel
import com.example.mobile_etno.viewmodels.PharmacyViewModel
import com.example.mobile_etno.views.ScreenTopBar
import com.example.mobile_etno.views.components.google.GoogleMapPharmacies
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun PharmaciesScreen(menuViewModel: MenuViewModel, pharmacyViewModel: PharmacyViewModel, navController: NavHostController){

    BackHandler() {
        navController.navigate(NavItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val pharmacies = pharmacyViewModel.pharmacies.collectAsState()

    var selectedTabIndex by remember { mutableStateOf(0) }

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
                    nameScreen = "Farmacias"
                )
            },
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            /*
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
             */
            backgroundColor = Color.Red
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                ) {
                    GoogleMapPharmacies(pharmacies.value)
                    CustomScrollableFilterPharmacy(
                        typeButtonList = listOf(Pharmacy(type = "Normal"), Pharmacy(type = "Guardia")),
                        pharmacyViewModel = pharmacyViewModel,
                        selectedTabIndex = selectedTabIndex
                    ){ index -> selectedTabIndex = index }
                Column {
                    Spacer(modifier = Modifier.padding(vertical = 220.dp))
                    LazyColumn(modifier = Modifier.padding(16.dp)){
                        items(pharmacies.value){
                            Card(backgroundColor = Color.White, modifier = Modifier
                                .padding(vertical = 4.dp)
                                .clickable {
                                    navController.navigate(
                                        "${NavItem.DetailPharmacy.route}?imageUrl=${
                                            URLEncoder.encode(
                                                it.imageUrl,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                        }&link=${it.link}&type=${it.type}&name=${it.name}&phone=${it.phone}&description=${it.description}"
                                    ) { }
                                }) {
                                Row(modifier = Modifier
                                    .height(IntrinsicSize.Min)
                                    .fillMaxWidth()) {
                                    Divider(
                                        color = if(it.type == "Normal") Color.Blue else Color.Red,
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(2.dp)
                                    )
                                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                                    Column() {
                                        Text(text = it.name!!, style = MaterialTheme.typography.h6)
                                        Row() {
                                            Text(text = "Teléfono: ${it.phone}")
                                            Spacer(modifier = Modifier.padding(horizontal = 50.dp))
                                            Text(text = "Servicio ${it.type}", color = if(it.type == "Normal") Color.Blue else Color.Red)
                                        }
                                        Text(text = "Horario: ${it.schedule}")
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
}

@Composable
fun CustomScrollableFilterPharmacy(
    typeButtonList: List<Pharmacy>,
    pharmacyViewModel: PharmacyViewModel,
    selectedTabIndex: Int,
    onItemClick: (Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        backgroundColor = Color.Transparent,
        contentColor = Color.Transparent,
        divider = { }
    ) {
        Button(onClick = { pharmacyViewModel.filterAll() }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
            Text(text = "Todo")
        }
        typeButtonList.forEachIndexed { index, pharmacy ->
            Tab(selected = selectedTabIndex == index, onClick = { onItemClick.invoke(index) }, modifier = Modifier
                .background(Color.Transparent)
                .padding(horizontal = 5.dp)) {
                Button(colors = ButtonDefaults.buttonColors(Color.White), onClick = {
                    if (pharmacy.type == "Normal") pharmacyViewModel.pharmaciesFilter(
                        "Normal"
                    ) else pharmacyViewModel.pharmaciesFilter("Guardia")
                }) {
                    Row() {
                        //Icon(painter = painterResource(id = if(pharmacy.type == "Normal") R.drawable.blue_pharmacy else R.drawable.red_pharmacy), contentDescription = pharmacy.type, modifier = Modifier.size(20.dp))
                        Image(painter = painterResource(id = if(pharmacy.type == "Normal") R.drawable.blue_pharmacy else R.drawable.red_pharmacy), contentDescription = "", modifier = Modifier.size(20.dp))
                        Text(text = pharmacy.type!!)
                    }
                }
            }
        }
    }
}

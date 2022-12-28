package com.example.mobile_etno.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobile_etno.*
import com.example.mobile_etno.R
import com.example.mobile_etno.models.service.database.SqlDataBase
import com.example.mobile_etno.navigation.Navigation
import com.example.mobile_etno.viewmodels.*
import com.example.mobile_etno.viewmodels.locality.LocalityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    list: List<String>,
    localityViewModel: LocalityViewModel,
    userVillagerViewModel: UserVillagerViewModel,
    fcmViewModel: FCMViewModel,
    eventNameViewModel: EventNameViewModel,
    sqlDataBase: SqlDataBase,
){
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(1) }

    Box() {
        Navigation(navController = navController,
          list = list,
            localityViewModel = localityViewModel,
            userVillagerViewModel = userVillagerViewModel,
            fcmViewModel = fcmViewModel,
           eventNameViewModel = eventNameViewModel,
           sqlDataBase = sqlDataBase,
        )
    }
}

@Composable
fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavHostController, menuViewModel: MenuViewModel) {
    val items = listOf(
        NavItem.Events,
        NavItem.Reservations,
        NavItem.Deaths,
        NavItem.Phone,
        NavItem.News,
        NavItem.Gallery,
        NavItem.Pharmacies,
        NavItem.Sponsors,
        NavItem.Festivities,
        NavItem.Advertisements,
        NavItem.Services,
        NavItem.Tourism,
        NavItem.Incidents,
        NavItem.Links,
        NavItem.Bandos
    )
    Column {
        // Header
        Image(
            painter = painterResource(id = R.drawable.etno_icon),
            contentDescription = "",
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(10.dp)
        )
        // Space between
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
        // List of navigation items
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            DrawerItem(item = item, selected = currentRoute == item.route, onItemClick = {
                navController.navigate(item.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items

                    menuViewModel.updateInvisible(false)

                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
                // Close drawer
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }

}

@Composable
fun DrawerItem(item: NavItem, selected: Boolean, onItemClick: (NavItem) -> Unit) {
    val background = if (selected) Color.Red else Color.Transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })
            .height(45.dp)
            .background(background)
            .padding(start = 10.dp)
    ) {
        Image(
            painter = painterResource(id = item.icon!!),
            contentDescription = item.title,
            colorFilter = ColorFilter.tint(Color.White),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(35.dp)
                .width(35.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))

        Column() {
            Text(
                text = item.title!!,
                fontSize = 18.sp,
                color = Color.Black
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.width(250.dp))
        }
    }
}

@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
        TopAppBar(
            title = {
                //Text(modifier = Modifier.width(260.dp), textAlign = TextAlign.Center, maxLines = 1, text = "Etno")
                Image(painter = painterResource(id = R.drawable.etno_icon), contentDescription = "Etno icon", modifier = Modifier.width(260.dp), alignment = Alignment.Center)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.internalization_icon), contentDescription = "Internalization to traslate the Etno app")
                }},
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Menu, "Menu to navigate to other screens")
                }
            },
            backgroundColor = Color.Red,
            contentColor = Color.White
        )
}

@Composable
fun ScreenTopBar(nameScreen: String, navController: NavHostController){
        TopAppBar(
            title = {
                Text(modifier = Modifier.width(260.dp), textAlign = TextAlign.Center, maxLines = 1, text = nameScreen)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.internalization_icon), contentDescription = "Internalization to traslate the Etno app")
                }},
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate(NavItem.Home.route){  }

                }) {
                    Icon(painter = painterResource(id = R.drawable.back_arrow), "back to Home screen", modifier = Modifier.size(35.dp))
                }
            },
            backgroundColor = Color.Red,
            contentColor = Color.White
        )
    }
@Composable
fun ScreenTopBarSpecial(nameScreen: String, navController: NavHostController){
    TopAppBar(
        title = {
            Text(modifier = Modifier.width(260.dp), textAlign = TextAlign.Center, maxLines = 1, text = nameScreen)
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.internalization_icon), contentDescription = "Internalization to traslate the Etno app")
            }},
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(NavItem.Home.route){  }
            }) {
                Icon(painter = painterResource(id = R.drawable.back_arrow), "back to Home screen", modifier = Modifier.size(35.dp))
            }
        },
        backgroundColor = Color.Red,
        contentColor = Color.White
    )
}



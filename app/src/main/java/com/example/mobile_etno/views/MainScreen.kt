package com.example.mobile_etno.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobile_etno.*
import com.example.mobile_etno.R
import com.example.mobile_etno.models.Event
import com.example.mobile_etno.models.Image
import com.example.mobile_etno.models.service.database.SqlDataBase
import com.example.mobile_etno.viewmodels.EventViewModel
import com.example.mobile_etno.viewmodels.MenuViewModel
import com.example.mobile_etno.views.screen.EventNameScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    list: List<String>,
    menuViewModel: MenuViewModel,
    eventViewModel: EventViewModel,
    sqlDataBase: SqlDataBase
){
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState, menuViewModel) },
        drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
        // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController, menuViewModel)
        },
        backgroundColor = Color.Red
    ) { padding ->  // We need to pass scaffold's inner padding to content. That's why we use Box.
        Box(modifier = Modifier.padding(padding)) {
            Navigation(navController = navController, list, menuViewModel, eventViewModel, sqlDataBase)
        }
    }
}

@Composable
fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavHostController, menuViewModel: MenuViewModel) {
    val items = listOf(
        NavDrawerItem.Events,
        NavDrawerItem.Reservations,
        NavDrawerItem.Deaths,
        NavDrawerItem.Phone,
        NavDrawerItem.News,
        NavDrawerItem.Gallery,
        NavDrawerItem.Pharmacies,
        NavDrawerItem.Sponsors,
        NavDrawerItem.Festivities,
        NavDrawerItem.Advertisements,
        NavDrawerItem.Services,
        NavDrawerItem.Tourism,
        NavDrawerItem.Incidents,
        NavDrawerItem.Links,
        NavDrawerItem.Bandos
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
fun DrawerItem(item: NavDrawerItem, selected: Boolean, onItemClick: (NavDrawerItem) -> Unit) {
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
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState, menuViewModel: MenuViewModel) {
    AnimatedVisibility(visible = menuViewModel.isInvisible) {
        TopAppBar(
            title = {
                Text(modifier = Modifier.width(260.dp), textAlign = TextAlign.Center, maxLines = 1, text = "Etno")
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
}

@Composable
fun ScreenTopBar(nameScreen: String, navController: NavHostController, menuViewModel: MenuViewModel){

    AnimatedVisibility(visible = true) {
        TopAppBar(
            title = {
                Text(modifier = Modifier.width(260.dp), textAlign = TextAlign.Center, maxLines = 1, text = nameScreen)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.internalization_icon), contentDescription = "Internalization to traslate the Etno app")
                }},
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate(NavDrawerItem.Home.route){
                        menuViewModel.updateInvisible(true)
                    }
                    /*
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                     */
                }) {
                    Icon(painter = painterResource(id = R.drawable.back_arrow), "back to Home screen", modifier = Modifier.size(35.dp))
                }
            },
            backgroundColor = Color.Red,
            contentColor = Color.White
        )
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    list: List<String>,
    menuViewModel: MenuViewModel,
    eventViewModel: EventViewModel,
    sqlDataBase: SqlDataBase
) {
    NavHost(navController, startDestination = NavDrawerItem.Home.route) {
        composable(NavDrawerItem.Home.route) {
           // eventViewModel.getEvents()
            HomeScreen(list, navController = navController, menuViewModel = menuViewModel, eventViewModel = eventViewModel)
        }
        composable(NavDrawerItem.Events.route,
        ) {
            EventsScreen(navController = navController, menuViewModel = menuViewModel, eventViewModel = eventViewModel, sqlDataBase = sqlDataBase)
        }
        composable("${NavDrawerItem.EventNameScreen.route}/{title}/{address}/{description}/{organization}/{link}/{startDate}/{endDate}/{publicationDate}/{time}/{lat}/{long}/{image}", arguments =
        listOf(navArgument("title"){type = NavType.StringType},
            navArgument("address"){type = NavType.StringType},
            navArgument("description"){type = NavType.StringType},
            navArgument("organization"){type = NavType.StringType},
            navArgument("link"){type = NavType.StringType},
            navArgument("endDate"){type = NavType.StringType},
            navArgument("publicationDate"){type = NavType.StringType},
            navArgument("time"){type = NavType.StringType},
            navArgument("lat"){type = NavType.StringType},
            navArgument("long"){type = NavType.StringType},
            navArgument("image"){type = NavType.StringType}
            )
        ){
            val event = Event(
                title = it.arguments?.getString("title"),
                address = it.arguments?.getString("address"),
                description = it.arguments?.getString("description"),
                organization = it.arguments?.getString("organization"),
                link = it.arguments?.getString("link"),
                startDate = it.arguments?.getString("startDate"),
                endDate = it.arguments?.getString("endDate"),
                publicationDate = it.arguments?.getString("publicationDate"),
                time = it.arguments?.getString("time"),
                lat = it.arguments?.getString("lat"),
                long = it.arguments?.getString("long"),
                images = null
            )
            val imageEvent = it.arguments?.getString("image")
            EventNameScreen(navController = navController, menuViewModel = menuViewModel, event = event, imageEvent = imageEvent!!)
        }
        composable(NavDrawerItem.Reservations.route) {
            ReservationsScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavDrawerItem.Deaths.route) {
            DeathsScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavDrawerItem.Phone.route) {
            PhoneScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavDrawerItem.News.route){
            NewsScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavDrawerItem.Gallery.route) {
            GalleryScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavDrawerItem.Pharmacies.route){
            PharmaciesScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavDrawerItem.Sponsors.route){
            SponsorsScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavDrawerItem.Festivities.route){
            FestivitiesScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavDrawerItem.Advertisements.route){
            AdvertisementsScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavDrawerItem.Services.route){
            ServicesScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavDrawerItem.Tourism.route){
            TourismScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavDrawerItem.Incidents.route){
            IncidentsScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavDrawerItem.Links.route){
            LinksScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavDrawerItem.Bandos.route){
            BandosScreen(navController = navController, menuViewModel = menuViewModel)
        }

    }
}
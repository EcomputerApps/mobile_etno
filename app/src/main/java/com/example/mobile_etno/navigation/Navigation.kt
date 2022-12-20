package com.example.mobile_etno.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mobile_etno.*
import com.example.mobile_etno.models.Event
import com.example.mobile_etno.models.NavigationBottom
import com.example.mobile_etno.models.service.database.SqlDataBase
import com.example.mobile_etno.viewmodels.*
import com.example.mobile_etno.views.screen.events.EventNameScreen
import com.example.mobile_etno.views.screen.*
import com.example.mobile_etno.views.screen.events.EventsScreen

@Composable
fun Navigation(
    navController: NavHostController,
    list: List<String>,
    menuViewModel: MenuViewModel,
    eventNameViewModel: EventNameViewModel,
    eventViewModel: EventViewModel,
    pharmacyViewModel: PharmacyViewModel,
    sqlDataBase: SqlDataBase
) {
    NavHost(navController, startDestination = NavDrawerItem.Home.route) {
        composable(NavDrawerItem.Home.route) {
            // eventViewModel.getEvents()
            HomeScreen(list, navController = navController,
                menuViewModel = menuViewModel,
                eventViewModel = eventViewModel,
                pharmacyViewModel = pharmacyViewModel,
                listBottomNavigation = listOf(NavigationBottom("Noticias", Icons.Filled.Search), NavigationBottom("Menu", Icons.Filled.Home), NavigationBottom("Anuncios", Icons.Filled.Warning)))
        }
        composable(
            NavDrawerItem.Events.route,
        ) {
            EventsScreen(
                navController = navController,
                menuViewModel = menuViewModel,
                eventViewModel = eventViewModel,
                sqlDataBase = sqlDataBase,
                listBottomNavigation = listOf(NavigationBottom("Noticias", Icons.Filled.Search), NavigationBottom("Menu", Icons.Filled.Home), NavigationBottom("Anuncios", Icons.Filled.Warning))
            )
        }
        composable("${NavDrawerItem.EventNameScreen.route}/{title}/{address}/{description}/{organization}/{link}/{startDate}/{endDate}/{publicationDate}/{time}/{lat}/{long}/{image}/{idEvent}", arguments =
        listOf(
            navArgument("title"){type = NavType.StringType},
            navArgument("address"){type = NavType.StringType},
            navArgument("description"){type = NavType.StringType},
            navArgument("organization"){type = NavType.StringType},
            navArgument("link"){type = NavType.StringType},
            navArgument("endDate"){type = NavType.StringType},
            navArgument("publicationDate"){type = NavType.StringType},
            navArgument("time"){type = NavType.StringType},
            navArgument("lat"){type = NavType.StringType},
            navArgument("long"){type = NavType.StringType},
            navArgument("image"){type = NavType.StringType},
            navArgument("idEvent"){type = NavType.StringType}
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
            val idEvent = it.arguments?.getString("idEvent")
            EventNameScreen(navController = navController, menuViewModel = menuViewModel, eventNameViewModel = eventNameViewModel, event = event, imageEvent = imageEvent!!, idEvent = idEvent!!, sqlDataBase = sqlDataBase)
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
            PharmaciesScreen(navController = navController, menuViewModel = menuViewModel, pharmacyViewModel = pharmacyViewModel)
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
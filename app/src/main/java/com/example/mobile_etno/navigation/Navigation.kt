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
import com.example.mobile_etno.models.Pharmacy
import com.example.mobile_etno.models.service.database.SqlDataBase
import com.example.mobile_etno.viewmodels.*
import com.example.mobile_etno.viewmodels.locality.LocalityViewModel
import com.example.mobile_etno.views.components.choose.ChooseLocalityScreen
import com.example.mobile_etno.views.components.choose.LocalitiesChooseScreen
import com.example.mobile_etno.views.screen.events.EventNameScreen
import com.example.mobile_etno.views.screen.*
import com.example.mobile_etno.views.screen.events.EventsScreen
import com.example.mobile_etno.views.screen.pharmacy.PharmaciesScreen
import com.example.mobile_etno.views.screen.pharmacy.PharmacyDetails
import com.example.mobile_etno.views.screen.splash.SplashScreen
import com.example.mobile_etno.views.screen.tourism.TourismScreen

@Composable
fun Navigation(
    navController: NavHostController,
    list: List<String>,
    localityViewModel: LocalityViewModel,
    menuViewModel: MenuViewModel,
    eventNameViewModel: EventNameViewModel,
    eventViewModel: EventViewModel,
    pharmacyViewModel: PharmacyViewModel,
    tourismViewModel: TourismViewModel,
    sqlDataBase: SqlDataBase
) {
    NavHost(navController, startDestination = NavItem.Splash.route) {
        composable(NavItem.Splash.route){
            SplashScreen(navController = navController)
        }
        composable(NavItem.ChooseLocality.route){
            ChooseLocalityScreen(navController = navController)
        }
        composable(NavItem.Localities.route){
            LocalitiesChooseScreen(localityViewModel = localityViewModel)
        }
        composable(NavItem.Home.route) {
            HomeScreen(list, navController = navController,
                menuViewModel = menuViewModel,
                eventViewModel = eventViewModel,
                pharmacyViewModel = pharmacyViewModel,
                tourismViewModel = tourismViewModel,
                listBottomNavigation = listOf(NavigationBottom("Noticias", Icons.Filled.Search), NavigationBottom("Menu", Icons.Filled.Home), NavigationBottom("Anuncios", Icons.Filled.Warning)))
        }
        composable(
            NavItem.Events.route,
        ) {
            EventsScreen(
                navController = navController,
                menuViewModel = menuViewModel,
                eventViewModel = eventViewModel,
                sqlDataBase = sqlDataBase,
                listBottomNavigation = listOf(NavigationBottom("Noticias", Icons.Filled.Search), NavigationBottom("Menu", Icons.Filled.Home), NavigationBottom("Anuncios", Icons.Filled.Warning))
            )
        }
        composable("${NavItem.EventNameScreen.route}/{title}/{address}/{description}/{organization}/{reservePrice}/{link}/{startDate}/{endDate}/{publicationDate}/{time}/{lat}/{long}/{image}/{idEvent}", arguments =
        listOf(
            navArgument("title"){type = NavType.StringType},
            navArgument("address"){type = NavType.StringType},
            navArgument("description"){type = NavType.StringType},
            navArgument("organization"){type = NavType.StringType},
            navArgument("reservePrice"){type = NavType.StringType},
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
                reservePrice = it.arguments?.getString("reservePrice")?.toDouble(),
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
        composable(NavItem.Reservations.route) {
            ReservationsScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavItem.Deaths.route) {
            DeathsScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavItem.Phone.route) {
            PhoneScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavItem.News.route){
            NewsScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavItem.Gallery.route) {
            GalleryScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavItem.Pharmacies.route){
            PharmaciesScreen(navController = navController, menuViewModel = menuViewModel, pharmacyViewModel = pharmacyViewModel)
        }
        composable("${NavItem.DetailPharmacy.route}?imageUrl={imageUrl}&link={link}&type={type}&name={name}&phone={phone}&description={description}", arguments = listOf(
            navArgument("link"){ nullable = true; type = NavType.StringType },
            navArgument("imageUrl"){ nullable = true; type = NavType.StringType },
            navArgument("name"){ nullable = true; type = NavType.StringType },
            navArgument("type"){ nullable = true; type = NavType.StringType },
            navArgument("phone"){ nullable = true; type = NavType.StringType },
            navArgument("description"){ nullable = true; type = NavType.StringType }
        )
        ){
            PharmacyDetails(navController = navController, pharmacy = Pharmacy(
                type = it.arguments?.getString("type"),
                name = it.arguments?.getString("name"),
                link = it.arguments?.getString("link"),
                imageUrl = it.arguments?.getString("imageUrl"),
                phone = it.arguments?.getString("phone"),
                description = it.arguments?.getString("description")
            ))
        }
        composable(NavItem.Sponsors.route){
            SponsorsScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavItem.Festivities.route){
            FestivitiesScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavItem.Advertisements.route){
            AdvertisementsScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavItem.Services.route){
            ServicesScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavItem.Tourism.route){
            TourismScreen(navController = navController, tourismViewModel = tourismViewModel)
        }
        composable(NavItem.Incidents.route){
            IncidentsScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavItem.Links.route){
            LinksScreen(navController = navController, menuViewModel = menuViewModel)
        }
        composable(NavItem.Bandos.route){
            BandosScreen(navController = navController, menuViewModel = menuViewModel)
        }
    }
}
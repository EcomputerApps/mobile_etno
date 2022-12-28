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
import com.example.mobile_etno.models.*
import com.example.mobile_etno.models.service.database.SqlDataBase
import com.example.mobile_etno.viewmodels.*
import com.example.mobile_etno.viewmodels.locality.LocalityViewModel
import com.example.mobile_etno.views.components.choose.ChooseLocalityScreen
import com.example.mobile_etno.views.components.choose.LocalitiesChooseScreen
import com.example.mobile_etno.views.screen.events.EventNameScreen
import com.example.mobile_etno.views.screen.*
import com.example.mobile_etno.views.screen.death.DeathDetails
import com.example.mobile_etno.views.screen.death.DeathsScreen
import com.example.mobile_etno.views.screen.events.EventsScreen
import com.example.mobile_etno.views.screen.pharmacy.PharmaciesScreen
import com.example.mobile_etno.views.screen.pharmacy.PharmacyDetails
import com.example.mobile_etno.views.screen.splash.SplashScreen
import com.example.mobile_etno.views.screen.tourism.TourismDetails
import com.example.mobile_etno.views.screen.tourism.TourismScreen

@Composable
fun Navigation(
    navController: NavHostController,
    list: List<String>,
    localityViewModel: LocalityViewModel,
    userVillagerViewModel: UserVillagerViewModel,
    eventNameViewModel: EventNameViewModel,
    fcmViewModel: FCMViewModel,
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
            LocalitiesChooseScreen(localityViewModel = localityViewModel, navController = navController)
        }
        composable(NavItem.Home.route) {
            HomeScreen(list, navController = navController,
                userVillagerViewModel = userVillagerViewModel,
                fcmViewModel = fcmViewModel,
                listBottomNavigation = listOf(NavigationBottom("Noticias", Icons.Filled.Search), NavigationBottom("Menu", Icons.Filled.Home), NavigationBottom("Anuncios", Icons.Filled.Warning)))
        }
        composable(
            NavItem.Events.route,
        ) {
            EventsScreen(
                navController = navController,
                userVillagerViewModel = userVillagerViewModel,
                sqlDataBase = sqlDataBase,
                listBottomNavigation = listOf(NavigationBottom("Noticias", Icons.Filled.Search), NavigationBottom("Menu", Icons.Filled.Home), NavigationBottom("Anuncios", Icons.Filled.Warning))
            )
        }
        composable("${NavItem.EventNameScreen.route}?title={title}?address={address}?description={description}?organization={organization}?reservePrice={reservePrice}?link={link}?startDate={startDate}?endDate={endDate}?publicationDate={publicationDate}?time={time}?lat={lat}?long={long}?image={image}?idEvent={idEvent}", arguments =
        listOf(
            navArgument("title"){type = NavType.StringType},
            navArgument("address"){type = NavType.StringType},
            navArgument("description"){type = NavType.StringType},
            navArgument("organization"){type = NavType.StringType},
            navArgument("reservePrice"){type = NavType.StringType},
            navArgument("link"){nullable = true; defaultValue = "null"; type = NavType.StringType},
            navArgument("endDate"){type = NavType.StringType},
            navArgument("publicationDate"){type = NavType.StringType},
            navArgument("time"){type = NavType.StringType},
            navArgument("lat"){type = NavType.StringType},
            navArgument("long"){type = NavType.StringType},
            navArgument("image"){nullable= true; type = NavType.StringType},
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
            EventNameScreen(navController = navController, eventNameViewModel = eventNameViewModel, event = event, imageEvent = imageEvent!!, idEvent = idEvent!!, sqlDataBase = sqlDataBase)
        }
        composable(NavItem.Reservations.route) {
            ReservationsScreen(navController = navController)
        }
        composable(NavItem.Deaths.route) {
            DeathsScreen(navController = navController, userVillagerViewModel = userVillagerViewModel)
        }
        composable("${NavItem.DetailDeath.route}?username={username}&name={name}&deathDate={deathDate}&description={description}&imageUrl={imageUrl}",
        arguments = listOf(
            navArgument("username"){ nullable = true; type = NavType.StringType; },
            navArgument("name"){ nullable = true; type = NavType.StringType },
            navArgument("deathDate"){ nullable = true; type = NavType.StringType },
            navArgument("description"){ nullable = true; type = NavType.StringType },
            navArgument("imageUrl"){ nullable = true; type = NavType.StringType; defaultValue = "null" }
        )) {
            DeathDetails(navController = navController, death = Death(
                username = it.arguments?.getString("username"),
                name = it.arguments?.getString("name"),
                deathDate = it.arguments?.getString("deathDate"),
                description = it.arguments?.getString("description"),
                imageUrl = it.arguments?.getString("imageUrl")
            ))
        }
        composable(NavItem.Phone.route) {
            PhoneScreen(navController = navController)
        }
        composable(NavItem.News.route){
            NewsScreen(navController = navController)
        }
        composable(NavItem.Gallery.route) {
            GalleryScreen(navController = navController)
        }
        composable(NavItem.Pharmacies.route){
            PharmaciesScreen(navController = navController, userVillagerViewModel = userVillagerViewModel)
        }
        composable("${NavItem.DetailPharmacy.route}?imageUrl={imageUrl}&link={link}&type={type}&name={name}&phone={phone}&description={description}", arguments = listOf(
            navArgument("link"){ nullable = true; type = NavType.StringType },
            navArgument("imageUrl"){ nullable = true; defaultValue = "null" ;type = NavType.StringType },
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
            SponsorsScreen(navController = navController)
        }
        composable(NavItem.Festivities.route){
            FestivitiesScreen(navController = navController)
        }
        composable(NavItem.Advertisements.route){
            AdvertisementsScreen(navController = navController)
        }
        composable(NavItem.Services.route){
            ServicesScreen(navController = navController)
        }
        composable(NavItem.Tourism.route){
            TourismScreen(navController = navController, userVillagerViewModel = userVillagerViewModel)
        }
        composable("${NavItem.DetailTourism.route}?type={type}&username={username}&title={title}&description={description}&imageUrl={imageUrl}", arguments = listOf(
            navArgument("type"){ nullable = true; type = NavType.StringType },
            navArgument("username"){ nullable = true; type = NavType.StringType },
            navArgument("title"){ nullable = true; type = NavType.StringType },
            navArgument("description"){ nullable = true; type = NavType.StringType },
            navArgument("imageUrl"){ nullable = true; type = NavType.StringType }
        )){
            TourismDetails(navController = navController, tourism = Tourism(
                type = it.arguments?.getString("type"),
                username = it.arguments?.getString("username"),
                title = it.arguments?.getString("title"),
                description = it.arguments?.getString("description"),
                imageUrl = it.arguments?.getString("imageUrl")
            ))
        }
        composable(NavItem.Incidents.route){
            IncidentsScreen(navController = navController)
        }
        composable(NavItem.Links.route){
            LinksScreen(navController = navController)
        }
        composable(NavItem.Bandos.route){
            BandosScreen(navController = navController)
        }
    }
}
package com.example.mobile_etno.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mobile_etno.*
import com.example.mobile_etno.models.*
import com.example.mobile_etno.models.news.New
import com.example.mobile_etno.models.service.database.SqlDataBase
import com.example.mobile_etno.viewmodels.*
import com.example.mobile_etno.viewmodels.locality.LocalityViewModel
import com.example.mobile_etno.views.components.choose.ChooseLocalityScreen
import com.example.mobile_etno.views.components.choose.LocalitiesChooseScreen
import com.example.mobile_etno.views.modern.HomeEtno
import com.example.mobile_etno.views.screen.*
import com.example.mobile_etno.views.screen.death.DeathDetails
import com.example.mobile_etno.views.screen.death.DeathsScreen
import com.example.mobile_etno.views.screen.discovery.DiscoveryScreen
import com.example.mobile_etno.views.screen.events.EventsScreen
import com.example.mobile_etno.views.screen.gallery.GalleryScreen
import com.example.mobile_etno.views.screen.gallery.ImageDetail
import com.example.mobile_etno.views.screen.incident.AddIncident
import com.example.mobile_etno.views.screen.incident.IncidentsScreen
import com.example.mobile_etno.views.screen.news.NewDetails
import com.example.mobile_etno.views.screen.news.NewsScreen
import com.example.mobile_etno.views.screen.pharmacy.PharmaciesScreen
import com.example.mobile_etno.views.screen.pharmacy.PharmacyDetails
import com.example.mobile_etno.views.screen.phone.PhoneDetailsList
import com.example.mobile_etno.views.screen.phone.PhoneScreen
import com.example.mobile_etno.views.screen.splash.SplashScreen
import com.example.mobile_etno.views.screen.tourism.TourismDetails
import com.example.mobile_etno.views.screen.tourism.TourismScreen

@Composable
fun Navigation(
    navController: NavHostController,
    list: List<String>,
    localityViewModel: LocalityViewModel,
    userVillagerViewModel: UserVillagerViewModel,
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
            LocalitiesChooseScreen(localityViewModel = localityViewModel, navController = navController, userVillagerViewModel = userVillagerViewModel)
        }
        composable(NavItem.Home.route) {
            HomeScreen(
                list,
                navController = navController,
                userVillagerViewModel = userVillagerViewModel,
                fcmViewModel = fcmViewModel)
                //listBottomNavigation = listOf(NavigationBottom("Noticias", Icons.Filled.Search), NavigationBottom("Menu", Icons.Filled.Home), NavigationBottom("Anuncios", Icons.Filled.Warning)))
        }
        composable(NavItem.HomeModern.route){
            HomeEtno(navController = navController, userVillagerViewModel = userVillagerViewModel)
        }
        composable(NavItem.DiscoverySections.route){
            DiscoveryScreen(navController, userVillagerViewModel)
        }
        composable(
            NavItem.Events.route,
        ) {
            EventsScreen(
                navController = navController,
                userVillagerViewModel = userVillagerViewModel,
                sqlDataBase = sqlDataBase
               // listBottomNavigation = listOf(NavigationBottom("Noticias", Icons.Filled.Search), NavigationBottom("Menu", Icons.Filled.Home), NavigationBottom("Anuncios", Icons.Filled.Warning))
            )
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
            PhoneScreen(navController = navController, userVillagerViewModel = userVillagerViewModel)
        }
        composable(NavItem.PhoneDetailsList.route) {
            PhoneDetailsList(navController = navController, userVillagerViewModel = userVillagerViewModel)
        }
        composable(NavItem.News.route){
            NewsScreen(navController = navController, userVillagerViewModel = userVillagerViewModel)
        }
        composable("${NavItem.NewDetails.route}?username={username}&category={category}&title={title}&publicationDate={publicationDate}&description={description}&imageUrl={imageUrl}",
        arguments = listOf(
            navArgument("username"){ nullable = true; type = NavType.StringType },
            navArgument("category"){ nullable = true; type = NavType.StringType },
            navArgument("title"){ nullable = true; type = NavType.StringType },
            navArgument("publicationDate"){ nullable = true; type = NavType.StringType },
            navArgument("description"){ nullable = true; type = NavType.StringType },
            navArgument("imageUrl"){ nullable = true; NavType.StringType; defaultValue = "image temp" }
        )){
            NewDetails(
                new = New(
                    username = it.arguments?.getString("username"),
                    category = it.arguments?.getString("category"),
                    title = it.arguments?.getString("title"),
                    publicationDate = it.arguments?.getString("publicationDate"),
                    description = it.arguments?.getString("description"),
                    imageUrl = it.arguments?.getString("imageUrl")
                )
            )
        }
        composable(NavItem.Gallery.route) {
            GalleryScreen(navController = navController, userVillagerViewModel = userVillagerViewModel)
        }
        composable("${NavItem.ImageDetail.route}?link={link}",
        arguments = listOf(navArgument("link"){ nullable = true; NavType.StringType; defaultValue = "null" }
        )
        ){
            ImageDetail(link = it.arguments?.getString("link")!!)
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
        composable(NavItem.Incidents.route){ IncidentsScreen(navController, userVillagerViewModel) }
        composable(NavItem.CreateIncident.route){ AddIncident(
            navController = navController,
            userVillagerViewModel = userVillagerViewModel
        ) }
        composable(NavItem.Links.route){
            LinksScreen(navController = navController)
        }
        composable(NavItem.Bandos.route){
            BandosScreen(navController = navController)
        }
    }
}
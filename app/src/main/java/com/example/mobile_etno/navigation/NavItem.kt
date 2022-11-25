package com.example.mobile_etno.navigation
import androidx.compose.runtime.Stable
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Stable
sealed class NavItem(
    val baseRoute: String,
    val navArgs: List<EtnoArg> = emptyList(),
){
    val route = run {
        //baseRoute/{arg1}/{arg2}/{arg3}...
        val argsKey = navArgs.map {
                item -> "{${item.key}}"
        }
        //Concat base then key arg with separator by this "/"
        listOf(baseRoute)
            .plus(argsKey)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key){type = it.navType}
    }
    object HomeScreen: NavItem("Home")
    object NavigationDrawerScreen: NavItem("Drawer")
    object EventScreen: NavItem("Eventos", listOf(EtnoArg.SCREENAME))
    object ReservationsScreen: NavItem("Reservaciones", listOf(EtnoArg.SCREENAME))
    object DeathsScreen: NavItem("Muertes", listOf(EtnoArg.SCREENAME))
    object PhoneScreen: NavItem("Telefonos", listOf(EtnoArg.SCREENAME))
    object NewsScreen: NavItem("Noticias", listOf(EtnoArg.SCREENAME))
    object GalleryScreen: NavItem("Galeria", listOf(EtnoArg.SCREENAME))
    object PharmaciesScreen: NavItem("Farmacias", listOf(EtnoArg.SCREENAME))
    object SponsorsScreen: NavItem("Patrocinadores", listOf(EtnoArg.SCREENAME))
    object FestivitiesScreen: NavItem("Fiestas", listOf(EtnoArg.SCREENAME))
    object AdvertisementsScreen: NavItem("Anuncios", listOf(EtnoArg.SCREENAME))
    object TourismScreen: NavItem("Turismo", listOf(EtnoArg.SCREENAME))
    object ServicesScreen: NavItem("Servicio", listOf(EtnoArg.SCREENAME))
    object IncidentsScreen: NavItem("Incidentes", listOf(EtnoArg.SCREENAME))
    object LinksScreen: NavItem("Enlaces", listOf(EtnoArg.SCREENAME))
    object BandosScreen: NavItem("Bandos", listOf(EtnoArg.SCREENAME))
}

enum class EtnoArg(
    val key: String,
    val navType: NavType<*>
){
    SCREENAME("name", NavType.StringType),
    LISTNAME("list", NavType.StringArrayType)
}

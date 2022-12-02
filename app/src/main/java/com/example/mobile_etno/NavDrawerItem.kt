package com.example.mobile_etno

sealed class NavDrawerItem(var route: String, var icon: Int? = null, var title: String? = null) {
    object Home : NavDrawerItem("home")
    object Events : NavDrawerItem("Eventos", R.drawable.home_test, "Eventos")
    object EventNameScreen: NavDrawerItem("NombreEventoScreen")
    object Reservations : NavDrawerItem("Reservaciones", R.drawable.home_test, "Reservaciones")
    object Deaths : NavDrawerItem("Muertes", R.drawable.home_test, "Muertes")
    object Phone : NavDrawerItem("Telefonos", R.drawable.home_test, "Telefono")
    object News : NavDrawerItem("Noticias", R.drawable.home_test, "Noticias")
    object Gallery : NavDrawerItem("Galeria", R.drawable.home_test, "Galería")
    object Pharmacies: NavDrawerItem("Farmacias", R.drawable.home_test, "Farmacias")
    object Sponsors: NavDrawerItem("Patrocinadores", R.drawable.home_test, "Patrocinadores")
    object Festivities: NavDrawerItem("Fiestas", R.drawable.home_test, "Fiestas")
    object Advertisements: NavDrawerItem("Anuncios", R.drawable.home_test, "Anuncios")
    object Services: NavDrawerItem("Servicios", R.drawable.home_test, "Servicios")
    object Tourism: NavDrawerItem("Turismo", R.drawable.home_test, "Turismo")
    object Incidents: NavDrawerItem("Incidentes", R.drawable.home_test, "Incidentes")
    object Links: NavDrawerItem("Enlaces", R.drawable.home_test, "enlaces")
    object Bandos: NavDrawerItem("Bandos", R.drawable.home_test, "bandos")
}
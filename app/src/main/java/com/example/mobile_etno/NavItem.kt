package com.example.mobile_etno

sealed class NavItem(var route: String, var icon: Int? = null, var title: String? = null) {
    object Home : NavItem("home")
    object Events : NavItem("Eventos", R.drawable.home_test, "Eventos")
    object EventNameScreen: NavItem("NombreEventoScreen")
    object Reservations : NavItem("Reservaciones", R.drawable.home_test, "Reservaciones")
    object Deaths : NavItem("Muertes", R.drawable.home_test, "Muertes")
    object Phone : NavItem("Telefonos", R.drawable.home_test, "Telefono")
    object News : NavItem("Noticias", R.drawable.home_test, "Noticias")
    object Gallery : NavItem("Galeria", R.drawable.home_test, "Galería")
    object Pharmacies: NavItem("Farmacias", R.drawable.home_test, "Farmacias")
    object DetailPharmacy: NavItem("PharmacyDetails")
    object Sponsors: NavItem("Patrocinadores", R.drawable.home_test, "Patrocinadores")
    object Festivities: NavItem("Fiestas", R.drawable.home_test, "Fiestas")
    object Advertisements: NavItem("Anuncios", R.drawable.home_test, "Anuncios")
    object Services: NavItem("Servicios", R.drawable.home_test, "Servicios")
    object Tourism: NavItem("Turismo", R.drawable.home_test, "Turismo")
    object Incidents: NavItem("Incidentes", R.drawable.home_test, "Incidentes")
    object Links: NavItem("Enlaces", R.drawable.home_test, "enlaces")
    object Bandos: NavItem("Bandos", R.drawable.home_test, "bandos")
}
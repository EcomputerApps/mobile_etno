package com.example.mobile_etno.views.screen.discovery

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.R
import com.example.mobile_etno.models.FCMToken
import com.example.mobile_etno.models.SectionsMenu
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.modern.navigationbottom.BottomNavigationCustom
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

@Composable
fun DiscoveryScreen(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
){
    Scaffold(
        topBar = {},
        bottomBar = { BottomNavigationCustom(
            navController = navController,
            stateNavigationButton = 2,
            userVillagerViewModel = userVillagerViewModel
        ) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Menú", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(8.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    items(
                        listOf(
                            SectionsMenu(R.drawable.events_icon, "Eventos"),
                            SectionsMenu(R.drawable.explore_tourism, "Turismo"),
                            SectionsMenu(R.drawable.vaccines_pharmacy, "Farmacias"),
                            SectionsMenu(R.drawable.service, "Servicios"),
                            SectionsMenu(R.drawable.news, "Noticias"),
                            SectionsMenu(R.drawable.news, "Festividades"),
                            SectionsMenu(R.drawable.news, "Bandos"),
                            SectionsMenu(R.drawable.news, "Anuncios"),
                            SectionsMenu(R.drawable.news, "Galería"),
                            SectionsMenu(R.drawable.news, "Difunciones"),
                            SectionsMenu(R.drawable.news, "Enlaces"),
                            SectionsMenu(R.drawable.news, "Patrocinadores"),
                            SectionsMenu(R.drawable.news, "Incidentes"),
                            SectionsMenu(R.drawable.news, "Reservaciones")
                        )){ item ->
                        Card(
                            elevation = 4.dp,
                            modifier = Modifier
                                .clickable {
                                    when(item.sectionName){
                                        "Turismo" -> navController.navigate(NavItem.Tourism.route){ userVillagerViewModel.getUserToVillagerTourism() }
                                        "Farmacias" -> navController.navigate(NavItem.Pharmacies.route){ userVillagerViewModel.getUserToVillagerPharmacies() }
                                        "Servicios" -> navController.navigate(NavItem.Phone.route){  }
                                        "Incidentes" -> navController.navigate(NavItem.Incidents.route){
                                            FirebaseMessaging.getInstance().token.addOnCompleteListener(
                                                OnCompleteListener { task ->
                                                if (!task.isSuccessful) {
                                                    Log.w("failed fcm", "Fetching FCM registration token failed", task.exception)
                                                    return@OnCompleteListener
                                                }
                                                val token = task.result
                                                userVillagerViewModel.getVillagerIncidents(fcmToken = token)
                                            })
                                        }
                                        "Galería" -> navController.navigate(NavItem.Gallery.route){ userVillagerViewModel.getImagesByLocality() }
                                        "Bandos" -> navController.navigate(NavItem.Bandos.route){  }
                                    }
                                }
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                                Column() {
                                    Icon(
                                        painter = painterResource(id = item.icon),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.Blue
                                    )
                                    Text(text = item.sectionName)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
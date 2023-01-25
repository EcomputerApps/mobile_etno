package com.example.mobile_etno.views.screen.incident

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.models.IncidentModel
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.components.connection.EmptyOrConnectionScreen
import com.example.mobile_etno.views.modern.navigationbottom.BottomNavigationCustom

@Composable
fun IncidentsScreen(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
){
    val currentContext = LocalContext.current
    val getIncidents = userVillagerViewModel.saveIncidentsToVillager.collectAsState()
    val connection = userVillagerViewModel.connection.collectAsState()

    Scaffold(
        topBar = {},
        bottomBar = { BottomNavigationCustom(
            navController = navController,
            stateNavigationButton = -1,
            userVillagerViewModel = userVillagerViewModel
        )}
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                if(connection.value){
                    Text(
                        text = "Mis Incidencias",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                    if(getIncidents.value.isNotEmpty()){
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ){
                            items(getIncidents.value){
                                    item ->
                                IncidentCard(
                                    title = item.title!!,
                                    description = item.description!!,
                                    onClick = { Toast.makeText(currentContext, item.title ,Toast.LENGTH_SHORT).show() }
                                )
                            }
                        }
                    }else{
                        EmptyOrConnectionScreen(icon = com.example.mobile_etno.R.drawable.no_backpack, prop = "No hay incidencias en este momento")
                    }
                }
            }
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = 35.dp,
                        end = 8.dp
                    ),
                onClick = { navController.navigate(NavItem.CreateIncident.route){  } }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Localized description")
            }
            if(!connection.value) {
                EmptyOrConnectionScreen(
                    icon = com.example.mobile_etno.R.drawable.wifi_off,
                    prop = "Por favor, comprueba tu conexión a internet"
                )
            }
        }
    }
}
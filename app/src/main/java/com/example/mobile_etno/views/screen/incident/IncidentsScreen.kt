package com.example.mobile_etno.views.screen.incident

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.models.IncidentModel
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.modern.navigationbottom.BottomNavigationCustom

@Composable
fun IncidentsScreen(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
){
    Box(

    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "Incidentes",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = FontFamily.SansSerif
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                items(listOf(
                    IncidentModel(description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."),
                    IncidentModel(description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."),
                    IncidentModel(description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."),
                    IncidentModel(description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."),
                    IncidentModel(description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."),
                    IncidentModel(description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."),
                    IncidentModel(description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."),
                    IncidentModel(description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                )){
                    IncidentCard(description = it.description!!)

                }

            }

        }
        BottomNavigationCustom(
            navController = navController,
            stateNavigationButton = -1,
            userVillagerViewModel = userVillagerViewModel
        )
    }
}
package com.example.mobile_etno.views.screen.death

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.R
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.modern.navigationbottom.BottomNavigationCustom

@Composable
fun DeathsScreen(navController: NavHostController, userVillagerViewModel: UserVillagerViewModel) {
   // BackHandler() { navController.navigate(NavItem.Home.route) { } }
    val deaths = userVillagerViewModel.userVillagerDeaths.collectAsState()

    Scaffold(
        bottomBar = { BottomNavigationCustom(
            navController = navController,
            stateNavigationButton = -1,
            userVillagerViewModel = userVillagerViewModel
        ) }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "Defunciones", fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    fontFamily = FontFamily.SansSerif)
                LazyColumn(
                    verticalArrangement = Arrangement
                        .spacedBy(16.dp)
                ){
                    items(deaths.value){ death ->
                        Card(
                            elevation = 3.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column() {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(painter = painterResource(id = R.drawable.pass_away), contentDescription = "", modifier = Modifier.size(40.dp), tint = Color.Black)
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(text = death.name!!, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                        Text(text = "Fallecido el ${death.deathDate}", color = Color.Gray, fontSize = 12.sp)
                                        Text(text = death.description!!)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            }
        }
    }
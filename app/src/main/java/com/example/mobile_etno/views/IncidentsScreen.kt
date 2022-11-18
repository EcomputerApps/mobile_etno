package com.example.mobile_etno.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.mobile_etno.components.TopBarScreen
import com.example.mobile_etno.viewmodels.MenuViewModel

@Composable
fun IncidentsScreen(screenName: String, navHostController: NavHostController){
    val screenNameProp = remember{ screenName }
    TopBarScreen(screenName = screenNameProp, navHostController = navHostController)
}
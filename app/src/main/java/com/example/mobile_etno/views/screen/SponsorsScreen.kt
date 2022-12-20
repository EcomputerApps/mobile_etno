package com.example.mobile_etno.views.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.MenuViewModel
import com.example.mobile_etno.views.ScreenTopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SponsorsScreen(menuViewModel: MenuViewModel, navController: NavHostController){

    BackHandler() {
        navController.navigate(NavItem.Home.route){
            menuViewModel.updateInvisible(true)
        }
    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.backgroundEtno)
            .wrapContentSize(Alignment.Center)
    ) {

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                ScreenTopBar(
                    navController = navController,
                    nameScreen = "Patrocinadores"
                )
            },
            drawerBackgroundColor = Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            /*
            drawerContent = {
                Drawer(
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    menuViewModel
                )
            },
             */
            backgroundColor = Color.Red
        ) {
            Surface(color = Colors.backgroundEtno, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Sponsors View",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }
}
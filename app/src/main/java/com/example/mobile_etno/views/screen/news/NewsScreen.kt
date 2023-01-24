package com.example.mobile_etno.views.screen.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.modern.navigationbottom.BottomNavigationCustom

@Composable
fun NewsScreen(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
){
    var selectedTabIndex by remember { mutableStateOf(0) }
    val news = userVillagerViewModel.userVillagerNews.collectAsState()
    val connection = userVillagerViewModel.connection.collectAsState()

    Scaffold(
        topBar = {},
        bottomBar = { BottomNavigationCustom(
            navController = navController,
            stateNavigationButton = 1,
            userVillagerViewModel = userVillagerViewModel
        ) }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            if(connection.value){
                CustomScrollableNews(
                    newCategoryList = listOf("General","Tecnología", "Salud", "Entretenimiento", "Negocios"),
                    selectedTabIndex = selectedTabIndex, userVillagerViewModel = userVillagerViewModel
                ){ index -> selectedTabIndex = index }
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding()
            ) {
                Column(
                ) {
                    if(connection.value){
                        CardNewList(news = news.value, navController)
                    }else{
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Text(text = "Por favor, comprueba tu conexión a internet", fontWeight = FontWeight.W700, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomScrollableNews(
    newCategoryList: List<String>,
    userVillagerViewModel: UserVillagerViewModel,
    selectedTabIndex: Int,
    onItemClick: (Int) -> Unit
){
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
       // backgroundColor = Color.Transparent,
       // contentColor = Color.Transparent,
        //divider = {  }
    ) {
        newCategoryList.forEachIndexed {
                index, category ->
            Tab(selected = selectedTabIndex == index, onClick =
            {
                onItemClick.invoke(index)
                when(category){
                    "General" -> userVillagerViewModel.newsFilterAll()
                    "Tecnología" -> userVillagerViewModel.newsFilter("Tecnología")
                    "Salud" -> userVillagerViewModel.newsFilter("Salud")
                    "Entretenimiento" -> userVillagerViewModel.newsFilter("Entretenimiento")
                    "Negocios" -> userVillagerViewModel.newsFilter("Negocios")
                }
            }, modifier = Modifier
                .padding(horizontal = 5.dp)
                .height(40.dp)
            ) {
                Text(text = category)
            }
        }
    }
}
package com.example.mobile_etno.views.screen.link

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.R
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.modern.navigationbottom.BottomNavigationCustom

@Composable
fun LinksScreen(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
){
    val uriHandler = LocalUriHandler.current
    val links = userVillagerViewModel.userLinks.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationCustom(
                navController = navController,
                stateNavigationButton = -1,
                userVillagerViewModel = userVillagerViewModel
        ) }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Enlaces", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                LazyColumn(
                    verticalArrangement = Arrangement
                        .spacedBy(14.dp)
                ){
                    items(links.value){
                            item ->
                        Card(
                            backgroundColor = Color.Red,
                            contentColor = Color.White,
                            elevation = 4.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    uriHandler.openUri(item.url!!)
                                }
                        ) {
                            Column() {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(painter = painterResource(id = R.drawable.link), contentDescription = "campaigns", modifier = Modifier.size(40.dp), tint = Color.White)
                                    Text(text = item.title!!, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        Icon(painter = painterResource(id = R.drawable.right), contentDescription = "right", modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .padding(4.dp), tint = Color.White)
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
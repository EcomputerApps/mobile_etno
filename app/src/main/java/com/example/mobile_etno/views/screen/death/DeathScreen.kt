package com.example.mobile_etno.views.screen.death

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.R
import com.example.mobile_etno.isInternetAvailable
import com.example.mobile_etno.utils.StringSpace
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.ScreenTopBar
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun DeathsScreen(navController: NavHostController, userVillagerViewModel: UserVillagerViewModel) {

    BackHandler() { navController.navigate(NavItem.Home.route) { } }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val deaths = userVillagerViewModel.userVillagerDeaths.collectAsState()
    val currentContext = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                ScreenTopBar(
                    navController = navController,
                    nameScreen = "Deaths"
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(it)
            )

            if (deaths.value.isNotEmpty() && isInternetAvailable(currentContext)) {
                LazyColumn(modifier = Modifier.padding(vertical = 8.dp)) {
                    items(deaths.value) { death ->
                        Card(elevation = 5.dp, modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {
                                val encodeUrlImage: String = if (death.imageUrl != null) {
                                    URLEncoder.encode(
                                        death.imageUrl,
                                        StandardCharsets.UTF_8.toString()
                                    )
                                } else {
                                    "null"
                                }
                                navController.navigate("${NavItem.DetailDeath.route}?username=${death.username}&name=${death.name}&deathDate=${death.deathDate}&description=${death.description}&imageUrl=${death.imageUrl}")
                            }) {

                            Row(
                                modifier = Modifier
                                    .background(Color.White)
                                    .width(400.dp)
                            ) {
                                Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.pass_away_icon),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .height(40.dp)
                                        .width(40.dp)
                                        .clip(
                                            CircleShape
                                        )
                                )
                                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                    Text(
                                        text = death.name!!,
                                        style = MaterialTheme.typography.h6,
                                    )
                                    Text(
                                        text = (if (death.description!!.length >= 70) death.description!!.substring(
                                            0,
                                            37
                                        ) else StringSpace.padRight(death.description!!, 40)),
                                        color = Color.LightGray
                                    )
                                }
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_rigth),
                                    contentDescription = "",
                                    tint = Color.Red,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)

                                )
                            }
                        }
                    }
                }
            }
            if (!isInternetAvailable(currentContext)){
                //SHOW DATABASE DATA... WHEN THE USER DOESN'T HAVE CONNECTION
            }

            if(deaths.value.isEmpty() && isInternetAvailable(currentContext)){
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(painter = painterResource(id = R.drawable.nothing), contentDescription = "", modifier = Modifier.size(150.dp))
                            Text(text = "¡No hay defunciones disponibles!")
                        }
                    }
            }
        }
    }
}


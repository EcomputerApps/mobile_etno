package com.example.mobile_etno.views.screen.events

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.R
import com.example.mobile_etno.isInternetAvailable
import com.example.mobile_etno.models.NavigationBottom
import com.example.mobile_etno.models.service.database.SqlDataBase
import com.example.mobile_etno.utils.Parse
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.EventViewModel
import com.example.mobile_etno.viewmodels.MenuViewModel
import com.example.mobile_etno.views.ScreenTopBar
import com.example.mobile_etno.views.components.NotConnectionScreen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.color.KalendarThemeColor
import com.himanshoe.kalendar.model.KalendarType
import kotlinx.coroutines.delay
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun EventsScreen(
    listBottomNavigation: List<NavigationBottom>,
    menuViewModel: MenuViewModel,
    eventViewModel: EventViewModel,
    navController: NavHostController,
    sqlDataBase: SqlDataBase
) {
    var selectedItem by remember { mutableStateOf(-1) }

    val currentContext = LocalContext.current
    val infoDialog = remember { mutableStateOf(false) }

    var date by remember {
        mutableStateOf("")
    }

    LaunchedEffect(eventViewModel.isRefreshing){
        if (eventViewModel.isRefreshing){
            eventViewModel.getEventRequest()
            delay(3000)
            // Log.d("winded up", "finish")
            eventViewModel.isRefreshing = false
        }
    }

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

    ) {

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { ScreenTopBar(navController = navController, nameScreen = "Events") },
            drawerBackgroundColor = com.example.mobile_etno.utils.colors.Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            /*
            drawerContent = {
                Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController, menuViewModel)
            },
             */
            backgroundColor = Color.Red
        ) {
            Box(modifier = Modifier.background(Color.White)) {
                Kalendar(kalendarEvents = eventViewModel.calendarEvents.value.toList(), onCurrentDayClick = { kalendarDay, list -> date =
                    "${kalendarDay.localDate.dayOfMonth}-${kalendarDay.localDate.monthNumber}-${kalendarDay.localDate.year}"
                    Log.d("events", list.size.toString())
                },
                    kalendarThemeColor = KalendarThemeColor(backgroundColor = Color.White, dayBackgroundColor = Color.Red, headerTextColor = Color.Black),
                    kalendarType = KalendarType.Firey)

                if(date != ""){
                    eventViewModel.eventsFilterByPublicationDate(date)
                }

                Log.d("day_current", date)

                SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = eventViewModel.isRefreshing), onRefresh = { eventViewModel.isRefreshing = true }, modifier = Modifier.fillMaxSize()) {
                    if(eventViewModel.events.value.isNotEmpty() && isInternetAvailable(currentContext)){
                        LazyColumn(modifier = Modifier
                            .padding(top = 470.dp)
                            .padding(horizontal = 35.dp)
                        ) {
                            items(eventViewModel.events.value) { item ->
                                Card(modifier = Modifier.clickable {
                                    // Toast.makeText(currentContext, item.title, Toast.LENGTH_SHORT).show()
                                    val encodeUrlLink = URLEncoder.encode(item.link, StandardCharsets.UTF_8.toString())
                                    val encodeStartDate = URLEncoder.encode(item.startDate, StandardCharsets.UTF_8.toString())
                                    val encodeEndDate = URLEncoder.encode(item.endDate, StandardCharsets.UTF_8.toString())
                                    val encodePublicationDate = URLEncoder.encode(item.publicationDate, StandardCharsets.UTF_8.toString())
                                    val encodeUrlImage = URLEncoder.encode(item.images!![0].link, StandardCharsets.UTF_8.toString())

                                    navController.navigate("${NavItem.EventNameScreen.route}/${item.title}/${item.address}/${item.description}/${item.organization}/${item.reservePrice}/$encodeUrlLink/$encodeStartDate/$encodeEndDate/$encodePublicationDate/${item.time}/${item.lat}/${item.long}/$encodeUrlImage/${item.idEvent}"){

                                    }
                                }) {
                                    Row(modifier = Modifier
                                        .background(Color.White)
                                        .width(400.dp)) {
                                        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                                        Spacer(modifier = Modifier.padding(vertical = 16.dp))
                                        Image(
                                            painter = rememberAsyncImagePainter(model = item.images!![0].link),
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
                                                text = item.title!!,
                                                style = MaterialTheme.typography.h6
                                            )
                                            Row(){
                                                Icon(painter = painterResource(id = R.drawable.chincheta) , contentDescription = item.title, modifier = Modifier.size(20.dp))
                                                Text(text = item.address!!)
                                            }

                                            Row() {
                                                Icon(painter = painterResource(id = R.drawable.date), contentDescription = item.publicationDate, modifier = Modifier.size(20.dp))
                                                Text(text = "Fecha: ${Parse.formatEuropean(item.publicationDate!!)}")
                                                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                                                Text(text = "Tiempo: ${item.time!!}")
                                            }
                                        }
                                        Icon(
                                            painter = painterResource(id = R.drawable.arrow_rigth),
                                            contentDescription = "",
                                            tint = Color.Red,
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .width(60.dp)
                                                .height(60.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.padding(vertical = 26.dp))
                            }
                        }
                    }; if(!isInternetAvailable(currentContext)) {
                    // Text(text = sqlDataBase.getEventDb()[0].title!!)
                    eventViewModel.resetListConnection()

                    LazyColumn(modifier = Modifier
                        .padding(top = 470.dp)
                        .padding(horizontal = 35.dp)
                    ) {
                        items(sqlDataBase.getEventDb()) { item ->
                            //Here to apply logic the card filters
                            Card(modifier = Modifier.clickable {
                                // Toast.makeText(currentContext, item.title, Toast.LENGTH_SHORT).show()
                                infoDialog.value = true
                            }) {
                                Row(modifier = Modifier
                                    .background(Color.White)
                                    .width(400.dp)) {
                                    Spacer(modifier = Modifier.padding(vertical = 16.dp))

                                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                        Text(
                                            text = item.title!!,
                                            style = MaterialTheme.typography.h6
                                        )
                                        Text(text = item.address!!)

                                        Row() {
                                            Text(text = "Fecha: ${item.publicationDate}")
                                            Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                                            Text(text = "Tiempo: ${item.time!!}")
                                        }
                                    }
                                    Image(
                                        painter = painterResource(id = com.example.mobile_etno.R.drawable.arrow_rigth),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .size(40.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.padding(vertical = 26.dp))
                        }
                    }

                }
                    if(infoDialog.value){
                        NotConnectionScreen(
                            title = "Whoops!",
                            description = "No hay conexión a internet.\n" +
                                    "Compruebe su conexión.",
                            onDismiss = {
                                infoDialog.value = false
                                //Log.d("down_show", infoDialog.value.toString())
                            }
                        )
                    }
                }
                BottomNavigation(
                    modifier = Modifier.align(Alignment.BottomCenter).height(50.dp),
                    backgroundColor = Color.Red,
                    contentColor = Color.White
                ) {
                    listBottomNavigation.forEachIndexed { index, item ->
                        BottomNavigationItem(selected = selectedItem == index, onClick = {
                            when (item.name) {
                                "Noticias" -> {
                                    navController.navigate(NavItem.News.route) { }
                                }
                                "Menu" -> {
                                    navController.navigate(NavItem.Home.route) { }
                                }
                            }
                            selectedItem = index
                        }, icon = {
                            Icon(
                                imageVector = item.icon!!,
                                contentDescription = null
                            )
                        }, label = { Text(text = item.name!!) })
                    }
                }
            }
        }
    }
}
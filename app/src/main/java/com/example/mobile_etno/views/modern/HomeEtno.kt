package com.example.mobile_etno.views.modern

import android.media.metrics.Event
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.R
import com.example.mobile_etno.models.news.New
import com.example.mobile_etno.utils.StringSpace
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.modern.scrollable.ScrollableTabEvents
import com.example.mobile_etno.views.modern.scrollable.ScrollableTabNews

@Composable
fun HomeEtno(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
){
    val news = userVillagerViewModel.userVillagerNews.collectAsState()
    val events = userVillagerViewModel.userVillagerEvents.collectAsState()
    val connection = userVillagerViewModel.connection.collectAsState()

    Log.d("events::", events.value.toString())

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier
            .padding(top = 20.dp, start = 14.dp, end = 14.dp)
            .verticalScroll(
                rememberScrollState()
            )) {
            if(connection.value) {
                Row() {
                    Text(text = "Explorar", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.padding(horizontal = 120.dp))
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(text = "Noticias sugeridas para ti", color = Color.Gray)
                /*
                ScrollableTabNews(
                    listNews = if (news.value.isEmpty()) listOf(New()) else news.value,
                    onItemClick = {},
                    navController = navController
                )

                 */
                Spacer(modifier = Modifier.padding(top = 8.dp))

                CardCustomHome(
                    R.drawable.vaccines_pharmacy,
                    section = "Farmacias",
                    title = "Encuentra las farmacias",
                    description = StringSpace.padRight("Farmacias de guardia y normal", 10)
                )
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                CardCustomHome(
                    R.drawable.explore_tourism,
                    section = "Turismo",
                    title = "Encuentra el turismo mas destacado",
                    description = StringSpace.padRight("Turismo más destacado", 10)
                )
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Text(text = "Eventos", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = "Eventos más destacados para ti", color = Color.Gray)
                ScrollableTabEvents(
                    listEvents = if (events.value.isEmpty()) listOf(com.example.mobile_etno.models.Event()) else events.value,
                    oneItemClick = {},
                    navController = navController)
            }
        }
        if(!connection.value){
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(text = "Por favor, comprueba tu conexión a internet", fontWeight = FontWeight.W700, fontSize = 14.sp, modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}





@Composable
fun CardCustomHome(
    @DrawableRes
    icon: Int,
    section: String,
    title: String,
    description: String,
){
    Text(text = section, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    Spacer(modifier = Modifier.padding(top = 4.dp))
    Card(
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = "services", modifier = Modifier.padding(top = 4.dp))
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column() {
                Text(text = title, fontWeight = FontWeight.Bold)
                Text(text = description, color = Color.Gray)
            }
            Spacer(modifier = Modifier.padding(horizontal = 10.dp))
            Icon(painter = painterResource(id = R.drawable.arrow), contentDescription = "arrow", modifier = Modifier
                .size(30.dp)
                .padding(top = 2.dp))
        }
    }
}

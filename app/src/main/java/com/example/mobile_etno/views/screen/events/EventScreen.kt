package com.example.mobile_etno.views.screen.events

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.R
import com.example.mobile_etno.models.UserSubscription
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.components.FormSubscription
import com.example.mobile_etno.views.modern.navigationbottom.BottomNavigationCustom
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventScreen(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
){
    val events = userVillagerViewModel.userVillagerEvents.collectAsState()
    val event = userVillagerViewModel.userVillagerEvent.collectAsState()
    val eventSeat = userVillagerViewModel.eventSubscriptionViewModel.eventSeats.collectAsState()
    val isSubscribe = userVillagerViewModel.eventSubscriptionViewModel.isSubscribe.collectAsState()
    val connection = userVillagerViewModel.connection.collectAsState()
    val currentContext = LocalContext.current

    val infoDialog = remember { mutableStateOf(false) }
    val skipHalfExpanded by remember { mutableStateOf(false) }
    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = skipHalfExpanded
    )
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = { BottomNavigationCustom(
            navController = navController,
            stateNavigationButton = -1,
            userVillagerViewModel = userVillagerViewModel
        ) }
    ) {
        ModalBottomSheetLayout(
            modifier = Modifier.padding(it),
            sheetState = state,
            sheetContent = {
                when(event.value.title){
                    null -> {
                        Text(text = "Null")
                    }
                    else -> {
                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                            OnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                Log.w("failed fcm", "Fetching FCM registration token failed", task.exception)
                                return@OnCompleteListener
                            }
                            // Get new FCM registration token
                            val token = task.result
                            Log.d("tok", token)
                            userVillagerViewModel.eventSubscriptionViewModel.getSubscription(token, event.value.title!!)
                        })

                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(

                            ) {
                                GlideImage(
                                    imageModel = { event.value.imageUrl},
                                    success = { imageState ->
                                        Image(
                                            bitmap = imageState.imageBitmap!!,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(300.dp)
                                                .padding(5.dp)
                                                .drawWithCache {
                                                    val gradient = Brush.verticalGradient(
                                                        colors = listOf(
                                                            Color.Transparent,
                                                            Color.Black
                                                        ),
                                                        startY = size.height / 3,
                                                        endY = size.height
                                                    )
                                                    onDrawWithContent {
                                                        drawContent()
                                                        drawRect(
                                                            gradient,
                                                            blendMode = BlendMode.Multiply
                                                        )
                                                    }
                                                }, contentScale = ContentScale.FillBounds
                                        )
                                    },
                                    loading = {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(
                                                    RoundedCornerShape(30.dp)
                                                )
                                        ) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.align(Alignment.Center)
                                            )
                                        }
                                    },
                                    failure = {
                                        Box(
                                            modifier = Modifier
                                                .height(300.dp)
                                                .fillMaxWidth()
                                                .background(Color.LightGray)
                                        ) {
                                            CircularProgressIndicator(
                                                color = Color.White,
                                                modifier = Modifier.align(Alignment.Center)
                                            )
                                        }
                                    }
                                )
                                Box(
                                    modifier = Modifier.padding(24.dp)
                                ) {
                                    Column {
                                        Text(text = event.value.title!!, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                        Text(text = "${event.value.username!!} · Huesca", color = Color.Gray, fontSize = 10.sp)
                                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                        Text(text = "Capacidad", fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                        Text(text = "Plazas ${eventSeat.value}/${event.value.capacity}", color = Color.Gray, fontSize = 10.sp)
                                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                        Divider(thickness = 1.dp, color = Color.Gray)
                                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column() {
                                                Text(text = event.value.startDate!!, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                                Text(text = "Fecha de inicio", color = Color.Gray, fontSize = 10.sp)
                                            }
                                            Spacer(modifier = Modifier.padding(horizontal = 60.dp))
                                            //if (event.value.seats!! > 0) {
                                            Button(
                                                colors = ButtonDefaults.buttonColors(if (isSubscribe.value) Color.Gray else Color.Red),
                                                onClick = {
                                                    if (isSubscribe.value) {
                                                        infoDialog.value = false
                                                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                                                            OnCompleteListener { task ->
                                                                if (!task.isSuccessful) {
                                                                    Log.w(
                                                                        "failed fcm",
                                                                        "Fetching FCM registration token failed",
                                                                        task.exception
                                                                    )
                                                                    return@OnCompleteListener
                                                                }
                                                                // Get new FCM registration token
                                                                val token = task.result
                                                                Log.d(
                                                                    "stater_fcmToken",
                                                                    token.toString()
                                                                )
                                                                // fcmViewModel.saveFCMToken(FCMToken(token = token))
                                                                userVillagerViewModel.eventSubscriptionViewModel.dropOutSubscription(
                                                                    title = event.value.title!!,
                                                                    userSubscription = UserSubscription(
                                                                        fcmToken = token
                                                                    )
                                                                )
                                                                Toast.makeText(
                                                                    currentContext,
                                                                    "Se ha desuscrito al Evento ${event.value.title}",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            })

                                                    } else {
                                                        infoDialog.value = true
                                                    }
                                                }) {
                                                Text(
                                                    text = if (isSubscribe.value) "Desuscribirse" else "Subscribirse",
                                                    color = Color.White
                                                )
                                                // }
                                            }
                                        }
                                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                        Divider(thickness = 1.dp, color = Color.Gray)
                                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                        Text(text = event.value.description!!, fontSize = 12.sp)
                                    }
                                    if(infoDialog.value){
                                        if(!isSubscribe.value){
                                            FormSubscription(userVillagerViewModel = userVillagerViewModel, reservePrice = event.value.reservePrice!!, title = event.value.title!!, onDismiss = { infoDialog.value = false }, onSubscription = {name, mail, phone, wallet ->

                                                Log.d("form::subscription", "name -> $name, direction -> $mail, phone -> $phone, wallet -> $wallet")

                                                FirebaseMessaging.getInstance().token.addOnCompleteListener(
                                                    OnCompleteListener { task ->
                                                        if (!task.isSuccessful) {
                                                            return@OnCompleteListener
                                                        }
                                                        val token = task.result
                                                        userVillagerViewModel.eventSubscriptionViewModel.addSubscriptionToUser(
                                                            title = event.value.title!!,
                                                            userSubscription = UserSubscription(
                                                                fcmToken = token,
                                                                name = name,
                                                                mail = mail,
                                                                phone = phone,
                                                                wallet = wallet.toDouble(),
                                                                isSubscribe = true
                                                            )
                                                        )
                                                        Toast.makeText(currentContext, "Se ha subscrito al Evento ${event.value.title}", Toast.LENGTH_SHORT).show()
                                                    }
                                                )
                                            })
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column() {
                        if (connection.value) {
                        Box() {
                            Image(
                                painter = painterResource(id = R.drawable.event_back),
                                contentDescription = "",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                            if(events.value.isEmpty()){
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(painter = painterResource(id = R.drawable.no_backpack), contentDescription = "")
                                        Text(
                                            text = "No hay eventos disponibles en este momento",
                                            fontWeight = FontWeight.W700,
                                            fontSize = 14.sp,
                                        )
                                    }
                                }
                            }else{
                                LazyVerticalGrid(
                                    modifier = Modifier.padding(16.dp),
                                    columns = GridCells.Fixed(2),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(events.value) { event ->
                                        Card(
                                            modifier = Modifier.clickable {
                                                userVillagerViewModel.getUserToVillagerEvents()
                                                scope.launch {
                                                    userVillagerViewModel.findEventByUsernameAndTitle(event.title!!)
                                                    state.show()
                                                }
                                            },
                                            elevation = 4.dp
                                        ) {
                                            Column() {
                                                GlideImage(
                                                    imageModel = { "" },
                                                    success = { imageState ->
                                                        Image(
                                                            bitmap = imageState.imageBitmap!!,
                                                            contentDescription = "",
                                                            modifier = Modifier.fillMaxWidth(),
                                                            contentScale = ContentScale.FillBounds
                                                        )
                                                    },
                                                    loading = {
                                                        Box(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                        ) {
                                                            CircularProgressIndicator(
                                                                modifier = Modifier.align(Alignment.Center)
                                                            )
                                                        }
                                                    },
                                                    failure = {
                                                        Box(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .height(150.dp)
                                                                .background(Color.LightGray)
                                                        ) {
                                                            CircularProgressIndicator(
                                                                color = Color.White,
                                                                modifier = Modifier.align(Alignment.Center)
                                                            )
                                                        }
                                                    }
                                                )
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = if (event.title?.length!! >= 20) "${
                                                            event.title!!.substring(
                                                                0,
                                                                20
                                                            )
                                                        }..." else event.title!!,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.padding(8.dp)
                                                    )
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(end = 8.dp)
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Filled.Info,
                                                            contentDescription = "info",
                                                            modifier = Modifier
                                                                .align(Alignment.BottomEnd)
                                                                .size(20.dp),
                                                            tint = Color.Red
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                    }
                }
                if (!connection.value) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.wifi_off),
                                contentDescription = ""
                            )
                            Text(
                                text = "Por favor, comprueba tu conexión a internet",
                                fontWeight = FontWeight.W700,
                                fontSize = 14.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}
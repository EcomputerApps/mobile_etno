package com.example.mobile_etno.views.modern

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.R
import com.example.mobile_etno.models.FCMToken
import com.example.mobile_etno.models.UserSubscription
import com.example.mobile_etno.utils.StringSpace
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.components.FormSubscription
import com.example.mobile_etno.views.modern.card.CardCustomHome
import com.example.mobile_etno.views.modern.navigationbottom.BottomNavigationCustom
import com.example.mobile_etno.views.modern.scrollable.ScrollableTabEvents
import com.example.mobile_etno.views.modern.scrollable.ScrollableTabNews
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeEtno(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
){
    val currentContext = LocalContext.current
    val news = userVillagerViewModel.userVillagerNews.collectAsState()
    val events = userVillagerViewModel.userVillagerEvents.collectAsState()
    val event = userVillagerViewModel.userVillagerEvent.collectAsState()
    val eventSeat = userVillagerViewModel.eventSubscriptionViewModel.eventSeats.collectAsState()
    val connection = userVillagerViewModel.connection.collectAsState()
    val isSubscribe = userVillagerViewModel.eventSubscriptionViewModel.isSubscribe.collectAsState()

    val infoDialog = remember { mutableStateOf(false) }
    val skipHalfExpanded by remember { mutableStateOf(false) }
    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = skipHalfExpanded
    )
    val scope = rememberCoroutineScope()

    BackHandler() {
        navController.navigate(NavItem.ChooseLocality.route){  }
    }

    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w("failed fcm", "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }
        // Get new FCM registration token
        val token = task.result
        Log.d("stater_fcmToken", token.toString())
        userVillagerViewModel.fcmViewModel.saveFCMToken(FCMToken(token = token))
    })

    Scaffold(
        topBar = {},
        bottomBar = { BottomNavigationCustom(navController = navController, 0, userVillagerViewModel = userVillagerViewModel) },
    ) {
        ModalBottomSheetLayout(
            modifier = Modifier.padding(it),
            sheetState = state,
            sheetContent = {
                if(event.value.title == null){
                    Text(text = "Null")
                }else{

                    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
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
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .padding(top = 20.dp, start = 14.dp, end = 14.dp)
                        .verticalScroll(
                            rememberScrollState()
                        )
                ) {
                    if (connection.value) {
                        Text(text = "Explorar", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text(text = "Noticias sugeridas para ti", color = Color.Gray)

                        ScrollableTabNews(
                            listNews = news.value,
                            onItemClick = {},
                            navController = navController
                        )
                        Spacer(modifier = Modifier.padding(top = 8.dp))

                        CardCustomHome(
                            R.drawable.vaccines_pharmacy,
                            section = "Farmacias",
                            title = "Encuentra las farmacias",
                            description = StringSpace.padRight("Farmacias de guardia y normal", 14)
                        ) {
                            navController.navigate(NavItem.Pharmacies.route){ userVillagerViewModel.getUserToVillagerPharmacies() }
                        }
                        Spacer(modifier = Modifier.padding(vertical = 8.dp))
                        CardCustomHome(
                            R.drawable.explore_tourism,
                            section = "Turismo",
                            title = "Encuentra el turismo mas destacado",
                            description = StringSpace.padRight("Turismo más destacado", 26)
                        ) {
                            navController.navigate(NavItem.Tourism.route){ userVillagerViewModel.getUserToVillagerTourism() }
                        }
                        Spacer(modifier = Modifier.padding(vertical = 8.dp))
                        Text(text = "Eventos", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text(text = "Eventos más destacados para ti", color = Color.Gray)

                        ScrollableTabEvents(
                            listEvents = events.value,
                            oneItemClick = {},
                            cardClick = {
                                userVillagerViewModel.getUserToVillagerEvents()
                                listOf(
                                    scope.launch
                                    {
                                        userVillagerViewModel.findEventByUsernameAndTitle(it)
                                        state.show()
                                    },
                                )
                            },
                            navController = navController
                        )
                        Spacer(modifier = Modifier.padding(vertical = 8.dp))

                        CardCustomHome(
                            R.drawable.service,
                            section = "Servicios",
                            title = "Aquí encontrarás los servicios",
                            description = StringSpace.padRight("Turismo más destacados...", 20)
                        ) {
                            navController.navigate(NavItem.Phone.route) {
                                userVillagerViewModel.getUserToVillagerPhones()
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 30.dp))
                }

                if (!connection.value) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
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
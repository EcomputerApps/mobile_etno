package com.example.mobile_etno.views.screen.events

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mobile_etno.models.Event
import com.example.mobile_etno.models.ImageModelDB
import com.example.mobile_etno.models.SectionSubscribe
import com.example.mobile_etno.models.service.database.SqlDataBase
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.EventNameViewModel
import com.example.mobile_etno.viewmodels.MenuViewModel
import com.example.mobile_etno.views.ScreenTopBar
import com.example.mobile_etno.views.components.FormSubscription
import com.example.mobile_etno.views.components.google.GoogleMapSection
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun EventNameScreen(menuViewModel: MenuViewModel?,
                    eventNameViewModel: EventNameViewModel?,
                    sqlDataBase: SqlDataBase,
                    navController: NavHostController?,
                    event: Event,
                    imageEvent: String,
                    idEvent: String){

    val imagesFilteredByEventId = sqlDataBase.getImagesDb(idEvent)
    val isSubscribe = eventNameViewModel?.isSubscribe!!.collectAsState()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val currentContext = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(event.link)) }
    val infoDialog = remember { mutableStateOf(false) }


    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w("failed fcm", "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }
        // Get new FCM registration token
        val token = task.result
        eventNameViewModel.getSubscriptionByTokenAndCategoryAndTitle(token, event.title!!)
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.backgroundEtno)
            .wrapContentSize(Alignment.Center)
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { ScreenTopBar(navController = navController!!, nameScreen = "Nombre de Evento") },
            drawerBackgroundColor = Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            /*
            drawerContent = {
                Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController!!, menuViewModel!!)
            },
             */
            backgroundColor = Color.Red
        ){
            Surface(color = Color.White,
                modifier = Modifier.fillMaxSize()) {
               Column(
                   modifier =
                   Modifier
                       .verticalScroll(rememberScrollState())
                       .padding(16.dp)) {
                   Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                       Image(painter = rememberAsyncImagePainter(imageEvent), contentDescription = "", modifier = Modifier.height(300.dp))
                   }
                   Row() {
                       Column() {
                           Text(text = "Título", fontWeight = FontWeight.Bold)
                           Text(text = event.title!!, modifier = Modifier.width(60.dp))
                       }
                       Spacer(modifier = Modifier.padding(horizontal = 70.dp))
                       Button(contentPadding = PaddingValues(horizontal = 15.dp) ,onClick = {

                           if(eventNameViewModel.isSubscribe.value){
                               infoDialog.value = false
                               FirebaseMessaging.getInstance().token.addOnCompleteListener(
                                   OnCompleteListener { task ->
                                       if (!task.isSuccessful) {
                                           Log.w("failed fcm", "Fetching FCM registration token failed", task.exception)
                                           return@OnCompleteListener
                                       }
                                       // Get new FCM registration token
                                       val token = task.result
                                       Log.d("stater_fcmToken", token.toString())
                                       // fcmViewModel.saveFCMToken(FCMToken(token = token))
                                       eventNameViewModel.changeStateButtonSubscribe( token = token,
                                           sectionSubscribe = SectionSubscribe(category = "Evento", title = event.title))
                                       Toast.makeText(currentContext, "Se ha desuscrito al Evento ${event.title}", Toast.LENGTH_SHORT).show()
                                   })

                           }else{
                               infoDialog.value = true
                           }

                           }, colors = ButtonDefaults.buttonColors(backgroundColor = if(isSubscribe.value) Color.Gray else Color.Red), shape = CircleShape) {
                           Text(text = if(eventNameViewModel.isSubscribe.value) "Desuscribirse" else "Subscribirse", color = Color.White)
                       }
                   }
                      Spacer(modifier = Modifier.padding(vertical = 5.dp))
                      Text(text = "Lugar", fontWeight = FontWeight.Bold)
                      Text(text = event.address!!)
                      Spacer(modifier = Modifier.padding(vertical = 5.dp))
                      Text(text = "Enlace", fontWeight = FontWeight.Bold)
                      Text(text = event.link!!, color = Color.Blue, modifier = Modifier.clickable {
                          currentContext.startActivity(intent)
                      })
                      Spacer(modifier = Modifier.padding(vertical = 5.dp))
                     Row() {
                         Column() {
                             Text(text = "Fecha inicial", fontWeight = FontWeight.Bold)
                             Text(text = event.startDate!!)
                         }
                         Spacer(modifier = Modifier.padding(horizontal = 70.dp))
                         Column() {
                             Text(text = "Organization", fontWeight = FontWeight.Bold)
                             Text(text = event.organization!!)
                         }
                     }
                      Spacer(modifier = Modifier.padding(vertical = 5.dp))
                      Text(text = "Fecha de publicación", fontWeight = FontWeight.Bold)
                      Text(text = event.publicationDate!!)
                      Spacer(modifier = Modifier.padding(vertical = 5.dp))
                      Text(text = "Description", fontWeight = FontWeight.Bold)
                      Text(text = event.description!!)
                      Spacer(modifier = Modifier.padding(vertical = 5.dp))
                      GoogleMapSection(latitude = event.lat!!, longitude = event.long!!, title = event.title!!, section = "Evento")
                      Spacer(modifier = Modifier.padding(vertical = 5.dp))
                      Text(text = "Fotos", fontWeight = FontWeight.Bold)

                   var selectedTabIndex by remember { mutableStateOf(0) }
                      CustomScrollableTabRow(imageList = imagesFilteredByEventId, selectedTabIndex = selectedTabIndex){
                          selectedTabIndex = it
                      }
                  }
                if(infoDialog.value){
                    if(!eventNameViewModel.isSubscribe.value){
                        FormSubscription(eventNameViewModel = eventNameViewModel, reservePrice = event.reservePrice!!, onDismiss = { infoDialog.value = false }, onSubscription = {name, mail, phone, wallet ->

                            Log.d("form::subscription", "name -> $name, direction -> $mail, phone -> $phone, wallet -> $wallet")

                            FirebaseMessaging.getInstance().token.addOnCompleteListener(
                                OnCompleteListener { task ->
                                    if (!task.isSuccessful) {
                                        Log.w("failed fcm", "Fetching FCM registration token failed", task.exception)
                                        return@OnCompleteListener
                                    }
                                    // Get new FCM registration token
                                    val token = task.result
                                    Log.d("stater_fcmToken", token.toString())
                                    // fcmViewModel.saveFCMToken(FCMToken(token = token))
                                    eventNameViewModel.changeStateButtonSubscribe(
                                        token = token,
                                        name = name,
                                        mail = mail,
                                        phone = phone,
                                        wallet = wallet.toDouble(),
                                        sectionSubscribe = SectionSubscribe(category = "Evento", title = event.title))
                                    Toast.makeText(currentContext, "Se ha subscrito al Evento ${event.title}", Toast.LENGTH_SHORT).show()
                                }
                            )
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun CustomScrollableTabRow(
    imageList: List<ImageModelDB>,
    selectedTabIndex: Int,
    onItemClick: (Int) -> Unit
){
    val imageInfoDialog = remember { mutableStateOf(false) }
    val saveImageUrl = remember { mutableStateOf("") }

    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        backgroundColor = Color.Transparent,
        contentColor = Color.Transparent,
        divider = {}
    ) {
        imageList.forEachIndexed { index, item ->
            Tab(selected = selectedTabIndex == index, onClick = {
                onItemClick.invoke(index)
                Log.d("tab_image", index.toString())
                imageInfoDialog.value = true
                saveImageUrl.value = item.linkImage!!
            }){
                Image(painter = rememberAsyncImagePainter(model = item.linkImage), contentDescription = "", modifier = Modifier
                    .width(180.dp)
                    .height(180.dp)
                    .padding(5.dp), contentScale = ContentScale.Fit)
            }
        }
        if(imageInfoDialog.value)
            ImageDetails(onDismiss = {
                imageInfoDialog.value = false
            }, list = imageList, selectedTabIndex = selectedTabIndex)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImageDetails(
    list: List<ImageModelDB>,
    selectedTabIndex: Int,
    onDismiss: () -> Unit
){
    Dialog(onDismissRequest = { onDismiss.invoke() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(color = Color.Transparent)) {
            Box(modifier = Modifier
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp)
                )
                .align(Alignment.BottomCenter)) {
                ScrollableTabRow(selectedTabIndex = selectedTabIndex, edgePadding = 0.dp, backgroundColor = Color.Transparent, divider = {Divider(startIndent = 0.dp, thickness = 0.dp, color = Color.Transparent)}) {
                    list.forEachIndexed { index, item ->
                        Tab(selected = selectedTabIndex == index, onClick = {  }, enabled = false ){
                            Image(painter = rememberAsyncImagePainter(model = item.linkImage), contentDescription = "", modifier = Modifier
                                .width(300.dp)
                                .height(300.dp)
                                .padding(5.dp))
                        }
                    }
                }
            }
        }
    }
}

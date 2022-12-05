package com.example.mobile_etno.views.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
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
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.EventNameViewModel
import com.example.mobile_etno.viewmodels.MenuViewModel
import com.example.mobile_etno.views.Drawer
import com.example.mobile_etno.views.ScreenTopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EventNameScreen(menuViewModel: MenuViewModel?,
                    eventNameViewModel: EventNameViewModel?,
                    navController: NavHostController?,
                    event: Event,
                    imageEvent: String){

    val eventState = remember {event}

    val titleSubscribe = eventNameViewModel!!.isSubscribeTitle.collectAsState()
    val isSubscribe = eventNameViewModel.isSubscribe.collectAsState()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val currentContext = LocalContext.current

    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(event.link)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.backgroundEtno)
            .wrapContentSize(Alignment.Center)
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { ScreenTopBar(menuViewModel =  menuViewModel!!, navController = navController!!, nameScreen = "Nombre de Evento") },
            drawerBackgroundColor = Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
            drawerContent = {
                Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController!!, menuViewModel!!)
            },
            backgroundColor = Color.Red
        ){
            Surface(color = Colors.backgroundEtno,
                modifier = Modifier.fillMaxSize()) {
               Column(
                   Modifier
                       .verticalScroll(rememberScrollState())
                       .padding(16.dp)) {
                   Image(painter = rememberAsyncImagePainter(imageEvent), contentDescription = "", modifier = Modifier.height(300.dp))
                   Row() {
                       Column() {
                           Text(text = "Título", fontWeight = FontWeight.Bold)
                           Text(text = event.title!!)
                       }
                       Spacer(modifier = Modifier.padding(horizontal = 65.dp))
                       Button(onClick = { eventNameViewModel.changeStateButtonSubscribe() }, colors = ButtonDefaults.buttonColors(backgroundColor = if(isSubscribe.value) Color.Gray else Color.Red ), shape = CircleShape) {
                           Text(text = titleSubscribe.value, color = Color.White)
                       }
                   }
                      Text(text = "Lugar", fontWeight = FontWeight.Bold)
                      Text(text = event.address!!)
                      Text(text = "Enlace", fontWeight = FontWeight.Bold)
                      Text(text = event.link!!, color = Color.Blue, modifier = Modifier.clickable {
                          currentContext.startActivity(intent)
                      })
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
                      Text(text = "Fecha de publicación", fontWeight = FontWeight.Bold)
                      Text(text = event.publicationDate!!)
                      Text(text = "Description", fontWeight = FontWeight.Bold)
                      Text(text = event.description!!)
                      Text(text = "Fotos", fontWeight = FontWeight.Bold)

                   var selectedTabIndex by remember { mutableStateOf(0) }
                      CustomScrollableTabRow(imageList = listOf(imageEvent, imageEvent, imageEvent, imageEvent, imageEvent), selectedTabIndex = selectedTabIndex){
                          selectedTabIndex = it
                      }
                  }
            }
        }
    }
}

@Composable
fun CustomScrollableTabRow(
    imageList: List<String>,
    selectedTabIndex: Int,
    onItemClick: (Int) -> Unit
){
    val imageInfoDialog = remember { mutableStateOf(false) }
    val saveImageUrl = remember { mutableStateOf("") }


    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        backgroundColor = Colors.backgroundEtno
    ) {
        imageList.forEachIndexed { index, item ->

            Tab(selected = selectedTabIndex == index, onClick = {
                onItemClick.invoke(index)
                Log.d("tab_image", index.toString())
                imageInfoDialog.value = true
                saveImageUrl.value = item
            }){
                Image(painter = rememberAsyncImagePainter(model = item), contentDescription = "", modifier = Modifier
                    .width(180.dp)
                    .height(180.dp)
                    .padding(5.dp))
            }
        }
        if(imageInfoDialog.value)
            ImageDetails(image = saveImageUrl.value, onDismiss = {
                imageInfoDialog.value = false
            }, list = imageList, selectedTabIndex = selectedTabIndex)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImageDetails(
    image: String,
    list: List<String>,
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
                /*
                Image(
                    painter = rememberAsyncImagePainter(model = image),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(300.dp)
                )
                 */
                ScrollableTabRow(selectedTabIndex = selectedTabIndex, edgePadding = 0.dp, backgroundColor = Color.Transparent, divider = {Divider(startIndent = 0.dp, thickness = 0.dp, color = Color.Transparent)}) {
                    list.forEachIndexed { index, item ->
                        Tab(selected = selectedTabIndex == index, onClick = {  }, enabled = false ){
                            Image(painter = rememberAsyncImagePainter(model = item), contentDescription = "", modifier = Modifier
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

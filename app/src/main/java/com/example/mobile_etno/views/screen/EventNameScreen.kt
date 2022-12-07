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
import com.example.mobile_etno.models.ImageModelDB
import com.example.mobile_etno.models.service.database.SqlDataBase
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.EventNameViewModel
import com.example.mobile_etno.viewmodels.EventViewModel
import com.example.mobile_etno.viewmodels.MenuViewModel
import com.example.mobile_etno.views.Drawer
import com.example.mobile_etno.views.ScreenTopBar
import com.google.maps.android.compose.GoogleMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun EventNameScreen(menuViewModel: MenuViewModel?,
                    eventNameViewModel: EventNameViewModel?,
                    sqlDataBase: SqlDataBase,
                    navController: NavHostController?,
                    event: Event,
                    imageEvent: String,
                    idEvent: String){

   // val coroutineScope = rememberCoroutineScope()

    val imagesFilteredByEventId = sqlDataBase.getImagesDb(idEvent)

    Log.d("show_list", imagesFilteredByEventId.toString())

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
                           Text(text = event.title!!)
                       }
                       Spacer(modifier = Modifier.padding(horizontal = 75.dp))
                       Button(contentPadding = PaddingValues(horizontal = 15.dp) ,onClick = { eventNameViewModel.changeStateButtonSubscribe() }, colors = ButtonDefaults.buttonColors(backgroundColor = if(isSubscribe.value) Color.Gray else Color.Red ), shape = CircleShape) {
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
                      MyGoogleMap(latitude = event.lat!!, longitude = event.long!!, title = event.title!!)
                      Spacer(modifier = Modifier.padding(vertical = 5.dp))
                      Text(text = "Fotos", fontWeight = FontWeight.Bold)

                   var selectedTabIndex by remember { mutableStateOf(0) }
                      CustomScrollableTabRow(imageList = imagesFilteredByEventId, selectedTabIndex = selectedTabIndex){
                          selectedTabIndex = it
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

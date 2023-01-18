package com.example.mobile_etno.views.screen.pharmacy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.R
import com.example.mobile_etno.models.Pharmacy
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.components.google.GoogleMapPharmacies
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PharmaciesScreen(userVillagerViewModel: UserVillagerViewModel, navController: NavHostController){

  //  val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val pharmacies = userVillagerViewModel.userVillagerPharmacies.collectAsState()
    val pharmacy = userVillagerViewModel.userVillagerPharmacy.collectAsState()
    var selectedTabIndex by remember { mutableStateOf(0) }
    var skipHalfExpanded by remember { mutableStateOf(false) }
    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = skipHalfExpanded
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            if(pharmacy.value.name == null){
                Text(text = "null")
            }else{
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(

                    ) {
                        GlideImage(
                            imageModel = { pharmacy.value.imageUrl},
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
                                        .background(Color.Gray)
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
                                Text(text = pharmacy.value.name!!, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                Text(text = "${pharmacy.value.username} · Huesca", color = Color.Gray, fontSize = 10.sp)
                                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                Divider(thickness = 1.dp, color = Color.Gray)
                                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column() {
                                        Text(text = pharmacy.value.schedule!!, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                        Text(text = "Horario", color = Color.Gray, fontSize = 10.sp)
                                    }
                                    Spacer(modifier = Modifier.padding(horizontal = 90.dp))
                                    Box(
                                        modifier = Modifier.background(if(pharmacy.value.type == "Normal") Color.Blue else Color.Red)
                                    ) {
                                        Text(text = pharmacy.value.type!!, modifier = Modifier.padding(4.dp), color = Color.White)
                                    }
                                }
                                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                Divider(thickness = 1.dp, color = Color.Gray)
                                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                Text(text = pharmacy.value.description!!, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {

            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                ) {
                    GoogleMapPharmacies(
                        pharmacies.value,
                        click = {
                            scope.launch {
                                userVillagerViewModel.pharmacyFilterByName(it)
                                state.show()
                            }
                        }
                    )
                    CustomScrollableFilterPharmacy(
                        typeButtonList = listOf(Pharmacy(type = "Normal"), Pharmacy(type = "Guardia")),
                        userVillagerViewModel = userVillagerViewModel,
                        selectedTabIndex = selectedTabIndex
                    ){ index -> selectedTabIndex = index }
                }
            }
        }
    }
}

@Composable
fun CustomScrollableFilterPharmacy(
    typeButtonList: List<Pharmacy>,
    userVillagerViewModel: UserVillagerViewModel,
    selectedTabIndex: Int,
    onItemClick: (Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        backgroundColor = Color.Transparent,
        contentColor = Color.Transparent,
        divider = { }
    ) {
        Button(onClick = { userVillagerViewModel.filterAllPharmacies() }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
            Text(text = "Todo")
        }
        typeButtonList.forEachIndexed { index, pharmacy ->
            Tab(selected = selectedTabIndex == index, onClick = { onItemClick.invoke(index) }, modifier = Modifier
                .background(Color.Transparent)
                .padding(horizontal = 5.dp)) {
                Button(colors = ButtonDefaults.buttonColors(Color.White), onClick = {
                    if (pharmacy.type == "Normal") userVillagerViewModel.pharmaciesFilter(
                        "Normal"
                    ) else userVillagerViewModel.pharmaciesFilter("Guardia")
                }) {
                    Row() {
                        //Icon(painter = painterResource(id = if(pharmacy.type == "Normal") R.drawable.blue_pharmacy else R.drawable.red_pharmacy), contentDescription = pharmacy.type, modifier = Modifier.size(20.dp))
                        Image(painter = painterResource(id = if(pharmacy.type == "Normal") R.drawable.blue_pharmacy else R.drawable.red_pharmacy), contentDescription = "", modifier = Modifier.size(20.dp))
                        Text(text = pharmacy.type!!)
                    }
                }
            }
        }
    }
}
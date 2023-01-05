package com.example.mobile_etno.views.screen.tourism

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.R
import com.example.mobile_etno.models.Tourism
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.TourismViewModel
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.ScreenTopBar
import com.example.mobile_etno.views.components.google.GoogleMapTourism
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TourismScreen( userVillagerViewModel: UserVillagerViewModel, navController: NavHostController){

    val currentContext = LocalContext.current
    val tourism = userVillagerViewModel.userVillagerTourism.collectAsState()
    val tour = userVillagerViewModel.userVillagerTour.collectAsState()
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
                      if(tour.value.title == null){
                          Text(text = "null")
                      }else{
                          Box(
                              modifier = Modifier.fillMaxWidth()
                          ) {
                              Column(

                              ) {
                                  GlideImage(
                                      imageModel = { tour.value.imageUrl},
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
                                          Text(text = tour.value.title!!, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                          Text(text = "${tour.value.username} · Huesca", color = Color.Gray, fontSize = 10.sp)

                                          Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                          Divider(thickness = 1.dp, color = Color.Gray)
                                          Spacer(modifier = Modifier.padding(vertical = 4.dp))

                                          Text(text = tour.value.type!!, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                          Text(text = "Tipo", color = Color.Gray, fontSize = 10.sp)

                                          Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                          Divider(thickness = 1.dp, color = Color.Gray)
                                          Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                          Text(text = tour.value.description!!, fontSize = 12.sp)
                                      }
                                  }
                              }
                          }
                      }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.backgroundEtno)
                .wrapContentSize(Alignment.Center)
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)) {

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                ) {
                    GoogleMapTourism(
                        listTourism = tourism.value,
                        click = {
                            scope.launch {
                                userVillagerViewModel.tourismFilterByTitle(it)
                                state.show()
                            }
                        }
                    )
                    CustomScrollableFilterTourism(
                        typeButtonList = listOf(Tourism(type = "Restaurante"), Tourism(type = "Monumento"), Tourism(type = "Museo"), Tourism(type = "Hotel")),
                        selectedTabIndex = selectedTabIndex,
                        userVillagerViewModel = userVillagerViewModel
                    ){
                            index ->
                        selectedTabIndex = index
                    }
                }
            }
        }
    }
}

@Composable
fun CustomScrollableFilterTourism(
    typeButtonList: List<Tourism>,
    userVillagerViewModel: UserVillagerViewModel,
    selectedTabIndex: Int,
    onItemClick: (Int) -> Unit
){
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        backgroundColor = Color.Transparent,
        contentColor = Color.Transparent,
        divider = {  }
    ) {
        Button(onClick = { userVillagerViewModel.filterAllTourism() }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
            Text(text = "Todo")
        }
        typeButtonList.forEachIndexed { index, tourism ->
            Tab(selected = selectedTabIndex == index, onClick = { onItemClick.invoke(index) }, modifier = Modifier
                .background(Color.Transparent)
                .padding(horizontal = 5.dp)) {
                Button(onClick = {
                                 when(tourism.type) {
                                     "Restaurante" -> userVillagerViewModel.tourismFilter("Restaurante")
                                     "Museo" -> userVillagerViewModel.tourismFilter("Museo")
                                     "Hotel" -> userVillagerViewModel.tourismFilter("Hotel")
                                     "Monumento" -> userVillagerViewModel.tourismFilter("Monumento")
                                 }
                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                    Row {
                       val typeIcon: Int = when(tourism.type){
                            "Restaurante" -> R.drawable.restaurant
                            "Museo" -> R.drawable.museo
                            "Hotel" -> R.drawable.hotel
                            "Monumento" -> R.drawable.monumental
                           else -> { R.drawable.etno_icon }
                        }
                        Image(painter = painterResource(id = typeIcon), contentDescription = tourism.type, modifier = Modifier.size(20.dp))
                        Text(text = tourism.type!!)
                    }
                }
            }
        }
    }
}
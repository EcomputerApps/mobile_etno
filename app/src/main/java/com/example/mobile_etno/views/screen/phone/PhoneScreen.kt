package com.example.mobile_etno.views.screen.phone

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.utils.phone_categories.PhoneCategories
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.ScreenTopBar
import com.example.mobile_etno.views.modern.navigationbottom.BottomNavigationCustom
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PhoneScreen(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
) {
    //BackHandler() { navController.navigate(NavItem.Home.route){  } }
    val currentContext= LocalContext.current
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {},
        bottomBar = { BottomNavigationCustom(
            navController = navController,
            stateNavigationButton = -1,
            userVillagerViewModel = userVillagerViewModel
        ) }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Colors.backgroundEtno)
                .wrapContentSize(Alignment.Center)
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        // .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(20.dp)
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ){
                        items(PhoneCategories.phoneCategories){
                                phone ->
                            Card(elevation = 7.dp, modifier = Modifier.clickable {
                                navController.navigate(NavItem.PhoneDetailsList.route){ userVillagerViewModel.phoneFilter(phone.categoryName!!) }
                            }.padding(8.dp)) {
                                Box(contentAlignment = Alignment.Center) {
                                    Image(painter = painterResource(id = phone.image!!), contentDescription = phone.categoryName, modifier = Modifier
                                        .width(400.dp)
                                        .height(200.dp)
                                        .drawWithCache {
                                            val gradient = Brush.verticalGradient(
                                                colors = listOf(Color.Transparent, Color.Black),
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
                                        }
                                        , contentScale = ContentScale.FillBounds)

                                    Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(phone.categoryName!!, fontWeight = FontWeight.W700, fontSize = 24.sp, color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
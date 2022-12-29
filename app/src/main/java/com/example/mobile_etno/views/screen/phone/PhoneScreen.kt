package com.example.mobile_etno.views.screen.phone

import android.widget.Toast
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

@Composable
fun PhoneScreen(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
) {
    BackHandler() { navController.navigate(NavItem.Home.route){  } }

    val currentContext= LocalContext.current
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.backgroundEtno)
            .wrapContentSize(Alignment.Center)
    ) {

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                ScreenTopBar(
                    navController = navController,
                    nameScreen = "Telefonos"
                )
            },
            drawerBackgroundColor = Colors.backgroundEtno,
            // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer

            backgroundColor = Color.Red
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
            ) {
                Column(
                    modifier = Modifier
                       // .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(20.dp)
                ) {
                    LazyColumn{
                        items(PhoneCategories.phoneCategories){
                            phone ->
                            Card(elevation = 7.dp, modifier = Modifier.clickable {
                                navController.navigate(NavItem.PhoneDetailsList.route){ userVillagerViewModel.phoneTourismFilter(phone.categoryName!!) }
                            }) {
                                Box(contentAlignment = Alignment.Center) {
                                    Image(painter = painterResource(id = phone.image!!), contentDescription = phone.categoryName, modifier = Modifier
                                        .width(400.dp)
                                        .height(200.dp), contentScale = ContentScale.FillBounds, alpha = 0.6F)

                                    Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(phone.categoryName!!, fontWeight = FontWeight.W700, fontSize = 24.sp, color = Color.White)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
        }
    }
}
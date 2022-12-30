package com.example.mobile_etno.views.screen.news

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.R
import com.example.mobile_etno.isInternetAvailable
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsScreen(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
){
    BackHandler() { navController.navigate(NavItem.Home.route){  } }

    var currentContext = LocalContext.current
    var selectedTabIndex by remember { mutableStateOf(0) }
    val news = userVillagerViewModel.userVillagerNews.collectAsState()
    val connection = userVillagerViewModel.connection.collectAsState()
    var skipHalfExpanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = skipHalfExpanded
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        if(connection.value && news.value.isNotEmpty()){
            CustomScrollableNews(
                newCategoryList = listOf("General","Tecnología", "Salud", "Entretenimiento"),
                selectedTabIndex = selectedTabIndex, userVillagerViewModel = userVillagerViewModel
            ){ index -> selectedTabIndex = index }
        }
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding()
            ) {
                Column(
                ) {
                    if(connection.value){
                        CardNewList(news = news.value, userVillagerViewModel)
                    }else{
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Text(text = "Por favor, comprueba tu conexión a internet", fontWeight = FontWeight.W700, fontSize = 14.sp)
                        }
                    }
            }
        }
    }
}

@Composable
fun CustomScrollableNews(
    newCategoryList: List<String>,
    userVillagerViewModel: UserVillagerViewModel,
    selectedTabIndex: Int,
    onItemClick: (Int) -> Unit
){
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
       // backgroundColor = Color.Transparent,
       // contentColor = Color.Transparent,
        //divider = {  }
    ) {
        newCategoryList.forEachIndexed {
                index, category ->
            Tab(selected = selectedTabIndex == index, onClick =
            {
                onItemClick.invoke(index)
                when(category){
                    "General" -> userVillagerViewModel.newsFilterAll()
                    "Tecnología" -> userVillagerViewModel.newsFilter("Tecnología")
                    "Salud" -> userVillagerViewModel.newsFilter("Salud")
                    "Entretenimiento" -> userVillagerViewModel.newsFilter("Entretenimiento")
                    "Negocios" -> userVillagerViewModel.newsFilter("Negocios")
                }
            }, modifier = Modifier
                .padding(horizontal = 5.dp)
                .height(40.dp)
            ) {
                Text(text = category)
            }
        }
    }
}
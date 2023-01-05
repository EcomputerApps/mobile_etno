package com.example.mobile_etno.views.modern.navigationbottom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.R
import com.example.mobile_etno.models.NavigationBottom
import com.example.mobile_etno.viewmodels.UserVillagerViewModel

@Composable
fun BottomNavigationCustom(
    navController: NavHostController,
    stateNavigationButton: Int,
    userVillagerViewModel: UserVillagerViewModel
) {
    var selectedItem by remember { mutableStateOf(stateNavigationButton) }
    val items = listOf(
        NavigationBottom(R.drawable.home, "Home"),
        NavigationBottom(R.drawable.news, "News"),
        NavigationBottom(R.drawable.explore_tourism, "Explorar")
    )
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BottomNavigation(
            backgroundColor = Color.White,
            contentColor = Color.Black,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            items.forEachIndexed { index, navigationBottom ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = navigationBottom.icon!!),
                            contentDescription = ""
                        )
                    },
                    label = { Text(text = navigationBottom.name!!) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        when(navigationBottom.name){
                            "Home" -> navController.navigate(NavItem.HomeModern.route)
                            "News" -> navController.navigate(NavItem.News.route){ userVillagerViewModel.getUserToVillagerNews() }
                        }
                    }
                )
            }
        }
    }
}
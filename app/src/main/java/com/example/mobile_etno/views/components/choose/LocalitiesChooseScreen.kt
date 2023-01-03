package com.example.mobile_etno.views.components.choose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.viewmodels.locality.LocalityViewModel

@Composable
fun LocalitiesChooseScreen(navController: NavHostController ,localityViewModel: LocalityViewModel) {
    val localities = localityViewModel.localities.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(
            Alignment.Center)) {
            items(localities.value){
                    item ->

                TextButton(onClick = {
                    localityViewModel.updateStateLocality(item.localityName!!)
                    navController.navigate(NavItem.HomeModern.route)
                }) {
                    Text(text = item.localityName!!)
                }
            }
        }
    }
}
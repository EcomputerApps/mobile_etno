package com.example.mobile_etno.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mobile_etno.R
import com.example.mobile_etno.components.TopBarHome
import com.example.mobile_etno.viewmodels.MenuViewModel

@Composable
fun ScreenHome(viewModel: MenuViewModel){

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TopBarHome()

            LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp) ){
                items(viewModel.listMenu){
                    item ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.padding(vertical = 16.dp))
                        Image(painter = painterResource(id = R.drawable.home_test), modifier = Modifier.size(60.dp), contentDescription = item.name)
                        Text(text = item.name)
                        Spacer(modifier = Modifier.padding(vertical = 16.dp))
                    }
                }
            }
        }
}
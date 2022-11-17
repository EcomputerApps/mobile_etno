package com.example.mobile_etno.views

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_etno.R
import com.example.mobile_etno.components.TopBarHome
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.MenuViewModel

@Composable
fun ScreenHome(viewModel: MenuViewModel, context: Context, navHostController: NavHostController){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TopBarHome()
            LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp) ){
                items(viewModel.listMenu){
                    item ->
                    Card(elevation = 0.dp,modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight()
                        .clickable {
                                   //Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
                                   navHostController.navigate("${item.name}/${item.name}")
                        },
                        backgroundColor = Colors.backgroundEtno) {
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
}
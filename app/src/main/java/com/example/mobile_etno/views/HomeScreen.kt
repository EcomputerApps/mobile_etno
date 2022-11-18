package com.example.mobile_etno.views

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_etno.R
import com.example.mobile_etno.components.TopBarHome
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.MenuViewModel
import kotlinx.coroutines.launch

@Composable
fun ScreenHome( context: Context, list: List<String> , navHostController: NavHostController, drawerState: DrawerState){
        //val listPathRoutes = context.resources.getStringArray(R.array.menu_path)
    val scope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(
            title = { Text("Etno", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), color = Color.White) },
            navigationIcon = {
                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(Icons.Filled.Menu, tint = Color.White, contentDescription = null)
                }
            },
            backgroundColor = Color.Red,
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = {

                }) {
                    Row() {
                        Icon(painterResource(id = R.drawable.internalization_icon), contentDescription = "Localized description", modifier = Modifier.size(25.dp), tint = Color.White)
                        Text(text = "EN", color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.padding(2.dp))
                    }
                }
            }
        )
            LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp) ){
                itemsIndexed(list){
                   index ,item ->
                    Card(elevation = 0.dp,modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight()
                        .clickable {
                            //Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
                            navHostController.navigate("${list[index]}/$item")

                        },
                        backgroundColor = Colors.backgroundEtno) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Spacer(modifier = Modifier.padding(vertical = 16.dp))
                            Image(painter = painterResource(id = R.drawable.home_test), modifier = Modifier.size(60.dp), contentDescription = item)
                            Text(text = item)
                            Spacer(modifier = Modifier.padding(vertical = 16.dp))
                        }
                    }
                }
            }
        }
}
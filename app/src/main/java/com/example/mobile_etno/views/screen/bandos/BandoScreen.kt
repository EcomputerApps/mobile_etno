package com.example.mobile_etno.views.screen.bandos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.R
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.modern.navigationbottom.BottomNavigationCustom
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BandoScreen(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
){
    val skipHalfExpanded by remember { mutableStateOf(false) }
    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = skipHalfExpanded
    )
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = { BottomNavigationCustom(
            navController = navController,
            stateNavigationButton = -1,
            userVillagerViewModel = userVillagerViewModel
        ) }
    ) {
        ModalBottomSheetLayout(
            modifier = Modifier.padding(it),
            sheetState = state,
            sheetContent = {
                Box(

                ) {
                    Column(

                    ) {
                        Box(
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(painter = painterResource(id = R.drawable.campaign), contentDescription = "", modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally), tint = Color.Red)
                                Text(text = "Convocación de elecciones", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                Text(text = "Bolea · Huesca", color = Color.Gray, fontSize = 10.sp)
                                Divider(thickness = 1.dp, color = Color.Gray)
                                Text(text = "Emitido", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text(text = "18-01-2023", color = Color.Gray, fontSize = 10.sp)
                                Divider(thickness = 1.dp, color = Color.Gray)
                                Text(text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement
                            .spacedBy(14.dp)
                    ){
                        items(listOf("Convocatoria de funcionarios 1", "Convocatoria de funcionarios 2", "Convocatoria de funcionarios 3")){
                                item ->
                            Card(
                                elevation = 4.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scope.launch {
                                            state.show()
                                        }
                                    }
                            ) {
                                Column() {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Icon(painter = painterResource(id = R.drawable.campaign), contentDescription = "campaigns", modifier = Modifier.size(40.dp), tint = Color.Red)
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text(text = item, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                            Text(text = "Emitido el 18-01-2023", color = Color.Gray, fontSize = 12.sp)
                                        }
                                        Box(modifier = Modifier.fillMaxSize()) {
                                            Icon(painter = painterResource(id = R.drawable.right), contentDescription = "right", modifier = Modifier
                                                .align(Alignment.BottomEnd)
                                                .padding(4.dp), tint = Color.Red)
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
}
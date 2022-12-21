package com.example.mobile_etno.views.screen.wallet

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_etno.views.ScreenTopBar

@Composable
fun WalletScreen(
    navController: NavHostController?
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        Scaffold(
            topBar = { ScreenTopBar(nameScreen = "Pago", navController = navController!!) }
        ) {
            Box(modifier = Modifier
                .padding(it)
                .fillMaxSize()) {
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                        Column() {
                            Button(onClick = { /*TODO*/ }) {
                                Text(text = "Pay")
                            }
                        }
                    }
                }
            }
        }
    }
}
package com.example.mobile_etno.views.screen.pharmacy

import android.content.Intent
import android.net.Uri
import android.widget.Space
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mobile_etno.R
import com.example.mobile_etno.models.Pharmacy
import com.example.mobile_etno.views.ScreenTopBar

@Composable
fun PharmacyDetails(
    navController: NavHostController?,
    pharmacy: Pharmacy?
){
    val currentContext = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(pharmacy?.link)) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        Scaffold(
            topBar = { ScreenTopBar(nameScreen = "Detalles Farmacia", navController = navController!!) }
        ) {
            Box(modifier = Modifier
                .padding(it)
                .fillMaxSize()) {
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = if (pharmacy?.imageUrl != "null") rememberAsyncImagePainter(
                                model = pharmacy?.imageUrl
                            ) else painterResource(
                                id = R.drawable.farmacia_icon
                            ), contentDescription = "", modifier = Modifier.height(300.dp)
                        )
                    }
                        Column() {

                            Spacer(modifier = Modifier.padding(vertical = 5.dp))

                            Text(text = "Nombre", fontWeight = FontWeight.Bold)
                            Text(text = pharmacy?.name!!)

                            Spacer(modifier = Modifier.padding(vertical = 5.dp))

                            Text(text = "Tipo de servicio", fontWeight = FontWeight.Bold)
                            Text(text = "Servicio ${pharmacy.type!!}")

                            Spacer(modifier = Modifier.padding(vertical = 5.dp))

                            Text(text = "Teléfono", fontWeight = FontWeight.Bold)
                            Text(text = pharmacy.phone!!)

                            Spacer(modifier = Modifier.padding(vertical = 5.dp))

                            Text(text = "link", fontWeight = FontWeight.Bold)
                            Text(text = pharmacy.link!!, color = Color.Blue, modifier = Modifier.clickable {
                                currentContext.startActivity(intent)
                            })
                            
                            Spacer(modifier = Modifier.padding(vertical = 5.dp))
                            
                            Text(text = "Descripción", fontWeight = FontWeight.Bold)
                            Text(text = pharmacy.description!!)
                        }
                    }
                }
        }
    }
}
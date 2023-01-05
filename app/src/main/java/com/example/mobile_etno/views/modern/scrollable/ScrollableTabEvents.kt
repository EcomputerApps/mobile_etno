package com.example.mobile_etno.views.modern.scrollable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.R
import com.example.mobile_etno.models.Event
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ScrollableTabEvents(
    listEvents: List<Event>,
    oneItemClick: (Int) -> Unit,
    cardClick: (title: String) -> Unit,
    navController: NavHostController,
){

    if(listEvents.isNotEmpty()) {
            ScrollableTabRow(
                selectedTabIndex = 0,
                backgroundColor = Color.Transparent,
                contentColor = Color.Transparent,
                edgePadding = 0.dp,
                divider = { Divider(startIndent = 0.dp, thickness = 0.dp, color = Color.Transparent) }
            ) {
                listEvents.forEachIndexed { index, event ->

                    Tab(selected = true, onClick = {
                        oneItemClick.invoke(index)
                    }) {
                        Card(
                            elevation = 4.dp,
                            backgroundColor = Color.Transparent,
                            modifier = Modifier
                                //  .height(300.dp)
                                .width(300.dp)
                                .padding(4.dp)
                                .clickable {
                                    cardClick.invoke(event.title!!)
                                }

                        ) {
                            Column() {
                                Box(
                                    modifier = Modifier
                                        .height(300.dp)
                                        .width(300.dp)
                                ) {

                                    GlideImage(
                                        imageModel = { event.imageUrl},
                                        success = { imageState ->
                                            Image(
                                                bitmap = imageState.imageBitmap!!,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .width(300.dp)
                                                    .height(300.dp)
                                                    .padding(5.dp)
                                                    .drawWithCache {
                                                        val gradient = Brush.verticalGradient(
                                                            colors = listOf(
                                                                Color.Transparent,
                                                                Color.Black
                                                            ),
                                                            startY = size.height / 3,
                                                            endY = size.height
                                                        )
                                                        onDrawWithContent {
                                                            drawContent()
                                                            drawRect(
                                                                gradient,
                                                                blendMode = BlendMode.Multiply
                                                            )
                                                        }
                                                    }, contentScale = ContentScale.FillBounds
                                            )
                                        },
                                        loading = {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clip(
                                                        RoundedCornerShape(30.dp)
                                                    )
                                            ) {
                                                CircularProgressIndicator(
                                                    modifier = Modifier.align(Alignment.Center)
                                                )
                                            }
                                        },
                                        failure = {
                                            Box(
                                                modifier = Modifier
                                                    .height(300.dp)
                                                    .width(300.dp)
                                                    .background(Color.Gray)
                                            ) {
                                                CircularProgressIndicator(
                                                    color = Color.White,
                                                    modifier = Modifier.align(Alignment.Center)
                                                )
                                            }
                                        }
                                    )
                                }
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(14.dp)
                                    ) {
                                        Row() {
                                            Icon(
                                                painter = painterResource(id = R.drawable.celebration),
                                                contentDescription = event.title,
                                                tint = Color.Black,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                                            Text(
                                                text = event.title!!,
                                                color = Color.Black,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }else{
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(text = "¡No hay eventos disponibles en estos momentos!", color = Color.Black)
        }
    }
}
package com.example.mobile_etno.views.modern.scrollable

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
    navController: NavHostController,

){
    Log.d("list_events", listEvents.size.toString())

    ScrollableTabRow(
        selectedTabIndex = 0,
        backgroundColor = Color.Transparent,
        contentColor = Color.Transparent,
        edgePadding = 0.dp, divider = { Divider(startIndent = 0.dp, thickness = 0.dp, color = Color.Transparent) }
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
            ) {
                Column() {
                    Box(
                        modifier = Modifier
                            .height(300.dp)
                            .width(300.dp)
                    ) {

                        GlideImage(
                            imageModel = {  },
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
                                                colors = listOf(Color.Transparent, Color.Black),
                                                startY = size.height / 3,
                                                endY = size.height
                                            )
                                            onDrawWithContent {
                                                drawContent()
                                                drawRect(gradient, blendMode = BlendMode.Multiply)
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
                        modifier = Modifier
                            .background(Color.White)
                            .width(300.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(14.dp)
                        ) {
                            Row() {
                                Icon(painter = painterResource(id = R.drawable.celebration), contentDescription = event.title, tint = Color.Black, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                                Text(text = event.title!!, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                            /*
                            Spacer(modifier = Modifier.padding(vertical = 4.dp))
                            Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Black)
                            Spacer(modifier = Modifier.padding(vertical = 4.dp))
                            Row() {
                                Text(text = event.startDate!!, color = Color.Gray)
                                Text(text = event.username!!, color = Color.Gray, modifier = Modifier.padding(start = 120.dp))
                            }

                             */
                        }
                    }
                }
            }
        }
    }
    }
}
package com.example.mobile_etno.views.screen.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.models.news.New
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CardNewList(news: List<New>, navController: NavHostController){
        LazyColumn {
            items(news) { new ->
                Card(modifier = Modifier.clickable {
                    navController.navigate("${NavItem.NewDetails.route}?username=${new.username}&category=${new.category}&title=${new.title}&publicationDate=${new.publicationDate}&description=${new.description}&imageUrl=${new.imageUrl}"){  }
                }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        GlideImage(
                            imageModel = { new.imageUrl },
                            success = { imageState ->
                                Image(
                                    bitmap = imageState.imageBitmap!!,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp)
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
                                        },
                                    contentScale = ContentScale.FillBounds)
                            },
                            loading = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()

                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            },
                            failure = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp)
                                        .background(Color.Gray)
                                ) {
                                    CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        )

                        Column(modifier = Modifier.align(Alignment.BottomStart)) {
                            Box(
                                modifier = Modifier.background(
                                    when (new.category) {
                                        "Tecnología" -> Color.Cyan
                                        "Salud" -> Color.Red
                                        "Entretenimiento" -> Color.Green
                                        "Negocios" -> Color.Yellow
                                        else -> {
                                            Color.Magenta
                                        }
                                    }
                                )
                            ) {
                                Text(
                                    text = new.category!!,
                                    fontWeight = FontWeight.W700,
                                    fontSize = 8.sp,
                                    modifier = Modifier.padding(6.dp)
                                )
                            }
                            Text(
                                new.title!!,
                                fontWeight = FontWeight.W700,
                                fontSize = 15.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(6.dp)
                            )
                        }
                    }
                }
        }
    }
}
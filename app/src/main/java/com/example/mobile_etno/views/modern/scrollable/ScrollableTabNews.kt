package com.example.mobile_etno.views.modern.scrollable

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.models.news.New
import com.skydoves.landscapist.glide.GlideImage
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ScrollableTabNews(
    listNews: List<New>,
    onItemClick: (Int) -> Unit,
    navController: NavHostController
){
    ScrollableTabRow(
        selectedTabIndex = 0,
        backgroundColor = Color.Transparent,
        contentColor = Color.Transparent,
        edgePadding = 0.dp,
        divider = { Divider(startIndent = 0.dp, thickness = 0.dp, color = Color.Transparent) }
    ) {
        listNews.forEachIndexed { index, new ->

            Tab(selected = true, onClick = {
                onItemClick.invoke(index)
                val encodeUrlImage: String = if(new.imageUrl != null){
                    URLEncoder.encode(new.imageUrl, StandardCharsets.UTF_8.toString())
                }else{
                    "null"
                }
                navController.navigate("${NavItem.NewDetails.route}?username=${new.username}&category=${new.category}&title=${new.title}&publicationDate=${new.publicationDate}&description=${new.description}&imageUrl=${encodeUrlImage}")
            }) {
                Card(
                    elevation = 4.dp,
                    backgroundColor = Color.Transparent,
                    modifier = Modifier
                        .height(300.dp)
                        .width(300.dp)
                        .padding(4.dp)
                ) {
                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .width(300.dp)
                ) {
                        GlideImage(
                            imageModel = { new.imageUrl },
                            success = {
                                    imageState ->
                                Image(bitmap = imageState.imageBitmap!!, contentDescription = "",  modifier = Modifier
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
                                    }, contentScale = ContentScale.FillBounds)
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
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(14.dp)
                        ) {
                            Box(
                                modifier = Modifier.background(
                                    when (new.category) {
                                        "Tecnología" -> Color.Cyan
                                        "Salud" -> Color.Red
                                        "Entretenimiento" -> Color.Green
                                        else -> {
                                            Color.Yellow
                                        }
                                    }
                                )
                            ) {
                                Text(
                                    text = if(new.category != null) new.category!! else "ASDFAS",
                                    fontWeight = FontWeight.W700,
                                    fontSize = 8.sp,
                                    modifier = Modifier
                                        .padding(4.dp),
                                    color = Color.Black,
                                )
                            }
                            Text(text = if(new.title != null)new.title!! else "asdfdsfzcv", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
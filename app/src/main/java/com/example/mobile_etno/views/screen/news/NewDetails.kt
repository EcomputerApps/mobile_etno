package com.example.mobile_etno.views.screen.news

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import coil.compose.rememberAsyncImagePainter
import com.example.mobile_etno.R
import com.example.mobile_etno.models.news.New
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun NewDetails(
    new: New
){
    Column() {
        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        ) {
            GlideImage(
                imageModel = { new.imageUrl },
                success = {
                        imageState ->
                    Image(
                        bitmap = imageState.imageBitmap!!, contentDescription = "",  modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(
                                RoundedCornerShape(25.dp)
                                    .copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
                            )
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.FillBounds)
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
                            .fillMaxWidth()
                            .background(Color.Gray)

                    ) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            )
            /*
            Image(painter = painterResource(id = R.drawable.test_new), contentDescription = "", modifier = Modifier
                .clip(
                    RoundedCornerShape(25.dp)
                        .copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
                )
                .fillMaxWidth()
                .height(300.dp),
                contentScale = ContentScale.FillBounds
            )

             */
            Box(
                modifier = Modifier
                    .background(
                        when (new.category) {
                            "Tecnología" -> Color.Cyan
                            "Salud" -> Color.Red
                            "Entretenimiento" -> Color.Green
                            else -> {
                                Color.Yellow
                            }
                        }
                    )
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = new.category!!,
                    fontWeight = FontWeight.W700,
                    fontSize = 8.sp,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = new.publicationDate!!, color = Color.Gray)

                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Text(text = new.title!!, fontWeight = FontWeight.Bold, fontSize = 20.sp)

                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Text(
                    text = new.description!!,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}
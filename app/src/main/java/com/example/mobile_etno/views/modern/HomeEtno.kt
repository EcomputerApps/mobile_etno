package com.example.mobile_etno.views.modern

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
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
import com.example.mobile_etno.models.news.New

@Composable
fun HomeEtno(){
    var selectedTabIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier
            .padding(top = 20.dp, start = 14.dp, end = 14.dp)
            .verticalScroll(
                rememberScrollState()
            )) {
            Row() {
                Text(text = "Explorar", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.padding(horizontal = 120.dp))
                Icon(imageVector = Icons.Filled.Warning, contentDescription = "", modifier = Modifier.size(30.dp))
            }
            Text(text = "Noticias sugeridas para ti")
            ScrollableTabNews(listNews = listOf(New(), New(), New()), selectedTabIndex = selectedTabIndex){
                selectedTabIndex = it
            }
        }
    }
}

@Composable
fun ScrollableTabNews(
    listNews: List<New>,
    selectedTabIndex: Int,
    onItemClick: (Int) -> Unit
){
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.Transparent,
        contentColor = Color.Transparent,
        edgePadding = 0.dp,
        divider = { Divider(startIndent = 0.dp, thickness = 0.dp, color = Color.Transparent) }
    ) {
        listNews.forEachIndexed {
                index, item ->
            Tab(selected = selectedTabIndex == 0, onClick = { onItemClick.invoke(index) }) {
                Image(painter = painterResource(
                    id = com.example.mobile_etno.R.drawable.ocio_image),
                    contentDescription = "",
                    modifier = Modifier
                        .width(300.dp)
                        .height(300.dp)
                        .clip(
                            RoundedCornerShape(30.dp)
                        )
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
                        },
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}
package com.example.mobile_etno.views.screen.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mobile_etno.NavItem
import com.example.mobile_etno.viewmodels.UserVillagerViewModel

@Composable
fun GalleryScreen(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
) {
    var searchState by remember { mutableStateOf(TextFieldValue()) }

    if(searchState.text != ""){ userVillagerViewModel.filterImages(searchState.text) } else { userVillagerViewModel.filterAllImages() }

    val images = userVillagerViewModel.userVillagerImages.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Surface(
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(60.dp).copy(
                        topStart = ZeroCornerSize,
                        topEnd = ZeroCornerSize
                    )
                )
            ) {

                Image(painter = painterResource(id = com.example.mobile_etno.R.drawable.image_gallery),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
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
                        }
                )
                Column(modifier = Modifier.padding(top = 100.dp, start = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Galería",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(singleLine = true, placeholder = { Text(text = "Paisaje, Museo, Hotel...") } ,colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White
                    ), shape = CircleShape, value = searchState, onValueChange = {
                        value ->
                        searchState = value
                    }, label = {
                        Text(text = "")
                    }, leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "search", tint = Color.Black)})
                }
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart){
                        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Todo", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text(text = "${images.value.size} fotos", fontSize = 10.sp, color = Color.Gray)
                        }

                //Text(text = "asdfdasafasd", modifier = Modifier.padding(top = 60.dp))
                LazyVerticalGrid(modifier = Modifier.padding(top = 450.dp), columns = GridCells.Fixed(3), content = {
                    items(images.value){
                        image ->
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    navController.navigate("${NavItem.ImageDetail.route}?link=${image.link}")
                                },
                            elevation = 8.dp
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = image.link),
                                contentDescription = "",
                                modifier = Modifier
                                    .height(100.dp)
                                    .width(100.dp),
                                contentScale = ContentScale.FillBounds)
                        }
                    }
                })
            }
        
    }
}
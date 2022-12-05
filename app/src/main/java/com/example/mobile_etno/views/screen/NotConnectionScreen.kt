package com.example.mobile_etno.views.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NotConnectionScreen(title: String?="Message",
                        description: String?="Your Message",
                        onDismiss: () -> Unit){

    Dialog(onDismissRequest = { onDismiss.invoke() },
            properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.Transparent)) {
            Box(modifier = Modifier
                .background(
                    color = MaterialTheme.colors.onPrimary,
                    shape = RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp)
                )
                .align(
                    Alignment.BottomCenter
                )) {
                Image(
                    painter = painterResource(id = com.example.mobile_etno.R.drawable.wifi),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth(),
                    )
                Column(modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(text = title!!,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 130.dp)
                            .fillMaxWidth(), style = MaterialTheme.typography.h6, color = MaterialTheme.colors.primary)
                    Spacer(modifier = Modifier.height(8.dp))

                    //.........................Text : description
                    Text(
                        text = description!!,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.primary,
                    )
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Button : OK button
                    Button(
                        onClick = {
                                 // Log.d("okButton", "executing")
                                  onDismiss.invoke()
                        },
                        //shape = Shapes.small,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors= ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                        //.clip(RoundedCornerShape(25.dp))
                    ) {
                        Text(
                            text = "Salir",
                            style = MaterialTheme.typography.subtitle1,
                            color = MaterialTheme.colors.onPrimary,
                        )
                    }
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
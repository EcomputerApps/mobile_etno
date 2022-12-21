package com.example.mobile_etno.views.components

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mobile_etno.R
import com.example.mobile_etno.viewmodels.EventNameViewModel
import com.example.mobile_etno.views.screen.wallet.WalletActivity


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FormSubscription(onDismiss: () -> Unit, reservePrice: Double ,onSubscription: (name: String, mail: String, phone: String, wallet: String) -> Unit, eventNameViewModel: EventNameViewModel){
    Dialog(onDismissRequest = { onDismiss.invoke() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(){
            Box(modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize()
                .fillMaxWidth()) {

            }
            Box(modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp)
                )
                .height(700.dp)
                .fillMaxWidth(), contentAlignment = Alignment.Center) {
                ComponentFormSubscription(onDismiss = onDismiss, reservePrice = reservePrice, onSubscription = onSubscription, eventNameViewModel = eventNameViewModel)
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ComponentFormSubscription(onDismiss: () -> Unit, reservePrice: Double, onSubscription: (name: String, mail: String, phone: String, wallet: String) -> Unit, eventNameViewModel: EventNameViewModel){
    var userState by remember{ mutableStateOf(TextFieldValue()) }
    var mailState by remember { mutableStateOf(TextFieldValue()) }
    var phoneState by remember { mutableStateOf(TextFieldValue()) }
    var walletState by remember { mutableStateOf(TextFieldValue(reservePrice.toString())) }
    val currentContext = LocalContext.current

    Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        val modifierInput = Modifier.padding(horizontal = 16.dp)

        Image(painter = painterResource(id = R.drawable.etno_icon), contentDescription = "Etno", modifier = Modifier.size(100.dp))
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedTextField(value = userState, onValueChange = { value ->
            userState = value
        }, label = { Text(text = "Nombre")}, leadingIcon = { Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "Nombre",
            modifier = modifierInput
        )})
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedTextField(keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            value = mailState,
            onValueChange = { value -> mailState = value

            }, label = { Text(text = "Correo")}, leadingIcon = { Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "Correo",
                modifier = modifierInput
            )})
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedTextField(keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            value = phoneState,
            onValueChange = { value -> phoneState = value
            }, label = { Text(text = "Teléfono")}, leadingIcon = { Icon(
                imageVector = Icons.Filled.Phone,
                contentDescription = "Teléfono",
                modifier = modifierInput
            )})
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedTextField(enabled = false, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            value = walletState,
            onValueChange = { value -> walletState = value
            }, label = { Text(text = "Cartera")}, leadingIcon = { Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = "Cartera",
                modifier = modifierInput
            )})
        Spacer(modifier = Modifier.padding(vertical = 12.dp))
        Button(onClick = {if(userState.text != "" && mailState.text != "" && phoneState.text != "" && walletState.text != "")
            onSubscription.invoke(userState.text, mailState.text, phoneState.text, walletState.text)
          else Toast.makeText(currentContext, "No se permiten campos vacíos", Toast.LENGTH_SHORT).show()},
            colors = ButtonDefaults.buttonColors(backgroundColor = if(eventNameViewModel.isSubscribe.value) Color.Gray else Color.Red),
            modifier = Modifier.width(250.dp),
            shape = CircleShape){
            Text(text = "Subscribirse", color = Color.White, style = MaterialTheme.typography.button)
        }
        Button(onClick = { onDismiss.invoke() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            modifier = Modifier.width(250.dp),
            shape = CircleShape){
            Text(text = "Cancelar", color = Color.White, style = MaterialTheme.typography.button)
        }
    }
}
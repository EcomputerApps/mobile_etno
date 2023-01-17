package com.example.mobile_etno.views.screen.incident

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mobile_etno.R
import com.example.mobile_etno.models.FCMToken
import com.example.mobile_etno.models.mail.Mail
import com.example.mobile_etno.utils.Parse
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.modern.navigationbottom.BottomNavigationCustom
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun AddIncident(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
){
    var titleState by remember { mutableStateOf(TextFieldValue()) }
    var descriptionState by remember { mutableStateOf(TextFieldValue()) }
    var nameState by remember { mutableStateOf(TextFieldValue()) }
    var phoneState by remember { mutableStateOf(TextFieldValue()) }
    val isFinishedMessage = userVillagerViewModel.isFinishedMessage.collectAsState()
    val getMessage = userVillagerViewModel.saveUserMessage.collectAsState()

    val context = LocalContext.current

    Scaffold(
        topBar = {  },
        bottomBar = {
            BottomNavigationCustom(
            navController = navController,
            stateNavigationButton = -1,
            userVillagerViewModel = userVillagerViewModel
        ) }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .padding(8.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                Image(painter = painterResource(id = R.drawable.etno_icon), contentDescription = "etno", modifier = Modifier.size(120.dp))
                
                OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = titleState, placeholder = { Text(text = "Motivo") }, onValueChange = { value ->
                    titleState = value
                }, label = { Text(text = "Motivo") }, leadingIcon = { Icon(
                    painter = painterResource(id = R.drawable.subject_icon),
                    contentDescription = "Motivo"
                )
                })

                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp), value = descriptionState, placeholder =
                {
                    Text(text = "Descripción")
                }, onValueChange = { value ->
                    descriptionState = value
                }, label = { Text(text = "Descripción") }, leadingIcon = { Icon(
                    painter = painterResource(id = R.drawable.description_icon),
                    contentDescription = "Descripción"
                )
                })

                OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = nameState, placeholder = { Text(text = "Nombre") }, onValueChange = { value ->
                    nameState = value
                }, label = { Text(text = "Nombre") }, leadingIcon = { Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Nombre"
                )
                })

                OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = phoneState, placeholder = { Text(text = "Teléfono") }, onValueChange = { value ->
                    phoneState = value
                }, label = { Text(text = "Teléfono") }, leadingIcon = { Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = "Teléfono"
                )
                })
                    Button(onClick =
                    {
                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                            OnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                Log.w("failed fcm", "Fetching FCM registration token failed", task.exception)
                                return@OnCompleteListener
                            }
                            // Get new FCM registration token
                            val token = task.result
                            Log.d("stater_fcmToken", token.toString())

                            userVillagerViewModel.sendIncidence(Mail(
                                message = "Buenas tardes mi nombre es ${nameState.text} con número de teléfono ${phoneState.text}. \n ${descriptionState.text}",
                                subject = titleState.text
                            ), fcmToken = token
                            )
                        })
                    }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)) {
                        Text(text = "Enviar", color = Color.White)
                    }
            }
        }
    }
    if(isFinishedMessage.value) {
        Toast.makeText(context, getMessage.value.message, Toast.LENGTH_SHORT).show()
        userVillagerViewModel.updateIsFinished(false)
    }
}
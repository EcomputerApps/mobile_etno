package com.example.mobile_etno.views.screen.incident

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_etno.R
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.views.modern.navigationbottom.BottomNavigationCustom

@Composable
fun AddIncident(
    navController: NavHostController,
    userVillagerViewModel: UserVillagerViewModel
){
    var titleState by remember { mutableStateOf(TextFieldValue()) }
    var descriptionState by remember { mutableStateOf(TextFieldValue()) }
    var nameState by remember { mutableStateOf(TextFieldValue()) }
    var phoneState by remember { mutableStateOf(TextFieldValue()) }

    val context = LocalContext.current
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
        uri: Uri? -> imageUri = uri
    }

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
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                Image(painter = painterResource(id = R.drawable.etno_icon), contentDescription = "etno", modifier = Modifier.size(120.dp))
                
                OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = titleState, placeholder = { Text(text = "Motivo") }, onValueChange = { value ->
                    titleState = value
                }, label = { Text(text = "Motivo") }, leadingIcon = { Icon(
                    imageVector = Icons.Filled.Person,
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
                    imageVector = Icons.Filled.Person,
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
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Teléfono"
                )
                })
                Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    Button(onClick = { launcher.launch("image/*") }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)) {
                        Text(text = "Subir Imagen", color = Color.White)
                    }
                    Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)) {
                        Text(text = "Enviar", color = Color.White)
                    }
                }
                Image(painter = painterResource(id = R.drawable.panda_contact), contentDescription = "", modifier = Modifier.size(120.dp), contentScale = ContentScale.FillBounds)
            }
        }
    }
}
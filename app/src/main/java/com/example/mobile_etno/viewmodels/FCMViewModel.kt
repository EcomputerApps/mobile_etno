package com.example.mobile_etno.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_etno.models.FCMToken
import com.example.mobile_etno.models.service.client.FCMClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FCMViewModel: ViewModel() {

    var token by mutableStateOf("")

    fun saveFCMToken(fcmToken: FCMToken){
        viewModelScope.launch {
            val fcmTokenRequest = FCMClient.FCMService.saveFcmToken(fcmToken)
            try {
                val body = withContext(Dispatchers.IO){ fcmTokenRequest?.execute()?.body()}
                token = body?.token!!
            }catch (_: Exception){}
        }
    }
}
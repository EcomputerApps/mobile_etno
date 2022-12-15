package com.example.mobile_etno.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_etno.models.FCMToken
import com.example.mobile_etno.models.service.client.FCMClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FCMViewModel: ViewModel() {

    //var token by mutableStateOf("")
   private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token

    fun saveFCMToken(fcmToken: FCMToken){
        viewModelScope.launch {
            val fcmTokenRequest = FCMClient.FCMService.saveFcmToken(fcmToken)
            try {
                val body = withContext(Dispatchers.IO){ fcmTokenRequest?.execute()?.body()}
                _token.value = body?.token!!
            }catch (_: Exception){}
        }
    }
}
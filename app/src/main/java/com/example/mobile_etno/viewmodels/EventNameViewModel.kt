package com.example.mobile_etno.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_etno.models.SectionSubscribe
import com.example.mobile_etno.models.service.client.FCMClient
import com.example.mobile_etno.models.service.client.SubscriptionClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventNameViewModel: ViewModel() {

    private val _isSubscribe = MutableStateFlow(false)
    val isSubscribe: StateFlow<Boolean> = _isSubscribe

    private val _isSubscribeTitle = MutableStateFlow("Subscribirse")
    val isSubscribeTitle: StateFlow<String> = _isSubscribeTitle

    //I can operate this state with parameter on this function (Event title)
   private fun updateIsSubscribe(isSubscribe: Boolean){
        _isSubscribe.value = isSubscribe
   }

    private fun addEventToFCM(token: String, sectionSubscribe: SectionSubscribe){
        viewModelScope.launch {
            try {
                val fcmClient = FCMClient.FCMService.addSectionToFCMToken(token, sectionSubscribe)
                val body = withContext(Dispatchers.IO){ fcmClient.execute().body() }
                _isSubscribe.value = body?.isSubscribe!!
            }catch (_: java.lang.Exception){}
        }
    }

    private fun dropOutSectionByTokenAndTitle(token: String, title: String){
        viewModelScope.launch {
            try {
                val fcmClient = FCMClient.FCMService.dropOutSectionByTokenAndTitle(token = token, title = title)
                val body = withContext(Dispatchers.IO){ fcmClient.execute().body() }
                _isSubscribe.value = body?.isSubscribe!!
            }catch (_: java.lang.Exception){}
        }
    }

    fun getSubscriptionByTokenAndCategoryAndTitle(token: String, title: String){
        viewModelScope.launch {
            try {
                val subscriptionClient = SubscriptionClient.subscriptionService.getSubscription(token = token, category = "Evento", title = title)
                val body = withContext(Dispatchers.IO){ subscriptionClient.execute().body() }
                Log.d("see_subscription", body.toString())
            _isSubscribe.value = body?.isSubscribe!!
            }catch (_:java.lang.Exception){}
        }
    }

    fun changeStateButtonSubscribe(token: String, sectionSubscribe: SectionSubscribe){
        when(isSubscribe.value){
            true -> {
                updateIsSubscribe(true)
               // _isSubscribeTitle.value = "Subscribirse"
                dropOutSectionByTokenAndTitle(token = token, title = sectionSubscribe.title!!)
                _isSubscribeTitle.value = "Subscribirse"

                //Return the subscription in backend :)
            }
            false -> {
                updateIsSubscribe(false)
               // _isSubscribeTitle.value = "Desuscribirse"
                addEventToFCM(token = token, sectionSubscribe)
                _isSubscribeTitle.value = "Desuscribirse"
            }
        }
    }


}
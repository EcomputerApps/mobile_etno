package com.example.mobile_etno.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_etno.models.SectionSubscribe
import com.example.mobile_etno.models.service.client.FCMClient
import com.example.mobile_etno.models.service.client.SubscriptionClient
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventNameViewModel: ViewModel() {

    private val _isSubscribe = MutableStateFlow(false)
    val isSubscribe: StateFlow<Boolean> = _isSubscribe

    //I can operate this state with parameter on this function (Event title)
   private fun updateIsSubscribe(isSubscribe: Boolean){
        _isSubscribe.value = isSubscribe
   }

    private fun addEventToFCM(token: String, name: String, mail: String, phone: String, wallet: Double, sectionSubscribe: SectionSubscribe){
        viewModelScope.launch {
            try {
                val fcmClient = FCMClient.FCMService.addSectionToFCMToken(token, name, mail, phone, wallet, sectionSubscribe)
                val body = withContext(Dispatchers.IO){ fcmClient.execute().body() }
                _isSubscribe.value = body?.subscriptionUsers?.find { it.fcmToken == token }?.isSubscribe!!
            }catch (_: java.lang.Exception){}
        }
    }

    private fun dropOutSectionByTokenAndTitle(token: String, title: String){
        viewModelScope.launch {
            try {
                val fcmClient = FCMClient.FCMService.dropOutSectionByTokenAndTitle(token = token, category = "Evento", title = title)
                val body = withContext(Dispatchers.IO){ fcmClient.execute().body() }
                _isSubscribe.value = body?.subscriptionUsers?.find { it.fcmToken == token }?.isSubscribe!!
            }catch (_: java.lang.Exception){}
        }
    }

    fun getSubscriptionByTokenAndCategoryAndTitle(token: String, title: String){
        viewModelScope.launch {
            try {
                val subscriptionClient = SubscriptionClient.subscriptionService.getSubscription(token = token, category = "Evento", title = title)
                val body = withContext(Dispatchers.IO){ subscriptionClient.execute().body() }
                Log.d("see_subscription", body.toString())
                if(body == null){_isSubscribe.value = false} else {_isSubscribe.value = body.isSubscribe!!}
            }catch (_:java.lang.Exception){}
        }
    }

    fun changeStateButtonSubscribe(token: String, name: String ? = null, mail: String ? = null, phone: String ? = null, wallet: Double ? = null, sectionSubscribe: SectionSubscribe){
        when(isSubscribe.value){
            true -> {
                updateIsSubscribe(true)
                dropOutSectionByTokenAndTitle(token = token, title = sectionSubscribe.title!!)
                //Return the subscription in backend :)
            }
            false -> {
                updateIsSubscribe(false)
                addEventToFCM(token = token, name = name!!, mail = mail!!, phone = phone!!, wallet = wallet!!, sectionSubscribe)
            }
        }
    }
}
package com.example.mobile_etno.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_etno.models.UserSubscription
import com.example.mobile_etno.service.client.SubscriptionClient
import com.example.mobile_etno.viewmodels.locality.LocalityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventSubscriptionViewModel(
    private val localityViewModel: LocalityViewModel
): ViewModel() {

    private val _isSubscribe = MutableStateFlow(false)
    val isSubscribe: StateFlow<Boolean> = _isSubscribe

    private val _userSubscription = MutableStateFlow(UserSubscription())
    val userSubscription: StateFlow<UserSubscription> = _userSubscription

    private val _eventSeats = MutableStateFlow(0)
    val eventSeats: StateFlow<Int> = _eventSeats

    //I can operate this state with parameter on this function (Event title)
    fun updateIsSubscribe(isSubscribe: Boolean){
        _isSubscribe.value = isSubscribe
    }
    fun updateEventSeats(seats: Int){
        _eventSeats.value = seats
    }

    fun addSubscriptionToUser(title: String, userSubscription: UserSubscription){

        Log.d("title", "$userSubscription and locality ${localityViewModel.saveStateLocality.value}")
        viewModelScope.launch {
            try {
                val userSubscriptionClient = SubscriptionClient.subscriptionService
                    .addSubscriptionToUser(
                        username = localityViewModel.saveStateLocality.value,
                        title = title,
                        subscription = userSubscription)
                val body = withContext(Dispatchers.IO){ userSubscriptionClient.execute().body() }

                _userSubscription.value = body!!
                _isSubscribe.value = body.isSubscribe!!
                updateEventSeats(body.seats!!)
            }catch (_: Exception){  }
        }
    }
    fun dropOutSubscription(title: String, userSubscription: UserSubscription){
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    val userSubscriptionClient = SubscriptionClient.subscriptionService
                        .dropOutSubscription(
                            username = localityViewModel.saveStateLocality.value,
                            title = title,
                            subscriptionUser = userSubscription
                        )
                    val body = withContext(Dispatchers.IO){ userSubscriptionClient.execute().body() }
                    _userSubscription.value = body!!
                    _isSubscribe.value = body.isSubscribe!!
                    updateEventSeats(body.seats!!)
                }catch (_: Exception){  }
            }
        }
    }
    fun getSubscription(fcmToken: String, title: String){
        viewModelScope.launch {
            try {
                val userSubscriptionClient = SubscriptionClient.subscriptionService
                    .getSubscription(fcmToken, title)
                val body = withContext(Dispatchers.IO){ userSubscriptionClient.execute().body() }
                if(body == null){ _isSubscribe.value = false } else {_isSubscribe.value = body.isSubscribe!!}
                Log.d("isSubscribe", body.toString())
            }catch (_: Exception){  }
        }

    }
}
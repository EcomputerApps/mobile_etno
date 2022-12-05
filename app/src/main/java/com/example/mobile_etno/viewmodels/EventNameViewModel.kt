package com.example.mobile_etno.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EventNameViewModel: ViewModel() {

    private val _isSubscribe = MutableStateFlow(false)
    val isSubscribe: StateFlow<Boolean> = _isSubscribe

    private val _isSubscribeTitle = MutableStateFlow("Subscribirse")
    val isSubscribeTitle: StateFlow<String> = _isSubscribeTitle

    fun updateIsSubscribe(isSubscribe: Boolean){
        _isSubscribe.value = isSubscribe
    }

    fun changeStateButtonSubscribe(){
        when(isSubscribe.value){
            true -> {
                updateIsSubscribe(false)
                _isSubscribeTitle.value = "Subscribirse"
            }
            false -> {
                updateIsSubscribe(true)
                _isSubscribeTitle.value = "Desuscribirse"
            }
        }
    }
}
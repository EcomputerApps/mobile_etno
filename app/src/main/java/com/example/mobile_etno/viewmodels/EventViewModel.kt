package com.example.mobile_etno.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_etno.models.Event
import com.example.mobile_etno.models.service.client.EventClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventViewModel: ViewModel() {

    var events: List<Event> by mutableStateOf(mutableListOf())

    fun getEvents(){
        viewModelScope.launch {
            val eventsRequest = EventClient.eventService.getEvent()
            try {
                val body =  withContext(Dispatchers.IO){ eventsRequest.execute().body()}
                events = body!!.toMutableList()
            }catch (_: java.lang.Exception){
            }
        }
    }
}
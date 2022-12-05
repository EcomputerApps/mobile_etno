package com.example.mobile_etno.viewmodels


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_etno.models.Event
import com.example.mobile_etno.models.service.client.EventClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventViewModel(): ViewModel() {

    private val _events = MutableStateFlow<MutableList<Event>>(mutableListOf())
    val events: StateFlow<MutableList<Event>> = _events.asStateFlow()

    var isRefreshing by mutableStateOf(false)

    fun getEventRequest(){
        viewModelScope.launch {
            try {
                val eventsRequest = EventClient.eventService.getEvent()
                val body = withContext(Dispatchers.IO){ eventsRequest.execute().body()}
                _events.value = body!!.toMutableList()
                Log.d("event_dato", events.value.size.toString())
                isRefreshing = false
            }catch (_: java.lang.Exception){}
        }
    }
}


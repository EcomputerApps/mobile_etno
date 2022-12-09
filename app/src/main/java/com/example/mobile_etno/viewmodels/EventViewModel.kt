package com.example.mobile_etno.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_etno.models.Event
import com.example.mobile_etno.models.service.client.EventClient
import com.example.mobile_etno.models.service.database.SqlDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventViewModel(private val sqlDataBase: SqlDataBase) : ViewModel() {

    private val _events = MutableStateFlow<MutableList<Event>>(mutableListOf())
    val events: StateFlow<MutableList<Event>> = _events.asStateFlow()

    private val _saveEvents = MutableStateFlow<MutableList<Event>>(mutableListOf())
    val saveEvents: StateFlow<MutableList<Event>> = _saveEvents

    var isRefreshing by mutableStateOf(false)

    fun getEventRequest(){
        viewModelScope.launch {
            try {
                val eventsRequest = EventClient.eventService.getEvent()
                val body = withContext(Dispatchers.IO){ eventsRequest.execute().body()}
                _events.value = body!!.toMutableList()
                _saveEvents.value = events.value
                Log.d("event_dato", events.value[0].toString())

                events.value.forEach { item ->
                    sqlDataBase.insertEventDb(
                        idEvent = item.idEvent,
                        title = item.title,
                        address = item.address,
                        description = item.description,
                        organization = item.organization,
                        link = item.link,
                        startDate = item.startDate,
                        endDate = item.endDate,
                        publicationDate = item.publicationDate,
                        time = item.time,
                        lat = item.lat,
                        long = item.long,
                    )
                    item.images?.forEach { image ->  sqlDataBase.insertImageDb(idImage = image.idImage, linkImage = image.link, idEvent = item.idEvent)}
                }
                isRefreshing = false
            }catch (_: java.lang.Exception){}
        }
    }

    fun eventsFilterByPublicationDate(date: String){
        Log.d("date_updated", date)

        val filteredEvents = _saveEvents.value.filter { event -> event.publicationDate == date }

        //if(filteredEvents.isEmpty()){
        //    _events.value = saveEvents.value
       // }else{
            _events.value = filteredEvents.toMutableList()
        //}
       // Log.d("list_filtered", events.value.toString())
        Log.d("size_list", events.value.size.toString())
    }
}
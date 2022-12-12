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
import com.example.mobile_etno.utils.Parse
import com.himanshoe.kalendar.model.KalendarEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import java.time.format.DateTimeFormatter

class EventViewModel(private val sqlDataBase: SqlDataBase) : ViewModel() {

    private val _events = MutableStateFlow<MutableList<Event>>(mutableListOf())
    val events: StateFlow<MutableList<Event>> = _events.asStateFlow()

    private val _calendarEvents = MutableStateFlow<MutableList<KalendarEvent>>(mutableListOf())
    val calendarEvents: StateFlow<MutableList<KalendarEvent>> = _calendarEvents

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

                    calendarEvents.value.add(KalendarEvent(
                        date = LocalDate.parse("${Parse.getYear(item.publicationDate!!)}-${Parse.getDay(item.publicationDate!!)}-${Parse.getMouth(item.publicationDate!!)}"),
                        eventName = item.title!!,
                        eventDescription = item.description
                    ))
                }
                isRefreshing = false
            }catch (_: java.lang.Exception){}
        }
    }

    fun eventsFilterByPublicationDate(date: String){
        Log.d("date_updated", date)
        val formatter = DateTimeFormatter.ofPattern("d-M-yyyy")

        val filteredEvents = _saveEvents.value.filter { event -> event.publicationDate == java.time.LocalDate.parse(date, formatter).toString() }
            _events.value = filteredEvents.toMutableList()
        Log.d("size_list", events.value.size.toString())
    }
}
package com.example.mobile_etno.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_etno.models.Death
import com.example.mobile_etno.models.Event
import com.example.mobile_etno.models.Pharmacy
import com.example.mobile_etno.models.Tourism
import com.example.mobile_etno.models.service.client.UserVillagerClient
import com.example.mobile_etno.models.service.database.SqlDataBase
import com.example.mobile_etno.utils.Parse
import com.example.mobile_etno.viewmodels.locality.LocalityViewModel
import com.himanshoe.kalendar.model.KalendarEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

class UserVillagerViewModel(private val sqlDataBase: SqlDataBase, private val localityViewModel: LocalityViewModel): ViewModel() {
    private val _userVillagerEvents = MutableStateFlow<MutableList<Event>>(mutableListOf())
    val userVillagerEvents: StateFlow<MutableList<Event>> = _userVillagerEvents

    private val _userVillagerPharmacies = MutableStateFlow<MutableList<Pharmacy>>(mutableListOf())
    val userVillagerPharmacies: StateFlow<MutableList<Pharmacy>> = _userVillagerPharmacies

    private val _userVillagerTourism = MutableStateFlow<MutableList<Tourism>>(mutableListOf())
    val userVillagerTourism: StateFlow<MutableList<Tourism>> = _userVillagerTourism

    private val _userVillagerDeaths = MutableStateFlow<MutableList<Death>>(mutableListOf())
    val userVillagerDeaths: StateFlow<MutableList<Death>> = _userVillagerDeaths

    private val _calendarEvents = MutableStateFlow<MutableSet<KalendarEvent>>(mutableSetOf())
    val calendarEvents: StateFlow<MutableSet<KalendarEvent>> = _calendarEvents

    private val _saveUserVillagerEvents = MutableStateFlow<MutableList<Event>>(mutableListOf())
    private val saveUserVillagerEvents: StateFlow<MutableList<Event>> = _saveUserVillagerEvents

    private val _saveUserVillagerPharmacies = MutableStateFlow<MutableList<Pharmacy>>(mutableListOf())
    private val saveUserVillagerPharmacies: StateFlow<MutableList<Pharmacy>> = _saveUserVillagerPharmacies

    private val _saveUserVillagerTourism = MutableStateFlow<MutableList<Tourism>>(mutableListOf())
    private val saveUserVillagerTourism: StateFlow<MutableList<Tourism>> = _saveUserVillagerTourism

    var isRefreshing by mutableStateOf(false)

    // Events -> ------------------------------------------------------------------------------------------------------------------------------------------
    fun getUserToVillagerEvents(){
        viewModelScope.launch {
            try {
                val requestUserToVillager = UserVillagerClient.userVillager.getUserByUsernameToVillager(localityViewModel.saveStateLocality.value)
                val body = withContext(Dispatchers.IO){ requestUserToVillager.execute().body() }
                _userVillagerEvents.value = body?.events!!
                _saveUserVillagerEvents.value = userVillagerEvents.value

                withContext(Dispatchers.IO){
                    userVillagerEvents.value.forEach { item ->
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

                        val date = "${Parse.getYear(item.publicationDate!!)}-${Parse.getMouth(item.publicationDate!!)}-${Parse.getDay(item.publicationDate!!)}"

                        Log.d("data:value", date)

                        calendarEvents.value.add(KalendarEvent(
                            date = LocalDate.parse(date),
                            eventName = item.title!!,
                            eventDescription = item.description
                        ))
                    }
                }
                isRefreshing = false
            }catch (_: Exception){  }
        }
    }

    fun eventsFilterByPublicationDate(date: String){
        val dateParsedToISO = "${Parse.getMouth(date)}-${Parse.getYear(date)}-${Parse.getDay(date)}"
        val filteredEvents = saveUserVillagerEvents.value.filter { event -> event.publicationDate == dateParsedToISO }
            _userVillagerEvents.value = filteredEvents.toMutableList()
    }
    fun resetListEventsConnection(){
        _userVillagerEvents.value.removeAll(userVillagerEvents.value)
    }

    //Pharmacies -> --------------------------------------------------------------------------------------------------------------------------------------------
    fun getUserToVillagerPharmacies(){
        viewModelScope.launch {
            try {
                val requestUserToVillager = UserVillagerClient.userVillager.getUserByUsernameToVillager(localityViewModel.saveStateLocality.value)
                val body = withContext(Dispatchers.IO){ requestUserToVillager.execute().body() }
                _userVillagerPharmacies.value = body?.pharmacies!!
                _saveUserVillagerPharmacies.value = userVillagerPharmacies.value
            }catch (_: Exception){  }
        }
    }
    fun filterAllPharmacies(){
        _userVillagerPharmacies.value = saveUserVillagerPharmacies.value
    }
    fun pharmaciesFilter(type: String){
        val filteredPharmacies = saveUserVillagerPharmacies.value.filter { it.type == type }
        _userVillagerPharmacies.value = filteredPharmacies.toMutableList()
    }

    //Tourism -> --------------------------------------------------------------------------------------------------------------------------------------------
    fun getUserToVillagerTourism(){
        viewModelScope.launch {
            try {
                val requestUserToVillager = UserVillagerClient.userVillager.getUserByUsernameToVillager(localityViewModel.saveStateLocality.value)
                val body = withContext(Dispatchers.IO){ requestUserToVillager.execute().body() }
                _userVillagerTourism.value = body?.tourism!!
                _saveUserVillagerTourism.value = userVillagerTourism.value
            }catch (_: Exception){  }
        }
    }
    fun filterAllTourism(){
        _userVillagerTourism.value = saveUserVillagerTourism.value
    }
    fun tourismFilter(type: String){
        val filteredTourism = saveUserVillagerTourism.value.filter { it.type == type }
        _userVillagerTourism.value = filteredTourism.toMutableList()
    }

    //Deaths -> ------------------------------------------------------------------------------------------------------------------------------------------------
    fun getUserToVillagerDeaths(){
        viewModelScope.launch {
            try {
                val requestUserToVillager = UserVillagerClient.userVillager.getUserByUsernameToVillager(localityViewModel.saveStateLocality.value)
                val body = withContext(Dispatchers.IO){ requestUserToVillager.execute().body() }
                _userVillagerDeaths.value = body?.deaths!!
            }catch (_: Exception){  }
        }
    }
}
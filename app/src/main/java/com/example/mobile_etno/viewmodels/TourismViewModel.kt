package com.example.mobile_etno.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_etno.models.Tourism
import com.example.mobile_etno.models.service.client.TourismClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TourismViewModel: ViewModel() {
    private val _tourism = MutableStateFlow<MutableList<Tourism>>(mutableListOf())
    val tourism: StateFlow<MutableList<Tourism>> = _tourism

    private val _saveTourism = MutableStateFlow<MutableList<Tourism>>(mutableListOf())
    var saveTourism: StateFlow<MutableList<Tourism>> = _saveTourism

    fun getRequestTourism(){
        viewModelScope.launch {
            try {
                val tourismClient = TourismClient.tourismService.getTourism()
                val body = withContext(Dispatchers.IO){ tourismClient.execute().body() }
                Log.d("see_tourism", body.toString())
                _tourism.value = body!!.toMutableList()
                _saveTourism.value = body.toMutableList()
            }catch (_: Exception){  }
        }
    }

    fun filterAll(){
        _tourism.value = saveTourism.value
    }

    fun tourismFilter(type: String){
        val filteredTourism = saveTourism.value.filter { it.type == type }
        _tourism.value = filteredTourism.toMutableList()
    }
}
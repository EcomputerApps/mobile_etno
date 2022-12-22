package com.example.mobile_etno.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_etno.models.Pharmacy
import com.example.mobile_etno.models.service.client.PharmacyClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PharmacyViewModel: ViewModel() {
    private val _pharmacies = MutableStateFlow<MutableList<Pharmacy>>(mutableListOf())
    val pharmacies: StateFlow<MutableList<Pharmacy>> = _pharmacies

    private val _savePharmacies = MutableStateFlow<MutableList<Pharmacy>>(mutableListOf())
    private val savePharmacies: StateFlow<MutableList<Pharmacy>> = _savePharmacies

    fun getPharmacies(){
        viewModelScope.launch {
            try {
                val pharmacyClient = PharmacyClient.pharmacyService.getPharmacies()
                val body = withContext(Dispatchers.IO){ pharmacyClient.execute().body() }
                _pharmacies.value = body!!
                _savePharmacies.value = body
                Log.d("pharmacies::list", pharmacies.value.toString())
            }catch (_: Exception){}
        }
    }
    fun filterAll(){
        _pharmacies.value = savePharmacies.value
    }
    fun pharmaciesFilter(type: String){
        val filteredPharmacies = savePharmacies.value.filter { it.type == type }
        _pharmacies.value = filteredPharmacies.toMutableList()
        Log.d("pharmacy_filtered", filteredPharmacies.size.toString())
    }
}
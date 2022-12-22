package com.example.mobile_etno.viewmodels.locality

import androidx.lifecycle.ViewModel
import com.example.mobile_etno.utils.localities.Locality
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LocalityViewModel: ViewModel() {

    private val _localities = MutableStateFlow(listOf(
        Locality.HuescaLocality,
        Locality.PlasenciaMonte,
        Locality.BoleaLocality,
        Locality.AyerbeLocality
    ))
    val localities: StateFlow<List<Locality>> = _localities
}
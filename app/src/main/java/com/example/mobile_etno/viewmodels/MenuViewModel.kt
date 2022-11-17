package com.example.mobile_etno.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.mobile_etno.models.MenuItem
import com.google.gson.Gson

class MenuViewModel: ViewModel() {
    private val gson = Gson()
    var listMenu: List<MenuItem> = mutableStateListOf(
        MenuItem("Events"),
        MenuItem("Reservations"),
        MenuItem("Deaths"),
        MenuItem("Phone"),
        MenuItem("News"),
        MenuItem("Gallery"),
        MenuItem("Pharmacies"),
        MenuItem("Sponsors"),
        MenuItem("Festivities"),
        MenuItem("Advertisements"),
        MenuItem("Tourism"),
        MenuItem("Services"),
        MenuItem("Incidents"),
        MenuItem("Links"),
        MenuItem("Bandos")
    )
}
package com.example.mobile_etno.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_etno.models.MenuItem
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.File

class MenuViewModel: ViewModel() {
    private val gson = Gson()
    var listMenu: List<MenuItem> = mutableStateListOf()

    init {
        viewModelScope.launch{
            //val fileRead:JsonReader = JsonReader(FileReader("/menu_items.json"))
            val file = File("/menu_items.json").inputStream().readBytes().toString()
            val listFromJson: Array<MenuItem> = gson.fromJson(file, Array<MenuItem>::class.java)
            listMenu = listFromJson.toList()
        }
    }
}
package com.example.mobile_etno.viewmodels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

import com.google.gson.Gson

class MenuViewModel(): ViewModel() {
    private val gson = Gson()

    var isInvisible by mutableStateOf(true)

    fun updateInvisible(isInvisible: Boolean){
        this.isInvisible = isInvisible
    }
}

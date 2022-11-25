package com.example.mobile_etno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mobile_etno.models.service.database.SqlDataBase
import com.example.mobile_etno.viewmodels.EventViewModel
import com.example.mobile_etno.viewmodels.MenuViewModel
import com.example.mobile_etno.views.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val menuViewModel = MenuViewModel()
        val eventViewModel = EventViewModel()
        val sqlDataBase = SqlDataBase(context = this)

       // sqlDataBase.deleteEvents()
        val menuItem = resources.getStringArray(R.array.menu_items).toList()

        setContent {
           MainScreen(menuItem, menuViewModel, eventViewModel, sqlDataBase)
        }
    }
}


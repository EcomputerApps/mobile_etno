package com.example.mobile_etno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mobile_etno.viewmodels.MenuViewModel
import com.example.mobile_etno.views.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val menuViewModel = MenuViewModel()

        val menuItem = resources.getStringArray(R.array.menu_items).toList()


        /*
        val localization = Locale("en", "EN")
        Locale.setDefault(localization)

        val config = Configuration()
        config.setLocale(localization)

        applicationContext.resources.updateConfiguration(config, applicationContext.resources.displayMetrics)
         */

        setContent {
            //i have to pass a list to show the elements in screen home
           MainScreen(menuItem, menuViewModel)
        }
    }
}


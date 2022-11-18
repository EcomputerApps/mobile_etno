package com.example.mobile_etno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mobile_etno.components.NavigationDrawer
import com.example.mobile_etno.navigation.Navigation
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.MenuViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val menuViewModel = MenuViewModel()

        /*
        val localization = Locale("en", "EN")
        Locale.setDefault(localization)

        val config = Configuration()
        config.setLocale(localization)

        applicationContext.resources.updateConfiguration(config, applicationContext.resources.displayMetrics)
         */

        setContent {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Colors.backgroundEtno)) {


                NavigationDrawer(context = applicationContext, listMenu = listOf(
                    applicationContext.getString(R.string.advertisements),
                    applicationContext.getString(R.string.reservations),
                    applicationContext.getString(R.string.deaths),
                    applicationContext.getString(R.string.phones),
                    applicationContext.getString(R.string.news),
                    applicationContext.getString(R.string.gallery),
                    applicationContext.getString(R.string.pharmacies),
                    applicationContext.getString(R.string.sponsors),
                    applicationContext.getString(R.string.festivities),
                    applicationContext.getString(R.string.advertisements),
                    applicationContext.getString(R.string.tourism),
                    applicationContext.getString(R.string.service),
                    applicationContext.getString(R.string.incidents),
                    applicationContext.getString(R.string.links),
                    applicationContext.getString(R.string.bandos)
                ))


                /*
                Navigation(context = applicationContext, list = listOf(
                    applicationContext.getString(R.string.advertisements),
                    applicationContext.getString(R.string.reservations),
                    applicationContext.getString(R.string.deaths),
                    applicationContext.getString(R.string.phones),
                    applicationContext.getString(R.string.news),
                    applicationContext.getString(R.string.gallery),
                    applicationContext.getString(R.string.pharmacies),
                    applicationContext.getString(R.string.sponsors),
                    applicationContext.getString(R.string.festivities),
                    applicationContext.getString(R.string.advertisements),
                    applicationContext.getString(R.string.tourism),
                    applicationContext.getString(R.string.service),
                    applicationContext.getString(R.string.incidents),
                    applicationContext.getString(R.string.links),
                    applicationContext.getString(R.string.bandos)
                ))
                 */
            }
        }
    }
}


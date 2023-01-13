package com.example.mobile_etno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mobile_etno.models.service.database.SqlDataBase
import com.example.mobile_etno.viewmodels.EventSubscriptionViewModel
import com.example.mobile_etno.viewmodels.FCMViewModel
import com.example.mobile_etno.viewmodels.UserVillagerViewModel
import com.example.mobile_etno.viewmodels.locality.LocalityViewModel
import com.example.mobile_etno.views.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sqlDataBase = SqlDataBase(context = this)
        val localityViewModel = LocalityViewModel()
        val eventSubscriptionViewModel = EventSubscriptionViewModel(localityViewModel)
        val fcmViewModel = FCMViewModel(localityViewModel)
        val userVillagerViewModel = UserVillagerViewModel(this, sqlDataBase, localityViewModel, eventSubscriptionViewModel, fcmViewModel)

       // sqlDataBase.deleteEvents()
       // sqlDataBase.deleteImages()

        val menuItem = resources.getStringArray(R.array.menu_items).toList()

        setContent {
           MainScreen(menuItem,
               localityViewModel = localityViewModel,
               userVillagerViewModel = userVillagerViewModel,
               fcmViewModel = fcmViewModel,
               sqlDataBase = sqlDataBase)
        }
    }
}


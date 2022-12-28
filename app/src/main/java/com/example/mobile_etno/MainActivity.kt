package com.example.mobile_etno

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mobile_etno.models.FCMToken
import com.example.mobile_etno.models.service.database.SqlDataBase
import com.example.mobile_etno.viewmodels.*
import com.example.mobile_etno.viewmodels.locality.LocalityViewModel
import com.example.mobile_etno.views.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sqlDataBase = SqlDataBase(context = this)
        val localityViewModel = LocalityViewModel()
        val userVillagerViewModel = UserVillagerViewModel(sqlDataBase, localityViewModel)
        val eventNameViewModel = EventNameViewModel()
        val fcmViewModel = FCMViewModel(localityViewModel)
        val tourismViewModel = TourismViewModel()

       // sqlDataBase.deleteEvents()
        sqlDataBase.deleteImages()

        val menuItem = resources.getStringArray(R.array.menu_items).toList()

        setContent {
           MainScreen(menuItem,
               localityViewModel = localityViewModel,
               userVillagerViewModel = userVillagerViewModel,
               fcmViewModel = fcmViewModel,
               eventNameViewModel = eventNameViewModel,
               sqlDataBase = sqlDataBase)
        }
    }
}


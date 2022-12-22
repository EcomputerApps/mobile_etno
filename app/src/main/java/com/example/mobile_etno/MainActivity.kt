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
        val menuViewModel = MenuViewModel()
        val eventViewModel = EventViewModel(sqlDataBase)
        val eventNameViewModel = EventNameViewModel()
        val fcmViewModel = FCMViewModel()
        val pharmacyViewModel = PharmacyViewModel()
        val tourismViewModel = TourismViewModel()

       // sqlDataBase.deleteEvents()
        sqlDataBase.deleteImages()

        val menuItem = resources.getStringArray(R.array.menu_items).toList()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("failed fcm", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            Log.d("stater_fcmToken", token.toString())
            fcmViewModel.saveFCMToken(FCMToken(token = token))
        })

        setContent {
           MainScreen(menuItem,
               localityViewModel = localityViewModel,
                menuViewModel = menuViewModel,
               eventViewModel = eventViewModel,
               eventNameViewModel = eventNameViewModel,
               sqlDataBase = sqlDataBase,
               pharmacyViewModel = pharmacyViewModel,
               tourismViewModel = tourismViewModel)
        }
    }
}


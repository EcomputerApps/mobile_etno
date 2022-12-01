package com.example.mobile_etno

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mobile_etno.models.FCMToken
import com.example.mobile_etno.models.service.database.SqlDataBase
import com.example.mobile_etno.viewmodels.EventViewModel
import com.example.mobile_etno.viewmodels.FCMViewModel
import com.example.mobile_etno.viewmodels.MenuViewModel
import com.example.mobile_etno.views.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val menuViewModel = MenuViewModel()
        val eventViewModel = EventViewModel()
        val fcmViewModel = FCMViewModel()
        val sqlDataBase = SqlDataBase(context = this)

       // sqlDataBase.deleteEvents()
        val menuItem = resources.getStringArray(R.array.menu_items).toList()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("failed fcm", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            fcmViewModel.saveFCMToken(FCMToken(token = token))

            Log.d("get fcm", token)
        })

        setContent {
           MainScreen(menuItem, menuViewModel, eventViewModel, sqlDataBase)
        }
    }
}


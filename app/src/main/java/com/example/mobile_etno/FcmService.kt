package com.example.mobile_etno

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService: FirebaseMessagingService() {
    private val tag = FcmService::class.java.name

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(tag, "Received FCM from ${remoteMessage.notification?.body}")
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Received FCM token: $token")
    }
}
package com.example.mobile_etno.models

import java.util.UUID

data class Subscription(
    var idSubscriptionUser: UUID ? = null,
    var fcmToken: String ? = null,
    var name: String ? = null,
    var mail: String ? = null,
    var phone: String ? = null,
    var wallet: Double ? = null,
    var isSubscribe: Boolean ? = null
)
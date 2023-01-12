package com.example.mobile_etno.models

import java.util.UUID

data class UserSubscription(
    var idSubscriptionUser: UUID ? = null,
    var fcmToken: String ? = null,
    var title: String ? = null,
    var seats: Int ? = null,
    var name: String ? = null,
    var mail: String ? = null,
    var phone: String ? = null,
    var wallet: Double ? = null,
    var isSubscribe: Boolean ? = null
)
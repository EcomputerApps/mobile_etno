package com.example.mobile_etno.models

import java.util.UUID

data class Subscription(
    var idSubscription: UUID ? = null,
    var token: String ? = null,
    var category: String ? = null,
    var title: String ? = null,
    var isSubscribe: Boolean? = null
)
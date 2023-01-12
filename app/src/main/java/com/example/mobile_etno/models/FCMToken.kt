package com.example.mobile_etno.models

import java.util.UUID

data class FCMToken(
    var idFMC: UUID ? = null,
    var locality: String ? = null,
    var token: String ? = null,
)

package com.example.mobile_etno.models

import java.util.UUID

data class IncidentModel(
    var idIncident: UUID ? = null,
    var username: String ? = null,
    var fcmToken: String ? = null,
    var title: String ? = null,
    var description: String ? = null
)

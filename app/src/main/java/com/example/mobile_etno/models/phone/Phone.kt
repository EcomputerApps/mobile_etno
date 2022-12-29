package com.example.mobile_etno.models.phone

import java.util.UUID

data class Phone(
    var idPhone: UUID ? = null,
    var username: String ? = null,
    var category: String ? = null,
    var owner: String ? = null,
    var number: String ? = null,
    var schedule: String ? = null,
    var imageUrl: String ? = null,
)
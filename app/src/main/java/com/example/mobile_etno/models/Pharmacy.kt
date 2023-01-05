package com.example.mobile_etno.models

import java.util.UUID

data class Pharmacy(
    var idPharmacy: UUID ? = null,
    var username: String ? = null,
    var type: String ? = null,
    var name: String ? = null,
    var link: String ? = null,
    var imageUrl: String ? = null,
    var phone: String ? = null,
    var schedule: String ? = null,
    var description: String ? = null,
    var latitude: String ? = null,
    var longitude: String ? = null
)

package com.example.mobile_etno.models

import java.util.UUID

data class Pharmacy(
    var idPharmacy: UUID ? = null,
    var type: String ? = null,
    var name: String ? = null,
    var phone: String ? = null,
    var schedule: String ? = null,
    var longitude: String ? = null,
    var latitude: String ? = null
)

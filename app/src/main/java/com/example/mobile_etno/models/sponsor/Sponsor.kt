package com.example.mobile_etno.models.sponsor

import java.util.UUID

data class Sponsor(
    var idSponsor: UUID ? = null,
    var username: String ? = null,
    var title: String ? = null,
    var description: String ? = null,
    var phone: String ? = null,
    var urlImage: String ? = null
)
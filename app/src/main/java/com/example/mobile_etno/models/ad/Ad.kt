package com.example.mobile_etno.models.ad

import java.util.UUID

data class Ad(
    var idAd: UUID ? = null,
    var username: String ? = null,
    var title: String ? = null,
    var description: String ? = null,
    var imageUrl: String ? = null,
    var webUrl: String ? = null
)
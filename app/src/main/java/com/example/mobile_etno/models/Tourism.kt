package com.example.mobile_etno.models

import java.util.UUID

data class Tourism(
    var idTourism: UUID ? = null,
    var type: String ? = null,
    var title: String ? = null,
    var description: String ? = null,
    var longitude: String ? = null,
    var latitude: String ? = null,
    var images: MutableList<Image> ? = mutableListOf()
)

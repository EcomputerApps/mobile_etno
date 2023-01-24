package com.example.mobile_etno.models.bando

import java.util.*

data class Bando(
    var idBando: UUID? = null,
    var username: String ? = null,
    var title: String ? = null,
    var description: String ? = null,
    var issuedDate: String ? = null,
    var imageUrl: String ? = null
)

package com.example.mobile_etno.models.link

import java.util.UUID

data class Link(
    var idLink: UUID ? = null,
    var username: String ? = null,
    var title: String ? = null,
    var url: String ? = null
)

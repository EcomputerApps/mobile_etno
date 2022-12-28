package com.example.mobile_etno.models

import java.util.*

data class Death(
    var idDeath: UUID? = null,
    var username: String ? = null,
    var name: String ? = null,
    var deathDate: String ? = null,
    var description: String ? = null,
    var imageUrl: String ? = null
)

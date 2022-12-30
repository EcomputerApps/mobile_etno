package com.example.mobile_etno.models.news

import java.util.*

data class New(
    var idNew: UUID? = null,
    var username: String ? = null,
    var category: String ? = null,
    var title: String ? = null,
    var publicationDate: String ? = null,
    var description: String ? = null,
    var imageUrl: String ? = null
)
package com.example.mobile_etno.models

import java.util.*

data class SectionSubscribe(
    var idSectionSubscribe: UUID ? = null,
    var category: String ? = null,
    var title: String ? = null,
    var isSubscribe: Boolean ? = null
)
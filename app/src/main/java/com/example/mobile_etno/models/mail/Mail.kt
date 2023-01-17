package com.example.mobile_etno.models.mail

data class Mail(
    var address: String ? = null,
    var message: String ? = null,
    var subject: String ? = null,
    var attachment: String ? = null
)

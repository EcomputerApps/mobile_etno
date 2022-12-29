package com.example.mobile_etno.models.phone

import androidx.annotation.DrawableRes

data class PhoneCategory(
    @DrawableRes
    var image: Int ? = null,
    var categoryName: String ? = null
)
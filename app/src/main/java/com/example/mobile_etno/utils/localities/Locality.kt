package com.example.mobile_etno.utils.localities

import androidx.annotation.DrawableRes
import com.example.mobile_etno.R

sealed class Locality(
    @DrawableRes
    var shield: Int ? = null,
    var localityName: String ? = null,
){
    object HuescaLocality: Locality(
        shield = R.drawable.etno_icon,
        localityName = "Huesca"
    )
    object PlasenciaMonte: Locality(
        shield = R.drawable.etno_icon,
        localityName = "Plasencia del Monte"
    )
    object BoleaLocality: Locality(
        shield = R.drawable.etno_icon,
        localityName = "Bolea"
    )
    object AyerbeLocality: Locality(
        shield = R.drawable.etno_icon,
        localityName = "Ayerbe"
    )
}

package com.example.mobile_etno.utils

import android.content.res.Resources

object DataResourceString {
    fun convertString(idResource: Int): String{
        return Resources.getSystem().getString(idResource)
    }
}
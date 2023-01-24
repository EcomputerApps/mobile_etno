package com.example.mobile_etno.service

import com.example.mobile_etno.models.Pharmacy
import retrofit2.Call
import retrofit2.http.GET

interface PharmacyServiceInterface {
    @GET("pharmacies")
    fun getPharmacies(): Call<MutableList<Pharmacy>>
}
package com.example.mobile_etno.models.service

import com.example.mobile_etno.models.Tourism
import retrofit2.http.GET
import retrofit2.Call

interface TourismServiceInterface {
    @GET("tourism")
    fun getTourism(): Call<List<Tourism>>
}
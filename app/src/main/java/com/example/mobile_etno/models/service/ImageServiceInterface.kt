package com.example.mobile_etno.models.service

import com.example.mobile_etno.models.Image
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageServiceInterface {
    @GET("images")
    fun getImagesByLocality(@Query("locality") locality: String): Call<MutableList<Image>>
}
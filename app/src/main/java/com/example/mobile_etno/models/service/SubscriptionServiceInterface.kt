package com.example.mobile_etno.models.service

import com.example.mobile_etno.models.Subscription
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SubscriptionServiceInterface {
    @GET("subscriptions")
    fun getSubscription(@Query("token") token: String, @Query("category") category: String, @Query("title") title: String): Call<Subscription>
}
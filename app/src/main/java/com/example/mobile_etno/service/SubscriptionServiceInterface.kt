package com.example.mobile_etno.service

import com.example.mobile_etno.models.UserSubscription
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface SubscriptionServiceInterface {
    @POST("users/add/event/subscription")
    fun addSubscriptionToUser(
        @Query("username") username: String,
        @Query("title") title: String,
        @Body subscription: UserSubscription
    ): Call<UserSubscription>

    @PUT("users/dropout/event/subscription")
    fun dropOutSubscription(
        @Query("username") username: String,
        @Query("title") title: String,
        @Body subscriptionUser: UserSubscription
    ): Call<UserSubscription>

    @GET("subscription_users")
    fun getSubscription(
        @Query("fcmToken") fcmToken: String,
        @Query("title") title: String
    ): Call<UserSubscription>
}
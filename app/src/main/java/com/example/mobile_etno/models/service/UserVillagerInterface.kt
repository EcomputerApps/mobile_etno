package com.example.mobile_etno.models.service

import com.example.mobile_etno.models.Event
import com.example.mobile_etno.models.UserVillager
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserVillagerInterface {
    @GET("users/villagers")
    fun getUserByUsernameToVillager(@Query("username") username: String): Call<UserVillager>

    @GET("events")
    fun getEventByUsernameAndTitle(@Query("username") username: String, @Query("title") title: String): Call<Event>
}
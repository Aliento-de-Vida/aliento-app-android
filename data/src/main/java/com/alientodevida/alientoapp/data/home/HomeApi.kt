package com.alientodevida.alientoapp.data.home

import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.domain.home.Notification
import retrofit2.http.GET

interface HomeApi {
  
  @GET("/v1/home")
  suspend fun getHome(): Home
  
  @GET("/v1/notifications")
  suspend fun getNotifications(): List<Notification>
  
}
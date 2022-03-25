package com.alientodevida.alientoapp.data.home

import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.domain.home.Notification
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface HomeApi {
  
  @GET("/v1/home")
  suspend fun getHome(): Home
  
  @GET("/v1/notifications")
  suspend fun getNotifications(): List<Notification>
  
  @FormUrlEncoded
  @HTTP(method = "DELETE", path = "/v1/notification", hasBody = true)
  suspend fun deleteNotification(
    @Header("Authorization") authorization: String = AUTHORIZATION_KEY,
    @Field("id") id: Int,
  )
  
  @Multipart
  @POST("/v1/files")
  suspend fun uploadImage(
    @Header("Authorization") authorization: String = AUTHORIZATION_KEY,
    @Part filePart: MultipartBody.Part?,
  )
  
  @PUT("/v1/notification")
  @FormUrlEncoded
  suspend fun editNotification(
    @Header("Authorization") authorization: String = AUTHORIZATION_KEY,
    @Field("id") id: Int,
    @Field("title") title: String,
    @Field("content") content: String,
    @Field("image") image: String,
  ): Notification
  
  @POST("/v1/notification")
  @FormUrlEncoded
  suspend fun createNotification(
    @Header("Authorization") authorization: String = AUTHORIZATION_KEY,
    @Field("title") title: String,
    @Field("content") content: String,
    @Field("image") image: String,
  ): Notification
  
}

val AUTHORIZATION_KEY = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcyI6InRvZG9TZXJ2ZXIiLCJpZCI6MSwiZXhwIjoxNjQ4MjI5MjcwfQ.L0SdhKK7I4V68EZ7cXg0Z0GEKFP9E1IJg6zIwT4-2ck2owZ6_aWxpv0gXUz0gr5M8gjSnHDf0AQKosp6prUEAg"
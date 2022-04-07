package com.alientodevida.alientoapp.data.notification

import com.alientodevida.alientoapp.domain.notification.Notification
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface NotificationAdminApi {
  
  @FormUrlEncoded
  @HTTP(method = "DELETE", path = "/v1/notification", hasBody = true)
  suspend fun deleteNotification(
    @Field("id") id: Int,
  )
  
  @PUT("/v1/notification")
  @FormUrlEncoded
  suspend fun editNotification(
    @Field("id") id: Int,
    @Field("title") title: String,
    @Field("content") content: String,
    @Field("image") image: String,
  ): Notification
  
  @POST("/v1/notification")
  @FormUrlEncoded
  suspend fun createNotification(
    @Field("title") title: String,
    @Field("content") content: String,
    @Field("image") image: String,
  ): Notification
  
}
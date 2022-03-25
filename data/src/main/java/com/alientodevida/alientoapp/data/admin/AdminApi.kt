package com.alientodevida.alientoapp.data.admin

import com.alientodevida.alientoapp.domain.admin.Token
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AdminApi {
  
  @FormUrlEncoded
  @POST("/v1/users/login")
  suspend fun login(
    @Field("email") email: String,
    @Field("password") password: String
  ): Token
  
}
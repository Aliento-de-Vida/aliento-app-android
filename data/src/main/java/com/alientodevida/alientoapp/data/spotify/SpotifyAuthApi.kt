package com.alientodevida.alientoapp.data.spotify

import com.alientodevida.alientoapp.domain.entities.network.Token
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface SpotifyAuthApi {
  
  @POST
  @FormUrlEncoded
  suspend fun getToken(
    @Url url: String,
    @Header("Authorization") authorization: String,
    @Field("grant_type") grantType: String
  ): Token
  
}
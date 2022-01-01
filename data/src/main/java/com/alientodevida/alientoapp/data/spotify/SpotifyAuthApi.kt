package com.alientodevida.alientoapp.data.spotify

import com.alientodevida.alientoapp.domain.entities.network.Token
import retrofit2.http.*

interface SpotifyAuthApi {
  
  @POST
  @FormUrlEncoded
  suspend fun getToken(
    @Url url: String,
    @Header("Authorization") authorization: String,
    @Field("grant_type") grantType: String
  ): Token
  
}
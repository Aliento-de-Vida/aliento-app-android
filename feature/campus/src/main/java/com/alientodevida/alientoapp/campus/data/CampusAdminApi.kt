package com.alientodevida.alientoapp.campus.data

import com.alientodevida.alientoapp.domain.common.Campus
import retrofit2.http.*

interface CampusAdminApi {
  
  @FormUrlEncoded
  @HTTP(method = "DELETE", path = "/v1/campus", hasBody = true)
  suspend fun deleteCampus(
    @Field("id") id: Int,
  )
  
  @PUT("/v1/campus")
  @FormUrlEncoded
  suspend fun editCampus(
    @Field("id") id: Int,
    @Field("name") name: String,
    @Field("description") description: String,
    @Field("short_description") shortDescription: String,
    @Field("image_url") image_url: String,
    @Field("video_url") video_url: String,
    @Field("latitude") latitude: String,
    @Field("longitude") longitude: String,
    @Field("contact") contact: String,
    @Field("images") images: String,
  ): Campus
  
  @POST("/v1/campus")
  @FormUrlEncoded
  suspend fun createCampus(
    @Field("name") name: String,
    @Field("description") description: String,
    @Field("short_description") shortDescription: String,
    @Field("image_url") image_url: String,
    @Field("video_url") video_url: String,
    @Field("latitude") latitude: String,
    @Field("longitude") longitude: String,
    @Field("contact") contact: String,
    @Field("images") images: String,
  ): Campus
  
}
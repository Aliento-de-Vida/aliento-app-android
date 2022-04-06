package com.alientodevida.alientoapp.data.gallery

import com.alientodevida.alientoapp.domain.campus.Campus
import com.alientodevida.alientoapp.domain.gallery.Gallery
import com.alientodevida.alientoapp.domain.notification.Notification
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface GalleryAdminApi {
  
  @FormUrlEncoded
  @HTTP(method = "DELETE", path = "/v1/gallery", hasBody = true)
  suspend fun deleteGallery(
    @Field("id") id: Int,
  )
  
  @PUT("/v1/gallery")
  @FormUrlEncoded
  suspend fun editGallery(
    @Field("id") id: Int,
    @Field("name") name: String,
    @Field("cover_picture") coverPicture: String,
    @Field("images") images: String,
  ): Gallery
  
  @POST("/v1/gallery")
  @FormUrlEncoded
  suspend fun createGallery(
    @Field("name") name: String,
    @Field("cover_picture") coverPicture: String,
    @Field("images") images: String,
  ): Gallery
  
}
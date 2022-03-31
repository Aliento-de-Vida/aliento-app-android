package com.alientodevida.alientoapp.data.file

import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileApi {
  
  @Multipart
  @POST("/v1/files")
  suspend fun uploadImage(
    @Part filePart: MultipartBody.Part?,
  )
  
  @GET("/v1/files")
  suspend fun geAllImages(): List<String>
  
}
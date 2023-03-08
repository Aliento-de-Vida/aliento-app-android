package com.alientodevida.alientoapp.gallery.data

import com.alientodevida.alientoapp.domain.common.Gallery
import retrofit2.http.GET

interface GalleryApi {
  
  @GET("/v1/galleries")
  suspend fun getGalleries(): List<Gallery>
  
}
package com.alientodevida.alientoapp.data.gallery

import com.alientodevida.alientoapp.domain.gallery.Gallery
import retrofit2.http.GET

interface GalleryApi {
  
  @GET("/v1/galleries")
  suspend fun getGalleries(): List<Gallery>
  
}
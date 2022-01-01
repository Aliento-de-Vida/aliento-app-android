package com.alientodevida.alientoapp.data.gallery

import com.alientodevida.alientoapp.domain.gallery.GalleryRepository

class GalleryRepositoryImpl(
  private val api: GalleryApi
) : GalleryRepository {
  
  override suspend fun getGalleries() = api.getGalleries()
  
}
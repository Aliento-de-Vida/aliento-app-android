package com.alientodevida.alientoapp.domain.gallery

interface GalleryRepository {
  suspend fun getGalleries(): List<Gallery>
  
  suspend fun deleteGallery(id: Int)
  suspend fun createGallery(gallery: GalleryRequest): Gallery
  suspend fun editGallery(gallery: GalleryRequest): Gallery
}
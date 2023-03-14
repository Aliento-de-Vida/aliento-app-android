package com.alientodevida.alientoapp.gallery.domain

import com.alientodevida.alientoapp.domain.common.Gallery

interface GalleryRepository {
    suspend fun getGalleries(): List<Gallery>

    suspend fun deleteGallery(id: Int)
    suspend fun createGallery(gallery: GalleryRequest): Gallery
    suspend fun editGallery(gallery: GalleryRequest): Gallery
}
package com.alientodevida.alientoapp.domain.gallery

interface GalleryRepository {
    suspend fun getGalleries(): List<Gallery>
}
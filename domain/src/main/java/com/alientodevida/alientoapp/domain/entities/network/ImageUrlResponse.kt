package com.alientodevida.alientoapp.domain.entities.network

import com.alientodevida.alientoapp.domain.entities.local.ImageUrlEntity
import kotlinx.serialization.Serializable

@Serializable
data class ImageUrlResponse(
    val resources: List<Resources>
)

@Serializable
data class Resources(
    val url: String
)

/**
 * Convert Network results to domain objects
 */
fun ImageUrlResponse.asDomainModel(searchUrl: String): ImageUrlEntity {
    return ImageUrlEntity(
        resources.first().url,
        searchUrl
    )

}
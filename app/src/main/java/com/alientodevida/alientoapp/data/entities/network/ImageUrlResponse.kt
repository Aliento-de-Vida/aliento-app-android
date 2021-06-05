package com.alientodevida.alientoapp.data.entities.network

import com.alientodevida.alientoapp.data.entities.local.ImageUrlEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageUrlResponse(
    val resources: List<Resources>
)

@JsonClass(generateAdapter = true)
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
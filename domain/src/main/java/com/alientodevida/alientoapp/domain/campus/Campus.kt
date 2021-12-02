package com.alientodevida.alientoapp.domain.campus

import kotlinx.serialization.Serializable

@Serializable
data class Campus(
    val id: Int,
    val name: String,
    val description: String,
    val shortDescription: String,
    val imageUrl: String?,
    val videoUrl: String?,
    val location: Location,
    val images: List<String>,
    val contact: String,
)

@Serializable
data class Location(
    val latitude: String,
    val longitude: String,
)
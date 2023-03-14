package com.alientodevida.alientoapp.domain.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Campus(
    val id: Int,
    val name: String,
    val description: String,
    val shortDescription: String,
    val imageUrl: String,
    val videoUrl: String?,
    val location: Location,
    val images: List<String>,
    val contact: String,
) : Parcelable {

    companion object {
        fun empty() = Campus(
            0,
            "",
            "",
            "",
            "",
            "",
            Location("", ""),
            emptyList(),
            "",
        )
    }
}

@Parcelize
@Serializable
data class Location(
    val latitude: String,
    val longitude: String,
) : Parcelable {
    val isValid: Boolean = latitude.isBlank().not() && longitude.isBlank().not()
}
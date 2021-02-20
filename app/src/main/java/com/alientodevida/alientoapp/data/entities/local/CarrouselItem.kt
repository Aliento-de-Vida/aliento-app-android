package com.alientodevida.alientoapp.data.entities.local

data class CarrouselItem (
    val type: CarrouselItemType,
    val imageUrl: String?,
    var imageResource: Int?
)

enum class CarrouselItemType {
    ALIENTO_DE_VIDA,
    MANOS_EXTENDIDAS,
    CURSOS,
}

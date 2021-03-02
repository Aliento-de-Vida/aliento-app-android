package com.alientodevida.alientoapp.data.entities.local


sealed class CarouselItem(
        open val title: String,
)

data class CategoryItem(
        override val title: String,
        val type: CategoryItemType,
        var imageResource: Int,
): CarouselItem(title)

enum class CategoryItemType {
    CHURCH,
    MANOS_EXTENDIDAS,
    CURSOS,
}

data class YoutubeItem(
        override val title: String,
        val imageUrl: String,
        var youtubeId: String,
): CarouselItem(title)

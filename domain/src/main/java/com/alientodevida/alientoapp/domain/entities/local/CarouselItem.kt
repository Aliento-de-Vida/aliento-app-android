package com.alientodevida.alientoapp.domain.entities.local

sealed class CarouselItem(
        open val title: String,
        open val imageUrl: String?,
)

data class CategoryItem(
    override val title: String,
    override val imageUrl: String?,
    val type: CategoryItemType,
): CarouselItem(title, imageUrl)

enum class CategoryItemType {
    CHURCH,
    SOCIAL_WORK,
    COURSES,
    SERMONS
}

data class YoutubeItem(
        override val title: String,
        override val imageUrl: String?,
        var youtubeId: String,
): CarouselItem(title, imageUrl)

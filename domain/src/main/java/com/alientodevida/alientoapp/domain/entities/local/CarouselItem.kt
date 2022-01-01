package com.alientodevida.alientoapp.domain.entities.local

data class CarouselItem(
  val title: String,
  val imageUrl: String?,
  val categoryItem: CategoryItem?,
  val youtubeItem: YoutubeItem?,
)

data class CategoryItem(
  val type: CategoryItemType,
)

enum class CategoryItemType {
  CHURCH,
  CAMPUSES,
  GALLERY,
  SERMONS,
}

data class YoutubeItem(
  var youtubeId: String,
)
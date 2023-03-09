package com.alientodevida.alientoapp.domain.common

data class CarouselItem(
  val title: String,
  val imageUrl: String? = null,
  val categoryItem: CategoryItem? = null,
  val youtubeItem: YoutubeItem? = null,
)

data class CategoryItem(
  val type: CategoryItemType,
)

enum class CategoryItemType {
  CHURCH,
  CAMPUSES,
  GALLERY,
  DONATIONS,
  PRAYER,
  EBOOK,
  SERMONS,
}

data class YoutubeItem(
  var youtubeId: String,
)
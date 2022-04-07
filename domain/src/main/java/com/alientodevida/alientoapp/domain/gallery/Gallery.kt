package com.alientodevida.alientoapp.domain.gallery

import android.os.Parcelable
import com.alientodevida.alientoapp.domain.common.Image
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Gallery(
  val id: Int,
  val name: String,
  val coverPicture: String,
  val images: List<Image>,
) : Parcelable {
  
  companion object {
    fun empty() = Gallery(
      0,
      "",
      "",
      emptyList(),
    )
  }
  
}
package com.alientodevida.alientoapp.domain.gallery

import android.os.Parcelable
import com.alientodevida.alientoapp.domain.common.Image
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Gallery(
  val name: String,
  val coverPicture: String,
  val images: List<Image>,
) : Parcelable
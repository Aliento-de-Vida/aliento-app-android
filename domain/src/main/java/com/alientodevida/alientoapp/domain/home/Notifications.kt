package com.alientodevida.alientoapp.domain.home

import android.os.Parcelable
import com.alientodevida.alientoapp.domain.common.Image
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Notification(
  val id: Int,
  val title: String,
  val content: String,
  val image: Image,
  val date: String,
): Parcelable

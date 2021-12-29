package com.alientodevida.alientoapp.domain.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Image(
    val name: String,
): Parcelable

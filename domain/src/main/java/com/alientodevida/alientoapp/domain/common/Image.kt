package com.alientodevida.alientoapp.domain.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val name: String,
): Parcelable

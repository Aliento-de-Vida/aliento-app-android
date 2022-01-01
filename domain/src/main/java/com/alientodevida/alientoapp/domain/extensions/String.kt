package com.alientodevida.alientoapp.domain.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String?.notEmptyOrNull(): String? = if (this == null || this.isEmpty()) null else this

fun String.toDate(pattern: String = "yyyy-MM-dd'T'HH:mm:ss'Z'"): Date? =
  SimpleDateFormat(pattern, Locale("es", "ES")).parse(this)

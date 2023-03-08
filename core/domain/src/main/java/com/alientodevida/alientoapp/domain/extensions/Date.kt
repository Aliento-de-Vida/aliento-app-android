package com.alientodevida.alientoapp.domain.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern: String = "yyyy-MM-dd'T'HH:mm:ss'Z'"): String =
  SimpleDateFormat(pattern, Locale("es", "ES")).format(this)

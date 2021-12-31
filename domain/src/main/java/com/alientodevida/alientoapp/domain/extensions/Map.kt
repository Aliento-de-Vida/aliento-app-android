package com.alientodevida.alientoapp.domain.extensions

fun <K, V> Map<out K, V>.notEmptyOrNull(): Map<out K, V>? = if (this.isEmpty()) null else this
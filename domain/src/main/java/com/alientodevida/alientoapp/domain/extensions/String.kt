package com.alientodevida.alientoapp.domain.extensions

fun String?.notEmptyOrNull(): String? = if (this == null || this.isEmpty()) null else this
package com.alientodevida.alientoapp.app.utils.extensions

import com.alientodevida.alientoapp.app.utils.Constants

fun String.toImageUrl() = "${Constants.BASE_IMAGES_URL}/${this}"
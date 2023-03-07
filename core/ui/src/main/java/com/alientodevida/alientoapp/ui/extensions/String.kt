package com.alientodevida.alientoapp.ui.extensions

import com.alientodevida.alientoapp.ui.utils.Constants


fun String.toImageUrl() = "${Constants.BASE_IMAGES_URL}/${this}"
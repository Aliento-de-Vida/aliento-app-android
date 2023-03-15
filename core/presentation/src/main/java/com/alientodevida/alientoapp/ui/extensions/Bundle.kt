package com.alientodevida.alientoapp.ui.extensions

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

@Suppress("deprecated")
fun <T : Parcelable> Bundle.getParcelableValue(key: String, clazz: Class<T>) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelable(key, clazz)!!
    } else {
        this.getParcelable(key)!!
    }

package com.alientodevida.alientoapp.data.entities

import com.alientodevida.alientoapp.data.entities.network.base.ResponseError

data class UserFriendlyError(
    val result: ResponseError<*>,
)

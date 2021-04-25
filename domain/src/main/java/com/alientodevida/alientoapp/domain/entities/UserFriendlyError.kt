package com.alientodevida.alientoapp.domain.entities

import com.alientodevida.alientoapp.domain.entities.network.base.ResponseError

data class UserFriendlyError(
    val result: ResponseError<*>,
)

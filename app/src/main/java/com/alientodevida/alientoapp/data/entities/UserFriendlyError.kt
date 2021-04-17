package com.alientodevida.alientoapp.data.entities

import com.alientodevida.alientoapp.data.entities.network.base.ApiError
import com.alientodevida.alientoapp.data.entities.network.base.ApiResult

data class UserFriendlyError(
    val result: ApiResult<Any, ApiError>,
)

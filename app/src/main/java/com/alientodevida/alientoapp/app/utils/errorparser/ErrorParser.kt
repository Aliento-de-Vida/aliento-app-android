package com.alientodevida.alientoapp.app.utils.errorparser

import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.state.Message
import com.google.android.gms.common.api.ApiException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorParser @Inject constructor() {
  operator fun invoke(ex: Exception?): Message {
    if (ex is ApiException) return Message.Resource.Error(
      id = UUID.randomUUID().mostSignificantBits,
      image = R.drawable.ic_error_48,
      title = R.string.error_api_title,
      message = R.string.error_server_message,
      arguments = listOf()
    )
    
    return Message.Resource.Error(UUID.randomUUID().mostSignificantBits)
  }
}

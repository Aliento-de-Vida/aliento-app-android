package com.alientodevida.alientoapp.ui.errorparser

import com.alientodevida.alientoapp.ui.R
import com.alientodevida.alientoapp.ui.state.Message
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorParser @Inject constructor() {
    operator fun invoke(ex: Exception?): Message {
        if (true /*ex is ApiException*/) {
            return Message.Resource.Error(
                id = UUID.randomUUID().mostSignificantBits,
                image = R.drawable.ic_error_48,
                title = R.string.error_api_title,
                message = R.string.error_server_message,
                arguments = listOf(),
            )
        }

        return Message.Resource.Error(UUID.randomUUID().mostSignificantBits)
    }
}

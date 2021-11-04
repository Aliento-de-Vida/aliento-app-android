package com.alientodevida.alientoapp.app.state

import androidx.annotation.StringRes
import com.alientodevida.alientoapp.app.R

sealed class Message {

    abstract val type: Type

    enum class Type {
        INFORMATIONAL,
        ERROR,
        WARNING,
    }

    data class Resource(
        override val type: Type,
        @StringRes
        val title: Int,
        @StringRes
        val message: Int,
        @StringRes
        val action: Int,
        val arguments: List<Argument> = emptyList(),
    ) : Message() {
        sealed class Argument {
            data class Resource(
                @StringRes
                val resource: Int,
            ) : Argument()

            data class Localized(
                val value: Any,
            ) : Argument()
        }

        companion object {
            @Suppress("FunctionName")
            fun Error(
				@StringRes
                title: Int = R.string.error_generic_title,
				@StringRes
                message: Int = R.string.error_generic_message,
				@StringRes
                action: Int = R.string.ok,
				arguments: List<Argument> = emptyList(),
            ): Resource = Resource(
                type = Type.ERROR,
                title = title,
                message = message,
                action = action,
                arguments = arguments,
            )
        }
    }

    data class Localized(
        override val type: Type,
        val title: String,
        val message: String,
        val action: String,
    ) : Message() {
        companion object {
            @Suppress("FunctionName")
            fun Error(
                title: String,
                message: String,
                action: String = "Ok",
            ): Localized = Localized(
                type = Type.ERROR,
                title = title,
                message = message,
                action = action,
            )
        }
    }

}

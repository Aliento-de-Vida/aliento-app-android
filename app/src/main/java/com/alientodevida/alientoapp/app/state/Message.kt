package com.alientodevida.alientoapp.app.state

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.alientodevida.alientoapp.app.R

sealed class Message {
  
  abstract val id: Long
  abstract val type: Type
  abstract val image: Int
  
  enum class Type {
    INFORMATIONAL,
    ERROR,
    WARNING,
  }
  
  data class Resource(
    override val id: Long,
    override val type: Type,
    @DrawableRes
    override val image: Int,
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
        id: Long,
        @StringRes
        title: Int = R.string.error_generic_title,
        @StringRes
        message: Int = R.string.error_generic_message,
        @DrawableRes
        image: Int = R.drawable.ic_error_48,
        @StringRes
        action: Int = R.string.ok,
        arguments: List<Argument> = emptyList(),
      ): Resource = Resource(
        id = id,
        type = Type.ERROR,
        image = image,
        title = title,
        message = message,
        action = action,
        arguments = arguments,
      )
  
      fun Informational(
        id: Long,
        @StringRes
        title: Int,
        @StringRes
        message: Int,
        @DrawableRes
        image: Int = R.drawable.ic_check_48,
        @StringRes
        action: Int = R.string.ok,
        arguments: List<Argument> = emptyList(),
      ): Resource = Resource(
        id = id,
        type = Type.INFORMATIONAL,
        image = image,
        title = title,
        message = message,
        action = action,
        arguments = arguments,
      )
    }
  }
  
  data class Localized(
    override val id: Long,
    override val type: Type,
    @DrawableRes
    override val image: Int,
    val title: String,
    val message: String,
    val action: String,
  ) : Message() {
    companion object {
      @Suppress("FunctionName")
      fun Error(
        id: Long,
        title: String,
        message: String,
        action: String = "Ok",
        @DrawableRes
        image: Int = R.drawable.ic_error_48,
      ): Localized = Localized(
        id = id,
        type = Type.ERROR,
        image = image,
        title = title,
        message = message,
        action = action,
      )
  
      fun Informational(
        id: Long,
        title: String,
        message: String,
        action: String,
        @DrawableRes
        image: Int = R.drawable.ic_check_48,
      ): Localized = Localized(
        id = id,
        type = Type.ERROR,
        image = image,
        title = title,
        message = message,
        action = action,
      )
    }
  }
  
}

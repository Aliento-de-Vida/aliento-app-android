package com.alientodevida.alientoapp.app.extensions

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.alientodevida.alientoapp.app.compose.components.Dialog
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.state.Message
import com.alientodevida.alientoapp.app.state.ViewModelResult

@Composable
fun Message.localized(): Message.Localized = when (this) {
  is Message.Localized -> this
  is Message.Resource -> Message.Localized(
    type = type,
    title = stringResource(title),
    message = if (arguments.isEmpty()) stringResource(message)
    else stringResource(
      message,
      *arguments.map { arg: Message.Resource.Argument ->
        when (arg) {
          is Message.Resource.Argument.Localized -> arg.value
          is Message.Resource.Argument.Resource -> stringResource(arg.resource)
        }
      }.toTypedArray(),
    ),
    action = stringResource(action),
  )
}

@Composable
fun Message.Dialog(
  onAction: (() -> Unit)? = null,
  onDismiss: (() -> Unit)? = null,
) {
  var showDialog by remember(this) { mutableStateOf(true) }
  if (showDialog) {
    val (_, title, message, action) = localized()
    Dialog(
      title = title,
      body = message,
      action = action,
      actionColor = MaterialTheme.colors.error,
      onAction = {
        showDialog = false
        onAction?.invoke()
      },
      onDismiss = {
        showDialog = false
        onDismiss?.invoke()
      },
    )
  }
}

@Composable
fun ViewModelResult.Error.Dialog(
  onAction: (() -> Unit)? = null,
  onDismiss: (() -> Unit)? = null,
) = message.Dialog(onAction, onDismiss)

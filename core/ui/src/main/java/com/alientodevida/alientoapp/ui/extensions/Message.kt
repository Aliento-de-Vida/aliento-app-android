package com.alientodevida.alientoapp.app.extensions

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.alientodevida.alientoapp.ui.components.Dialog
import com.alientodevida.alientoapp.ui.state.Message
import com.alientodevida.alientoapp.ui.state.ViewModelResult

@Composable
fun Message.localized(): Message.Localized = when (this) {
  is Message.Localized -> this
  is Message.Resource -> Message.Localized(
    id = id,
    type = type,
    image = image,
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
    val (id, _, image, title, message, action) = localized()
    Dialog(
      title = title,
      image = image,
      imageContentDescription = title,
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

@Composable
fun Message.SnackBar(
  scaffoldState: ScaffoldState,
  onMessageDismiss: (Long) -> Unit,
) {
  val message = remember(this.id) { this }
  
  val (_, _, _, _, messageText, _) = this.localized()
  
  val onMessageDismissState by rememberUpdatedState(onMessageDismiss)
  
  LaunchedEffect(messageText, scaffoldState) {
    val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(message = messageText)
    if (snackbarResult == SnackbarResult.ActionPerformed) {
      onMessageDismissState(message.id)
    }
    onMessageDismissState(message.id)
  }
}
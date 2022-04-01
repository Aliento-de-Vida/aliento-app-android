package com.alientodevida.alientoapp.app.features.notifications.editcreate

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.compose.components.AttachmentModel
import com.alientodevida.alientoapp.app.compose.components.Attachments
import com.alientodevida.alientoapp.app.compose.components.ClickableIcon
import com.alientodevida.alientoapp.app.compose.components.H5
import com.alientodevida.alientoapp.app.compose.components.Icon
import com.alientodevida.alientoapp.app.compose.components.InputField
import com.alientodevida.alientoapp.app.compose.components.LoadingIndicator
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.extensions.Dialog
import com.alientodevida.alientoapp.domain.notification.Notification

@Composable
fun EditNotification(
  viewModel: EditCreateNotificationViewModel,
  onBackPressed: () -> Unit,
) {
  val viewModelState by viewModel.viewModelState.collectAsState()
  
  EditNotificationContent(
    uiState = viewModelState,
    onMessageDismiss = viewModel::onMessageDismiss,
    onNotificationTitleChanged = viewModel::onNotificationTitleChanged,
    onNotificationDescriptionChanged = viewModel::onNotificationDescriptionChanged,
    addAttachment = viewModel::addAttachment,
    removeAttachment = viewModel::removeAttachment,
    saveNotification = viewModel::saveNotification,
    onBackPressed = onBackPressed,
  )
}

@Composable
fun EditNotificationContent(
  uiState: NotificationUiState,
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  onMessageDismiss: (Long) -> Unit,
  onNotificationTitleChanged: (String) -> Unit,
  onNotificationDescriptionChanged: (String) -> Unit,
  addAttachment: (AttachmentModel) -> Unit,
  removeAttachment: (AttachmentModel) -> Unit,
  saveNotification: (NotificationRequest) -> Unit,
  onBackPressed: () -> Unit,
) {
  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(onBackPressed = onBackPressed)
    },
    floatingActionButton = {
      if (uiState.notificationRequest.isComplete) FloatingActionButton(
        onClick = { saveNotification(uiState.notificationRequest) },
        contentColor = MaterialTheme.colors.surface,
      ) {
        Icon(
          icon = R.drawable.ic_send_24,
          contentDescription = "Create Notification",
          tint = MaterialTheme.colors.onSurface
        )
      }
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .padding(paddingValues = paddingValues)
        .background(color = MaterialTheme.colors.background),
    ) {
      NotificationsBody(
        notification = uiState.notificationRequest,
        onNotificationTitleChanged = onNotificationTitleChanged,
        onNotificationDescriptionChanged = onNotificationDescriptionChanged,
        addAttachment = addAttachment,
        removeAttachment = removeAttachment,
      )
      
      if (uiState.loading) LoadingIndicator()
      uiState.messages.firstOrNull()?.let {
        it.Dialog(
          onAction = { onMessageDismiss(it.id) },
          onDismiss = { onMessageDismiss(it.id) },
        )
      }
    }
  }
}

// TODO can we extract a component ?
@Composable
fun TopAppBar(
  onBackPressed: () -> Unit,
) {
  val modifier = Modifier.size(width = 60.dp, height = 50.dp)
  
  androidx.compose.material.TopAppBar(
    title = {
      Image(
        painter = painterResource(id = R.drawable.logo_negro),
        colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onBackground),
        contentScale = ContentScale.Inside,
        alignment = Alignment.Center,
        modifier = Modifier
          .padding(8.dp)
          .fillMaxWidth(),
        contentDescription = null,
      )
    },
    navigationIcon = {
      ClickableIcon(
        modifier = modifier,
        icon = R.drawable.ic_back_24,
        contentDescription = "Back Button",
        tint = MaterialTheme.colors.onBackground,
        onClick = onBackPressed,
      )
    },
    actions = {
      Box(modifier = modifier, contentAlignment = Alignment.Center) { }
    },
    backgroundColor = MaterialTheme.colors.background,
  )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun NotificationsBody(
  notification: NotificationRequest,
  onNotificationTitleChanged: (String) -> Unit,
  onNotificationDescriptionChanged: (String) -> Unit,
  addAttachment: (AttachmentModel) -> Unit,
  removeAttachment: (AttachmentModel) -> Unit,
) {
  Column(
    Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  ) {
    val onSurfaceAlpha = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
  
    Spacer(modifier = Modifier.height(16.dp))
    H5(
      modifier = Modifier.padding(horizontal = 8.dp),
      text = if (notification.id == 0) "Nueva Notificación" else "Editar Notificación",
      color = MaterialTheme.colors.onBackground,
    )
  
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = notification.title,
      onChanged = onNotificationTitleChanged,
      placeholder = "Título",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      label = "Título",
      labelColor = onSurfaceAlpha,
    )
  
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = notification.content,
      onChanged = onNotificationDescriptionChanged,
      placeholder = "Descripción",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      label = "Descripción",
      labelColor = onSurfaceAlpha,
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    Attachments(
      limit = 1,
      attachments = if (notification.attachment != null) listOf(notification.attachment) else emptyList(),
      addAttachment = addAttachment,
      removeAttachment = removeAttachment,
    )
  }
}

@Preview
@Composable
fun NotificationsPreview() {
  AppTheme {
    EditNotificationContent(
      NotificationUiState(
        Notification(
          1,
          "Test Notification",
          "This is a test",
          com.alientodevida.alientoapp.domain.common.Image("cursos.png"),
          "2021-12-31T18:58:34Z"
        ).toNotificationRequest(),
        loading = true,
        messages = emptyList(),
      ),
      onMessageDismiss = { },
      onNotificationTitleChanged = {},
      onNotificationDescriptionChanged = {},
      addAttachment = {},
      removeAttachment = {},
      saveNotification = {},
      onBackPressed = {},
    )
  }
}
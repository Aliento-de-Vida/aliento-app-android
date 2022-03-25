package com.alientodevida.alientoapp.app.features.notifications.editcreate

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.compose.components.Body2
import com.alientodevida.alientoapp.app.compose.components.ClickableIcon
import com.alientodevida.alientoapp.app.compose.components.FilledButton
import com.alientodevida.alientoapp.app.compose.components.H5
import com.alientodevida.alientoapp.app.compose.components.Icon
import com.alientodevida.alientoapp.app.compose.components.InputField
import com.alientodevida.alientoapp.app.compose.components.LoadingIndicator
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.state.ViewModelResult
import com.alientodevida.alientoapp.domain.home.Notification

@Composable
fun EditNotification(
  viewModel: EditCreateNotificationViewModel,
  onBackPressed: () -> Unit,
) {
  var notificationRequest by remember { mutableStateOf(viewModel.initialNotificationRequest) }
  
  val notificationResult by viewModel.notificationRequest.collectAsState()
  (notificationResult as? ViewModelResult.Success<NotificationRequest>)?.let { result -> notificationRequest = result.data }
  
  val isNotificationRequestComplete by viewModel.isNotificationRequestComplete.collectAsState()
  
  EditNotificationContent(
    notification = notificationRequest,
    isNotificationRequestComplete = isNotificationRequestComplete,
    showLoading = notificationResult == ViewModelResult.Loading,
    onNotificationTitleChanged = viewModel::onNotificationTitleChanged,
    onNotificationDescriptionChanged = viewModel::onNotificationDescriptionChanged,
    onNotificationImageNameChanged = viewModel::onNotificationImageNameChanged,
    onNotificationImageChanged = viewModel::onNotificationImageChanged,
    saveNotification = viewModel::saveNotification,
    onBackPressed = onBackPressed,
  )
}

@Composable
fun EditNotificationContent(
  notification: NotificationRequest,
  isNotificationRequestComplete: Boolean,
  showLoading: Boolean,
  onNotificationTitleChanged: (String) -> Unit,
  onNotificationDescriptionChanged: (String) -> Unit,
  onNotificationImageNameChanged: (String) -> Unit,
  onNotificationImageChanged: (Attachment?) -> Unit,
  saveNotification: (NotificationRequest) -> Unit,
  onBackPressed: () -> Unit,
) {
  Scaffold(
    topBar = {
      TopAppBar(onBackPressed = onBackPressed)
    },
    floatingActionButton = {
      if (isNotificationRequestComplete) FloatingActionButton(
        onClick = { saveNotification(notification) },
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
        notification = notification,
        onNotificationTitleChanged = onNotificationTitleChanged,
        onNotificationDescriptionChanged = onNotificationDescriptionChanged,
        onNotificationImageNameChanged = onNotificationImageNameChanged,
        onNotificationImageChanged = onNotificationImageChanged,
      )
      if (showLoading) LoadingIndicator()
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
        tint = Color.Black,
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
  onNotificationImageNameChanged: (String) -> Unit,
  onNotificationImageChanged: (Attachment?) -> Unit,
) {
  Column(
    Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  ) {
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
    )
  
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = notification.content,
      onChanged = onNotificationDescriptionChanged,
      placeholder = "Descripción",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = notification.image?.name ?: "",
      onChanged = onNotificationImageNameChanged,
      placeholder = "Nombre de la imágen",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
  
    Spacer(modifier = Modifier.height(16.dp))
  
    Attachment(notification, onNotificationImageChanged)
  }
}

@Composable
private fun Attachment(
  notification: NotificationRequest,
  onNotificationImageChanged: (Attachment?) -> Unit,
) {
  var uri: Uri? by remember { mutableStateOf(null) }
  val chooserTitle = "Imágen"
  val launcher = rememberLauncherForActivityResult(
    object : ActivityResultContract<Unit, Uri?>() {
      override fun createIntent(context: Context, input: Unit): Intent =
        Intent.createChooser(
          Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" },
          chooserTitle,
        )
      
      override fun parseResult(resultCode: Int, intent: Intent?): Uri? = intent?.data
    }
  ) { result ->
    uri = result
  }
  uri?.let { value ->
    val context = LocalContext.current
    val attachment = context.createAttachment(value)
    onNotificationImageChanged(attachment)
    uri = null
  }
  FilledButton(
    text = "Agregar Imágen".uppercase(),
    modifier = Modifier.fillMaxWidth(),
    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface),
    textColor = MaterialTheme.colors.onSurface,
  ) { launcher.launch(Unit) }
  
  notification.attachment?.let {
    Spacer(modifier = Modifier.height(16.dp))
    Row(
      modifier = Modifier
        .border(width = 1.dp, color = MaterialTheme.colors.onBackground)
        .padding(horizontal = 16.dp, vertical = 2.dp)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Icon(
        icon = R.drawable.ic_attachment_24,
        contentDescription = "Attachment",
        tint = MaterialTheme.colors.onSurface,
      )
      
      Spacer(modifier = Modifier.width(8.dp))
      Body2(text = it.displayName, color = MaterialTheme.colors.onBackground)
      
      Spacer(modifier = Modifier.weight(1.0f))
    }
    Spacer(modifier = Modifier.height(16.dp))
  }
}

@Preview
@Composable
fun NotificationsPreview() {
  AppTheme {
    EditNotificationContent(
      notification = Notification(
        1,
        "Test Notification",
        "This is a test",
        com.alientodevida.alientoapp.domain.common.Image("cursos.png"),
        "2021-12-31T18:58:34Z"
      ).toNotificationRequest(),
      isNotificationRequestComplete = true,
      showLoading = true,
      onNotificationTitleChanged = {},
      onNotificationDescriptionChanged = {},
      onNotificationImageNameChanged = {},
      onNotificationImageChanged = {},
      saveNotification = {},
      onBackPressed = {},
    )
  }
}
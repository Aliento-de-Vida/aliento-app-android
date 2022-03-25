package com.alientodevida.alientoapp.app.features.notifications.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.compose.components.Body2
import com.alientodevida.alientoapp.app.compose.components.Caption
import com.alientodevida.alientoapp.app.compose.components.ClickableIcon
import com.alientodevida.alientoapp.app.compose.components.H5
import com.alientodevida.alientoapp.app.compose.components.Icon
import com.alientodevida.alientoapp.app.compose.components.LoadingIndicator
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.state.ViewModelResult
import com.alientodevida.alientoapp.app.utils.extensions.toImageUrl
import com.alientodevida.alientoapp.domain.extensions.format
import com.alientodevida.alientoapp.domain.extensions.toDate
import com.alientodevida.alientoapp.domain.notification.Notification

@Composable
fun Notifications(
  viewModel: NotificationsViewModel,
  onBackPressed: () -> Unit,
  goToNotificationDetail: (Notification) -> Unit,
  goToEditNotification: (Notification) -> Unit,
  goToCreateNotification: () -> Unit,
) {
  LaunchedEffect(true) {
    viewModel.getNotifications()
  }
  
  var notifications by remember { mutableStateOf(listOf<Notification>()) }
  
  val notificationsResult by viewModel.notifications.collectAsState(ViewModelResult.Loading)
  (notificationsResult as? ViewModelResult.Success<List<Notification>>)?.let { result ->
    notifications = result.data
  }
  
  NotificationsContent(
    notifications = notifications,
    isAdmin = viewModel.isAdmin,
    deleteNotification = viewModel::deleteNotification,
    showLoading = notificationsResult == ViewModelResult.Loading,
    onBackPressed = onBackPressed,
    goToNotificationDetail = goToNotificationDetail,
    goToNotificationsAdmin = goToEditNotification,
    goToCreateNotification = goToCreateNotification,
  )
}

@Composable
fun NotificationsContent(
  notifications: List<Notification>,
  isAdmin: Boolean,
  deleteNotification: (Notification) -> Unit,
  showLoading: Boolean,
  onBackPressed: () -> Unit,
  goToNotificationDetail: (Notification) -> Unit,
  goToNotificationsAdmin: (Notification) -> Unit,
  goToCreateNotification: () -> Unit,
) {
  Scaffold(
    topBar = {
      TopAppBar(onBackPressed = onBackPressed)
    },
    floatingActionButton = {
      if (isAdmin) FloatingActionButton(
        onClick = { goToCreateNotification() },
        contentColor = MaterialTheme.colors.surface,
      ) {
        Icon(
          icon = R.drawable.ic_add_24,
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
        notifications = notifications,
        deleteNotification = deleteNotification,
        goToNotificationDetail = goToNotificationDetail,
        goToNotificationsAdmin = goToNotificationsAdmin,
        isAdmin = isAdmin,
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
  notifications: List<Notification>,
  isAdmin: Boolean,
  deleteNotification: (Notification) -> Unit,
  goToNotificationDetail: (Notification) -> Unit,
  goToNotificationsAdmin: (Notification) -> Unit,
) {
  Column(Modifier.padding(horizontal = 8.dp)) {
    Spacer(modifier = Modifier.height(8.dp))
    H5(
      modifier = Modifier.padding(horizontal = 8.dp),
      text = "Notificaciones",
      color = MaterialTheme.colors.onBackground,
    )
    Spacer(modifier = Modifier.height(16.dp))
    LazyColumn(
      contentPadding = PaddingValues(bottom = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      items(notifications, key = { it.id }) { notification ->
        NotificationItem(
          modifier = Modifier.animateItemPlacement(),
          notification = notification,
          isAdmin = isAdmin,
          deleteNotification = deleteNotification,
          goToNotificationDetail = goToNotificationDetail,
          goToNotificationsAdmin = goToNotificationsAdmin,
        )
      }
    }
  }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun NotificationItem(
  modifier: Modifier,
  notification: Notification,
  isAdmin: Boolean,
  deleteNotification: (Notification) -> Unit,
  goToNotificationDetail: (Notification) -> Unit,
  goToNotificationsAdmin: (Notification) -> Unit,
) {
  var expanded by remember { mutableStateOf(false) }
  val hapticFeedback = LocalHapticFeedback.current
  
  Card(
    modifier
      .fillMaxWidth()
      .height(120.dp)
      .combinedClickable(
        onClick = { goToNotificationDetail(notification) },
        onLongClick = {
          if (isAdmin) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            expanded = expanded.not()
          }
        }
      ),
  ) {
    Box {
      NotificationItemContent(notification)
      
      DropdownMenu(
        modifier = Modifier.background(MaterialTheme.colors.surface),
        expanded = expanded,
        onDismissRequest = { expanded = false }
      ) {
        DropdownMenuItem(
          onClick = { deleteNotification(notification) }) {
          Body2(
            text = "Eliminar",
            color = MaterialTheme.colors.onSurface,
          )
        }
        DropdownMenuItem(onClick = { goToNotificationsAdmin(notification) }) {
          Body2(
            text = "Editar",
            color = MaterialTheme.colors.onSurface,
          )
        }
      }
    }
  }
}

@Composable
private fun NotificationItemContent(notification: Notification) {
  Box {
    AsyncImage(
      model = notification.image?.name?.toImageUrl(), // TODO the ViewModel should do this conversion
      contentDescription = null,
      contentScale = ContentScale.Crop,
    )
    
    Column {
      Spacer(Modifier.weight(0.38f))
      Box(
        Modifier
          .fillMaxWidth()
          .weight(0.62f)
          .background(
            brush = Brush.verticalGradient(colors = listOf(Color.Transparent, Color.Black))
          )
      ) {
        Column(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
          Spacer(Modifier.weight(1.0f))
          H5(
            text = notification.title,
            color = MaterialTheme.colors.secondary,
          )
          Row {
            Body2(
              text = notification.content,
              color = MaterialTheme.colors.secondary,
            )
            Spacer(Modifier.weight(1.0f))
            Caption(
              text = notification.date.toDate()?.format("dd MMM yy") ?: ""
            ) // TODO the ViewModel should do this conversion
          }
        }
      }
    }
  }
}


@Preview
@Composable
fun NotificationsPreview() {
  AppTheme {
    NotificationsContent(
      notifications = listOf(
        Notification(
          1,
          "Test Notification",
          "This is a test",
          com.alientodevida.alientoapp.domain.common.Image("cursos.png"),
          "2021-12-31T18:58:34Z"
        ),
        Notification(
          2,
          "Test Notification",
          "This is a test",
          com.alientodevida.alientoapp.domain.common.Image("cursos.png"),
          "2021-12-31T18:58:34Z"
        ),
      ),
      isAdmin = true,
      showLoading = true,
      onBackPressed = {},
      deleteNotification = {},
      goToNotificationDetail = {},
      goToNotificationsAdmin = {},
      goToCreateNotification = {},
    )
  }
}
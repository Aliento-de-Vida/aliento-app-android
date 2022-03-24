package com.alientodevida.alientoapp.app.features.notifications.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.state.ViewModelResult
import com.alientodevida.alientoapp.app.utils.extensions.toImageUrl
import com.alientodevida.alientoapp.domain.common.Image
import com.alientodevida.alientoapp.domain.extensions.format
import com.alientodevida.alientoapp.domain.extensions.toDate
import com.alientodevida.alientoapp.domain.home.Notification

@Composable
fun Notifications(
  viewModel: NotificationsViewModel,
  onBackPressed: () -> Unit,
  goToNotificationDetail: (Notification) -> Unit,
  goToNotificationsAdmin: () -> Unit,
) {
  val notificationsState by viewModel.notifications.observeAsState()
  
  if (notificationsState == ViewModelResult.Loading) CircularProgressIndicator()
  
  val notifications = (notificationsState as? ViewModelResult.Success)?.data ?: emptyList()
  
  NotificationsContent(
    notifications = notifications,
    isAdmin = viewModel.isAdmin,
    onBackPressed = onBackPressed,
    goToNotificationDetail = goToNotificationDetail,
    goToNotificationsAdmin = goToNotificationsAdmin,
  )
}

@Composable
fun NotificationsContent(
  notifications: List<Notification>,
  isAdmin: Boolean,
  onBackPressed: () -> Unit,
  goToNotificationDetail: (Notification) -> Unit,
  goToNotificationsAdmin: () -> Unit,
) {
  Scaffold(
    topBar = {
      TopAppBar(onBackPressed = onBackPressed)
    },
    floatingActionButton = {
      if (isAdmin) FloatingActionButton(
        onClick = { /*TODO*/ },
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
        .fillMaxHeight()
        .background(color = MaterialTheme.colors.background),
    ) {
      NotificationsBody(
        notifications = notifications,
        goToNotificationDetail = goToNotificationDetail,
        goToNotificationsAdmin = goToNotificationsAdmin,
        isAdmin = isAdmin,
      )
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
fun NotificationsBody(
  notifications: List<Notification>,
  isAdmin: Boolean,
  goToNotificationDetail: (Notification) -> Unit,
  goToNotificationsAdmin: () -> Unit,
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
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      items(notifications) { notification ->
        NotificationItem(
          notification = notification,
          isAdmin = isAdmin,
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
  notification: Notification,
  isAdmin: Boolean,
  goToNotificationDetail: (Notification) -> Unit,
  goToNotificationsAdmin: () -> Unit,
) {
  var expanded by remember { mutableStateOf(false) }
  val hapticFeedback = LocalHapticFeedback.current
  
  Card(
    Modifier
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
          onClick = { }) {
          Body2(
            text = "Eliminar",
            color = MaterialTheme.colors.onSurface,
          )
        }
        DropdownMenuItem(onClick = { }) {
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
          1,
          "Test Notification",
          "This is a test",
          com.alientodevida.alientoapp.domain.common.Image("cursos.png"),
          "2021-12-31T18:58:34Z"
        ),
      ),
      isAdmin = true,
      onBackPressed = {},
      goToNotificationDetail = {},
      goToNotificationsAdmin = {},
    )
  }
}
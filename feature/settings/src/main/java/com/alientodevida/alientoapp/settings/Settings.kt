package com.alientodevida.alientoapp.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun Settings(
  viewModel: SettingsViewModel,
  onBackPressed: () -> Unit,
) {
  val isDarkTheme by viewModel.isDarkTheme.collectAsState(false)
  val pushNotificatiosn by viewModel.areNotificationsEnabled.collectAsState(false)
  
  SettingsContent(
    isDarkTheme = isDarkTheme,
    pushNotifications = pushNotificatiosn,
    onDarkThemeChanged = viewModel::onDarkThemeChanged,
    onPushNotificationsChanged = viewModel::onPushNotificationsChanged,
    onBackPressed = onBackPressed,
  )
}

@Composable
fun SettingsContent(
  isDarkTheme: Boolean,
  pushNotifications: Boolean,
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  onDarkThemeChanged: (Boolean) -> Unit,
  onPushNotificationsChanged: (Boolean) -> Unit,
  onBackPressed: () -> Unit,
) {
  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(onBackPressed = onBackPressed)
    },
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .padding(paddingValues = paddingValues)
        .background(color = MaterialTheme.colors.background),
    ) {
      SettingsBody(
        isDarkTheme = isDarkTheme,
        pushNotificatiosn = pushNotifications,
        onDarkThemeChanged = onDarkThemeChanged,
        onPushNotificationsChanged = onPushNotificationsChanged,
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
        com.alientodevida.alientoapp.designsystem.components.ClickableIcon(
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
fun SettingsBody(
  isDarkTheme: Boolean,
  pushNotificatiosn: Boolean,
  onDarkThemeChanged: (Boolean) -> Unit,
  onPushNotificationsChanged: (Boolean) -> Unit,
) {
  val scrollState = rememberScrollState()
  
  Column(
    Modifier
      .verticalScroll(scrollState)
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  ) {
  
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.H5(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Configuración",
          color = MaterialTheme.colors.onBackground,
      )
    
    Spacer(modifier = Modifier.height(32.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
      com.alientodevida.alientoapp.designsystem.components.Body1(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Tema Oscuro",
          color = MaterialTheme.colors.onBackground,
      )
      Spacer(modifier = Modifier.weight(1.0f))
      Switch(checked = isDarkTheme, onCheckedChange = onDarkThemeChanged)
    }
  
    Spacer(modifier = Modifier.height(16.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
      com.alientodevida.alientoapp.designsystem.components.Body1(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Notificaciones Push",
          color = MaterialTheme.colors.onBackground,
      )
      Spacer(modifier = Modifier.weight(1.0f))
      Switch(checked = pushNotificatiosn, onCheckedChange = onPushNotificationsChanged)
    }
  
    Spacer(modifier = Modifier.height(80.dp))
  }
}

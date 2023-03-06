package com.alientodevida.alientoapp.app.features.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.compose.components.LoadingIndicator
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(goToMobileNavigation: () -> Unit) {
  LaunchedEffect(true) {
    delay(1000)
    goToMobileNavigation()
  }
  
  SplashScreenContent()
}

@Composable
fun SplashScreenContent() {
  Box(
    Modifier.background(color = MaterialTheme.colors.background),
  ) {
    Column(Modifier.fillMaxSize()) {
      Spacer(modifier = Modifier.weight(0.2f))
      Image(
        painter = painterResource(id = R.drawable.logo_blanco),
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onBackground),
        alignment = Alignment.Center,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 40.dp)
          .weight(0.3f),
        contentDescription = null,
      )
      Spacer(modifier = Modifier.weight(0.1f))
      LoadingIndicator(Modifier.weight(0.3f))
      Spacer(modifier = Modifier.weight(0.1f))
    }
  }
}

@Preview
@Composable
fun NotificationsPreview() {
  AppTheme {
    SplashScreen({})
  }
}
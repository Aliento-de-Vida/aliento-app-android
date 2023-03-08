package com.alientodevida.alientoapp.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp

@Composable
fun SocialMedia(
  isAdmin: Boolean,
  goToInstagram: () -> Unit,
  goToYoutube: () -> Unit,
  goToFacebook: () -> Unit,
  goToTwitter: () -> Unit,
  goToSpotify: () -> Unit,
  goToAdminLogin: () -> Unit,
  adminLogout: () -> Unit,
) {
  Column(
    Modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp)
  ) {
    com.alientodevida.alientoapp.designsystem.components.H5(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = "Redes Sociales",
        color = MaterialTheme.colors.onBackground,
    )
    Spacer(modifier = Modifier.height(16.dp))
    
    Card(Modifier.height(60.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        com.alientodevida.alientoapp.designsystem.components.ClickableIcon(
            icon = R.drawable.ic_instagram_logo,
            contentDescription = "Instagram Button",
            tint = MaterialTheme.colors.onBackground,
            onClick = goToInstagram,
        )

          com.alientodevida.alientoapp.designsystem.components.ClickableIcon(
              icon = R.drawable.ic_001_youtube,
              contentDescription = "Youtube Button",
              tint = MaterialTheme.colors.onBackground,
              onClick = goToYoutube,
          )

          com.alientodevida.alientoapp.designsystem.components.ClickableIcon(
              icon = R.drawable.ic_facebook,
              contentDescription = "Facebook Button",
              tint = MaterialTheme.colors.onBackground,
              onClick = goToFacebook,
          )
        
        val twitterLongClickCount = remember { mutableStateOf(0) }
        val spotifyLongClickCount = remember { mutableStateOf(0) }
        val hapticFeedback = LocalHapticFeedback.current

          com.alientodevida.alientoapp.designsystem.components.ClickableIcon(
              icon = R.drawable.ic_008_twitter,
              contentDescription = "Twitter Button",
              tint = MaterialTheme.colors.onBackground,
              onClick = goToTwitter,
              onLongClick = {
                  hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)

                  twitterLongClickCount.value = twitterLongClickCount.value.plus(1)
              },
          )

          com.alientodevida.alientoapp.designsystem.components.ClickableIcon(
              icon = R.drawable.spotify_icon,
              contentDescription = "Spotify Button",
              tint = MaterialTheme.colors.onBackground,
              onClick = goToSpotify,
              onLongClick = {
                  hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)

                  spotifyLongClickCount.value = spotifyLongClickCount.value.plus(1)
                  if (spotifyLongClickCount.value == 1 && twitterLongClickCount.value == 1) {
                      twitterLongClickCount.value = 0
                      spotifyLongClickCount.value = 0

                      if (isAdmin) adminLogout() else goToAdminLogin()
                  }
              },
          )
      }
    }
  }
}

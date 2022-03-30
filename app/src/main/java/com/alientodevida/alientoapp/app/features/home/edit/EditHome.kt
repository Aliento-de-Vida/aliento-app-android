package com.alientodevida.alientoapp.app.features.home.edit

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.compose.components.ClickableIcon
import com.alientodevida.alientoapp.app.compose.components.H5
import com.alientodevida.alientoapp.app.compose.components.Icon
import com.alientodevida.alientoapp.app.compose.components.InputField
import com.alientodevida.alientoapp.app.compose.components.LoadingIndicator
import com.alientodevida.alientoapp.app.extensions.Dialog
import com.alientodevida.alientoapp.domain.home.Home

@Composable
fun EditHome(
  viewModel: EditHomeViewModel,
  onBackPressed: () -> Unit,
) {
  val viewModelState by viewModel.viewModelState.collectAsState()
  
  EditHomeContent(
    uiState = viewModelState,
    onMessageDismiss = viewModel::onMessageDismiss,
    onEbookChanged = viewModel::onEbookChanged,
    onYoutubePlaylistIdChanged = viewModel::onYoutubePlaylistIdChanged,
    onYoutubeChannelIdChanged = viewModel::onYoutubeChannelIdChanged,
    onSpotifyPlaylistIdChanged = viewModel::onSpotifyPlaylistIdChanged,
    onPrayerEmailChanged = viewModel::onPrayerEmailChanged,
    onInstagramUrlChanged = viewModel::onInstagramUrlChanged,
    onYoutubeChanelUrlChanged = viewModel::onYoutubeChanelUrlChanged,
    onFacebookPageIdChanged = viewModel::onFacebookPageIdChanged,
    onFacebookPageUrlChanged = viewModel::onFacebookPageUrlChanged,
    onTwitterUserIdChanged = viewModel::onTwitterUserIdChanged,
    onTwitterUrlChanged = viewModel::onTwitterUrlChanged,
    onSpotifyArtistIdChanged = viewModel::onSpotifyArtistIdChanged,
    saveHome = viewModel::save,
    onBackPressed = onBackPressed,
  )
}

@Composable
fun EditHomeContent(
  uiState: HomeUiState,
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  onMessageDismiss: (Long) -> Unit,
  onEbookChanged: (String) -> Unit,
  onYoutubePlaylistIdChanged: (String) -> Unit,
  onYoutubeChannelIdChanged: (String) -> Unit,
  onSpotifyPlaylistIdChanged: (String) -> Unit,
  onPrayerEmailChanged: (String) -> Unit,
  onInstagramUrlChanged: (String) -> Unit,
  onYoutubeChanelUrlChanged: (String) -> Unit,
  onFacebookPageIdChanged: (String) -> Unit,
  onFacebookPageUrlChanged: (String) -> Unit,
  onTwitterUserIdChanged: (String) -> Unit,
  onTwitterUrlChanged: (String) -> Unit,
  onSpotifyArtistIdChanged: (String) -> Unit,
  saveHome: (Home) -> Unit,
  onBackPressed: () -> Unit,
) {
  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(onBackPressed = onBackPressed)
    },
    floatingActionButton = {
      if (uiState.home.isComplete) FloatingActionButton(
        onClick = { saveHome(uiState.home) },
        contentColor = MaterialTheme.colors.surface,
      ) {
        Icon(
          icon = R.drawable.ic_send_24,
          contentDescription = "Edit",
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
        home = uiState.home,
        onEbookChanged = onEbookChanged,
        onYoutubePlaylistIdChanged = onYoutubePlaylistIdChanged,
        onYoutubeChannelIdChanged = onYoutubeChannelIdChanged,
        onSpotifyPlaylistIdChanged = onSpotifyPlaylistIdChanged,
        onPrayerEmailChanged = onPrayerEmailChanged,
        onInstagramUrlChanged = onInstagramUrlChanged,
        onYoutubeChanelUrlChanged = onYoutubeChanelUrlChanged,
        onFacebookPageIdChanged = onFacebookPageIdChanged,
        onFacebookPageUrlChanged = onFacebookPageUrlChanged,
        onTwitterUserIdChanged = onTwitterUserIdChanged,
        onTwitterUrlChanged = onTwitterUrlChanged,
        onSpotifyArtistIdChanged = onSpotifyArtistIdChanged,
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
  home: Home,
  onEbookChanged: (String) -> Unit,
  onYoutubePlaylistIdChanged: (String) -> Unit,
  onYoutubeChannelIdChanged: (String) -> Unit,
  onSpotifyPlaylistIdChanged: (String) -> Unit,
  onPrayerEmailChanged: (String) -> Unit,
  onInstagramUrlChanged: (String) -> Unit,
  onYoutubeChanelUrlChanged: (String) -> Unit,
  onFacebookPageIdChanged: (String) -> Unit,
  onFacebookPageUrlChanged: (String) -> Unit,
  onTwitterUserIdChanged: (String) -> Unit,
  onTwitterUrlChanged: (String) -> Unit,
  onSpotifyArtistIdChanged: (String) -> Unit,
) {
  val scrollState = rememberScrollState()
  
  Column(
    Modifier
      .fillMaxWidth()
      .verticalScroll(scrollState)
      .padding(horizontal = 16.dp)
  ) {
    Spacer(modifier = Modifier.height(16.dp))
    H5(
      modifier = Modifier.padding(horizontal = 8.dp),
      text = "Home",
      color = MaterialTheme.colors.onBackground,
    )
  
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = home.ebook,
      onChanged = onEbookChanged,
      placeholder = "Ebook Url",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      label = "Ebook Url",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
  
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = home.youtubePlaylistId,
      onChanged = onYoutubePlaylistIdChanged,
      placeholder = "Youtube Playlist Id",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      label = "Youtube Playlist Id",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = home.youtubeChannelId,
      onChanged = onYoutubeChannelIdChanged,
      placeholder = "Youtube Channel Id",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      label = "Youtube Channel Id",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = home.spotifyPlaylistId,
      onChanged = onSpotifyPlaylistIdChanged,
      placeholder = "Spotify Playlist Id",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      label = "Spotify Playlist Id",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = home.prayerEmail,
      onChanged = onPrayerEmailChanged,
      placeholder = "Prayer Email",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      label = "Prayer Email",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
  
    Spacer(modifier = Modifier.height(16.dp))
    H5(
      modifier = Modifier.padding(horizontal = 8.dp),
      text = "Social Media",
      color = MaterialTheme.colors.onBackground,
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = home.socialMedia.instagramUrl,
      onChanged = onInstagramUrlChanged,
      placeholder = "Instagram Url",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      label = "Instagram Url",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = home.socialMedia.youtubeChannelUrl,
      onChanged = onYoutubeChanelUrlChanged,
      placeholder = "Youtube Channel Url",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      label = "Youtube Channel Url",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = home.socialMedia.facebookPageId,
      onChanged = onFacebookPageIdChanged,
      placeholder = "Facebook Page Id",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      label = "Facebook Page Id",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = home.socialMedia.facebookPageUrl,
      onChanged = onFacebookPageUrlChanged,
      placeholder = "Facebook Page Url",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      label = "Facebook Page Url",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = home.socialMedia.twitterUserId,
      onChanged = onTwitterUserIdChanged,
      placeholder = "Twitter User Id",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      label = "Twitter User Id",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = home.socialMedia.twitterUrl,
      onChanged = onTwitterUrlChanged,
      placeholder = "Twitter Url",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      label = "Twitter Url",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = home.socialMedia.spotifyArtistId,
      onChanged = onSpotifyArtistIdChanged,
      placeholder = "Spotify Artist Id",
      placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      label = "Spotify Artist Id",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
  
    Spacer(modifier = Modifier.height(16.dp))
  }
}

/*
@Preview
@Composable
fun NotificationsPreview() {
  AppTheme {
    EditHomeContent(
      HomeUiState(
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
      onNotificationImageNameChanged = {},
      onNotificationImageChanged = {},
      saveHome = {},
      onBackPressed = {},
    )
  }
}*/

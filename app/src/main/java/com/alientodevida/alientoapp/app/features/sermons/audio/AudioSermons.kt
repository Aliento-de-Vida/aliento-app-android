package com.alientodevida.alientoapp.app.features.sermons.audio

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.compose.components.Caption
import com.alientodevida.alientoapp.app.compose.components.ClickableIcon
import com.alientodevida.alientoapp.app.compose.components.Icon
import com.alientodevida.alientoapp.app.compose.components.ImageWithShimmering
import com.alientodevida.alientoapp.app.compose.components.LoadingIndicator
import com.alientodevida.alientoapp.app.compose.components.Subtitle1
import com.alientodevida.alientoapp.app.compose.components.Subtitle2
import com.alientodevida.alientoapp.app.extensions.SnackBar
import com.alientodevida.alientoapp.domain.audio.Audio
import com.alientodevida.alientoapp.domain.extensions.format
import com.alientodevida.alientoapp.domain.extensions.toDate
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.concurrent.TimeUnit

@Composable
fun AudioSermons(
  viewModel: AudioViewModel,
  onBackPressed: () -> Unit,
  goToSpotifyPage: () -> Unit,
  goToSpotifyAudio: (Audio) -> Unit,
) {
  LaunchedEffect(true) {
    viewModel.getPodcasts()
  }
  
  val viewModelState by viewModel.viewModelState.collectAsState()
  
  AudioSermonsContent(
    uiState = viewModelState,
    refresh = viewModel::refreshContent,
    onMessageDismiss = viewModel::onMessageDismiss,
    onBackPressed = onBackPressed,
    goToSpotifyPage = goToSpotifyPage,
    goToSpotifyAudio = goToSpotifyAudio,
  )
}

@Composable
fun AudioSermonsContent(
  uiState: AudioSermonsUiState,
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  refresh: () -> Unit,
  onMessageDismiss: (Long) -> Unit,
  onBackPressed: () -> Unit,
  goToSpotifyPage: () -> Unit,
  goToSpotifyAudio: (Audio) -> Unit,
) {
  Scaffold(
    scaffoldState = scaffoldState,
    /*topBar = {
      TopAppBar(onBackPressed = onBackPressed)
    },*/
    floatingActionButton = {
      FloatingActionButton(
        onClick = { goToSpotifyPage() },
        backgroundColor = colorResource(id = R.color.green_spotify),
      ) {
        Icon(
          icon = R.drawable.spotify_icon,
          contentDescription = "Go To Spotify",
          tint = Color.White,
        )
      }
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .padding(paddingValues = paddingValues)
        .background(color = MaterialTheme.colors.background),
    ) {
      AudioSermonsBody(
        audios = uiState.audios,
        loading = uiState.loading,
        refresh = refresh,
        goToSpotifyAudio = goToSpotifyAudio,
      )
      if (uiState.loading) LoadingIndicator()
      
      uiState.messages.firstOrNull()?.SnackBar(scaffoldState, onMessageDismiss)
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
fun AudioSermonsBody(
  audios: List<Audio>,
  loading: Boolean,
  refresh: () -> Unit,
  goToSpotifyAudio: (Audio) -> Unit,
) {
  SwipeRefresh(
    state = rememberSwipeRefreshState(loading),
    onRefresh = refresh,
  ) {
    Column(Modifier.padding(horizontal = 8.dp)) {
      LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        items(audios, key = { it.uri }) { audio ->
          AudioItem(
            modifier = Modifier.animateItemPlacement(),
            audio = audio,
            height = 90.dp,
            goToSpotifyAudio = goToSpotifyAudio,
          )
        }
      }
    }
  }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun AudioItem(
  modifier: Modifier = Modifier,
  audio: Audio,
  height: Dp,
  goToSpotifyAudio: (Audio) -> Unit,
) {
  Card(
    modifier
      .fillMaxWidth()
      .height(height)
      .clickable { goToSpotifyAudio(audio) },
  ) {
    Row {
      ImageWithShimmering(
        Modifier
          .fillMaxHeight()
          .width(90.dp),
        url = audio.imageUrl,
        description = audio.title
      )
      
      Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Subtitle2(
          text = audio.title,
          color = MaterialTheme.colors.onSurface,
        )
        Subtitle1(
          text = audio.subtitle,
          color = MaterialTheme.colors.onSurface,
        )
        
        Spacer(Modifier.weight(1.0f))
        Row {
          Caption(
            text = audio.releaseDate?.toDate("yyyy-MM-dd")?.format("d MMMM yyyy") ?: "",
            color = MaterialTheme.colors.onSurface,
          )
          Spacer(Modifier.weight(1.0f))
          Caption(
            text = "${TimeUnit.MILLISECONDS.toMinutes(audio.duration.toLong())}  min",
            color = MaterialTheme.colors.onSurface,
          )
        }
        
      }
      
    }
  }
}

/*
@Preview
@Composable
fun NotificationsPreview() {
  AppTheme {
    AudioSermonsContent(
      NotificationsUiState(
        listOf(
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
        true,
        emptyList(),
      ),
      isAdmin = true,
      refresh = {},
      onMessageDismiss = {},
      onBackPressed = {},
      deleteNotification = {},
      goToNotificationDetail = {},
      goToNotificationsAdmin = {},
      goToCreateNotification = {},
    )
  }
}*/
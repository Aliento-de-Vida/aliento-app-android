package com.alientodevida.alientoapp.sermons.presentation.audio

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.extensions.SnackBar
import com.alientodevida.alientoapp.domain.common.Home
import com.alientodevida.alientoapp.domain.extensions.format
import com.alientodevida.alientoapp.domain.extensions.toDate
import com.alientodevida.alientoapp.sermons.R
import com.alientodevida.alientoapp.sermons.domain.audio.Audio
import com.alientodevida.alientoapp.ui.extensions.openSpotifyArtistPage
import com.alientodevida.alientoapp.ui.extensions.openSpotifyWith
import java.util.concurrent.TimeUnit

@Composable
fun AudioSermons(
    viewModel: AudioViewModel,
) {
  LaunchedEffect(true) {
    viewModel.getCachedPodcasts()
  }
  
  val viewModelState by viewModel.viewModelState.collectAsState()
  val context = LocalContext.current
  
  AudioSermonsContent(
    uiState = viewModelState,
    refresh = viewModel::refreshContent,
    onMessageDismiss = viewModel::onMessageDismiss,
    goToSpotifyPage = { goToSpotifyPage(context, viewModel.viewModelState.value.home) },
    goToSpotifyAudio = { context.openSpotifyWith(Uri.parse(it.uri)) },
  )
}

private fun goToSpotifyPage(context: Context, home: Home?) {
  home?.socialMedia?.spotifyArtistId?.let {
    context.openSpotifyArtistPage(it)
  }
}

@Composable
fun AudioSermonsContent(
    uiState: AudioSermonsUiState,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    refresh: () -> Unit,
    onMessageDismiss: (Long) -> Unit,
    goToSpotifyPage: () -> Unit,
    goToSpotifyAudio: (Audio) -> Unit,
) {
  Box(
    modifier = Modifier
      .background(color = MaterialTheme.colors.background),
  ) {
    AudioSermonsBody(
      audios = uiState.audios,
      loading = uiState.loading,
      refresh = refresh,
      goToSpotifyAudio = goToSpotifyAudio,
    )
    
    SpotifyFab(
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(16.dp),
      goToSpotifyPage = goToSpotifyPage,
    )
    
    if (uiState.loading) com.alientodevida.alientoapp.designsystem.components.LoadingIndicator()
    
    uiState.messages.firstOrNull()?.SnackBar(scaffoldState, onMessageDismiss)
  }
  
}

@Composable
fun SpotifyFab(
  modifier: Modifier,
  goToSpotifyPage: () -> Unit,
) {
  FloatingActionButton(
    modifier = modifier,
    onClick = { goToSpotifyPage() },
    backgroundColor = colorResource(id = R.color.green_spotify),
  ) {
      com.alientodevida.alientoapp.designsystem.components.Icon(
          icon = R.drawable.spotify_icon,
          contentDescription = "Go To Spotify",
          tint = Color.White,
      )
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
@OptIn(ExperimentalFoundationApi::class)
fun AudioSermonsBody(
    audios: List<Audio>,
    loading: Boolean,
    refresh: () -> Unit,
    goToSpotifyAudio: (Audio) -> Unit,
) {
    com.alientodevida.alientoapp.designsystem.components.AlwaysRefreshableSwipeRefresh(
        isRefreshing = loading,
        items = audios,
        onRefresh = refresh,
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
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
      com.alientodevida.alientoapp.designsystem.components.ImageWithShimmering(
          Modifier
              .fillMaxHeight()
              .width(90.dp),
          url = audio.imageUrl,
          description = audio.title
      )
      
      Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        com.alientodevida.alientoapp.designsystem.components.Subtitle2(
            text = audio.title,
            color = MaterialTheme.colors.onSurface,
        )
          com.alientodevida.alientoapp.designsystem.components.Subtitle1(
              text = audio.subtitle,
              color = MaterialTheme.colors.onSurface,
          )
        
        Spacer(Modifier.weight(1.0f))
        Row {
          com.alientodevida.alientoapp.designsystem.components.Caption(
              text = audio.releaseDate?.toDate("yyyy-MM-dd")?.format("d MMMM yyyy") ?: "",
              color = MaterialTheme.colors.onSurface,
          )
          Spacer(Modifier.weight(1.0f))
            com.alientodevida.alientoapp.designsystem.components.Caption(
                text = "${TimeUnit.MILLISECONDS.toMinutes(audio.duration.toLong())}  min",
                color = MaterialTheme.colors.onSurface,
            )
        }
      }
    }
  }
}
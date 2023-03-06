package com.alientodevida.alientoapp.app.features.sermons.video

import android.content.Context
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
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.compose.components.*
import com.alientodevida.alientoapp.app.extensions.SnackBar
import com.alientodevida.alientoapp.app.utils.extensions.goToYoutubeVideo
import com.alientodevida.alientoapp.app.utils.extensions.openYoutubeChannel
import com.alientodevida.alientoapp.domain.extensions.format
import com.alientodevida.alientoapp.domain.extensions.toDate
import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.domain.video.YoutubeVideo

@Composable
fun VideoSermons(viewModel: VideoViewModel) {
  LaunchedEffect(true) {
    viewModel.getCachedVideos()
  }
  
  val viewModelState by viewModel.viewModelState.collectAsState()
  val context = LocalContext.current
  
  AudioSermonsContent(
    uiState = viewModelState,
    refresh = viewModel::refreshContent,
    onMessageDismiss = viewModel::onMessageDismiss,
    goToYoutubePage = { goToYoutubePage(context, viewModel.viewModelState.value.home) },
    goToYoutubeVideo = { context.goToYoutubeVideo(it.id) },
  )
}

private fun goToYoutubePage(context: Context, home: Home?) {
  home?.socialMedia?.youtubeChannelUrl?.let {
    context.openYoutubeChannel(it)
  }
}

@Composable
fun AudioSermonsContent(
  uiState: VideoSermonsUiState,
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  refresh: () -> Unit,
  onMessageDismiss: (Long) -> Unit,
  goToYoutubePage: () -> Unit,
  goToYoutubeVideo: (YoutubeVideo) -> Unit,
) {
  Box(
    modifier = Modifier
      .background(color = MaterialTheme.colors.background),
  ) {
    VideoSermonsBody(
      videos = uiState.videos,
      loading = uiState.loading,
      refresh = refresh,
      goToYoutubeVideo = goToYoutubeVideo,
    )
    
    YoutubeFab(
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(16.dp),
      goToYoutubePage = goToYoutubePage,
    )
    
    if (uiState.loading) LoadingIndicator()
    
    uiState.messages.firstOrNull()?.SnackBar(scaffoldState, onMessageDismiss)
  }
}

@Composable
fun YoutubeFab(
  modifier: Modifier,
  goToYoutubePage: () -> Unit,
) {
  FloatingActionButton(
    modifier = modifier,
    onClick = { goToYoutubePage() },
    backgroundColor = colorResource(id = R.color.red_youtube),
  ) {
    Icon(
      icon = R.drawable.ic_001_youtube,
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
fun VideoSermonsBody(
  videos: List<YoutubeVideo>,
  loading: Boolean,
  refresh: () -> Unit,
  goToYoutubeVideo: (YoutubeVideo) -> Unit,
) {
  AlwaysRefreshableSwipeRefresh(
    isRefreshing = loading,
    items = videos,
    onRefresh = refresh,
  ) {
    LazyColumn(
      contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      items(videos, key = { it.id }) { video ->
        VideoItem(
          modifier = Modifier.animateItemPlacement(),
          video = video,
          goToYoutubeVideo = goToYoutubeVideo,
        )
      }
    }
  }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun VideoItem(
  modifier: Modifier = Modifier,
  video: YoutubeVideo,
  goToYoutubeVideo: (YoutubeVideo) -> Unit,
) {
  Card(
    modifier
      .fillMaxWidth()
      .clickable { goToYoutubeVideo(video) },
  ) {
    Column {
      ImageWithShimmering(
        Modifier
          .fillMaxWidth()
          .aspectRatio(1.77f),
        url = video.thumbnilsUrl,
        description = video.name
      )
      Row {
        Image(
          modifier = Modifier
            .padding(8.dp)
            .align(Alignment.CenterVertically),
          painter = painterResource(R.drawable.ic_logo_round),
          contentDescription = null,
        )
        Column(Modifier.padding(end = 8.dp, top = 8.dp, bottom = 8.dp)) {
          Subtitle2(
            text = video.name,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
          )
          Subtitle1(
            text = video.description,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
          )
          
          Spacer(Modifier.weight(1.0f))
          
          Caption(
            modifier = Modifier.align(Alignment.End),
            text = video.date.toDate("yyyy-MM-dd")?.format("d MMMM yyyy") ?: "",
            color = MaterialTheme.colors.onSurface,
          )
          Spacer(Modifier.weight(1.0f))
        }
      }
    }
  }
}
package com.alientodevida.alientoapp.app.features.sermons.video

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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.alientodevida.alientoapp.domain.extensions.format
import com.alientodevida.alientoapp.domain.extensions.toDate
import com.alientodevida.alientoapp.domain.video.YoutubeVideo
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun VideoSermons(
  viewModel: VideoViewModel,
  onBackPressed: () -> Unit,
  goToYoutubePage: () -> Unit,
  goToYoutubeVideo: (YoutubeVideo) -> Unit,
) {
  LaunchedEffect(true) {
    viewModel.getCachedVideos()
  }
  
  val viewModelState by viewModel.viewModelState.collectAsState()
  
  AudioSermonsContent(
    uiState = viewModelState,
    refresh = viewModel::refreshContent,
    onMessageDismiss = viewModel::onMessageDismiss,
    onBackPressed = onBackPressed,
    goToYoutubePage = goToYoutubePage,
    goToYoutubeVideo = goToYoutubeVideo,
  )
}

@Composable
fun AudioSermonsContent(
  uiState: VideoSermonsUiState,
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  refresh: () -> Unit,
  onMessageDismiss: (Long) -> Unit,
  onBackPressed: () -> Unit,
  goToYoutubePage: () -> Unit,
  goToYoutubeVideo: (YoutubeVideo) -> Unit,
) {
  Scaffold(
    scaffoldState = scaffoldState,
    /*topBar = {
      TopAppBar(onBackPressed = onBackPressed)
    },*/
    floatingActionButton = {
      FloatingActionButton(
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
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .padding(paddingValues = paddingValues)
        .background(color = MaterialTheme.colors.background),
    ) {
      VideoSermonsBody(
        videos = uiState.videos,
        loading = uiState.loading,
        refresh = refresh,
        goToYoutubeVideo = goToYoutubeVideo,
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
fun VideoSermonsBody(
  videos: List<YoutubeVideo>,
  loading: Boolean,
  refresh: () -> Unit,
  goToYoutubeVideo: (YoutubeVideo) -> Unit,
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
          modifier  = Modifier.padding(8.dp).align(Alignment.CenterVertically),
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

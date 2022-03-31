package com.alientodevida.alientoapp.app.features.home

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.compose.components.ClickableIcon
import com.alientodevida.alientoapp.app.compose.components.Icon
import com.alientodevida.alientoapp.app.compose.components.LoadingIndicator
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.extensions.SnackBar
import com.alientodevida.alientoapp.domain.home.HomeImages

@Composable
fun Home(
  viewModel: HomeViewModel,
  goToEditHome: () -> Unit,
  goToNotifications: () -> Unit,
  goToSettings: () -> Unit,
  goToSermons: () -> Unit,
  goToChurch: () -> Unit,
  goToCampus: () -> Unit,
  goToGallery: () -> Unit,
  goToPrayer: () -> Unit,
  goToDonations: () -> Unit,
  goToEbook: () -> Unit,
  goToInstagram: () -> Unit,
  goToYoutube: () -> Unit,
  goToFacebook: () -> Unit,
  goToTwitter: () -> Unit,
  goToSpotify: () -> Unit,
  goToAdminLogin: () -> Unit,
) {
  
  val viewModelState by viewModel.viewModelState.collectAsState()
  val isAdmin by viewModel.isAdmin.collectAsState(false)
  
  HomeContent(
    uiState = viewModelState,
    isAdmin = isAdmin,
    onMessageDismiss = viewModel::onMessageDismiss,
    goToEditHome = goToEditHome,
    goToNotifications = goToNotifications,
    goToSettings = goToSettings,
    goToSermons = goToSermons,
    goToChurch = goToChurch,
    goToCampus = goToCampus,
    goToGallery = goToGallery,
    goToPrayer = goToPrayer,
    goToDonations = goToDonations,
    goToEbook = goToEbook,
    goToInstagram = goToInstagram,
    goToYoutube = goToYoutube,
    goToFacebook = goToFacebook,
    goToTwitter = goToTwitter,
    goToSpotify = goToSpotify,
    goToAdminLogin = goToAdminLogin,
    adminLogout = viewModel::adminLogout,
  )
}

@Composable
fun HomeContent(
  uiState: HomeUiState,
  isAdmin: Boolean,
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  onMessageDismiss: (Long) -> Unit,
  goToEditHome: () -> Unit,
  goToNotifications: () -> Unit,
  goToSettings: () -> Unit,
  goToSermons: () -> Unit,
  goToChurch: () -> Unit,
  goToCampus: () -> Unit,
  goToGallery: () -> Unit,
  goToPrayer: () -> Unit,
  goToDonations: () -> Unit,
  goToEbook: () -> Unit,
  goToInstagram: () -> Unit,
  goToYoutube: () -> Unit,
  goToFacebook: () -> Unit,
  goToTwitter: () -> Unit,
  goToSpotify: () -> Unit,
  goToAdminLogin: () -> Unit,
  adminLogout: () -> Unit
) {
  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(
        goToNotifications = goToNotifications,
        goToSettings = goToSettings,
      )
    },
    floatingActionButton = {
      if (isAdmin) FloatingActionButton(
        onClick = { goToEditHome() },
        contentColor = MaterialTheme.colors.surface,
      ) {
        Icon(
          icon = R.drawable.ic_edit_24,
          contentDescription = "Edit Home",
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
      HomeBody(
        uiState = uiState,
        isAdmin = isAdmin,
        goToSermons = goToSermons,
        goToChurch = goToChurch,
        goToCampus = goToCampus,
        goToGallery = goToGallery,
        goToPrayer = goToPrayer,
        goToDonations = goToDonations,
        goToEbook = goToEbook,
        goToInstagram = goToInstagram,
        goToYoutube = goToYoutube,
        goToFacebook = goToFacebook,
        goToTwitter = goToTwitter,
        goToSpotify = goToSpotify,
        goToAdminLogin = goToAdminLogin,
        adminLogout = adminLogout,
      )
      if (uiState.loading) LoadingIndicator()
      
      uiState.messages.firstOrNull()?.SnackBar(scaffoldState, onMessageDismiss)
    }
  }
}

// TODO can we extract a component ?
@Composable
fun TopAppBar(
  goToNotifications: () -> Unit,
  goToSettings: () -> Unit,
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
        icon = R.drawable.ic_baseline_settings_24,
        contentDescription = "Settings Button",
        tint = MaterialTheme.colors.onBackground,
        onClick = goToSettings,
      )
    },
    actions = {
      ClickableIcon(
        modifier = modifier,
        icon = R.drawable.ic_notifications_black_24dp,
        contentDescription = "Notifications Button",
        tint = MaterialTheme.colors.onBackground,
        onClick = goToNotifications,
      )
    },
    backgroundColor = MaterialTheme.colors.background,
  )
}

@Composable
fun HomeBody(
  uiState: HomeUiState,
  isAdmin: Boolean,
  goToSermons: () -> Unit,
  goToChurch: () -> Unit,
  goToCampus: () -> Unit,
  goToGallery: () -> Unit,
  goToPrayer: () -> Unit,
  goToDonations: () -> Unit,
  goToEbook: () -> Unit,
  goToInstagram: () -> Unit,
  goToYoutube: () -> Unit,
  goToFacebook: () -> Unit,
  goToTwitter: () -> Unit,
  goToSpotify: () -> Unit,
  goToAdminLogin: () -> Unit,
  adminLogout: () -> Unit,
) {
  val scrollState = rememberScrollState()
  
  Column(Modifier.verticalScroll(scrollState)) {
    Pager(
      items = uiState.sermonItems.take(3),
      goToSermons = goToSermons,
    )
    
    Categories(
      carouselItems = uiState.carouselItems,
      goToChurch = goToChurch,
      goToCampus = goToCampus,
      goToGallery = goToGallery,
    )
    
    QuickAccess(
      homeImages = uiState.homeImages,
      goToDonations = goToDonations,
      goToPrayer = goToPrayer,
      goToEbook = goToEbook,
    )
    
    SocialMedia(
      isAdmin = isAdmin,
      goToInstagram = goToInstagram,
      goToYoutube = goToYoutube,
      goToFacebook = goToFacebook,
      goToTwitter = goToTwitter,
      goToSpotify = goToSpotify,
      goToAdminLogin = goToAdminLogin,
      adminLogout = adminLogout,
    )
    Spacer(modifier = Modifier.height(if (isAdmin) 80.dp else 16.dp))
  }
}

@Preview
@Composable
fun HomePreview() {
  AppTheme {
    HomeContent(
      HomeUiState(
        home = null,
        homeImages = HomeImages(),
        carouselItems = emptyList(),
        sermonItems = emptyList(),
        loading = true,
        emptyList(),
      ),
      isAdmin = true,
      onMessageDismiss = {},
      goToEditHome = {},
      goToNotifications = {},
      goToSettings = {},
      goToSermons = {},
      goToChurch = {},
      goToCampus = {},
      goToGallery = {},
      goToPrayer = {},
      goToDonations = {},
      goToEbook = {},
      goToInstagram = {},
      goToYoutube = {},
      goToFacebook = {},
      goToTwitter = {},
      goToSpotify = {},
      goToAdminLogin = {},
      adminLogout = {},
    )
  }
}
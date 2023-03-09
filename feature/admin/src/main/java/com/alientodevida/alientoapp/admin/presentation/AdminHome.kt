package com.alientodevida.alientoapp.admin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.ui.extensions.Dialog
import com.alientodevida.alientoapp.domain.common.Home


@Composable
fun AdminHome(
    viewModel: AdminHomeViewModel,
    home: Home,
    onBackPressed: () -> Unit,
) {
  
  LaunchedEffect(true) {
    viewModel.setHome(home)
  }
  
  val viewModelState by viewModel.viewModelState.collectAsState()
  
  AdminHomeContent(
    uiState = viewModelState,
    onMessageDismiss = viewModel::onMessageDismiss,
    addSermonsImage = viewModel::addSermonsImage,
    removeSermonsImage = viewModel::removeSermonsImage,
    addChurchImage = viewModel::addChurchImage,
    removeChurchImage = viewModel::removeChurchImage,
    addCampusImage = viewModel::addCampusImage,
    removeCampusImage = viewModel::removeCampusImage,
    addGalleriesImage = viewModel::addGalleriesImage,
    removeGalleriesImage = viewModel::removeGalleriesImage,
    addDonationsImage = viewModel::addDonationsImage,
    removeDonationsImage = viewModel::removeDonationsImage,
    addPrayerImage = viewModel::addPrayerImage,
    removePrayerImage = viewModel::removePrayerImage,
    addEbookImage = viewModel::addEbookImage,
    removeEbookImage = viewModel::removeEbookImage,
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
fun AdminHomeContent(
    uiState: HomeUiState,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onMessageDismiss: (Long) -> Unit,
    addSermonsImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeSermonsImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addChurchImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeChurchImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addCampusImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeCampusImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addGalleriesImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeGalleriesImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addDonationsImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeDonationsImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addPrayerImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removePrayerImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addEbookImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeEbookImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
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
          com.alientodevida.alientoapp.designsystem.components.Icon(
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
      AdminHomeBody(
        home = uiState.home,
        images = uiState.images,
        addSermonsImage = addSermonsImage,
        removeSermonsImage = removeSermonsImage,
        addChurchImage = addChurchImage,
        removeChurchImage = removeChurchImage,
        addCampusImage = addCampusImage,
        removeCampusImage = removeCampusImage,
        addGalleriesImage = addGalleriesImage,
        removeGalleriesImage = removeGalleriesImage,
        addDonationsImage = addDonationsImage,
        removeDonationsImage = removeDonationsImage,
        addPrayerImage = addPrayerImage,
        removePrayerImage = removePrayerImage,
        addEbookImage = addEbookImage,
        removeEbookImage = removeEbookImage,
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
      
      if (uiState.loading) com.alientodevida.alientoapp.designsystem.components.LoadingIndicator()
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
fun AdminHomeBody(
    home: Home,
    images: HomeImages,
    addSermonsImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeSermonsImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addChurchImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeChurchImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addCampusImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeCampusImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addGalleriesImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeGalleriesImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addDonationsImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeDonationsImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addPrayerImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removePrayerImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addEbookImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeEbookImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
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
    EditImages(
      images = images,
      addSermonsImage = addSermonsImage,
      removeSermonsImage = removeSermonsImage,
      addChurchImage = addChurchImage,
      removeChurchImage = removeChurchImage,
      addCampusImage = addCampusImage,
      removeCampusImage = removeCampusImage,
      addGalleriesImage = addGalleriesImage,
      removeGalleriesImage = removeGalleriesImage,
      addDonationsImage = addDonationsImage,
      removeDonationsImage = removeDonationsImage,
      addPrayerImage = addPrayerImage,
      removePrayerImage = removePrayerImage,
      addEbookImage = addEbookImage,
      removeEbookImage = removeEbookImage,
    )
    
    AdminHome(
      home,
      onEbookChanged,
      onYoutubePlaylistIdChanged,
      onYoutubeChannelIdChanged,
      onSpotifyPlaylistIdChanged,
      onPrayerEmailChanged,
      onInstagramUrlChanged,
      onYoutubeChanelUrlChanged,
      onFacebookPageIdChanged,
      onFacebookPageUrlChanged,
      onTwitterUserIdChanged,
      onTwitterUrlChanged,
      onSpotifyArtistIdChanged
    )
  }
}

@Composable
private fun EditImages(
    images: HomeImages,
    addSermonsImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeSermonsImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addChurchImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeChurchImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addCampusImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeCampusImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addGalleriesImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeGalleriesImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addDonationsImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeDonationsImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addPrayerImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removePrayerImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    addEbookImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
    removeEbookImage: (com.alientodevida.alientoapp.designsystem.components.attachment.AttachmentModel) -> Unit,
) {
  Column(Modifier.fillMaxWidth()) {
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.H5(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Images",
          color = MaterialTheme.colors.onBackground,
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.Body1(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Prédicas",
          color = MaterialTheme.colors.onBackground,
      )
    
    Spacer(modifier = Modifier.height(8.dp))
      com.alientodevida.alientoapp.designsystem.components.attachment.Attachments(
          limit = 1,
          attachments = if (images.sermonsImage != null) listOf(images.sermonsImage) else emptyList(),
          addAttachment = addSermonsImage,
          removeAttachment = removeSermonsImage,
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.Body1(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Nosotros",
          color = MaterialTheme.colors.onBackground,
      )
    
    Spacer(modifier = Modifier.height(8.dp))
      com.alientodevida.alientoapp.designsystem.components.attachment.Attachments(
          limit = 1,
          attachments = if (images.churchImage != null) listOf(images.churchImage) else emptyList(),
          addAttachment = addChurchImage,
          removeAttachment = removeChurchImage,
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.Body1(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Campus",
          color = MaterialTheme.colors.onBackground,
      )
    
    Spacer(modifier = Modifier.height(8.dp))
      com.alientodevida.alientoapp.designsystem.components.attachment.Attachments(
          limit = 1,
          attachments = if (images.campusImage != null) listOf(images.campusImage) else emptyList(),
          addAttachment = addCampusImage,
          removeAttachment = removeCampusImage,
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.Body1(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Galerías",
          color = MaterialTheme.colors.onBackground,
      )
    
    Spacer(modifier = Modifier.height(8.dp))
      com.alientodevida.alientoapp.designsystem.components.attachment.Attachments(
          limit = 1,
          attachments = if (images.galleriesImage != null) listOf(images.galleriesImage) else emptyList(),
          addAttachment = addGalleriesImage,
          removeAttachment = removeGalleriesImage,
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.Body1(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Donaciones",
          color = MaterialTheme.colors.onBackground,
      )
    
    Spacer(modifier = Modifier.height(8.dp))
      com.alientodevida.alientoapp.designsystem.components.attachment.Attachments(
          limit = 1,
          attachments = if (images.donationsImage != null) listOf(images.donationsImage) else emptyList(),
          addAttachment = addDonationsImage,
          removeAttachment = removeDonationsImage,
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.Body1(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Oración",
          color = MaterialTheme.colors.onBackground,
      )
    
    Spacer(modifier = Modifier.height(8.dp))
      com.alientodevida.alientoapp.designsystem.components.attachment.Attachments(
          limit = 1,
          attachments = if (images.prayerImage != null) listOf(images.prayerImage) else emptyList(),
          addAttachment = addPrayerImage,
          removeAttachment = removePrayerImage,
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.Body1(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Ebook",
          color = MaterialTheme.colors.onBackground,
      )
    
    Spacer(modifier = Modifier.height(8.dp))
      com.alientodevida.alientoapp.designsystem.components.attachment.Attachments(
          limit = 1,
          attachments = if (images.ebookImage != null) listOf(images.ebookImage) else emptyList(),
          addAttachment = addEbookImage,
          removeAttachment = removeEbookImage,
      )
  }
}

@Composable
private fun AdminHome(
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
    onSpotifyArtistIdChanged: (String) -> Unit
) {
  Column(Modifier.fillMaxWidth()) {
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.H5(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Home",
          color = MaterialTheme.colors.onBackground,
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.InputField(
          modifier = Modifier.fillMaxWidth(),
          value = home.ebook,
          onChanged = onEbookChanged,
          placeholder = "Ebook Url",
          placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
          label = "Ebook Url",
          labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.InputField(
          modifier = Modifier.fillMaxWidth(),
          value = home.youtubePlaylistId,
          onChanged = onYoutubePlaylistIdChanged,
          placeholder = "Youtube Playlist Id",
          placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
          label = "Youtube Playlist Id",
          labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.InputField(
          modifier = Modifier.fillMaxWidth(),
          value = home.youtubeChannelId,
          onChanged = onYoutubeChannelIdChanged,
          placeholder = "Youtube Channel Id",
          placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
          label = "Youtube Channel Id",
          labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.InputField(
          modifier = Modifier.fillMaxWidth(),
          value = home.spotifyPlaylistId,
          onChanged = onSpotifyPlaylistIdChanged,
          placeholder = "Spotify Playlist Id",
          placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
          label = "Spotify Playlist Id",
          labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.InputField(
          modifier = Modifier.fillMaxWidth(),
          value = home.prayerEmail,
          onChanged = onPrayerEmailChanged,
          placeholder = "Prayer Email",
          placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
          label = "Prayer Email",
          labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.H5(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Social Media",
          color = MaterialTheme.colors.onBackground,
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.InputField(
          modifier = Modifier.fillMaxWidth(),
          value = home.socialMedia.instagramUrl,
          onChanged = onInstagramUrlChanged,
          placeholder = "Instagram Url",
          placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
          label = "Instagram Url",
          labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.InputField(
          modifier = Modifier.fillMaxWidth(),
          value = home.socialMedia.youtubeChannelUrl,
          onChanged = onYoutubeChanelUrlChanged,
          placeholder = "Youtube Channel Url",
          placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
          label = "Youtube Channel Url",
          labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.InputField(
          modifier = Modifier.fillMaxWidth(),
          value = home.socialMedia.facebookPageId,
          onChanged = onFacebookPageIdChanged,
          placeholder = "Facebook Page Id",
          placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
          label = "Facebook Page Id",
          labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.InputField(
          modifier = Modifier.fillMaxWidth(),
          value = home.socialMedia.facebookPageUrl,
          onChanged = onFacebookPageUrlChanged,
          placeholder = "Facebook Page Url",
          placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
          label = "Facebook Page Url",
          labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.InputField(
          modifier = Modifier.fillMaxWidth(),
          value = home.socialMedia.twitterUserId,
          onChanged = onTwitterUserIdChanged,
          placeholder = "Twitter User Id",
          placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
          label = "Twitter User Id",
          labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.InputField(
          modifier = Modifier.fillMaxWidth(),
          value = home.socialMedia.twitterUrl,
          onChanged = onTwitterUrlChanged,
          placeholder = "Twitter Url",
          placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
          label = "Twitter Url",
          labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.InputField(
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

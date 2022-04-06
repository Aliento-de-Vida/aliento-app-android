package com.alientodevida.alientoapp.app.features.gallery.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.compose.components.Body2
import com.alientodevida.alientoapp.app.compose.components.ClickableIcon
import com.alientodevida.alientoapp.app.compose.components.Gradient
import com.alientodevida.alientoapp.app.compose.components.H5
import com.alientodevida.alientoapp.app.compose.components.Icon
import com.alientodevida.alientoapp.app.compose.components.ImageWithShimmering
import com.alientodevida.alientoapp.app.compose.components.LoadingIndicator
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.extensions.SnackBar
import com.alientodevida.alientoapp.app.utils.extensions.toImageUrl
import com.alientodevida.alientoapp.domain.gallery.Gallery

@Composable
fun Galleries(
  viewModel: GalleriesViewModel,
  onBackPressed: () -> Unit,
  goToGallery: (Gallery) -> Unit,
  goToEditGallery: (Gallery) -> Unit,
  goToCreateGallery: () -> Unit,
) {
  
  val viewModelState by viewModel.viewModelState.collectAsState()
  val isAdmin by viewModel.isAdmin.collectAsState(false)
  
  GalleriesContent(
    uiState = viewModelState,
    isAdmin = isAdmin,
    onMessageDismiss = viewModel::onMessageDismiss,
    deleteGallery = viewModel::deleteGallery,
    onBackPressed = onBackPressed,
    goToGallery = goToGallery,
    goToEditGallery = goToEditGallery,
    goToCreateGallery = goToCreateGallery,
  )
}

@Composable
fun GalleriesContent(
  uiState: GalleriesUiState,
  isAdmin: Boolean,
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  onMessageDismiss: (Long) -> Unit,
  deleteGallery: (Gallery) -> Unit,
  onBackPressed: () -> Unit,
  goToGallery: (Gallery) -> Unit,
  goToEditGallery: (Gallery) -> Unit,
  goToCreateGallery: () -> Unit,
) {
  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(onBackPressed = onBackPressed)
    },
    floatingActionButton = {
      if (isAdmin) FloatingActionButton(
        onClick = { goToCreateGallery() },
        contentColor = MaterialTheme.colors.surface,
      ) {
        Icon(
          icon = R.drawable.ic_add_24,
          contentDescription = "Create Gallery",
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
      GalleriesBody(
        galleries = uiState.galleries,
        deleteGallery = deleteGallery,
        goToGallery = goToGallery,
        goToEditGallery = goToEditGallery,
        isAdmin = isAdmin,
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
fun GalleriesBody(
  galleries: List<Gallery>,
  isAdmin: Boolean,
  goToGallery: (Gallery) -> Unit,
  goToEditGallery: (Gallery) -> Unit,
  deleteGallery: (Gallery) -> Unit,
) {
  Column(Modifier.padding(horizontal = 8.dp)) {
    Spacer(modifier = Modifier.height(8.dp))
    H5(
      modifier = Modifier.padding(horizontal = 8.dp),
      text = "Galerías",
      color = MaterialTheme.colors.onBackground,
    )
    Spacer(modifier = Modifier.height(16.dp))
    LazyColumn(
      contentPadding = PaddingValues(bottom = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      items(galleries, key = { it.id }) { gallery ->
        GalleryItem(
          modifier = Modifier.animateItemPlacement(),
          Gallery = gallery,
          isAdmin = isAdmin,
          height = 180.dp,
          deleteGallery = deleteGallery,
          goToGallery = goToGallery,
          goToEditGallery = goToEditGallery,
        )
      }
    }
  }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun GalleryItem(
  modifier: Modifier = Modifier,
  Gallery: Gallery,
  height: Dp,
  isAdmin: Boolean,
  goToGallery: (Gallery) -> Unit,
  goToEditGallery: (Gallery) -> Unit,
  deleteGallery: (Gallery) -> Unit,
) {
  var expanded by remember { mutableStateOf(false) }
  val hapticFeedback = LocalHapticFeedback.current
  
  Card(
    modifier
      .fillMaxWidth()
      .height(height)
      .combinedClickable(
        onClick = { goToGallery(Gallery) },
        onLongClick = {
          if (isAdmin) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            expanded = expanded.not()
          }
        }
      ),
  ) {
    Box {
      GalleryItemContent(Gallery)
      
      DropdownMenu(
        modifier = Modifier.background(MaterialTheme.colors.surface),
        expanded = expanded,
        onDismissRequest = { expanded = false }
      ) {
        DropdownMenuItem(
          onClick = { deleteGallery(Gallery) }) {
          Body2(
            text = "Eliminar",
            color = MaterialTheme.colors.onSurface,
          )
        }
        DropdownMenuItem(onClick = { goToEditGallery(Gallery) }) {
          Body2(
            text = "Editar",
            color = MaterialTheme.colors.onSurface,
          )
        }
      }
    }
  }
}

@Composable
private fun GalleryItemContent(gallery: Gallery) {
  Box {
    ImageWithShimmering(url = gallery.coverPicture.toImageUrl(), description = gallery.name)
  
    Column {
      Spacer(Modifier.weight(0.38f))
      Gradient(
        modifier = Modifier.fillMaxWidth().weight(0.62f),
      ) {
        Column(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
          Spacer(Modifier.weight(1.0f))
          H5(
            text = gallery.name,
            color = colorResource(R.color.pantone_white_c),
          )
        }
      }
    }
  }
}


@Preview
@Composable
fun NotificationsPreview() {
  AppTheme {
    GalleriesContent(
      GalleriesUiState(
        listOf(
          Gallery(
            1,
            "Test Notification",
            "This is a test",
            emptyList(),
          ),
          Gallery(
            2,
            "Test Notification",
            "This is a test",
            emptyList(),
          ),
        ),
        true,
        emptyList(),
      ),
      isAdmin = true,
      onMessageDismiss = {},
      onBackPressed = {},
      deleteGallery = {},
      goToGallery = {},
      goToEditGallery = {},
      goToCreateGallery = {},
    )
  }
}
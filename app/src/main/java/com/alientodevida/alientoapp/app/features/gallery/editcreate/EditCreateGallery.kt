package com.alientodevida.alientoapp.app.features.gallery.editcreate

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
import com.alientodevida.alientoapp.app.compose.components.Body1
import com.alientodevida.alientoapp.app.compose.components.ClickableIcon
import com.alientodevida.alientoapp.app.compose.components.H5
import com.alientodevida.alientoapp.app.compose.components.Icon
import com.alientodevida.alientoapp.app.compose.components.InputField
import com.alientodevida.alientoapp.app.compose.components.LoadingIndicator
import com.alientodevida.alientoapp.app.compose.components.attachment.AttachmentModel
import com.alientodevida.alientoapp.app.compose.components.attachment.Attachments
import com.alientodevida.alientoapp.app.compose.components.attachment.AttachmentsWithCurrentImages
import com.alientodevida.alientoapp.app.extensions.Dialog

@Composable
fun EditCreateGallery(
  viewModel: EditCreateGalleryViewModel,
  onBackPressed: () -> Unit,
) {
  val viewModelState by viewModel.viewModelState.collectAsState()
  
  EditCreateGalleryContent(
    uiState = viewModelState,
    onMessageDismiss = viewModel::onMessageDismiss,
    onNameChanged = viewModel::onNameChanged,
    addCoverAttachment = viewModel::addCoverAttachment,
    removeCoverAttachment = viewModel::removeCoverAttachment,
    addAttachmentToList = viewModel::addAttachmentToList,
    removeAttachmentFromList = viewModel::removeAttachmentFromList,
    removeImage = viewModel::removeImage,
    saveGallery = viewModel::saveGallery,
    onBackPressed = onBackPressed,
  )
}

@Composable
fun EditCreateGalleryContent(
  uiState: GalleryUiState,
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  onMessageDismiss: (Long) -> Unit,
  onNameChanged: (String) -> Unit,
  addCoverAttachment: (AttachmentModel) -> Unit,
  removeCoverAttachment: (AttachmentModel) -> Unit,
  addAttachmentToList: (AttachmentModel) -> Unit,
  removeAttachmentFromList: (AttachmentModel) -> Unit,
  removeImage: (String) -> Unit,
  saveGallery: (GalleryRequest) -> Unit,
  onBackPressed: () -> Unit,
) {
  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(onBackPressed = onBackPressed)
    },
    floatingActionButton = {
      if (uiState.galleryRequest.isComplete) FloatingActionButton(
        onClick = { saveGallery(uiState.galleryRequest) },
        contentColor = MaterialTheme.colors.surface,
      ) {
        Icon(
          icon = R.drawable.ic_send_24,
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
      EditCreateGalleryBody(
        campus = uiState.galleryRequest,
        onNameChanged = onNameChanged,
        addCoverAttachment = addCoverAttachment,
        removeCoverAttachment = removeCoverAttachment,
        addAttachmentToList = addAttachmentToList,
        removeAttachmentFromList = removeAttachmentFromList,
        removeImage = removeImage,
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
fun EditCreateGalleryBody(
  campus: GalleryRequest,
  onNameChanged: (String) -> Unit,
  addCoverAttachment: (AttachmentModel) -> Unit,
  removeCoverAttachment: (AttachmentModel) -> Unit,
  addAttachmentToList: (AttachmentModel) -> Unit,
  removeAttachmentFromList: (AttachmentModel) -> Unit,
  removeImage: (String) -> Unit,
) {
  val scrollState = rememberScrollState()
  
  Column(
    Modifier
      .verticalScroll(scrollState)
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  ) {
    val onSurfaceAlpha = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    
    Spacer(modifier = Modifier.height(16.dp))
    H5(
      modifier = Modifier.padding(horizontal = 8.dp),
      text = if (campus.id == 0) "Nuevo Campus" else "Editar Campus",
      color = MaterialTheme.colors.onBackground,
    )
  
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = campus.name,
      onChanged = onNameChanged,
      placeholder = "Nombre",
      placeholderColor = onSurfaceAlpha,
      label = "Nombre",
      labelColor = onSurfaceAlpha,
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    Body1(
      modifier = Modifier.padding(horizontal = 8.dp),
      text = "Cover Image",
      color = MaterialTheme.colors.onBackground,
    )
  
    Spacer(modifier = Modifier.height(16.dp))
    Attachments(
      limit = 1,
      attachments = if (campus.attachment != null) listOf(campus.attachment) else emptyList(),
      addAttachment = addCoverAttachment,
      removeAttachment = removeCoverAttachment,
    )
  
    Spacer(modifier = Modifier.height(16.dp))
    Body1(
      modifier = Modifier.padding(horizontal = 8.dp),
      text = "Albums Images",
      color = MaterialTheme.colors.onBackground,
    )
  
    AttachmentsWithCurrentImages(
      newAttachmentsTitle = "New Attachments",
      currentAttachmentsTitle = "Current Images",
      newAttachments = campus.attachmentList,
      currentAttachments = campus.images,
      addToNewAttachments = addAttachmentToList,
      removeFromNewAttachments = removeAttachmentFromList,
      removeCurrentAttachment = removeImage
    )
  
    Spacer(modifier = Modifier.height(80.dp))
  }
}

/*
@Preview
@Composable
fun NotificationsPreview() {
  AppTheme {
    EditCreateCampusContent(
      NotificationUiState(
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
      onNotificationImageChanged = {},
      saveCampus = {},
      onBackPressed = {},
    )
  }
}*/

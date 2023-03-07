package com.alientodevida.alientoapp.campus.editcreate

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
import com.alientodevida.alientoapp.campus.R
import com.alientodevida.alientoapp.ui.components.attachment.AttachmentModel
import com.alientodevida.alientoapp.ui.components.attachment.Attachments
import com.alientodevida.alientoapp.ui.components.attachment.AttachmentsWithCurrentImages
import com.alientodevida.alientoapp.app.extensions.Dialog
import com.alientodevida.alientoapp.domain.campus.Campus
import com.alientodevida.alientoapp.ui.components.*

@Composable
fun EditCreateCampus(
    viewModel: EditCreateNotificationViewModel,
    campus: Campus,
    onBackPressed: () -> Unit,
) {
  
  LaunchedEffect(true) {
    viewModel.setCampus(campus)
  }
  
  val viewModelState by viewModel.viewModelState.collectAsState()
  
  EditCreateCampusContent(
    uiState = viewModelState,
    onMessageDismiss = viewModel::onMessageDismiss,
    onNameChanged = viewModel::onNameChanged,
    onDescriptionChanged = viewModel::onDescriptionChanged,
    onShortDescriptionChanged = viewModel::onShortDescriptionChanged,
    onLatitudeChanged = viewModel::onLatitudeChanged,
    onLongitudeChanged = viewModel::onLongitudeChanged,
    onContactChanged = viewModel::onContactChanged,
    onVideoUrlChanged = viewModel::onVideoUrlChanged,
    addCoverAttachment = viewModel::addCoverAttachment,
    removeCoverAttachment = viewModel::removeCoverAttachment,
    addAttachmentToList = viewModel::addAttachmentToList,
    removeAttachmentFromList = viewModel::removeAttachmentFromList,
    removeImage = viewModel::removeImage,
    saveCampus = viewModel::saveCampus,
    onBackPressed = onBackPressed,
  )
}

@Composable
fun EditCreateCampusContent(
    uiState: CampusUiState,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onMessageDismiss: (Long) -> Unit,
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onShortDescriptionChanged: (String) -> Unit,
    onLatitudeChanged: (String) -> Unit,
    onLongitudeChanged: (String) -> Unit,
    onContactChanged: (String) -> Unit,
    onVideoUrlChanged: (String) -> Unit,
    addCoverAttachment: (AttachmentModel) -> Unit,
    removeCoverAttachment: (AttachmentModel) -> Unit,
    addAttachmentToList: (AttachmentModel) -> Unit,
    removeAttachmentFromList: (AttachmentModel) -> Unit,
    removeImage: (String) -> Unit,
    saveCampus: (CampusRequest) -> Unit,
    onBackPressed: () -> Unit,
) {
  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(onBackPressed = onBackPressed)
    },
    floatingActionButton = {
      if (uiState.campusRequest.isComplete) FloatingActionButton(
        onClick = { saveCampus(uiState.campusRequest) },
        contentColor = MaterialTheme.colors.surface,
      ) {
          com.alientodevida.alientoapp.ui.components.Icon(
              icon = R.drawable.ic_send_24,
              contentDescription = "Create Campus",
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
      EditCreateCampusBody(
        campus = uiState.campusRequest,
        onNameChanged = onNameChanged,
        onDescriptionChanged = onDescriptionChanged,
        onShortDescriptionChanged = onShortDescriptionChanged,
        onLatitudeChanged = onLatitudeChanged,
        onLongitudeChanged = onLongitudeChanged,
        onContactChanged = onContactChanged,
        onVideoUrlChanged = onVideoUrlChanged,
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
fun EditCreateCampusBody(
    campus: CampusRequest,
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onShortDescriptionChanged: (String) -> Unit,
    onLatitudeChanged: (String) -> Unit,
    onLongitudeChanged: (String) -> Unit,
    onContactChanged: (String) -> Unit,
    onVideoUrlChanged: (String) -> Unit,
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
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = campus.description,
      onChanged = onDescriptionChanged,
      placeholder = "Descripción",
      placeholderColor = onSurfaceAlpha,
      label = "Descripción",
      labelColor = onSurfaceAlpha,
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = campus.shortDescription,
      onChanged = onShortDescriptionChanged,
      placeholder = "Descripción corta",
      placeholderColor = onSurfaceAlpha,
      label = "Descripción corta",
      labelColor = onSurfaceAlpha,
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = campus.location.latitude,
      onChanged = onLatitudeChanged,
      placeholder = "Latitud",
      placeholderColor = onSurfaceAlpha,
      label = "Latitud",
      labelColor = onSurfaceAlpha,
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = campus.location.longitude,
      onChanged = onLongitudeChanged,
      placeholder = "Longitud",
      placeholderColor = onSurfaceAlpha,
      label = "Longitud",
      labelColor = onSurfaceAlpha,
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = campus.contact,
      onChanged = onContactChanged,
      placeholder = "Contacto",
      placeholderColor = onSurfaceAlpha,
      label = "Contacto",
      labelColor = onSurfaceAlpha,
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = campus.videoUrl ?: "",
      onChanged = onVideoUrlChanged,
      placeholder = "Video Url",
      placeholderColor = onSurfaceAlpha,
      label = "Video Url",
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

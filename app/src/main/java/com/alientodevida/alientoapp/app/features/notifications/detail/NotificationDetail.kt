package com.alientodevida.alientoapp.app.features.notifications.detail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.compose.components.Button
import com.alientodevida.alientoapp.app.compose.components.H5
import com.alientodevida.alientoapp.app.compose.components.ImageWithShimmering
import com.alientodevida.alientoapp.app.compose.components.Subtitle2
import com.alientodevida.alientoapp.app.utils.extensions.toImageUrl
import com.alientodevida.alientoapp.domain.notification.Notification
import com.bumptech.glide.Glide
import com.stfalcon.imageviewer.StfalconImageViewer

private fun openGallery(context: Context, images: List<String>) {
  StfalconImageViewer.Builder(context, images) { view, imageUrl ->
    Glide.with(context).load(imageUrl).into(view)
  }.show()
}

@Composable
fun NotificationDetail(viewModel: NotificationDetailViewModel) {
  val context = LocalContext.current
  
  NotificationDetailContent(
    notification = viewModel.notification,
    goToImage = { openGallery(context, listOf(it)) },
  )
}

@Composable
fun NotificationDetailContent(
  notification: Notification,
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  goToImage: (String) -> Unit,
) {
  Scaffold(scaffoldState = scaffoldState) { paddingValues ->
    Box(
      modifier = Modifier
        .padding(paddingValues = paddingValues)
        .background(color = MaterialTheme.colors.background),
    ) {
      EditCreateCampusBody(
        notification = notification,
        goToImage = goToImage,
      )
    }
  }
}

@Composable
fun EditCreateCampusBody(
  notification: Notification,
  goToImage: (String) -> Unit,
) {
  val scrollState = rememberScrollState()
  val imageUrl = notification.image?.name?.toImageUrl()
  
  Column(
    Modifier
      .verticalScroll(scrollState)
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .clickable {
        imageUrl?.let { goToImage(imageUrl) }
      }
  ) {
    Spacer(modifier = Modifier.height(16.dp))
    H5(
      modifier = Modifier.align(Alignment.CenterHorizontally),
      text = notification.title,
      color = MaterialTheme.colors.onBackground,
    )
  
    Spacer(modifier = Modifier.height(16.dp))
    ImageWithShimmering(
      modifier = Modifier
        .padding(horizontal = 48.dp)
        .aspectRatio(0.6f),
      url = imageUrl,
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    Subtitle2(
      modifier = Modifier.padding(horizontal = 8.dp),
      text = notification.content,
      color = MaterialTheme.colors.onBackground,
    )
  
    Spacer(modifier = Modifier.height(80.dp))
    
    Row(Modifier.clickable { imageUrl?.let { goToImage(imageUrl) } }) {
      Spacer(modifier = Modifier.weight(1.0f))
      Icon(
        modifier = Modifier
          .size(24.dp)
          .align(Alignment.CenterVertically),
        painter = painterResource(R.drawable.ic_image_24),
        contentDescription = "galería",
        tint = MaterialTheme.colors.onBackground,
      )
      TextButton(
        modifier = Modifier.align(Alignment.CenterVertically),
        onClick = { imageUrl?.let { goToImage(imageUrl) } },
      ) { Button(text = "FULL SCREEN") }
    }
  }
}
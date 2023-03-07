package com.alientodevida.alientoapp.app.features.notifications.detail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.ui.components.Button
import com.alientodevida.alientoapp.ui.components.H5
import com.alientodevida.alientoapp.ui.components.ImageWithShimmering
import com.alientodevida.alientoapp.ui.components.Subtitle2
import com.alientodevida.alientoapp.ui.extensions.toImageUrl
import com.alientodevida.alientoapp.domain.notification.Notification
import com.bumptech.glide.Glide
import com.stfalcon.imageviewer.StfalconImageViewer

private fun openGallery(context: Context, images: List<String>) {
  StfalconImageViewer.Builder(context, images) { view, imageUrl ->
    Glide.with(context).load(imageUrl).into(view)
  }.show()
}

@Composable
fun NotificationDetail(notification: Notification) {
  val context = LocalContext.current
  
  NotificationDetailContent(
    notification = notification,
    goToImage = { openGallery(context, listOf(it)) },
  )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationDetailContent(
  notification: Notification,
  goToImage: (String) -> Unit,
) {
  Box(
    modifier = Modifier
      .background(color = MaterialTheme.colors.background),
  ) {
    NotificationDetailBody(
      notification = notification,
      goToImage = goToImage,
    )
  }
}

@Composable
fun NotificationDetailBody(
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
      ) { Button(
        text = "FULL SCREEN",
        color = MaterialTheme.colors.onBackground,
      ) }
    }
  }
}
package com.alientodevida.alientoapp.app.features.gallery.detail

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.compose.components.H5
import com.alientodevida.alientoapp.app.compose.components.ImageWithShimmering
import com.alientodevida.alientoapp.app.utils.extensions.load
import com.alientodevida.alientoapp.app.utils.extensions.toImageUrl
import com.alientodevida.alientoapp.domain.gallery.Gallery
import com.stfalcon.imageviewer.StfalconImageViewer

private fun openGallery(context: Context, images: List<String>) {
  StfalconImageViewer.Builder(context, images) { view, imageUrl ->
    view.load(imageUrl, false)
  }
    .show()
}

@Composable
fun Gallery(gallery: Gallery) {
  val context = LocalContext.current
  
  GalleryContent(
    gallery = gallery,
    goToImage = { openGallery(context, listOf(it)) },
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryContent(
  gallery: Gallery,
  goToImage: (String) -> Unit,
) {
  Column(
    Modifier
      .fillMaxWidth()
      .background(color = MaterialTheme.colors.background),
    ) {
    Spacer(modifier = Modifier.height(16.dp))
    H5(
      modifier = Modifier.align(Alignment.CenterHorizontally),
      text = gallery.name,
      color = MaterialTheme.colors.onBackground,
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    
    LazyVerticalGrid(cells = GridCells.Fixed(3)) {
      items(gallery.images) { image ->
        val imageUrl = image.name.toImageUrl()
        ImageWithShimmering(
          modifier = Modifier
            .aspectRatio(0.66f)
            .clickable { goToImage(imageUrl) },
          url = imageUrl,
        )
      }
    }
  }
}
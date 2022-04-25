package com.alientodevida.alientoapp.app.features.gallery.detail

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
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
//    .withStartPosition(index)
    .show()}

@Composable
fun Gallery(viewModel: GalleryViewModel) {
  val context = LocalContext.current
  
  GalleryContent(
    gallery = viewModel.gallery,
    goToImage = { openGallery(context, listOf(it)) },
  )
}

@Composable
fun GalleryContent(
  gallery: Gallery,
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  goToImage: (String) -> Unit,
) {
  Scaffold(scaffoldState = scaffoldState) { paddingValues ->
    Box(
      modifier = Modifier
        .padding(paddingValues = paddingValues)
        .background(color = MaterialTheme.colors.background),
    ) {
      GalleryBody(
        gallery = gallery,
        goToImage = goToImage,
      )
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryBody(
  gallery: Gallery,
  goToImage: (String) -> Unit,
) {
  Column(
    Modifier.fillMaxWidth()
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
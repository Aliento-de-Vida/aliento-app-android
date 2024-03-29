package com.alientodevida.alientoapp.gallery.presentation.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.domain.common.Gallery
import com.alientodevida.alientoapp.domain.extensions.toImageUrl
import com.alientodevida.alientoapp.ui.imageviewer.openFullScreenImage

@Composable
fun Gallery(gallery: Gallery) {
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colors.background

    GalleryContent(
        gallery = gallery,
        goToImage = { openFullScreenImage(context, listOf(it), backgroundColor.toArgb()) },
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
        com.alientodevida.alientoapp.designsystem.components.H5(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = gallery.name,
            color = MaterialTheme.colors.onBackground,
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            items(gallery.images) { image ->
                val imageUrl = image.name.toImageUrl()
                com.alientodevida.alientoapp.designsystem.components.ImageWithShimmering(
                    modifier = Modifier
                        .aspectRatio(0.66f)
                        .clickable { goToImage(imageUrl) },
                    url = imageUrl,
                )
            }
        }
    }
}

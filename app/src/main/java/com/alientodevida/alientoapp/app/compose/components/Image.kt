package com.alientodevida.alientoapp.app.compose.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun ImageWithShimmering(
  modifier: Modifier = Modifier,
  url: String?,
  description: String? = null,
  contentScale: ContentScale = ContentScale.Crop,
) {
  CoilImage(
    modifier = modifier,
    imageModel = url,
    shimmerParams = ShimmerParams(
      baseColor = MaterialTheme.colors.background,
      highlightColor = MaterialTheme.colors.onBackground,
      durationMillis = 500,
      dropOff = 0.65f,
      tilt = 20f
    ),
    contentDescription = description,
    contentScale = contentScale,
  )
}